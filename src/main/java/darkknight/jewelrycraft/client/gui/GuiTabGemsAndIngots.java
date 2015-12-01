package darkknight.jewelrycraft.client.gui;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

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
        float scale = 0.75F;
        for(int i = (page - 1) * 12; i < page * 12; i++)
            if (i < JewelrycraftUtil.gem.size()){
            	GL11.glPushMatrix();
                if(i==(page - 1) * 12) gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Gems", gui.getLeft() + xPos + 40, gui.getTop(), 0);
            	GL11.glScalef(scale, scale, 0f);
                gui.getFont().drawString(String.format("%-1.26s", JewelrycraftUtil.gem.get(i).getDisplayName()), (int)((gui.getLeft() + xPos + 12)/scale), (int)((gui.getTop() + 12 + 12 * (i - 12 * (page - 1)))/scale), 0);
                GL11.glPopMatrix();
                gui.renderItem(JewelrycraftUtil.gem.get(i), gui.getLeft() + xPos + 5, gui.getTop() + 18 + 12 * (i - 12 * (page - 1)), 24f, true, 0, 0, 0);
            }
        page -= JewelrycraftUtil.gem.size() / 13 + 1;
        for(int i = (page - 1) * 12; i < page * 12; i++)
            if (i < JewelrycraftUtil.metal.size() && page > 0){
            	GL11.glPushMatrix();
            	if(i==(page - 1) * 12) gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Ingots", gui.getLeft() + xPos + 40, gui.getTop(), 0);
            	GL11.glScalef(scale, scale, 0f);
                gui.getFont().drawString(String.format("%-1.18s", JewelrycraftUtil.metal.get(i).copy().getDisplayName()), (int)((gui.getLeft() + xPos + 12)/scale), (int)((gui.getTop() + 12 + 12 * (i - 12 * (page - 1)))/scale), 0);
                GL11.glPopMatrix();
                gui.renderItem(JewelrycraftUtil.metal.get(i), gui.getLeft() + xPos + 5, gui.getTop() + 18 + 12 * (i - 12 * (page - 1)), 24f, true, 0, 0, 0);
            }
        page -= JewelrycraftUtil.metal.size() / 13 + 1;
        for(int i = (page - 1) * 12; i < page * 12; i++)
            if (i < JewelrycraftUtil.ores.size() && page > 0){
            	GL11.glPushMatrix();
            	if(i==(page - 1) * 12) gui.getFont().drawString(EnumChatFormatting.DARK_BLUE + "\u00a7n" + "Ores", gui.getLeft() + xPos + 40, gui.getTop(), 0);
            	GL11.glScalef(scale, scale, 0f);
                gui.getFont().drawString(String.format("%-1.18s", JewelrycraftUtil.ores.get(i).copy().getDisplayName()), (int)((gui.getLeft() + xPos + 12)/scale), (int)((gui.getTop() + 12 + 12 * (i - 12 * (page - 1)))/scale), 0);
                GL11.glPopMatrix();
                gui.renderItem(JewelrycraftUtil.ores.get(i), gui.getLeft() + xPos + 5, gui.getTop() + 18 + 12 * (i - 12 * (page - 1)), 24f, true, 0, 0, 0);
            }
    }
    
    /**
     * @return
     */
    @Override
    public int getMaxPages()
    {
        return (JewelrycraftUtil.gem.size() / 13) + (JewelrycraftUtil.metal.size() / 13) + (JewelrycraftUtil.ores.size() / 13) + 3;
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
