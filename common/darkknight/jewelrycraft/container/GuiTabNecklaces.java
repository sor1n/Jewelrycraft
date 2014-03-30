package darkknight.jewelrycraft.container;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;

public class GuiTabNecklaces extends GuiTab
{
    public GuiTabNecklaces(int id)
    {
        super("Necklaces", id);
    }
    
    public ItemStack getIcon()
    {        
        ItemStack it = new ItemStack(ItemList.necklace);
        JewelryNBT.addMetal(it, new ItemStack(Item.ingotIron));
        JewelryNBT.addJewel(it, new ItemStack(Block.blockRedstone)); 
        return it;
    }

    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {        
        gui.getFont().drawString(this.getName(), gui.getLeft() + 4,  gui.getTop() + 5, 3432135);
    }

    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {        
    }

}
