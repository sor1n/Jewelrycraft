package darkknight.jewelrycraft.client.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.client.TabCurses;
import darkknight.jewelrycraft.client.TabRegistry;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.events.KeyBindings;
import darkknight.jewelrycraft.network.PacketSendClientPlayerInfo;
import darkknight.jewelrycraft.network.PacketSendServerPlayerInfo;
import darkknight.jewelrycraft.network.PacketSendServerPlayersInfo;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import scala.swing.event.Key;

public class GuiCurseInfo extends GuiContainer {
	World				world;
	EntityPlayer		player;
	ResourceLocation	texture;
	int					page			= 0, maxPages = 1;
	String				selectedCurse	= "";

	public GuiCurseInfo(Container container, World world, EntityPlayer player, ResourceLocation texture) {
		super(container);
		this.world = world;
		this.player = player;
		this.texture = texture;
		xSize = 214;
		ySize = 166;
	}

	public void drawScreen(int x, int y, float size) {
		super.drawScreen(x, y, size);
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor3f(1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		if (player != null) {
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			if (!player.capabilities.isCreativeMode) maxPages = playerInfo.getInteger("activeCurses") / 5 - (playerInfo.getInteger("activeCurses") % 5 == 0 ? 1 : 0);
			else maxPages = Curse.getCurseList().size() / 5 - (Curse.getCurseList().size() % 5 == 0 ? 1 : 0);
			if (!player.capabilities.isCreativeMode) survivalCurses(playerInfo);
			else creativeCurses(playerInfo);
			if (playerInfo.hasKey("cursePoints")) this.drawString(fontRendererObj, "Curse points: " + playerInfo.getInteger("cursePoints") + " | Active curses: " + playerInfo.getInteger("activeCurses"), guiLeft, guiTop - 10, 0xffffff);
		}
		this.drawString(fontRendererObj, page + "/" + maxPages, guiLeft + 90, guiTop + 153, 0xffffff);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft + 5, guiTop + ySize - 14, 0, ySize, 13, 11);
		drawTexturedModalRect(guiLeft + xSize - 38, guiTop + ySize - 14, 13, ySize, 13, 11);
	}

