package darkknight.jewelrycraft.container;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;

public class GuiTabIntroduction extends GuiTab
{
    public GuiTabIntroduction(int id)
    {
        super("Introduction", id);
    }
    
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(ItemList.ring);
    }
   
    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {
        String text = "";
        int xPos = page % 2 == 0 ? 107 : -35;
        switch(page)
        {
            case 1:
                text = "Welcome to Jewelrycraft 2! This mod is about making jewelry that you can modify to your own will. To find out how to create a jewelry, please consult the book and look at the Smelter block. To add modifiers to it you need to perform a ritual. To see how to do that, look at the Cursed Eye";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 2:
                text = "block in this giude. Please be aware that even if you can add anything as a modifier and can have multiple modifiers on one jewelry, this mod is still in alpha and does not have that many modifiers implemtnted and currently don't have any different effects depending on";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 3:
                text = "the gem used. To see what modifiers are currently implemented, just look in the Modifiers tab located in this guide (it is the one with the blaze powder as an icon).";
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
