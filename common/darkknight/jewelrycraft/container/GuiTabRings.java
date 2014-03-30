package darkknight.jewelrycraft.container;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class GuiTabRings extends GuiTab
{

    public GuiTabRings(int id)
    {
        super("Rings", id);
    }
    
    public ItemStack getIcon()
    {        
        ItemStack it = new ItemStack(ItemList.ring);
        JewelryNBT.addMetal(it, new ItemStack(Item.ingotGold));
        JewelryNBT.addJewel(it, new ItemStack(Item.enderPearl)); 
        return it;
    }

    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {        
        ArrayList<String> text = new ArrayList<String>();
        ItemStack item = new ItemStack(ItemList.ring);
        int xPos = (page%2==0)?107:-35;
        switch(page)
        {    
            case 1: 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, new ItemStack(Item.enderPearl));

                text.add("§2Jewel: §0Ender Pearl");
                text.add("§2Modifier: §0None");
                text.add("§2Ingot: §0Any");
                text.add("  This ring allows you");
                text.add("to teleport in any");
                text.add("location from the same");
                text.add("dimension. Simply right");
                text.add("click once in a location");
                text.add("to se the position. Then");
                text.add("right click any time you");
                text.add("want to teleport there.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10);
                break;
            default:;
        }
    }

    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {        
    }

}
