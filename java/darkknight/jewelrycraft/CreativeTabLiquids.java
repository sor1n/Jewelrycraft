package darkknight.jewelrycraft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CreativeTabLiquids extends CreativeTabs
{
    public CreativeTabLiquids(String par2Str)
    {
        super(par2Str);
    }
    
    @Override
    public Item getTabIconItem()
    {
        return ItemList.bucket;
    }
    
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    public void displayAllReleventItems(List par1List)
    {
        for (int i = 0; i < JewelrycraftUtil.metal.size(); i++)
            par1List.add(ItemList.bucket.getModifiedItemStack(JewelrycraftUtil.metal.get(i)));
    }
    
}
