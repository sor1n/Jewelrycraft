package darkknight.jewelrycraft.client.gui;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import darkknight.jewelrycraft.client.Page;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.Variables;

public class GuiTabIntroduction extends GuiTab
{
    public GuiTabIntroduction(int id)
    {
        super(id);
    }
    
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(ItemList.ring);
    }
    
    public String getName()
    {
        return StatCollector.translateToLocal("guide." + Variables.MODID + ".tab.introduction");
    }
   
    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {
        String text = "";
        int xPos = page % 2 == 0 ? 107 : -35;
        switch(page)
        {
            case 1:
                text = StatCollector.translateToLocal("guide." + Variables.MODID + ".tab.introduction."+page);
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 2:
                text = StatCollector.translateToLocal("guide." + Variables.MODID + ".tab.introduction."+page);
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 3:
                text = StatCollector.translateToLocal("guide." + Variables.MODID + ".tab.introduction."+page);
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
        }
    }
    
    @Override
    public int getMaxPages()
    {
        return 4;
    }
    
    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {}
}
