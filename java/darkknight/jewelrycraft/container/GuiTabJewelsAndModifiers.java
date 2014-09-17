package darkknight.jewelrycraft.container;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class GuiTabJewelsAndModifiers extends GuiTab
{
    public GuiTabJewelsAndModifiers(int id)
    {
        super("Jewels, modifiers and ingots", id);
    }
    
    public ItemStack getIcon()
    {
        return new ItemStack(Items.emerald);
    }
    
    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {
        int xPos = (page % 2 == 0) ? 107 : -35;
        for (int i = (page - 1) * 9; i < page * 9; i++)
            if (i < JewelrycraftUtil.jewel.size())
            {
                gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Jewels", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                gui.renderItem(JewelrycraftUtil.jewel.get(i), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16 * (i - 9 * (page - 1)), 30f);
                gui.getFont().drawString(JewelrycraftUtil.jewel.get(i).getDisplayName(), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16 * (i - 9 * (page - 1)), 0);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
        page -= (JewelrycraftUtil.jewel.size() / 9 + 1);
        for (int i = (page - 1) * 9; i < page * 9; i++)
        {
            if (i < JewelrycraftUtil.modifiers.size() && page > 0)
            {
                gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Modifiers", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                gui.renderItem(JewelrycraftUtil.modifiers.get(i), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16 * (i - 9 * (page - 1)), 30f);
                gui.getFont().drawString(JewelrycraftUtil.modifiers.get(i).getDisplayName(), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16 * (i - 9 * (page - 1)), 0);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
        }
        page -= (JewelrycraftUtil.modifiers.size() / 9 + 1);
        for (int i = (page - 1) * 9; i < page * 9; i++)
        {
            if (i < JewelrycraftUtil.metal.size() && page > 0)
            {
                gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Ingots", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                gui.renderItem(JewelrycraftUtil.metal.get(i).copy(), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16 * (i - 9 * (page - 1)), 30f);
                gui.getFont().drawString(JewelrycraftUtil.metal.get(i).copy().getDisplayName(), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16 * (i - 9 * (page - 1)), 0);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
        }
    }
    
    public int getMaxPages()
    {
        return JewelrycraftUtil.jewel.size() / 9 + JewelrycraftUtil.modifiers.size() / 9 + JewelrycraftUtil.metal.size() / 9 + 2;
    }
    
    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {
    }
    
}
