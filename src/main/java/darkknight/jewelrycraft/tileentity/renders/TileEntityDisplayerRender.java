package darkknight.jewelrycraft.tileentity.renders;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.model.ModelDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class TileEntityDisplayerRender extends TileEntitySpecialRenderer
{
    ModelDisplayer displayer = new ModelDisplayer();
    String texture = "textures/tileentities/Displayer.png";
    HashMap<EnumChatFormatting, Integer> colors = new HashMap<EnumChatFormatting, Integer>(){
        {
            put(EnumChatFormatting.AQUA, 5636095);
            put(EnumChatFormatting.BLACK, 0);
            put(EnumChatFormatting.BLUE, 5592575);
            put(EnumChatFormatting.DARK_AQUA, 43690);
            put(EnumChatFormatting.DARK_BLUE, 170);
            put(EnumChatFormatting.DARK_GRAY, 5592405);
            put(EnumChatFormatting.DARK_GREEN, 43520);
            put(EnumChatFormatting.DARK_PURPLE, 11141290);
            put(EnumChatFormatting.DARK_RED, 11141120);
            put(EnumChatFormatting.GOLD, 16755200);
            put(EnumChatFormatting.GRAY, 11184810);
            put(EnumChatFormatting.GREEN, 5635925);
            put(EnumChatFormatting.LIGHT_PURPLE, 16733695);
            put(EnumChatFormatting.RED, 16733525);
            put(EnumChatFormatting.WHITE, 16777215);
            put(EnumChatFormatting.YELLOW, 16777045);
        }
    };
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        TileEntityDisplayer disp = (TileEntityDisplayer)te;
        ResourceLocation blockTexture = new ResourceLocation(Variables.MODID, texture);
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        displayer.render((Entity)null, disp.ringTranslation1, disp.ringTranslation2, disp.ringTranslation3, 0.0F, 0.0F, 0.0625F);
        try{
            int block = disp.getBlockMetadata();
            if (disp != null && disp.hasObject && disp.object != null && disp.object.getItem() != null && disp.object != new ItemStack(Item.getItemById(0), 0, 0)){
                int ind = -3;
                GL11.glPushMatrix();
                EntityItem entityitem = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, disp.object);
                entityitem.hoverStart = 0.0F;
                disp.object.stackSize = 1;
                GL11.glRotatef(180F, 1F, 0F, 0F);
                GL11.glTranslatef(0.0F, -0.6F + disp.ringTranslation1 / 5, 0F);
                GL11.glRotatef(disp.rotAngle, 0F, 1F, 0F);
                GL11.glColor4f(1F, 1F, 1F, 1F);
                if (RenderManager.instance.options.fancyGraphics) RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                else{
                    GL11.glRotatef(180F, 0F, 1F, 0F);
                    RenderManager.instance.options.fancyGraphics = true;
                    int i = 15728880;
                    int j = i % 65536;
                    int k = i / 65536;
                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
                    RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    RenderManager.instance.options.fancyGraphics = false;
                }
                EntityPlayer player = te.getWorldObj().getClosestPlayer(te.xCoord, te.yCoord, te.zCoord, 5D);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                if (player != null) renderLabel(disp.object.getDisplayName(), 0F, -0.171F * ind, 0F, block, disp, colors.get(disp.object.getRarity().rarityColor));
                GL11.glPopMatrix();
                ind++;
                if (player != null && disp.quantity > 1){
                    GL11.glPushMatrix();
                    renderLabel("x" + Integer.toString(disp.quantity), 0F, -0.171F * ind, 0F, block, disp, Color.GRAY.getRGB());
                    GL11.glPopMatrix();
                    ind++;
                }
                if (disp.object.getItem() != Items.map && player != null && disp.object.getTooltip(player, true) != null){
                    List tooltips = disp.object.getTooltip(player, true);
                    if (disp.infoIndex + 5 > tooltips.size()) disp.infoIndex = 1;
                    if (tooltips.size() < 5) for(int i = 1; i < tooltips.size(); i++){
                        String tooltip = tooltips.get(i).toString();
                        RenderManager.instance.getFontRenderer();
                        if (tooltip != ""){
                            GL11.glPushMatrix();
                            renderLabel(tooltip, 0F, -0.171F * ind, 0F, block, disp, Color.GRAY.getRGB());
                            GL11.glPopMatrix();
                            ind++;
                        }
                    }
                    else for(int i = disp.infoIndex; i < disp.infoIndex + 5; i++){
                        String tooltip = tooltips.get(i).toString();
                        RenderManager.instance.getFontRenderer();
                        if (tooltip != ""){
                            GL11.glPushMatrix();
                            renderLabel(tooltip, 0F, -0.171F * ind, 0F, block, disp, Color.GRAY.getRGB());
                            GL11.glPopMatrix();
                            ind++;
                        }
                    }
                }
            }
        }
        catch(Exception e){}
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    protected void renderLabel(String text, double x, double y, double z, int metadata, TileEntity te, int color)
    {
        FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
        float width = 0.015F, height = 1F;
        GL11.glRotatef(180F, 0F, 0F, 1F);
        GL11.glRotatef(360 - metadata * 90F, 0F, 1F, 0F);
        GL11.glTranslatef((float)x, (float)y, (float)z + 0.45F);
        GL11.glScalef(-0.015F, -0.019F, 0.015F);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int j = fontrenderer.getStringWidth(text) / 2;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.275F, 0.145F, 0.431F, 0.5F);
        tessellator.addVertex(-33.333 - 0, 0D, 0.1D);
        tessellator.addVertex(-33.333 - 0, 9D, 0.1D);
        tessellator.addVertex(33.333 + 0, 9D, 0.1D);
        tessellator.addVertex(33.333 + 0, 0D, 0.1D);
        tessellator.draw();
        if (fontrenderer.getStringWidth(text) / 2 > 20){
            width = 0.9F / fontrenderer.getStringWidth(text);
            height = 1F - fontrenderer.getStringWidth(text)/20*0.06F;
        }
        GL11.glTranslatef((float)x + 1f, (float)y + 1f, (float)z);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glScalef(width * 70F, height, 0F);
        fontrenderer.drawString(text.replaceFirst("§0", "§r").replaceFirst("§1", "§r").replaceFirst("§2", "§r").replaceFirst("§3", "§r").replaceFirst("§4", "§r").replaceFirst("§5", "§r").replaceFirst("§6", "§r").replaceFirst("§7", "§r").replaceFirst("§8", "§r").replaceFirst("§9", "§r").replaceFirst("§a", "§r").replaceFirst("§b", "§r").replaceFirst("§c", "§r").replaceFirst("§d", "§r").replaceFirst("§e", "§r").replaceFirst("§f", "§r"), -j, 0, 1973790);
        GL11.glPopMatrix();
        GL11.glTranslatef((float)x - 0.5f, (float)y - 0.5f, (float)z - 0.5F);
        GL11.glScalef(width * 70F, height, 0F);
        fontrenderer.drawString(text, -j, 0, color);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}