	private void survivalCurses(NBTTagCompound playerInfo) {
		int size = 32;
		int ind = 0;
		if (!playerInfo.hasNoTags()) {
			for (Curse curse : Curse.getCurseList()) {
				if (playerInfo.getInteger(curse.getName()) > 0) {
					if (ind >= page * 5 && ind < (page + 1) * 5) {
						if (ind == page * 5 && selectedCurse == "") selectedCurse = curse.getName();
						mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
						drawTexturedModalRect(guiLeft + 43, guiTop + 8 + (size - 8) * (ind - page * 5), 0, 32, 112, 22);
						if (selectedCurse == curse.getName()) {
							GL11.glPushMatrix();
							GL11.glScalef(1.2f, 1.5f, 0f);
							drawTexturedModalRect((int) (guiLeft / (1.2)) + 126, (int) (guiTop / (1.5)) + 7 + (size - 16) * (ind - page * 5), 48, 16, 32, 16);
							drawTexturedModalRect((int) (guiLeft / (1.2)) + 5, (int) (guiTop / (1.5)) + 7 + (size - 16) * (ind - page * 5), 80, 16, 32, 16);
							GL11.glPopMatrix();
							GL11.glPushMatrix();
							List descr = fontRendererObj.listFormattedStringToWidth(curse.getDescription(), 238);
							GL11.glScalef(0.75F, 0.75F, 0F);
							for (int i = 0; i < descr.size(); i++)
								this.drawString(fontRendererObj, descr.get(i).toString(), (int) (guiLeft / 0.75F) + 12, (int) (guiTop / 0.75F) + 184 + i * 12 - (descr.size() > 1 ? 6 : 0), 0xffffff);
							GL11.glPopMatrix();
						}
						if (playerInfo.getInteger(curse.getName()) == 2) {
							mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
							GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_BLEND);
							GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
							GL11.glColor4f(1f, 1f, 1f, 0.5f);
							drawTexturedModalRect(guiLeft + 134, guiTop + 11 + (size - 8) * (ind - page * 5), 3 * 16, 0, 16, 16);
							GL11.glDisable(GL11.GL_BLEND);
							GL11.glPopMatrix();
						}
						int halfDescrSize = fontRendererObj.getStringWidth(curse.getDescription()) / 2;
						mc.renderEngine.bindTexture(new ResourceLocation(Variables.MODID, "textures/gui/" + curse.getTexturePack() + ".png"));
						int tag = curse.getTextureID();
						GL11.glPushMatrix();
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						GL11.glDisable(GL11.GL_LIGHTING);
						GL11.glScalef(0.5f, 0.5f, 0.0f);
						drawTexturedModalRect(guiLeft * 2 + 100, guiTop * 2 + 22 + (ind - page * 5) * 48, tag % 8 * 32, tag / 8 * 32, 32, 32);
						GL11.glPopMatrix();
						this.drawString(fontRendererObj, curse.getDisplayName(), guiLeft + 70, guiTop + 15 + (ind - page * 5) * (size - 8), 0xffffff);
					}
					ind++;
				}
			}
		}
	}

	private void creativeCurses(NBTTagCompound playerInfo) {
		int size = 32;
		int ind = 0;
		for (Curse curse : Curse.getCurseList()) {
			if (ind >= page * 5 && ind < (page + 1) * 5) {
				if (ind == page * 5 && selectedCurse == "") selectedCurse = curse.getName();
				if (playerInfo.getInteger(curse.getName()) <= 0) {
					GL11.glPushMatrix();
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					if(!curse.canCurseBeActivated() || !ConfigHandler.CURSES_ENABLED) GL11.glColor4f(1f, 0f, 0f, 0.5f);
					else GL11.glColor4f(1f, 1f, 1f, 0.5f);
				}
				mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
				drawTexturedModalRect(guiLeft + 43, guiTop + 8 + (size - 8) * (ind - page * 5), 0, 32, 112, 22);
				if (selectedCurse == curse.getName()) {
					GL11.glPushMatrix();
					GL11.glScalef(1.2f, 1.5f, 0f);
					drawTexturedModalRect((int) (guiLeft / (1.2)) + 126, (int) (guiTop / (1.5)) + 7 + (size - 16) * (ind - page * 5), 48, 16, 32, 16);
					drawTexturedModalRect((int) (guiLeft / (1.2)) + 5, (int) (guiTop / (1.5)) + 7 + (size - 16) * (ind - page * 5), 80, 16, 32, 16);
					GL11.glPopMatrix();
					GL11.glPushMatrix();
					List descr = fontRendererObj.listFormattedStringToWidth(curse.getDescription(), 238);
					GL11.glScalef(0.75F, 0.75F, 0F);
					for (int i = 0; i < descr.size(); i++)
						this.drawString(fontRendererObj, descr.get(i).toString(), (int) (guiLeft / 0.75F) + 12, (int) (guiTop / 0.75F) + 184 + i * 12 - (descr.size() > 1 ? 6 : 0), 0xffffff);
					GL11.glPopMatrix();
				}
				int halfDescrSize = fontRendererObj.getStringWidth(curse.getDescription()) / 2;
				mc.renderEngine.bindTexture(new ResourceLocation(Variables.MODID, "textures/gui/" + curse.getTexturePack() + ".png"));
				int tag = curse.getTextureID();
				GL11.glPushMatrix();
				if (playerInfo.getInteger(curse.getName()) <= 0) GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
				else GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glScalef(0.5f, 0.5f, 0.0f);
				drawTexturedModalRect(guiLeft * 2 + 100, guiTop * 2 + 22 + (ind - page * 5) * 48, tag % 8 * 32, tag / 8 * 32, 32, 32);
				GL11.glPopMatrix();
				this.drawString(fontRendererObj, curse.getDisplayName(), guiLeft + 70, guiTop + 15 + (ind - page * 5) * (size - 8), playerInfo.getInteger(curse.getName()) > 0 ? 0xffffffff : 0xaaffffff);
				if (playerInfo.getInteger(curse.getName()) <= 0) {
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glPopMatrix();
				}
			}
			ind++;
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	}

	@Override
	protected void keyTyped(char charecter, int key) {
		super.keyTyped(charecter, key);
		if (key == KeyBindings.curses.getKeyCode()) mc.thePlayer.closeScreen();
	}

	@Override
	public void initGui() {
		super.initGui();
		int cornerX = guiLeft;
		int cornerY = guiTop;
		this.buttonList.clear();
		TabRegistry.updateTabValues(cornerX, cornerY, TabCurses.class);
		TabRegistry.addTabsToList(this.buttonList);
	}

	protected void mouseClicked(int x, int y, int id) {
		super.mouseClicked(x, y, id);
		int ind = 0;
		int size = 32;
		if (player != null) {
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			for (Curse curse : Curse.getCurseList()) {
				if (player.capabilities.isCreativeMode) {
					if (x > (guiLeft + 43) && y > (guiTop + 8 + (size - 8) * (ind - page * 5)) && x < (guiLeft + 43 + 112) && y < (guiTop + 40 + (size - 8) * (ind - page * 5)) && ind >= page * 5 && ind < (page + 1) * 5) {
						if (player.capabilities.isCreativeMode && isCtrlKeyDown() && playerInfo.getInteger(curse.getName()) > 0) {
							playerInfo.setInteger(curse.getName(), 0);
							playerInfo.setInteger("activeCurses", playerInfo.getInteger("activeCurses") - 1);
							Curse.availableCurses.add(curse);
							JewelrycraftMod.netWrapper.sendToServer(new PacketSendServerPlayerInfo("remove", curse.getName(), playerInfo));
							JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
						} else if (player.capabilities.isCreativeMode && isCtrlKeyDown() && playerInfo.getInteger(curse.getName()) <= 0 && curse.canCurseBeActivated() && ConfigHandler.CURSES_ENABLED) {
							playerInfo.setInteger(curse.getName(), 1);
							playerInfo.setInteger("activeCurses", playerInfo.getInteger("activeCurses") + 1);
							Curse.availableCurses.remove(curse);
							JewelrycraftMod.netWrapper.sendToServer(new PacketSendServerPlayerInfo("add", curse.getName(), playerInfo));
							JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
						} else selectedCurse = curse.getName();
					}
					ind++;
				} else if (playerInfo.getInteger(curse.getName()) > 0) {
					if (x > (guiLeft + 43) && y > (guiTop + 8 + (size - 8) * (ind - page * 5)) && x < (guiLeft + 43 + 112) && y < (guiTop + 40 + (size - 8) * (ind - page * 5)) && ind >= page * 5 && ind < (page + 1) * 5) selectedCurse = curse.getName();
					ind++;
				}
			}
		}
		if (page > 0 && x > guiLeft + 5 && x < guiLeft + 18 && y > guiTop + ySize - 14 && y < guiTop + ySize - 3) {
			page--;
			selectedCurse = "";
		}
		if (page < maxPages && x > guiLeft + xSize - 38 && x < guiLeft + xSize - 25 && y > guiTop + ySize - 14 && y < guiTop + ySize - 3) {
			page++;
			selectedCurse = "";
		}
	}
}