package darkknight.jewelrycraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public abstract class GuiTab extends GuiRectangle
{
    protected int values;
    protected int del;
    protected String name;
    
    /**
     * @param name
     * @param id
     */
    public GuiTab(String name, int id)
    {
        super(-62, 10 + 19 * id, 19, 18);
        this.name = name;
        values = 0;
        del = 0;
    }
    
    /**
     * @return
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param page
     */
    public abstract void drawBackground(GuiGuide gui, int x, int y, int page);
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param page
     */
    public abstract void drawForeground(GuiGuide gui, int x, int y, int page);
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param button
     */
    public void mouseClick(GuiGuide gui, int x, int y, int button)
    {}
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param button
     * @param timeSinceClicked
     */
    public void mouseMoveClick(GuiGuide gui, int x, int y, int button, long timeSinceClicked)
    {}
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param button
     */
    public void mouseReleased(GuiGuide gui, int x, int y, int button)
    {}
    
    /**
     * @return
     */
    public int getMaxPages()
    {
        return 1;
    }
}
