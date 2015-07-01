package darkknight.jewelrycraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.client.TabCurses;
import darkknight.jewelrycraft.client.TabRegistry;
import darkknight.jewelrycraft.events.KeyBindings;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

public class GuiCurseInfo extends GuiContainer {
	World				world;
	EntityPlayer		player;
	ResourceLocation	texture;

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
		// this.drawGradientRect(0, 0, this.width, this.height, -1072689136,
		// -804253680);
		// int ind = 0;
		// if (player != null){
		// NBTTagCompound playerInfo =
		// PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
		// if (!playerInfo.hasNoTags()){
		// for(Curse curse: Curse.getCurseList())
		// if (playerInfo.getInteger(curse.getName()) > 0){
		// int halfDescrSize =
		// fontRendererObj.getStringWidth(curse.getDescription()) / 2;
		// mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
		// this.drawRect(0, 12 + ind * 34, this.width, 10 + (ind + 1) * 34,
		// 0xff000000);
		// mc.renderEngine.bindTexture(new ResourceLocation(Variables.MODID,
		// "textures/gui/" + curse.getTexturePack() + ".png"));
		// int tag = curse.getTextureID();
		// GL11.glColor3f(1F, 1F, 1F);
		// drawTexturedModalRect(this.width/2 - halfDescrSize - 35, 12 + ind *
		// 34, tag % 8 * 32, tag / 8 * 32, 32, 32);
		// this.drawString(fontRendererObj,
		// curse.getName().substring(curse.getName().indexOf(':') + 1),
		// this.width/2 - halfDescrSize, 20 + ind * 34, 0xffff00);
		// this.drawCenteredString(fontRendererObj, curse.getDescription(),
		// this.width/2, 30 + ind * 34, 0xffffff);
		// ind++;
		// }
		// }
		// }
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor3f(1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int ind = 0;
		int size = 32;
		if (player != null) {
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			if (!playerInfo.hasNoTags()) {
				for (Curse curse : Curse.getCurseList())
					if (playerInfo.getInteger(curse.getName()) > 0) {
						mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
						drawTexturedModalRect(guiLeft + 43, guiTop + 5 + (size - 8) * ind, 0, 32, 112, 22);
						if (playerInfo.getInteger(curse.getName()) == 1) {
							GL11.glPushMatrix();
							GL11.glEnable(GL11.GL_BLEND);
							GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
							GL11.glColor4f(1f, 1f, 1f, 0.5f);
							drawTexturedModalRect(guiLeft + 134, guiTop + 8 + (size - 8) * ind, 3 * 16, 0, 16, 16);
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
						drawTexturedModalRect(guiLeft*2 + 100, guiTop*2 + 16 + ind * 48, tag % 8 * 32, tag / 8 * 32, 32, 32);
						GL11.glPopMatrix();
						this.drawString(fontRendererObj, curse.getDisplayName(), guiLeft + 70, guiTop + 12 + ind * (size - 8), 0xffffff);
						// this.drawCenteredString(fontRendererObj,
						// curse.getDescription(), this.width/2, 30 + ind * 34,
						// 0xffffff);
						ind++;
					}
			}
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
	}
	
    @Override
    protected void keyTyped(char charecter, int key)
    {
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
	}
}