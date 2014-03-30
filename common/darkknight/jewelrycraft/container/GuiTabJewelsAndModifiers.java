package darkknight.jewelrycraft.container;

import org.lwjgl.opengl.GL11;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class GuiTabJewelsAndModifiers extends GuiTab
{
    public GuiTabJewelsAndModifiers(int id)
    {
        super("Jewels and modifiers", id);
    }

    public ItemStack getIcon()
    {        
        return new ItemStack(Item.emerald);
    }

    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {        
        int xPos = (page%2==0)?107:-35;
        switch(page)
        {
            case 1:
                gui.getFont().drawString("§1§n" + "Jewels", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                for(int i = 0; i <= 8; i++){
                    gui.renderItem(JewelrycraftUtil.jewel.get(i), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16*i, 30f);
                    gui.getFont().drawString(JewelrycraftUtil.jewel.get(i).getDisplayName(), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16*i, 0);
                }
                break;
            case 2:
                gui.getFont().drawString("§1§n" + "Jewels", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                for(int i = 0; i <= 8; i++){
                    gui.renderItem(JewelrycraftUtil.jewel.get(i+9), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16*i, 30f);
                    gui.getFont().drawString(JewelrycraftUtil.jewel.get(i+9).getDisplayName(), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16*i, 0);
                }
                break;
            case 3:
                gui.getFont().drawString("§1§n" + "Jewels", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                for(int i = 0; i <= 8; i++)
                    if(i+18 < JewelrycraftUtil.jewel.size()){
                        gui.renderItem(JewelrycraftUtil.jewel.get(i+18), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16*i, 30f);
                        gui.getFont().drawString(JewelrycraftUtil.jewel.get(i+18).getDisplayName(), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16*i, 0);
                        GL11.glDisable(GL11.GL_LIGHTING);
                    }
                break;
            case 4:
                gui.getFont().drawString("§1§n" + "Modifiers", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                for(int i = 0; i <= 8; i++){
                    if(i < JewelrycraftUtil.modifiers.size())
                    {
                        gui.renderItem(JewelrycraftUtil.modifiers.get(i), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16*i, 30f);
                        gui.getFont().drawString(JewelrycraftUtil.modifiers.get(i).getDisplayName(), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16*i, 0);
                    }
                }
                break;
            case 5:
                gui.getFont().drawString("§1§n" + "Modifiers", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                for(int i = 0; i <= 8; i++){
                    if(i+9 < JewelrycraftUtil.modifiers.size()){
                        gui.renderItem(JewelrycraftUtil.modifiers.get(i + 9), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16*i, 30f);
                        gui.getFont().drawString(JewelrycraftUtil.modifiers.get(i + 9).getDisplayName(), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16*i, 0);
                        GL11.glDisable(GL11.GL_LIGHTING);
                    }
                }
                break;
            default:;
        }
    }

    public int getMaxPages()
    {
        return 5;
    }

    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {        
    }

}
