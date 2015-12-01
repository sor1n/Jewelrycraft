package darkknight.jewelrycraft.item.render;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.model.ModelRing;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RingRender extends TileEntitySpecialRenderer
{
    public ModelRing ring = new ModelRing();
    ResourceLocation texture = new ResourceLocation(Variables.MODID, "textures/entities/Ring.png");
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
    }
    
    public void doRender(Entity entity, double x, double y, double z, float f, float g)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        if ((float)z != -1) ring.render(entity, 0F, 0F, 0F, (float)z, f, 1.0F);
        GL11.glPopMatrix();
    }
}