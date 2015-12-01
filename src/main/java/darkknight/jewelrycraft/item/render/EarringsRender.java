package darkknight.jewelrycraft.item.render;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.model.ModelEarrings;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class EarringsRender extends TileEntitySpecialRenderer
{
    ModelEarrings earrings = new ModelEarrings();
    ResourceLocation texture = new ResourceLocation(Variables.MODID, "textures/entities/Earrings.png");
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
    }
    
    public void doRender(Entity entity, double x, double y, double z, float f, float g)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        earrings.render(entity, 0F, 0F, 0F, (float)z, f, 1.0F);
        GL11.glPopMatrix();
    }
}