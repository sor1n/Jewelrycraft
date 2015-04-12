package darkknight.jewelrycraft.client.gui;

import java.util.Arrays;
import net.minecraft.item.ItemStack;

public class GuiRectangle
{
    private int x;
    private int y;
    private int w;
    private int h;
    
    /**
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public GuiRectangle(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    /**
     * @param gui
     * @param mouseX
     * @param mouseY
     * @return
     */
    public boolean inRect(GuiGuide gui, int mouseX, int mouseY)
    {
        mouseX -= gui.getLeft();
        mouseY -= gui.getTop();
        return x <= mouseX && mouseX <= x + w && y <= mouseY && mouseY <= y + h;
    }
    
    /**
     * @param x
     */
    public void setX(int x)
    {
        this.x = x;
    }
    
    /**
     * @param y
     */
    public void setY(int y)
    {
        this.y = y;
    }
    
    /**
     * @param gui
     * @param srcX
     * @param srcY
     */
    public void draw(GuiGuide gui, int srcX, int srcY)
    {
        gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, w, h);
    }
    
    /**
     * @param gui
     * @param srcX
     * @param srcY
     * @param width
     * @param height
     */
    public void draw(GuiGuide gui, int srcX, int srcY, int width, int height)
    {
        gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, width, height);
    }
    
    /**
     * @param gui
     * @param mouseX
     * @param mouseY
     * @param str
     */
    public void drawString(GuiGuide gui, int mouseX, int mouseY, String str)
    {
        if (inRect(gui, mouseX, mouseY)) gui.drawHoverString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
    }
    
    /**
     * @return
     */
    public ItemStack getIcon()
    {
        return null;
    }
}
