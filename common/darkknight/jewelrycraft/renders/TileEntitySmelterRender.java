package darkknight.jewelrycraft.renders;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.model.ModelSmelter;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntitySmelterRender extends TileEntitySpecialRenderer
{
    ModelSmelter modelSmelter = new ModelSmelter();
    String texture = "textures/tileentities/Smelter.png", lava = "texture/blocks/lava_still.png";

    @Override
    public void renderTileEntityAt (TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        ResourceLocation blockTexture = new ResourceLocation("jewelrycraft", texture);
        Tessellator tessellator = Tessellator.instance;
        ResourceLocation lava = new ResourceLocation(null, "textures/blocks/lava_still.png");
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);
        int block = te.getBlockMetadata();

        GL11.glPushMatrix();
        if(block == 0) GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        else if(block == 1) GL11.glRotatef(180F, 1F, 0.0F, 1F);
        else if(block == 2) GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        else if(block == 3) GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
        
        modelSmelter.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(lava);
        Block.lavaStill.getIcon(3, 0).getInterpolatedU(0);
        double minu = Block.lavaStill.getIcon(3, 0).getInterpolatedU(0);
        double minv = Block.lavaStill.getIcon(3, 0).getInterpolatedV(((TileEntitySmelter)te).flow);
        double maxu = Block.lavaStill.getIcon(3, 0).getInterpolatedU(256);
        double maxv = Block.lavaStill.getIcon(3, 0).getInterpolatedV(16 + ((TileEntitySmelter)te).flow);
        GL11.glPushMatrix();
        GL11.glScalef(1f/16f, 1f/16f, 1f/16f);
        GL11.glDisable(GL11.GL_LIGHTING);
        
        tessellator.startDrawingQuads();        
        tessellator.addVertexWithUV(5, 20, 6, minu, minv);
        tessellator.addVertexWithUV(-5, 20, 6, maxu, minv);
        tessellator.addVertexWithUV(-5, 20, -6, maxu, maxv);
        tessellator.addVertexWithUV(5, 20, -6, minu, maxv);

        tessellator.addVertexWithUV(-4, 20, -7, maxu, maxv);
        tessellator.addVertexWithUV(4, 20, -7, maxu, minv);
        tessellator.addVertexWithUV(4, 20, -6, minu, minv);
        tessellator.addVertexWithUV(-4, 20, -6, minu, maxv);

        tessellator.addVertexWithUV(4, 20, 7, maxu, maxv);
        tessellator.addVertexWithUV(-4, 20, 7, maxu, minv);
        tessellator.addVertexWithUV(-4, 20, 6, minu, minv);
        tessellator.addVertexWithUV(4, 20, 6, minu, maxv);
        tessellator.draw();
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public void adjustLightFixture (World world, int i, int j, int k, Block block)
    {
        Tessellator tess = Tessellator.instance;
        float brightness = block.getBlockBrightness(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
    }


}
