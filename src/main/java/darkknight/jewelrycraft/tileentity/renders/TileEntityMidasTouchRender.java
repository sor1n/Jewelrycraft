package darkknight.jewelrycraft.tileentity.renders;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.tileentity.TileEntityMidasTouch;

public class TileEntityMidasTouchRender extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        TileEntityMidasTouch midas = (TileEntityMidasTouch)te;
        if (midas.target != null){
            EntityLiving target = ((EntityLiving)midas.target);
            target.hurtTime = 0;
            GL11.glTranslatef(0.5F, 0.0F, 0.5F);
            GL11.glColor3f(1.0F, 1.0F, 0.0F);
            RenderManager.instance.renderEntityWithPosYaw(target, midas.xCoord - RenderManager.instance.renderPosX, midas.yCoord - RenderManager.instance.renderPosY, midas.zCoord - RenderManager.instance.renderPosZ, 0F, 1F);   
        }
        else{
            GL11.glTranslatef(0.5F, 0.0F, 0.5F);
            GL11.glColor3f(1.0F, 1.0F, 0.0F);
            RenderManager.instance.renderEntityWithPosYaw(new EntityPig(te.getWorldObj()), midas.xCoord - RenderManager.instance.renderPosX, midas.yCoord - RenderManager.instance.renderPosY, midas.zCoord - RenderManager.instance.renderPosZ, 0F, 1F);
        }
        GL11.glPopMatrix();
    }
}
