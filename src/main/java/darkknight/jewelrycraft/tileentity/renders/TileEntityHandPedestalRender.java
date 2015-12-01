package darkknight.jewelrycraft.tileentity.renders;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.model.ModelHandPedestal;
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * @author Paul Fulham (pau101)
 */
public class TileEntityHandPedestalRender extends TileEntitySpecialRenderer
{
    private ModelHandPedestal model;
    private ResourceLocation texture;
    
    /**
     * @param model
     * @param texture
     */
    public TileEntityHandPedestalRender(ModelHandPedestal model, ResourceLocation texture)
    {
        this.model = model;
        this.texture = texture;
    }
    
    /**
     * @param te
     * @param x
     * @param y
     * @param z
     * @param partialRenderTicks
     */
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialRenderTicks)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        TileEntityHandPedestal pedestal = (TileEntityHandPedestal)te;
        bindTexture(texture);
        GL11.glRotatef(180, 0, 0, 1);
        GL11.glRotatef(pedestal.getWorldObj() == null ? 180 : pedestal.getBlockMetadata() % 8 / 8F * 360, 0, 1, 0);
        model.render(pedestal, partialRenderTicks, 0.0625F);
        GL11.glPopMatrix();
    }
}