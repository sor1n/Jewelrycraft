package darkknight.jewelrycraft.entities.renders;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
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

    public HeartRender(ModelBase modelBase, float shadowSize)
    {
        super(modelBase, shadowSize);
//        texture = new ResourceLocation("jewelrycraft", "textures/entities/RedHeart.png");
    }
 
    @Override
    protected void preRenderCallback(EntityLivingBase entity, float f)
    {
        preRenderCallbackHeart((EntityHeart) entity, f);
    }
  
    protected void preRenderCallbackHeart(EntityHeart entity, float f)
    {
        GL11.glScalef(0.4F, 0.4F, 0.4F);
        GL11.glRotatef(55F, 1F, 0F, 0F);
        String type = entity.getType();
        if(type == "" || type == null) type = "Red";
        texture = new ResourceLocation(Variables.MODID, "textures/entities/"+type+"Heart.png");
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity)
    {
        return texture;
    }
}
