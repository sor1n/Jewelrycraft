package darkknight.jewelrycraft.client.gui;

import java.awt.Desktop;
import java.net.URL;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.Page;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;

public class GuiTabCurses extends GuiTab 
{
	public GuiTabCurses(int id) {
		super(id);
	}

	public String getName() {
		return StatCollector.translateToLocal("guide." + Variables.MODID + ".tab.curses");
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ItemList.testItem);
	}

	@Override
	public void drawBackground(GuiGuide gui, int x, int y, int page) {
		String text = "";
		int xPos = page % 2 == 0 ? 107 : -35;
		switch (page) {
		case 1:
			text = "You are poisoned for as long as this curse is active.";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 0), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 2:
			text = "Whenever you attack an entity you are set on fire.";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 1), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 3:
			text = "You can no longer throw any item. I mean, you might need them later, right?";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 2), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 4:
			text = "You are blinded. Literally. I mean, what did you expect?";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 3), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 5:
			text = "There is a random chance that when you attack a passive mob you will lose a full heart, but gain a black heart.";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 4), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 6:
			text = "Whatever you touch turns into gold. Believe me, this is no good to you, you can't even harvest this gold.";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 5), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 7:
			text = "Entities have a higher chance of dropping more items and hearts when killed.";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 6), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 8:
			text = "There is a random chance that you'll steal 1 heart from an entity that is collided with you. If you are low on health, you will heal. If you are fully healed, an extra heart will be added to you. You can steal up to 2 hearts per entity (3 if you complete the challenge). When you steal a heart, that entity will have their max health reduced by 1.";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 7), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 9:
			text = "When you attack an entity and are low on health, there is a 50% chance that you'll heal half a heart. Also you will catch on fire if you're exposed to sun.";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 8), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		case 10:
			text = "Any mob you kill or block you destroy(eg. Diamond Ore, Redstone Ore, stuff that drops items not the block itself) drops double the amount of items it normally would.";
			Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.testItem, 1, 9), text, 40f, 0, 0, Curse.getCurseList().get(page-1).getDisplayName(), 45, 10, false);
			break;
		default:;
		}
	}

	@Override
	public int getMaxPages() {
		return 10;
	}

	@Override
	public void drawForeground(GuiGuide gui, int x, int y, int page) {}
}
