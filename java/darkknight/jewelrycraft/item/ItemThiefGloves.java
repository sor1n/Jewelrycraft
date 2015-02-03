package darkknight.jewelrycraft.item;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import darkknight.jewelrycraft.util.PlayerUtils;

public class ItemThiefGloves extends Item
{
    public Random rand = new Random();
    
    public ItemThiefGloves()
    {
        super();
        this.setCreativeTab(CreativeTabs.tabTools);
        this.setMaxStackSize(1);
        this.setMaxDamage(10);
    }
    
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (entity instanceof EntityVillager)
        {
            EntityVillager villager = (EntityVillager) entity;
            int wealth = (Integer) ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "wealth", "field_70956_bz");
            MerchantRecipeList buyingList = (MerchantRecipeList) ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "buyingList", "field_70963_i");
            int chance = 5;
            boolean areOtherVillagersAround = false, canTheySeeYou = false;
            AxisAlignedBB axisalignedbb = villager.boundingBox.expand(4.0D, 4.0D, 4.0D);
            List entities = villager.worldObj.getEntitiesWithinAABBExcludingEntity(villager, axisalignedbb);
            for (Object s : entities)
                if (s instanceof EntityVillager)
                {
                    areOtherVillagersAround = true;
                    chance += rand.nextInt(2);
                    if (((EntityVillager) s).canEntityBeSeen(player))
                    {
                        chance += 2;
                        canTheySeeYou = true;
                    }
                }
            if (villager.canEntityBeSeen(player)) chance += 5;
            if (player.isPotionActive(Potion.invisibility)) chance -= (0.8 * chance);
            if (player.capabilities.isCreativeMode) chance = 1;
            int steal = rand.nextInt(chance);
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            if (buyingList != null)
            {
                Iterator<?> iterator = buyingList.iterator();
                if (steal == 0)
                {
                    while (iterator.hasNext())
                    {
                        MerchantRecipe recipe = (MerchantRecipe) iterator.next();
                        int toolUses = (Integer) ReflectionHelper.getPrivateValue(MerchantRecipe.class, recipe, "toolUses", "field_77400_d");
                        int quantity;
                        if (recipe.getItemToSell().isStackable()) quantity = recipe.getItemToSell().stackSize * (7 - toolUses);
                        else quantity = recipe.getItemToSell().stackSize;
                        ItemStack s = new ItemStack(recipe.getItemToSell().getItem(), quantity, recipe.getItemToSell().getItemDamage());
                        s.setTagCompound(recipe.getItemToSell().getTagCompound());
                        if (player.inventory.addItemStackToInventory(s))
                        ;
                        else villager.entityDropItem(s, 0);
                        if (playerInfo.hasKey("cursePoints")) playerInfo.setInteger("cursePoints", playerInfo.getInteger("cursePoints") + 5);
                        else playerInfo.setInteger("cursePoints", 5);
                        player.addChatMessage(new ChatComponentText("Villager #" + villager.getProfession() + ": Hmmm... I seem to have lost my " + s.getDisplayName() + "!"));
                        stack.damageItem(1, player);
                    }
                    buyingList.clear();
                    ReflectionHelper.setPrivateValue(EntityVillager.class, villager, 300, "timeUntilReset", "field_70961_j");
                    ReflectionHelper.setPrivateValue(EntityVillager.class, villager, true, "needsInitilization", "field_70959_by");
                    player.addChatMessage(new ChatComponentText("You hear a faint whisper in your ear: "));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "Those who steal but don't get caught get rewarded and do not."));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "Embrace the path you have gone, for the darkness will not"));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "dwell on."));
                }
                else
                {
                    if (player.isPotionActive(Potion.invisibility)) player.addChatMessage(new ChatComponentText("Villager #" + villager.getProfession() + " sensed a strange presence around him, making him cling on to his items. You didn't get anything."));
                    else
                    {
                        if (areOtherVillagersAround)
                        {
                            if (!canTheySeeYou) player.addChatMessage(new ChatComponentText("As he was passing by, a random villager caught you trying to steal from Villager #" + villager.getProfession() + "."));
                            else player.addChatMessage(new ChatComponentText("A villager nearby saw you trying to steal from Villager #" + villager.getProfession() + "."));
                        }
                        else player.addChatMessage(new ChatComponentText("Villager #" + villager.getProfession() + " caught you trying to steal from him."));
                        player.addChatMessage(new ChatComponentText("Villager #" + villager.getProfession() + " curses you for the attempt."));
                    }
                    stack.damageItem(1, player);
                    if (playerInfo.hasKey("cursePoints")) playerInfo.setInteger("cursePoints", playerInfo.getInteger("cursePoints") + 25);
                    else playerInfo.setInteger("cursePoints", 25);
                }
            }
            if (steal == 0)
            {
                villager.dropItem(Items.emerald, wealth);
                ReflectionHelper.setPrivateValue(EntityVillager.class, villager, 0, "wealth", "field_70956_bz");
            }
            return true;
        }
        else
        {
            return super.itemInteractionForEntity(stack, player, entity);
        }
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List list, boolean par4)
    {
        if (!shouldAddAdditionalInfo()) list.add(EnumChatFormatting.GRAY + additionalInfoInstructions());
        else
        {
            list.add(EnumChatFormatting.GRAY + "Right click with the gloves,");
            list.add(EnumChatFormatting.GRAY + "while sneaking, on a villager");
            list.add(EnumChatFormatting.GRAY + "to steal his stuff.");
        }
    }
    
    public static boolean shouldAddAdditionalInfo()
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) { return true; }
        }
        return false;
    }
    
    public static String additionalInfoInstructions()
    {
        String message = "\247oPress \247b<SHIFT>\2477\247o for more information.";
        return message;
    }
    
}
