package darkknight.jewelrycraft;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class CreativeTabRings extends CreativeTabs
{
    public CreativeTabRings(String par2Str)
    {
        super(par2Str);
    }
    
    @Override
    public Item getTabIconItem()
    {
        return ItemList.ring;
    }
    
    @SuppressWarnings(
    { "rawtypes", "unchecked" })
    public void displayAllReleventItems(List par1List)
    {
        for (int i = 0; i < JewelrycraftUtil.metal.size(); i++)
            for (int j = 0; j < JewelrycraftUtil.modifiers.size(); j++)
                for (int k = 0; k < JewelrycraftUtil.jewel.size(); k++)
                    par1List.add(ItemList.ring.getModifiedItemStack(JewelrycraftUtil.metal.get(i).copy(), JewelrycraftUtil.modifiers.get(j).copy(), JewelrycraftUtil.jewel.get(k).copy()));
    }
    
}
