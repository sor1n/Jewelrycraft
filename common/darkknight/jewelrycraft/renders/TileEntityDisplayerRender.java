package darkknight.jewelrycraft.renders;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.model.ModelDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayer;

public class TileEntityDisplayerRender extends TileEntitySpecialRenderer
{
    ModelDisplayer displayer = new ModelDisplayer();
    String texture = "textures/tileentities/Displayer.png";

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        TileEntityDisplayer disp = (TileEntityDisplayer)te;
        int block = disp.getBlockMetadata();

        ResourceLocation blockTexture = new ResourceLocation("jewelrycraft", texture);
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        displayer.render((Entity) null, disp.ringTranslation1, disp.ringTranslation2, disp.ringTranslation3, 0.0F, 0.0F, 0.0625F);
        if(disp != null && disp.hasObject && disp.object != null && disp.object != new ItemStack(0, 0, 0))
        {
            int ind = -3;
            GL11.glPushMatrix();
            renderLabel(disp.object.getDisplayName(), 0F, (-0.171F)*ind, 0F, block, disp, Color.YELLOW.getRGB());
            GL11.glPopMatrix();
            ind++;
            GL11.glPushMatrix();
            renderLabel(Integer.toString(disp.quantity), 0F, (-0.171F)*ind, 0F, block, disp, Color.GRAY.getRGB());
            GL11.glPopMatrix();
            ind++;
            EntityPlayer player = new FakePlayer(te.worldObj, "Player");
            if(disp.object.itemID != Item.map.itemID && disp.object != null && disp.object != new ItemStack(0, 0, 0) && disp.object.getTooltip(player, true) != null)
            {
                for(int i = 1; i < disp.object.getTooltip(player, true).size(); i++)
                {
                    if(disp.object.getTooltip(player, true).get(i).toString() != "")
                    {
                        GL11.glPushMatrix();
                        renderLabel(disp.object.getTooltip(player, true).get(i).toString(), 0F, (-0.171F)*ind, 0F, block, disp, Color.GRAY.getRGB());
                        GL11.glPopMatrix();
                        ind++;
                    }
                }
            }
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            EntityItem entityitem = new EntityItem(te.worldObj, 0.0D, 0.0D, 0.0D, disp.object);
            entityitem.hoverStart = 0.0F;
            disp.object.stackSize = 1;
            GL11.glRotatef(180F, 1F, 0F, 0F);
            GL11.glTranslatef(0.0F, -0.6F + disp.ringTranslation1/5, 0F);
            GL11.glRotatef(disp.rotAngle, 0F, 1F, 0F);
            if(RenderManager.instance.options.fancyGraphics)
                RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            else
            {
                GL11.glRotatef(180F, 0F, 1F, 0F);
                RenderManager.instance.options.fancyGraphics = true;
                RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderManager.instance.options.fancyGraphics = false;
            }
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public void adjustLightFixture(World world, int i, int j, int k, Block block)
    {
        Tessellator tess = Tessellator.instance;
        float brightness = block.getBlockBrightness(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
    }

    protected void renderLabel(String par2Str, double x, double y, double z, int metadata, TileEntity te, int color)
    {
        FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
        float var14 = 0.01266667F * 1.5F;
        float var17 = 0.015F;
        GL11.glRotatef(180F, 0F, 0F, 1F);
        if(metadata == 0) GL11.glRotatef(0F, 0F, 1F, 0F);
        else if(metadata == 1) GL11.glRotatef(270F, 0F, 1F, 0F);
        else if(metadata == 2) GL11.glRotatef(180F, 0F, 1F, 0F);
        else if(metadata == 3) GL11.glRotatef(90F, 0F, 1F, 0F);
        GL11.glTranslatef((float)x, (float)y, (float)z + 0.45F);
        GL11.glScalef(-0.015F, -var14, 0.015F);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        int j = fontrenderer.getStringWidth(par2Str) / 2;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(0.0F, 0.2F, 0.2F, 0.9F);
        tessellator.addVertex((double)(-33.333 - 0), -1D, 0.1D);
        tessellator.addVertex((double)(-33.333 - 0), 8D, 0.1D);
        tessellator.addVertex((double)(33.333 + 0), 8D, 0.1D);
        tessellator.addVertex((double)(33.333 + 0), -1D, 0.1D);
        tessellator.draw();
        if ((fontrenderer.getStringWidth(par2Str)/2) > 20) var17 = 0.9F / fontrenderer.getStringWidth(par2Str); 
        else var17 = var14;
        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = color & 0xFF;
        GL11.glTranslatef((float)x + 1f, (float)y + 1f, (float)z);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);     
        GL11.glScalef(var17*70F, 1F, 0F);
        fontrenderer.drawString(par2Str.replaceFirst("§0", "§r").replaceFirst("§1", "§r").replaceFirst("§2", "§r").replaceFirst("§3", "§r").replaceFirst("§4", "§r").replaceFirst("§5", "§r").replaceFirst("§6", "§r").replaceFirst("§7", "§r").replaceFirst("§8", "§r").replaceFirst("§9", "§r").replaceFirst("§a", "§r").replaceFirst("§b", "§r").replaceFirst("§c", "§r").replaceFirst("§d", "§r").replaceFirst("§e", "§r").replaceFirst("§f", "§r"), -j, 0, 65536 * (red/2) + 256 * (green/2) + blue/2);
        GL11.glPopMatrix();
        GL11.glTranslatef((float)x - 1f, (float)y - 1f, (float)z - 1F);   
        GL11.glScalef(var17*70F, 1F, 0F);
        fontrenderer.drawString(par2Str, -j, 0, color);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
    
    public void replaceEnumEnchValues(String str, int color)
    {
        if(str.contains("§0"))
        {
            color = Color.BLACK.getRGB();
            str.replace("§0", "");
        }
        if(str.contains("§1"))
        {
            color = 85;
            str.replace("§1", "");
        }
        if(str.contains("§2"))
        {
            color = 17920;
            str.replace("§2", "");
        }
        if(str.contains("§3"))
        {
            color = 1336183;
            str.replace("§3", "");
        }
        if(str.contains("§4"))
        {
            color = 4587520;
            str.replace("§4", "");
        }
        if(str.contains("§5"))
        {
            color = 5701759;
            str.replace("§5", "");
        }
        if(str.contains("§6"))
        {
            color = 16762880;
            str.replace("§6", "");
        }
        if(str.contains("§7"))
        {
            color = Color.GRAY.getRGB();
            str.replace("§7", "");
        }
        if(str.contains("§8"))
        {
            color = Color.DARK_GRAY.getRGB();
            str.replace("§8", "");
        }
        if(str.contains("§9"))
        {
            color = Color.BLUE.getRGB();
            str.replace("§9", "");
        }
        if(str.contains("§a"))
        {
            color = Color.GREEN.getRGB();
            str.replace("§a", "");
        }
        if(str.contains("§b"))
        {
            color = Color.CYAN.getRGB();
            str.replace("§b", "");
        }
        if(str.contains("§c"))
        {
            color = Color.RED.getRGB();
            str.replace("§c", "");
        }
        if(str.contains("§d"))
        {
            color = 11665663;
            str.replace("§d", "");
        }
        if(str.contains("§e"))
        {
            color = Color.YELLOW.getRGB();
            str.replace("§e", "");
        }
        if(str.contains("§f"))
        {
            color = Color.WHITE.getRGB();
            str.replace("§f", "");
        }
    }
}