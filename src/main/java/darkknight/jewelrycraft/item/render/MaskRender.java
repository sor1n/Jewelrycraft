package darkknight.jewelrycraft.item.render;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.model.ModelMask;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class MaskRender extends TileEntitySpecialRenderer
{
    ModelMask mask = new ModelMask();
    ResourceLocation texture = new ResourceLocation(Variables.MODID, "textures/entities/Mask.png");
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
    }
    
    public void doRender(Entity entity, double x, double y, double z, float f, float g)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glTranslatef(0.1615F, 0F, 0.03F);
        GL11.glScalef(1.0F, 1.0F, 0.5F);
        mask.render(entity, 0F, 0F, 0F, 0F, 0F, 0.02F);
        GL11.glPopMatrix();
    }
}