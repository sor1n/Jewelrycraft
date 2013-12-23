package darkknight.jewelrycraft.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class JewelryNBT
{

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

    public static void addJewel(ItemStack item, ItemStack jewel)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound jewelNBT = new NBTTagCompound();
        jewel.writeToNBT(jewelNBT);
        itemStackData.setTag("jewel", jewelNBT);
    }

    public static void addModifier(ItemStack item, ItemStack modifier)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound modifierNBT = new NBTTagCompound();
        modifier.writeToNBT(modifierNBT);
        itemStackData.setTag("modifier", modifierNBT);
    }

    public static void addCoordonates(ItemStack item, double x, double y, double z)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound coords = new NBTTagCompound();
        coords.setDouble("x", x);
        coords.setDouble("y", y);
        coords.setDouble("z", z);
        itemStackData.setTag("x", coords);
        itemStackData.setTag("y", coords);
        itemStackData.setTag("z", coords);
    }

}
