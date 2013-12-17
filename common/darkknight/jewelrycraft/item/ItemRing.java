package darkknight.jewelrycraft.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemRing extends ItemBase
{
    public ItemRing(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
    }

    public static void addMetal(ItemStack item, ItemStack metal)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound ingotNBT = new NBTTagCompound();
        metal.writeToNBT(ingotNBT);
        itemStackData.setTag("ingot", ingotNBT);
    }

    public static void addEffect(ItemStack item, int potion)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound potionNBT = new NBTTagCompound();
        potionNBT.setInteger("potion", potion);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().hasKey("ingot"))
            {
                NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("ingot");
                ItemStack ingotStack = new ItemStack(0, 0, 0);
                ingotStack.readFromNBT(ingotNBT);
                list.add(EnumChatFormatting.GRAY + ingotStack.getDisplayName());
            }

            if (stack.getTagCompound().hasKey("potion"))
            {
                NBTTagCompound potionNBT = new NBTTagCompound();;
                int potion = 0;
                potion = potionNBT.getInteger("potion");
                list.add(EnumChatFormatting.GREEN + Integer.toString(potion));
            }
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (stack.hasTagCompound())
        {
            if(stack.getTagCompound().hasKey("effect"))
            {
                if (par3Entity instanceof EntityPlayer)
                {
                    EntityPlayer entityplayer = (EntityPlayer)par3Entity;
                    NBTTagCompound potionNBT = new NBTTagCompound();
                    int potion = 0;
                    potion = potionNBT.getInteger("potion");
                    if(potion != 0 && entityplayer != null) entityplayer.addPotionEffect(new PotionEffect(potion, 4));
                }
            }
        }
    }
}
