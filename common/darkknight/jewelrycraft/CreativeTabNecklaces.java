package darkknight.jewelrycraft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CreativeTabNecklaces extends CreativeTabs
{
    public static ArrayList<ItemStack> metal = new ArrayList<ItemStack>();

    public CreativeTabNecklaces(String par2Str)
    {
        super(par2Str);
        metal.add(new ItemStack(Item.ingotGold));
        metal.add(new ItemStack(Item.ingotIron));
    }

    @Override
    public ItemStack getIconItemStack()
    {
        ItemStack ring = new ItemStack(ItemList.necklace);
        return ring;
    }

    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    public void displayAllReleventItems(List par1List)
    {
        int index = 0;// index2 = 0;
        while (index < OreDictionary.getOreNames().length)
        {
            // while(index2 <
            // OreDictionary.getOres(OreDictionary.getOreNames()[index]).size()
            // && index2 < 32767)
            // {
            Iterator<ItemStack> i = OreDictionary.getOres(OreDictionary.getOreNames()[index]).iterator();

            while (i.hasNext())
            {
                ItemStack nextStack = i.next();
                
                if (nextStack.getItem().getUnlocalizedName().toLowerCase().contains("ingot") && !metal.contains(nextStack))
                    metal.add(i.next());
            }

            // if(OreDictionary.getOres(OreDictionary.getOreNames()[index]).get(index2).getUnlocalizedName().toLowerCase().contains("ingot")
            // &&
            // !metal.contains(OreDictionary.getOres(OreDictionary.getOreNames()[index]).get(index2)))
            // metal.add(OreDictionary.getOres(OreDictionary.getOreNames()[index]).get(index2));
            // index2++;
            // }
            // index2 = 0;
            index++;
        }
        for (int i = 0; i < metal.size(); i++)
            for (int j = 0; j < JewelrycraftUtil.modifiers.size(); j++)
                for (int k = 0; k < JewelrycraftUtil.jewel.size(); k++)
                {
                    par1List.add(ItemList.necklace.getModifiedItemStack(metal.get(i), null, JewelrycraftUtil.jewel.get(k)));
                    par1List.add(ItemList.necklace.getModifiedItemStack(metal.get(i), JewelrycraftUtil.modifiers.get(j), JewelrycraftUtil.jewel.get(k)));
                }
    }

}
