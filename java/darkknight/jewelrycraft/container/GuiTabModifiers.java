package darkknight.jewelrycraft.container;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;

public class GuiTabModifiers extends GuiTab
{    
    public GuiTabModifiers(int id)
    {
        super("Modifiers", id);
    }
    
    public ItemStack getIcon()
    {
        return new ItemStack(Items.blaze_powder);
    }
    
    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {
        String text = "";
        int xPos = (page % 2 == 0) ? 107 : -35;
        switch (page)
        {
            case 1:
                text = "This is a test to see if the program works or not! And it does seem to be working. Yaaay! Thank God I made this. This is so much easier :D";
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), new ItemStack(Items.blaze_powder), text, 40f);
                break;
            default:;
        }
    }
    
    public int getMaxPages()
    {
        return 1;
    }
    
    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {
    }
    
}
