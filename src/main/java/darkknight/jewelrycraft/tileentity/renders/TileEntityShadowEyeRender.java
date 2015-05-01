package darkknight.jewelrycraft.tileentity.renders;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;
import darkknight.jewelrycraft.model.ModelShadowEye;
import darkknight.jewelrycraft.tileentity.TileEntityShadowEye;
import darkknight.jewelrycraft.util.Variables;

public class TileEntityShadowEyeRender extends TileEntitySpecialRenderer
{
    ModelShadowEye eye = new ModelShadowEye();
    Sphere shadow = new Sphere();
    
    /**
     * @param te
     * @param x
     * @param y
     * @param z
     * @param scale
     */
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 1.6F, (float)z + 0.5F);
        TileEntityShadowEye eyeS = (TileEntityShadowEye)te;
        String texture = "textures/tileentities/ShadowEye" + eyeS.opening + ".png";
        ResourceLocation blockTexture = new ResourceLocation(Variables.MODID, texture);
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        try{
            int block = te.getBlockMetadata();
            if (block == 0) GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            else if (block == 1){
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
            }else if (block == 2){
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
            }
        }
        catch(Exception e){}
        try{
            EntityPlayer player = te.getWorldObj().getClosestPlayer(te.xCoord, te.yCoord, te.zCoord, 16D);
            if (player != null) eye.render(player, te.xCoord, te.yCoord, te.zCoord, te.blockMetadata, eyeS.opening, 0.0625F);
        }
        catch(Exception e){
            eye.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        }
        if (eyeS.opening == 4){
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 1F);
            GL11.glRotatef(eyeS.timer*10F, 0, 1, 0);
            GL11.glRotatef(90.0F, 1, 0, 0);
            EntityPlayer player = te.getWorldObj().getClosestPlayer(te.xCoord, te.yCoord, te.zCoord, 9D);
            shadow.setNormals(GLU.GLU_NONE);
            shadow.draw(9.5F, 6, 6);
            GL11.glScalef(-1,-1,-1);
            shadow.draw(9.5F, 6, 6);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    /**
     * @param world
     * @param i
     * @param j
     * @param k
     * @param block
     */
    public void adjustLightFixture(World world, int i, int j, int k, Block block)
    {
        Tessellator tess = Tessellator.instance;
        float brightness = block.getLightOpacity(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, modulousModifier, divModifier);
    }
}
