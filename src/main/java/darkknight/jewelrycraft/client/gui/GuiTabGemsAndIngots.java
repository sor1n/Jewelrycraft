package darkknight.jewelrycraft.client.gui;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;

public class GuiTabGemsAndIngots extends GuiTab
{
    
    /**
     * @param id
     */
    public GuiTabGemsAndIngots(int id)
    {
        super(id);
    }
    
    public String getName()
    {
        return StatCollector.translateToLocal("guide." + Variables.MODID + ".tab.misc");
    }
    
    /**
     * @return
     */
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(Items.emerald);
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
        int xPos = page % 2 == 0 ? 107 : -35;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        for(int i = (page - 1) * 9; i < page * 9; i++)
            if (i < JewelrycraftUtil.gem.size()){
                gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Gems", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                gui.renderItem(JewelrycraftUtil.gem.get(i), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16 * (i - 9 * (page - 1)), 30f, true, 0, 0, 0);
                gui.getFont().drawString(String.format("%-1.18s", JewelrycraftUtil.gem.get(i).getDisplayName()), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16 * (i - 9 * (page - 1)), 0);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
        page -= JewelrycraftUtil.gem.size() / 9 + 1;
        for(int i = (page - 1) * 9; i < page * 9; i++)
            if (i < JewelrycraftUtil.metal.size() && page > 0){
                gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Ingots", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                gui.renderItem(JewelrycraftUtil.metal.get(i).copy(), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16 * (i - 9 * (page - 1)), 30f, true, 0, 0, 0);
                gui.getFont().drawString(String.format("%-1.18s", JewelrycraftUtil.metal.get(i).copy().getDisplayName()), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16 * (i - 9 * (page - 1)), 0);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
        page -= JewelrycraftUtil.metal.size() / 9 + 1;
        for(int i = (page - 1) * 9; i < page * 9; i++)
            if (i < JewelrycraftUtil.ores.size() && page > 0){
                gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Ores", gui.getLeft() + xPos + 40, gui.getTop(), 0);
                gui.renderItem(JewelrycraftUtil.ores.get(i).copy(), gui.getLeft() + xPos + 10, gui.getTop() + 22 + 16 * (i - 9 * (page - 1)), 30f, true, 0, 0, 0);
                gui.getFont().drawString(String.format("%-1.18s", JewelrycraftUtil.ores.get(i).copy().getDisplayName()), gui.getLeft() + xPos + 20, gui.getTop() + 12 + 16 * (i - 9 * (page - 1)), 0);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    /**
     * @return
     */
    @Override
    public int getMaxPages()
    {
        return JewelrycraftUtil.gem.size() / 9 + JewelrycraftUtil.metal.size() / 9 + JewelrycraftUtil.ores.size() / 9 + 4;
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
