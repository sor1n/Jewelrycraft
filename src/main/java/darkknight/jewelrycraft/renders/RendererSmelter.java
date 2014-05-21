package darkknight.jewelrycraft.renders;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RendererSmelter implements ISimpleBlockRenderingHandler
{
    public static int renderID;
    
    public static Tessellator t;
    public static float minU, minV, maxU, maxV;
    
    public RendererSmelter(int id)
    {
        renderID = id;
    }
    
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer)
    {
        
    }
    
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
    {
        t = Tessellator.instance;
        
        t.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        
        t.setColorOpaque_F(0.6F, 0.6F, 0.6F);
        
        bindTexture(Blocks.dirt.getIcon(1, 2));
        
        t.addVertexWithUV(x, y + 0.5F, z, minU, minV);
        t.addVertexWithUV(x, y, z, minU, maxV);
        t.addVertexWithUV(x, y, z + 1F, maxU, maxV);
        t.addVertexWithUV(x, y + 0.5F, z + 1F, maxU, minV);
        
        return true;
    }
    
    public void bindTexture(IIcon texture)
    {
        minU = texture.getMinU();
        minV = texture.getInterpolatedV(8);
        maxU = texture.getMaxU();
        maxV = texture.getMaxV();
    }
    
    @Override
    public boolean shouldRender3DInInventory(int modelId)
    {
        return false;
    }
    
    @Override
    public int getRenderId()
    {
        return renderID;
    }
}
