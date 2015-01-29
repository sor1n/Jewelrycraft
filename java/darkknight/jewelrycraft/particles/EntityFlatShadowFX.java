package darkknight.jewelrycraft.particles;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class EntityFlatShadowFX extends EntityFX
{
    float moteParticleScale;
    
    public EntityFlatShadowFX(World world, double x, double y, double z, float size, float maxAge)
    {
        super(world, x, y, z, 0D, 0D, 0D);        
        particleMaxAge = (int) (28D / (Math.random() * 0.3D + 0.7D) * maxAge);
        particleGravity = 0F;
        motionX = motionY = motionZ = 0;
        particleScale = size;
        noClip = true;
        setSize(0.1F, 0.1F);
    }
    
    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks, float minX, float minY, float minZ, float maxX, float maxZ)
    {
        tessellator.draw();
        ResourceLocation particle = new ResourceLocation("jewelrycraft", "textures/particle/shadows.png");
        Minecraft.getMinecraft().renderEngine.bindTexture(particle);
        tessellator.startDrawingQuads();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        float scale = 1F * particleScale;
        float x = (float) (posX - interpPosX);
        float y = (float) (posX - interpPosY);
        float z = (float) (posZ - interpPosZ);
        tessellator.setColorRGBA_F(0F, 0F, 0F, 1F);
        tessellator.addVertexWithUV(x - minX * scale - maxX * scale, y + minY * scale, z - minZ * scale - maxZ * scale, 0, 0);
        tessellator.addVertexWithUV(x - minX * scale + maxX * scale, y + minY * scale, z - minZ * scale + maxZ * scale, 1, 0);
        tessellator.addVertexWithUV(x + minX * scale + maxX * scale, y + minY * scale, z + minZ * scale + maxZ * scale, 1, 1);
        tessellator.addVertexWithUV(x + minX * scale - maxX * scale, y + minY * scale, z + minZ * scale - maxZ * scale, 0, 1);
        tessellator.draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthMask(true);
        tessellator.startDrawingQuads();
    }
    
    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        motionX = motionY = motionZ = 0;
        
        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }
    }
    
    public int getFXLayer()
    {
        return 0;
    }
    
}
