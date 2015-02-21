package darkknight.jewelrycraft.tileentity.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.model.ModelMask;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;

public class MaskRender extends TileEntitySpecialRenderer
{
    ModelMask mask = new ModelMask();
    ResourceLocation texture = new ResourceLocation("jewelrycraft", "textures/entities/Mask.png");
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
    }
    
    public void doRender(Entity entity, double x, double y, double z, float f, float g)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        GL11.glScalef(1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef((float)g, 0f, 1f, 0f);
        GL11.glRotatef((float)f, 1f, 0f, 0f);
        GL11.glTranslatef(-0.33f, -0.33f, -0.33f);
        float relY = 0F, relZ = 0F;
        if (f >-2 && f < 30)
        {
            relY = -(float)f / 90 * 0.25F;
            if(!entity.isSneaking()) relZ = -0.1f;
            else{
                relZ = -0.2f;
                relY += 0.1f;
            }
        }
        else if (f > 0){
            relY = -(float)f / 90 * 0.25F;
            relZ = -(float)f / 90 * 0.3F;
            if(entity.isSneaking()){
                relZ -= 0.05f;
                relY += 0.05f;
            }
        }
        else{
            relY = (float)f / 90 * 0.15F;
            relZ = -(float)f / 90 * 0.15F;
            if(entity.isSneaking()){
                relZ -= 0.05f;
                relY += 0.05f;
            }
        }
        GL11.glTranslatef(0F, relY, relZ);
        mask.render(entity, (float)x, (float)y, (float)z, 0F, 0F, 0.02F);
        GL11.glPopMatrix();
    }
}