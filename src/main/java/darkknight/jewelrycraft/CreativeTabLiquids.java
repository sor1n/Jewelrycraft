package darkknight.jewelrycraft;

import java.util.List;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * The Class CreativeTabLiquids.
 */
public class CreativeTabLiquids extends CreativeTabs
{
    /**
     * Instantiates a new creative tab liquids.
     *
     * @param name the name
     */
    public CreativeTabLiquids(String name)
    {
        super(name);
    }
 
    public boolean hasSearchBar() {
         
        return true;
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
    public void displayAllReleventItems(List list)
    {
        for(int i = 0; i < JewelrycraftUtil.objects.size(); i++)
            list.add(ItemList.bucket.getModifiedItemStack(JewelrycraftUtil.objects.get(i)));
    }
}
