package darkknight.jewelrycraft.entities.renders;

import java.util.Random;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.util.Variables;

/**
 * @author Sorin
 */
public class HeartRender extends RenderLiving
{
    protected ResourceLocation texture;
    Random rnd = new Random();
    
    public HeartRender(ModelBase modelBase, float shadowSize)
    {
        super(modelBase, shadowSize);
    }
    
    protected void renderModel(EntityLivingBase entity, float x, float y, float z, float rot1, float rot2, float rot3)
    {
        super.renderModel(entity, x, y, z, rot1, rot2, rot3);
        rnd.setSeed(1542372345);
        if (((EntityHeart)entity).getQuantity() > 2f){
            for(int i = 0; i < ((EntityHeart)entity).getQuantity() / 10f; i++){
                float posX = rnd.nextFloat() * 0.2f * (rnd.nextBoolean()?1:-1);
                float posY = rnd.nextFloat() * 0.2f * (rnd.nextBoolean()?1:-1);
                float posZ = rnd.nextFloat() * 0.2f * (rnd.nextBoolean()?1:-1);
                float rotX = rnd.nextFloat() * 35f * (rnd.nextBoolean()?1:-1);
                float rotY = rnd.nextFloat() * 35f * (rnd.nextBoolean()?1:-1);
                float rotZ = rnd.nextFloat() * 35f * (rnd.nextBoolean()?1:-1);
                GL11.glTranslatef(-0.15F, 0.0F, 0.0F);
                GL11.glTranslatef(posX, posY, 0F);
                GL11.glRotatef(rotY, 0F, 1F, 0F);
                GL11.glRotatef(rotZ, 0F, 0F, 1F);
                GL11.glPushMatrix();
                this.mainModel.render(entity, x, y, z, rot1, rot2, rot3);
                GL11.glPopMatrix();
            }
        }
    }
    
    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f)
    {
        preRenderCallbackHeart((EntityHeart)entity, f);
    }
    
    protected void preRenderCallbackHeart(EntityHeart entity, float f)
    {
        GL11.glScalef(0.4F, 0.4F, 0.4F);
        GL11.glRotatef(55F, 1F, 0F, 0F);
        String type = entity.getType();
        if (type == "" || type == null) type = "Red";
        texture = new ResourceLocation(Variables.MODID, "textures/entities/" + type + "Heart.png");
    }
    
    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return texture;
    }
}
