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
import darkknight.jewelrycraft.util.Variables;

public class ScreenHandler extends Gui
{
    private Minecraft mc;
    public static NBTTagCompound tagCache = null;
    public static int cooldown;
    
    public ScreenHandler(Minecraft mc)
    {
        super();
        this.mc = mc;
    }
    
    @SubscribeEvent
    public void renderScreen(RenderGameOverlayEvent event)
    {
        if (event.isCancelable() || event.type != ElementType.ALL || tagCache == null) return;
        if (!mc.gameSettings.showDebugInfo && !(mc.currentScreen instanceof GuiChat)){
            int count = 0;
            int size = 32;
            ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            if (tagCache.hasKey("cursePoints") && tagCache.getInteger("cursePoints") > 0){
                mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
                for(Curse curse: Curse.getCurseList()){
                    if (tagCache.hasKey(curse.getName()) && tagCache.getInteger(curse.getName()) > 0){
                        drawTexturedModalRect(-16, -16 + (size - 6) * count, 0, 32, 144, 60);
                        count++;
                    }
                }
                count = 0;
                for(Curse curse: Curse.getCurseList())
                    if (tagCache.hasKey(curse.getName()) && tagCache.getInteger(curse.getName()) > 0){
                        mc.renderEngine.bindTexture(new ResourceLocation(Variables.MODID, "textures/gui/" + curse.getTexturePack() + ".png"));
                        int tag = curse.getTextureID();
                        GL11.glPushMatrix();
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glScalef(0.45f, 0.45f, 0.0f);
                        drawTexturedModalRect(28, 18 + (size + 26) * count, tag % 8 * size, tag / 8 * size, size, size);
                        GL11.glPopMatrix();
                        count++;
                    }
                count = 0;
                size = 16;
                for(Curse curse: Curse.getCurseList())
                    if (tagCache.hasKey(curse.getName()) && tagCache.getInteger(curse.getName()) > 0){
                        mc.fontRenderer.drawStringWithShadow(curse.getName().split(":")[1], 30, 11 + (size + 10) * count, 16777215);
                        if (tagCache.getInteger(curse.getName()) == 2){
                            mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
                            GL11.glPushMatrix();
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                            GL11.glColor4f(1f, 1f, 1f, 0.5f);
                            drawTexturedModalRect(95, 7 + (size + 10) * count, 3 * size, 0, size, size);
                            GL11.glPopMatrix();
                        }
                        count++;
                    }
            }
            GL11.glPushMatrix();
            GL11.glColor4f(1f, 1f, 1f, 1.0f);
            mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
            count = 0;
            size = 16;
            if (tagCache.getFloat("BlueHeart") > 0){
                    drawTexturedModalRect(5 + 35*count, resolution.getScaledHeight() - 20, 0 * size, 0 * size, size, size);
                    mc.fontRenderer.drawStringWithShadow("x" + (MathHelper.ceiling_float_int(tagCache.getFloat("BlueHeart")) / 2.0F), 15 + 35*count, resolution.getScaledHeight() - 16, 16777215);
            }
            count++;
            mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
            if (tagCache.getFloat("BlackHeart") > 0){
                    drawTexturedModalRect(5 + 35*count, resolution.getScaledHeight() - 20, 1 * size, 0 * size, size, size);
                    mc.fontRenderer.drawStringWithShadow("x" + (MathHelper.ceiling_float_int(tagCache.getFloat("BlackHeart")) / 2.0F), 15 + 35*count, resolution.getScaledHeight() - 16, 16777215);
            }
            count++;
            mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
            if (tagCache.getFloat("WhiteHeart") > 0)
                drawTexturedModalRect(5 + 35*count, resolution.getScaledHeight() - 20, 2 * size, 1 * size, size, size);
            GL11.glPopMatrix();
        }
    }
}