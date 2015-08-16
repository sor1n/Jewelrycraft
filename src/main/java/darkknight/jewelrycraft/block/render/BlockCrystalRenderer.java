/**
 * 
 */
package darkknight.jewelrycraft.block.render;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.block.BlockCrystal;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.proxy.ClientProxy.BlockRenderIDs;
import darkknight.jewelrycraft.tileentity.TileEntityCrystal;

/**
 * @author Sorin
 */
@SideOnly (Side.CLIENT)
public class BlockCrystalRenderer implements ISimpleBlockRenderingHandler
{
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
    {
        Tessellator tessellator = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        tessellator.startDrawingQuads();
        if (metadata < 16) tessellator.setColorRGBA_I(((BlockCrystal)block).colors[metadata], 100);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        this.renderWorldBlock(null, 0, 0, 0, block, modelID, renderer);
        tessellator.draw();
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
    
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        int crystals = 5;
        Random rnd = new Random();
        Tessellator tessellator = Tessellator.instance;
        IIcon icon = ((BlockCrystal)block).getIcon(0, 0);
        double umin = icon.getMinU();
        double vmin = icon.getMinV();
        double umax = icon.getMaxU();
        double vmax = icon.getMaxV();
        tessellator.addTranslation(x, y, z);
        if (world != null && world.getTileEntity(x, y, z) != null && ConfigHandler.CRYSTAL_GLOW) tessellator.setBrightness(((TileEntityCrystal)world.getTileEntity(x, y, z)).shine);
        else tessellator.setBrightness(230);
        // 37435, 76464
        if (world != null) rnd.setSeed(2 * x * 3 * y * z * 5);
        else rnd.setSeed(76464);
        if (world != null && world.getBlockMetadata(x, y, z) < 16) tessellator.setColorRGBA_I(((BlockCrystal)block).colors[world.getBlockMetadata(x, y, z)], 100);
        crystal(tessellator, umin, vmin, umax, vmax, 0.8D, 0.2D, 0D, 0D, 0D, 0.0D, 0.0D);
        for(int i = 0; i < crystals; i++){
            double rotation = Math.PI * 2.0f / (float)crystals * (float)i;
            double xp1 = Math.sin(rotation) * ((0.15F + rnd.nextFloat())/2f);
            double zp1 = Math.cos(rotation) * ((0.15F + rnd.nextFloat())/2f);
            double height = 0.2f + rnd.nextFloat();
            double topHeight = 0.1f + rnd.nextFloat() / 2f;
            crystal(tessellator, umin, vmin, umax, vmax, height, topHeight, -xp1, 0D, zp1, xp1, zp1);
        }
        if (world != null){
            crystals = 4;
            for(int i = 0; i < crystals; i++){
                double rotation = Math.PI * 2F / (float)crystals * (float)i;
                double xp1 = Math.sin(rotation) * ((0.15F + rnd.nextFloat())/2f);
                double zp1 = Math.cos(rotation) * ((0.15F + rnd.nextFloat())/2f);
                double height = 0.1f + rnd.nextFloat();
                double topHeight = 0.1f + rnd.nextFloat() / 2f;
                float xOff = (rnd.nextFloat() * 1.5f - 0.5f) * (rnd.nextFloat()/2f);
                float zOff = (rnd.nextFloat() * 1.5f - 0.5f) * (rnd.nextFloat()/2f);
                crystal(tessellator, umin, vmin, umax, vmax, height, topHeight, xOff, 0D, zOff, xp1, zp1);
            }
        }
        tessellator.addTranslation(-x, -y, -z);
        return true;
    }
    
    private void crystal(Tessellator tessellator, double umin, double vmin, double umax, double vmax, double height, double topHeight, double posX, double posY, double posZ, double rotX, double rotZ)
    {
        // Negative X
        tessellator.addVertexWithUV(0.4 + rotX + posX, 0.0 + posY, 0.6 - rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.4 - rotX + posX, height + posY, 0.6 + rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.4 - rotX + posX, height + posY, 0.4 + rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.4 + rotX + posX, 0.0 + posY, 0.4 - rotZ + posZ, umax, vmin);
        // Positive X
        tessellator.addVertexWithUV(0.6 + rotX + posX, 0.0 + posY, 0.4 - rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.6 - rotX + posX, height + posY, 0.4 + rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.6 - rotX + posX, height + posY, 0.6 + rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.6 + rotX + posX, 0.0 + posY, 0.6 - rotZ + posZ, umax, vmin);
        // Negative Z
        tessellator.addVertexWithUV(0.4 + rotX + posX, 0.0 + posY, 0.4 - rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.4 - rotX + posX, height + posY, 0.4 + rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.6 - rotX + posX, height + posY, 0.4 + rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.6 + rotX + posX, 0.0 + posY, 0.4 - rotZ + posZ, umax, vmin);
        // Positive Z
        tessellator.addVertexWithUV(0.6 + rotX + posX, 0.0 + posY, 0.6 - rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.6 - rotX + posX, height + posY, 0.6 + rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.4 - rotX + posX, height + posY, 0.6 + rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.4 + rotX + posX, 0.0 + posY, 0.6 - rotZ + posZ, umax, vmin);
        // Top -X
        tessellator.addVertexWithUV(0.4 - rotX + posX, height + posY, 0.6 + rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.5 - rotX - rotX + posX, height + topHeight + posY, 0.5 + rotZ + rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.5 - rotX - rotX + posX, height + topHeight + posY, 0.5 + rotZ + rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.4 - rotX + posX, height + posY, 0.4 + rotZ + posZ, umax, vmin);
        // Top +X
        tessellator.addVertexWithUV(0.6 - rotX + posX, height + posY, 0.4 + rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.5 - rotX - rotX + posX, height + topHeight + posY, 0.5 + rotZ + rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.5 - rotX - rotX + posX, height + topHeight + posY, 0.5 + rotZ + rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.6 - rotX + posX, height + posY, 0.6 + rotZ + posZ, umax, vmin);
        // Top +Z
        tessellator.addVertexWithUV(0.6 - rotX + posX, height + posY, 0.6 + rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.5 - rotX - rotX + posX, height + topHeight + posY, 0.5 + rotZ + rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.5 - rotX - rotX + posX, height + topHeight + posY, 0.5 + rotZ + rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.4 - rotX + posX, height + posY, 0.6 + rotZ + posZ, umax, vmin);
        // Top -Z
        tessellator.addVertexWithUV(0.4 - rotX + posX, height + posY, 0.4 + rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.5 - rotX - rotX + posX, height + topHeight + posY, 0.5 + rotZ + rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.5 - rotX - rotX + posX, height + topHeight + posY, 0.5 + rotZ + rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.6 - rotX + posX, height + posY, 0.4 + rotZ + posZ, umax, vmin);
        // Bottom
        tessellator.addVertexWithUV(0.4 + rotX + posX, 0.0 + posY, 0.6 - rotZ + posZ, umin, vmin);
        tessellator.addVertexWithUV(0.4 + rotX + posX, 0.0 + posY, 0.4 - rotZ + posZ, umin, vmax);
        tessellator.addVertexWithUV(0.6 + rotX + posX, 0.0 + posY, 0.4 - rotZ + posZ, umax, vmax);
        tessellator.addVertexWithUV(0.6 + rotX + posX, 0.0 + posY, 0.6 - rotZ + posZ, umax, vmin);
    }
    
    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return true;
    }
    
    @Override
    public int getRenderId()
    {
        return BlockRenderIDs.CRYSTAL.id();
    }
}
