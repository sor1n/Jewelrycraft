package darkknight.jewelrycraft.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.client.gui.container.ContainerJewelryModifier;
import darkknight.jewelrycraft.network.PacketRequestSetSlot;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class GuiJewelryModifier extends GuiContainer {
	private ResourceLocation							texture;
	private GuiButton									addItems;
	private GuiTextField								searchField, pages;
	private boolean										clicked;
	private int											page				= 1, maxPages = 1, selectedX = 0, selectedY = 0, selectedPage = 0, enabled = 0;
	private ItemStack									selectedItem;
	private ArrayList<ItemStack>						selectedItems		= new ArrayList<ItemStack>();
	private List<Map<Integer, Map<Integer, Integer>>>	selectedItemsPos	= new ArrayList<Map<Integer, Map<Integer, Integer>>>();
	ContainerJewelryModifier							jMod;

	public GuiJewelryModifier(ContainerJewelryModifier containerJewelryTab, ResourceLocation texture) {
		super(containerJewelryTab);
		xSize = 211;
		ySize = 247;
		jMod = containerJewelryTab;
		this.maxPages = JewelrycraftUtil.objects.size() / 48 + 1;
		this.texture = texture;
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor3f(1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (selectedX != 0 && selectedY != 0 && page == selectedPage) drawTexturedModalRect(k + selectedX, l + selectedY, 211, 0, 18, 18);
		for (Map<Integer, Map<Integer, Integer>> items : selectedItemsPos) {
			for (Object itemPage : items.keySet()) {
				if (page == (Integer) itemPage) for (int x : ((Map<Integer, Integer>) items.get(itemPage)).keySet())
					drawTexturedModalRect(k + x, l + (int) ((Map<Integer, Integer>) items.get(itemPage)).get(x), 211, 0, 18, 18);
			}
		}
		this.searchField.drawTextBox();
		this.pages.drawTextBox();
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int i = 0;
		for (ItemStack item : JewelrycraftUtil.objects) {
			try {
				if (item != null && item.getDisplayName() != null && (this.searchField.getText() == "" || item.getDisplayName().toLowerCase().contains(this.searchField.getText().toLowerCase()))) {
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glColor3f(1F, 1F, 1F);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glEnable(GL12.GL_RESCALE_NORMAL);
					if (i >= (page - 1) * 48 && i < page * 48) try {
						itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), item, 88 + 20 * (i % 6), 7 + 17 * (i / 6) - 136 * (page - 1));
					} catch (Exception e) {
						JewelrycraftMod.logger.info("Trying to display an item but gets this error: " + e.getMessage() + "\nThe item causing the issue is: " + item);
					}
					GL11.glDisable(GL11.GL_LIGHTING);
					i++;
				}
			} catch (Exception e) {
				JewelrycraftMod.logger.info("Trying to display an item but gets this error: " + e.getMessage() + "\nThe item causing the issue is: " + item);
			}
		}
	}

	@Override
	protected void keyTyped(char character, int key) {
		if (this.searchField.textboxKeyTyped(character, key)) {
			try {
				int items = 0;
				for (ItemStack item : JewelrycraftUtil.objects)
					if (item != null && searchField.getText() != "" && item.getDisplayName() != null && item.getDisplayName().toLowerCase().contains(this.searchField.getText().toLowerCase())) items++;
				maxPages = items / 48 + 1;
				page = 1;
				this.pages.setText(page + "/" + maxPages);
			} catch (Exception e) {
				JewelrycraftMod.logger.info("Trying to display an item but gets this error: " + e.getMessage());
			}
		} else super.keyTyped(character, key);
	}

	protected void mouseClicked(int x, int y, int id) {
		super.mouseClicked(x, y, id);
		if (x >= this.searchField.xPosition && x <= this.searchField.xPosition + this.searchField.width && y >= this.searchField.yPosition && y <= this.searchField.yPosition + this.searchField.height) {
			this.searchField.setText("");
			this.searchField.setFocused(true);
			maxPages = JewelrycraftUtil.objects.size() / 48 + 1;
		} else this.searchField.setFocused(false);
		for (Object button : this.buttonList) {
			if (((GuiButton) button).id < 4 && ((GuiButton) button).mousePressed(mc, x, y)) {
				if (((GuiButton) button).id != 3) {
					this.selectedItems.removeAll(selectedItems);
					this.selectedItemsPos.removeAll(selectedItemsPos);
				} else {
					this.selectedX = 0;
					this.selectedY = 0;
					this.selectedItem = null;
				}
				((GuiButton) buttonList.get(0)).enabled = true;
				((GuiButton) buttonList.get(1)).enabled = true;
				((GuiButton) buttonList.get(2)).enabled = true;
				((GuiButton) buttonList.get(3)).enabled = true;
				((GuiButton) button).enabled = false;
				enabled = ((GuiButton) button).id;
			}
		}
		int i = 0;
		for (ItemStack item : JewelrycraftUtil.objects) {
			if (item != null && item.getDisplayName() != null && (this.searchField.getText() == "" || item.getDisplayName().toLowerCase().contains(this.searchField.getText().toLowerCase()))) {
				if (i >= (page - 1) * 48 && i < page * 48 && x >= this.guiLeft + 88 + 20 * (i % 6) && x < this.guiLeft + 108 + 20 * (i % 6) && y >= this.guiTop + 9 + 17 * (i / 6) - 136 * (page - 1) && y < this.guiTop + 25 + 17 * (i / 6) - 136 * (page - 1)) {
					if (!((GuiButton) buttonList.get(0)).enabled || !((GuiButton) buttonList.get(1)).enabled || !((GuiButton) buttonList.get(2)).enabled) {
						this.selectedItem = item;
						this.selectedX = 87 + 20 * (i % 6);
						this.selectedY = 6 + 17 * (i / 6) - 136 * (page - 1);
						this.selectedPage = page;
					} else if (!((GuiButton) buttonList.get(3)).enabled) {
						Map<Integer, Map<Integer, Integer>> itemPage = new HashMap<Integer, Map<Integer, Integer>>();
						Map<Integer, Integer> pos = new HashMap<Integer, Integer>();
						pos.put(87 + 20 * (i % 6), 6 + 17 * (i / 6) - 136 * (page - 1));
						itemPage.put(page, pos);
						if (!this.selectedItems.contains(item)) {
							this.selectedItems.add(item);
							this.selectedItemsPos.add(itemPage);
						} else {
							this.selectedItems.remove(item);
							this.selectedItemsPos.remove(itemPage);
						}
					}
				}
				i++;
			}
		}
		if (((GuiButton) buttonList.get(5)).mousePressed(mc, x, y) && page > 1) page--;
		if (((GuiButton) buttonList.get(6)).mousePressed(mc, x, y) && page < maxPages) page++;
		if (jMod.modInv.getStackInSlot(36) != null) {
			ItemStack targetItem = jMod.modInv.getStackInSlot(36).copy();
			if (((GuiButton) buttonList.get(4)).mousePressed(mc, x, y) && !((GuiButton) buttonList.get(0)).enabled) {
				JewelryNBT.addIngotColor(targetItem, 16777215);
				JewelryNBT.addMetal(targetItem, new ItemStack(Item.getItemById(0), 0, 0));
				if (selectedItem != null) JewelryNBT.addMetal(targetItem, this.selectedItem);
				JewelrycraftMod.netWrapper.sendToServer(new PacketRequestSetSlot(targetItem));
			}
			if (((GuiButton) buttonList.get(4)).mousePressed(mc, x, y) && !((GuiButton) buttonList.get(1)).enabled) {
				JewelryNBT.addGemColor(targetItem, 16777215);
				JewelryNBT.addGem(targetItem, new ItemStack(Item.getItemById(0), 0, 0));
				if (selectedItem != null) JewelryNBT.addGem(targetItem, this.selectedItem);
				JewelrycraftMod.netWrapper.sendToServer(new PacketRequestSetSlot(targetItem));
			}
			if (((GuiButton) buttonList.get(4)).mousePressed(mc, x, y) && !((GuiButton) buttonList.get(2)).enabled) {
				if (selectedItem != null) JewelryNBT.addItem(targetItem, selectedItem);
				JewelrycraftMod.netWrapper.sendToServer(new PacketRequestSetSlot(targetItem));
			}
			if (((GuiButton) buttonList.get(4)).mousePressed(mc, x, y) && !((GuiButton) buttonList.get(3)).enabled) {
				if (!selectedItems.isEmpty()) JewelryNBT.addModifiers(targetItem, selectedItems);
				JewelrycraftMod.netWrapper.sendToServer(new PacketRequestSetSlot(targetItem));
			}
		}
		this.pages.setText(page + "/" + maxPages);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.searchField = new GuiTextField(this.fontRendererObj, this.guiLeft + 89, this.guiTop + 148, 115, this.fontRendererObj.FONT_HEIGHT + 3);
		this.searchField.setMaxStringLength(15);
		this.searchField.setTextColor(16777215);
		this.searchField.setVisible(true);
		this.searchField.setCanLoseFocus(true);
		this.pages = new GuiTextField(this.fontRendererObj, this.guiLeft + 20, this.guiTop + 146, 50, this.fontRendererObj.FONT_HEIGHT + 3);
		this.pages.setMaxStringLength(15);
		this.pages.setTextColor(16777215);
		this.pages.setVisible(true);
		this.pages.setText(page + "/" + maxPages);
		this.buttonList.add(new GuiButton(0, this.guiLeft + 17, this.guiTop + 30, 52, 20, "Metal"));
		this.buttonList.add(new GuiButton(1, this.guiLeft + 17, this.guiTop + 52, 52, 20, "Gem"));
		this.buttonList.add(new GuiButton(2, this.guiLeft + 17, this.guiTop + 74, 52, 20, "Item"));
		this.buttonList.add(new GuiButton(3, this.guiLeft + 17, this.guiTop + 96, 52, 20, "Modifiers"));
		this.buttonList.add(new GuiButton(4, this.guiLeft + 17, this.guiTop + 118, 52, 20, "Add Items"));
		this.buttonList.add(new GuiButton(5, this.guiLeft + 5, this.guiTop + 142, 13, 20, "<<"));
		this.buttonList.add(new GuiButton(6, this.guiLeft + 73, this.guiTop + 142, 13, 20, ">>"));
		((GuiButton) buttonList.get(enabled)).enabled = false;
	}

	public void drawScreen(int x, int y, float z) {
		super.drawScreen(x, y, z);
		int i = 0;
		List<String> list = new ArrayList<String>();
		for (ItemStack item : JewelrycraftUtil.objects) {
			try {
				if (item != null && item.getDisplayName() != null && (this.searchField.getText() == "" || item.getDisplayName().toLowerCase().contains(this.searchField.getText().toLowerCase()))) {
					if (i >= (page - 1) * 48 && i < page * 48 && x >= this.guiLeft + 88 + 20 * (i % 6) && x < this.guiLeft + 108 + 20 * (i % 6) && y >= this.guiTop + 9 + 17 * (i / 6) - 136 * (page - 1) && y < this.guiTop + 25 + 17 * (i / 6) - 136 * (page - 1)) {
						list.add(item.getDisplayName());
						if (item.getTooltip(mc.thePlayer, mc.gameSettings.advancedItemTooltips) != null) this.renderToolTip(item, x, y);
						else this.drawHoveringText(list, x, y, this.fontRendererObj);
					}
					i++;
				}
			} catch (Exception e) {
				JewelrycraftMod.logger.info("Trying to display an item but gets this error: " + e.getMessage());
			}
		}
	}
}
