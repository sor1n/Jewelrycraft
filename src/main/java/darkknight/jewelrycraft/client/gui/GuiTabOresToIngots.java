package darkknight.jewelrycraft.client.gui;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GuiTabOresToIngots extends GuiTab
{
    /**
     * @param id
     */
    public GuiTabOresToIngots(int id)
    {
        super(id);
    }
    
    public String getName()
    {
        return StatCollector.translateToLocal("guide." + Variables.MODID + ".tab.oretoingot");
    }
    
    /**
     * @return
     */
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(BlockList.shadowOre);
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
        int i = 0;
        for(ItemStack ore: JewelrycraftUtil.oreToIngot.keySet()){
            if (i >= (page - 1) * 5 && i < page * 5){
                gui.renderItem(ore, gui.getLeft() + xPos + 10, gui.getTop() + 12 + 32 * (i - 5 * (page - 1)), 30f, true, 0, 0, 0);
                gui.renderItem(JewelrycraftUtil.oreToIngot.get(ore), gui.getLeft() + xPos + 10, gui.getTop() + 28 + 32 * (i - 5 * (page - 1)), 30f, true, 0, 0, 0);
                gui.getFont().drawString(String.format("%-1.18s", ore.getDisplayName()), gui.getLeft() + xPos + 20, gui.getTop() + 2 + 32 * (i - 5 * (page - 1)), 0);
                gui.getFont().drawString(String.format("%-1.18s", JewelrycraftUtil.oreToIngot.get(ore).getDisplayName()), gui.getLeft() + xPos + 20, gui.getTop() + 18 + 32 * (i - 5 * (page - 1)), 0);
                GL11.glDisable(GL11.GL_LIGHTING);
            }
            i++;
        }
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    /**
     * @return
     */
    @Override
    public int getMaxPages()
    {
        return JewelrycraftUtil.oreToIngot.size() / 5 + 2;
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
