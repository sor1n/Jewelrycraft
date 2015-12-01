package darkknight.jewelrycraft.particles;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFlatShadowFX extends EntityFX
{
    float moteParticleScale;
    ResourceLocation texture;
    
    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @param size
     * @param maxAge
     * @param texture
     */
    public EntityFlatShadowFX(World world, double x, double y, double z, float size, float maxAge, ResourceLocation texture)
    {
        super(world, x, y, z, 0D, 0D, 0D);
        particleMaxAge = (int)(28D / (Math.random() * 0.3D + 0.7D) * maxAge);
        particleGravity = 0F;
        motionX = motionY = motionZ = 0;
        particleScale = size;
        noClip = true;
        this.texture = texture;
        setSize(0.1F, 0.1F);
    }
    
    /**
     * @param tessellator
     * @param partialTicks
     * @param minX
     * @param minY
     * @param minZ
     * @param maxX
     * @param maxZ
     */
    @Override
    public void renderParticle(Tessellator tessellator, float partialTicks, float minX, float minY, float minZ, float maxX, float maxZ)
    {
        tessellator.draw();
        Minecraft.getMinecraft().renderEngine.bindTexture(texture);
        tessellator.startDrawingQuads();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        float scale = 1F * particleScale;
        float x = (float)(posX - interpPosX);
        float y = (float)(posX - interpPosY);
        float z = (float)(posZ - interpPosZ);
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
    
    /**
     * 
     */
    @Override
    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        motionX = motionY = motionZ = 0;
        if (particleAge++ >= particleMaxAge) setDead();
    }
    
    /**
     * @return
     */
    @Override
    public int getFXLayer()
    {
        return 0;
    }
}
