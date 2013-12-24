package darkknight.jewelrycraft.util;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

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

    public static void addEntity(ItemStack item, EntityLivingBase entity)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound entityNBT = new NBTTagCompound();
        entity.writeToNBT(entityNBT);
        itemStackData.setTag("entity", entityNBT);
    }

    public static void addEntityID(ItemStack item, EntityLivingBase entity)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound entityNBT = new NBTTagCompound();
        int id = EntityList.getEntityID(entity); 
        entityNBT.setInteger("entityID", id);
        itemStackData.setTag("entityID", entityNBT);
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

    public static void addCoordonatesAndDimension(ItemStack item, double x, double y, double z, int dim, String name)
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
        coords.setInteger("dimension", dim);
        coords.setString("dimName", name);
        itemStackData.setTag("x", coords);
        itemStackData.setTag("y", coords);
        itemStackData.setTag("z", coords);
        itemStackData.setTag("dimension", coords);
        itemStackData.setTag("dimName", coords);
    }
    
    public static void removeNBT(ItemStack item, String tag)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        itemStackData.removeTag(tag);
    }

    public static void addEnchantment(ItemStack item)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        itemStackData.setTag("ench", new NBTTagList("ench"));
    }

}
