package darkknight.jewelrycraft.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.Variables;

public class ScreenHandler extends Gui {
	private Minecraft				mc;
	public static NBTTagCompound	tagCache	= null;
	public static int				cooldown;

	public ScreenHandler(Minecraft mc) {
		super();
		this.mc = mc;
	}

	@SubscribeEvent
	public void renderScreen(RenderGameOverlayEvent event) {
		if (event.isCancelable() || event.type != ElementType.ALL || tagCache == null) return;
		int count = 0;
		int size = 32;
		ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		GL11.glPushMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1.0f);
		mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
		count = 0;
		size = 16;
		if (tagCache.getFloat("BlueHeart") > 0) {
			drawTexturedModalRect(resolution.getScaledWidth() / 2 + 90 + 35 * count, resolution.getScaledHeight() - 20, 0 * size, 0 * size, size, size);
			mc.fontRenderer.drawStringWithShadow("x" + (MathHelper.ceiling_float_int(tagCache.getFloat("BlueHeart")) / 2.0F), resolution.getScaledWidth() / 2 + 105 + 35 * count, resolution.getScaledHeight() - 16, 16777215);
		}
		count++;
		mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
		if (tagCache.getFloat("BlackHeart") > 0) {
			drawTexturedModalRect(resolution.getScaledWidth() / 2 + 90 + (mc.fontRenderer.getStringWidth(String.valueOf((MathHelper.ceiling_float_int(tagCache.getFloat("BlueHeart")) / 2.0F))) - 14) + 35 * count, resolution.getScaledHeight() - 20, 1 * size, 0 * size, size, size);
			mc.fontRenderer.drawStringWithShadow("x" + (MathHelper.ceiling_float_int(tagCache.getFloat("BlackHeart")) / 2.0F), resolution.getScaledWidth() / 2 + 105 + (mc.fontRenderer.getStringWidth(String.valueOf((MathHelper.ceiling_float_int(tagCache.getFloat("BlueHeart")) / 2.0F))) - 14) + 35 * count, resolution.getScaledHeight() - 16, 16777215);
		}
		count++;
		mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
		if (tagCache.getFloat("WhiteHeart") > 0) drawTexturedModalRect(resolution.getScaledWidth() / 2 + 90 + (mc.fontRenderer.getStringWidth(String.valueOf((MathHelper.ceiling_float_int(tagCache.getFloat("BlueHeart")) / 2.0F))) - 14) + (mc.fontRenderer.getStringWidth(String.valueOf((MathHelper.ceiling_float_int(tagCache.getFloat("BlackHeart")) / 2.0F))) - 14) + 35 * count, resolution.getScaledHeight() - 20, 2 * size, 1 * size, size, size);
		GL11.glPopMatrix();
	}
}