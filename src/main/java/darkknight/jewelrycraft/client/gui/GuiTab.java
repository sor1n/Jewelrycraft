package darkknight.jewelrycraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiTab extends GuiRectangle {
	protected int values;
	protected int del;

	public GuiTab(int id) {
		super(-62, 10 + 19 * id, 19, 18);
		values = 0;
		del = 0;
	}

	public String getName() {
		return "";
	}

	public abstract void drawBackground(GuiGuide gui, int x, int y, int page);

	public abstract void drawForeground(GuiGuide gui, int x, int y, int page);

	public void mouseClick(GuiGuide gui, int x, int y, int button) {}

	public void mouseMoveClick(GuiGuide gui, int x, int y, int button, long timeSinceClicked) {}

	public void mouseReleased(GuiGuide gui, int x, int y, int button) {}

	public int getMaxPages() {
		return 1;
	}
}
