package darkknight.jewelrycraft.container;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.client.GuiGuide;

public class GuiTabModifiers extends GuiTab
{
    
    /**
     * @param id
     */
    public GuiTabModifiers(int id)
    {
        super("Modifiers", id);
    }
    
    /**
     * @return
     */
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(Items.blaze_powder);
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param page
     */
    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {
        String text = "";
        int xPos = page % 2 == 0 ? 107 : -35;
        switch(page)
        {
            case 1:
                text = "Although you can add anything as a modifier, only some objects have an effect. In this tab you can find all of modifiers that have a use and what they do, in the form of a story/riddle/poem. However different jewellery have different effects for the same modifier.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 2:
                text = "The ancient ones talked about a rising fire in your heart. Fret do not, for flames do not burn, but water might sting a turn. Watch your step, do not be cocky, for its protection is a bit sloppy.";
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), new ItemStack(Items.blaze_powder), text, 40f);
                break;
            default:
                ;
        }
    }
    
    /**
     * @return
     */
    @Override
    public int getMaxPages()
    {
        return 1;
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param page
     */
    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {}
}
