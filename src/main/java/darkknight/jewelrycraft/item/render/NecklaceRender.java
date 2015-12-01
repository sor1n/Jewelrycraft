package darkknight.jewelrycraft.item.render;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.model.ModelNeckalce;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class NecklaceRender extends TileEntitySpecialRenderer
{
    public ModelNeckalce neckalce = new ModelNeckalce();
    ResourceLocation texture = new ResourceLocation(Variables.MODID, "textures/entities/Necklace.png");
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
    }
    
    public void doRender(Entity entity, double x, double y, double z, float f, float g)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        if ((float)z != -1) neckalce.render(entity, 0F, 0F, 0F, (float)z, f, 1.0F);
        GL11.glPopMatrix();
    }
}