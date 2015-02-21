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
import darkknight.jewelrycraft.model.ModelShadowEye;
import darkknight.jewelrycraft.tileentity.TileEntityShadowEye;

public class TileEntityShadowEyeRender extends TileEntitySpecialRenderer
{
    ModelShadowEye eye = new ModelShadowEye();
    
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
        ResourceLocation blockTexture = new ResourceLocation("jewelrycraft", texture);
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
