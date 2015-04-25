package darkknight.jewelrycraft;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

/**
 * The Class CreativeTabLiquids.
 */
public class CreativeTabLiquids extends CreativeTabs
{
    /**
     * Instantiates a new creative tab liquids.
     *
     * @param par2Str the par2 str
     */
    public CreativeTabLiquids(String par2Str)
    {
        super(par2Str);
    }
    
    /**
     * @return the Item for the icon
     */
    @Override
    public Item getTabIconItem()
    {
        return ItemList.bucket;
    }
    
    /**
     * @param par1List List of items to display
     */
    @Override
    public void displayAllReleventItems(List par1List)
    {
        for(int i = 0; i < JewelrycraftUtil.objects.size(); i++)
            par1List.add(ItemList.bucket.getModifiedItemStack(JewelrycraftUtil.objects.get(i)));
    }
}
