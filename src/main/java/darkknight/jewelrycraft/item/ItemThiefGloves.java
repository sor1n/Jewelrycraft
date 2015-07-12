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
import net.minecraft.util.StatCollector;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

public class ItemThiefGloves extends Item
{
    public Random rand = new Random();
    
    public ItemThiefGloves()
    {
        super();
        setCreativeTab(CreativeTabs.tabTools);
        setMaxStackSize(1);
        setMaxDamage(10);
    }
    
    /**
     * @param stack
     * @param player
     * @param entity
     * @return
     */
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        String villagerString = StatCollector.translateToLocal("info." + Variables.MODID + ".villager");
        if (entity instanceof EntityVillager){
            EntityVillager villager = (EntityVillager)entity;
            int wealth = (Integer)ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "wealth", "field_70956_bz");
            MerchantRecipeList buyingList = (MerchantRecipeList)ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "buyingList", "field_70963_i");
            int chance = 5;
            boolean areOtherVillagersAround = false, canTheySeeYou = false;
            AxisAlignedBB axisalignedbb = villager.boundingBox.expand(4.0D, 4.0D, 4.0D);
            List entities = villager.worldObj.getEntitiesWithinAABBExcludingEntity(villager, axisalignedbb);
            for(Object s: entities)
                if (s instanceof EntityVillager){
                    areOtherVillagersAround = true;
                    chance += rand.nextInt(2);
                    if (((EntityVillager)s).canEntityBeSeen(player)){
                        chance += 2;
                        canTheySeeYou = true;
                    }
                }
            if (villager.canEntityBeSeen(player)) chance += 5;
            if (player.isPotionActive(Potion.invisibility)) chance -= 0.8 * chance;
            if (player.capabilities.isCreativeMode) chance = 1;
            int steal = rand.nextInt(chance);
            if (steal == 0){
                villager.dropItem(Items.emerald, wealth);
                ReflectionHelper.setPrivateValue(EntityVillager.class, villager, 0, "wealth", "field_70956_bz");
            }
            if (buyingList != null){
                Iterator<?> iterator = buyingList.iterator();
                if (steal == 0){
                    while (iterator.hasNext()){
                        MerchantRecipe recipe = (MerchantRecipe)iterator.next();
                        int toolUses = (Integer)ReflectionHelper.getPrivateValue(MerchantRecipe.class, recipe, "toolUses", "field_77400_d");
                        int quantity;
                        if (recipe.getItemToSell().isStackable()) quantity = recipe.getItemToSell().stackSize * (7 - toolUses);
                        else quantity = recipe.getItemToSell().stackSize;
                        ItemStack s = new ItemStack(recipe.getItemToSell().getItem(), quantity, recipe.getItemToSell().getItemDamage());
                        s.setTagCompound(recipe.getItemToSell().getTagCompound());
                        if (player.inventory.addItemStackToInventory(s)) ;
                        else villager.entityDropItem(s, 0);
                        if (!player.capabilities.isCreativeMode) JewelrycraftUtil.addCursePoints(player, 5);
                        player.addChatMessage(new ChatComponentText(villagerString+" #" + villager.getProfession() + ": "+StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".villager.confusion") + " " + s.getDisplayName() + "!"));
                        stack.damageItem(1, player);
                    }
                    buyingList.clear();
                    ReflectionHelper.setPrivateValue(EntityVillager.class, villager, 300, "timeUntilReset", "field_70961_j");
                    ReflectionHelper.setPrivateValue(EntityVillager.class, villager, true, "needsInitilization", "field_70959_by");
                    player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".whisper")+": "));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".stealSuccess1")));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".stealSuccess2")));
                    player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".stealSuccess3")));
                }else{
                    stack.damageItem(1, player);
                    if (!player.capabilities.isCreativeMode) JewelrycraftUtil.addCursePoints(player, 25);
                    if (player.isPotionActive(Potion.invisibility)){
                        player.addChatMessage(new ChatComponentText(villagerString+" #" + villager.getProfession() + " " +StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".steal.fail")));
                    }
                    else{
                        if (areOtherVillagersAround){
                            if (!canTheySeeYou){
                                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".steal.caught1")+" "+villagerString+" #" + villager.getProfession() + "."));
                                player.addChatMessage(new ChatComponentText(villagerString+" #" + villager.getProfession() + " "+StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".steal2")));
                                return true;
                            }
                            else{
                                player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".steal.caught2")+" "+villagerString+" #" + villager.getProfession() + "."));
                                player.addChatMessage(new ChatComponentText(villagerString+" #" + villager.getProfession() + " "+StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".steal2")));
                                return true;
                            }
                        }else{
                            player.addChatMessage(new ChatComponentText(villagerString+" #" + villager.getProfession() + " "+StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".steal1")));
                            player.addChatMessage(new ChatComponentText(villagerString+" #" + villager.getProfession() + " "+StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".steal2")));
                            return true;
                        }
                    }
                }
            }
            return true;
        }else return super.itemInteractionForEntity(stack, player, entity);
    }
    
    /**
     * @param stack
     * @param player
     * @param list
     * @param par4
     */
    @Override
    @SuppressWarnings ("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer player, @SuppressWarnings ("rawtypes") List list, boolean par4)
    {
        if (!shouldAddAdditionalInfo()) list.add(EnumChatFormatting.GRAY + additionalInfoInstructions());
        else{
            list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item." + Variables.MODID + ".thievingGloves.info.1"));
            list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item." + Variables.MODID + ".thievingGloves.info.2"));
            list.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item." + Variables.MODID + ".thievingGloves.info.3"));
        }
    }
    
    /**
     * @return
     */
    public static boolean shouldAddAdditionalInfo()
    {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) return true;
        return false;
    }
    
    /**
     * @return
     */
    public static String additionalInfoInstructions()
    {
        String message = StatCollector.translateToLocal("item." + Variables.MODID + ".thievingGloves.info.extra");
        return message;
    }
}
