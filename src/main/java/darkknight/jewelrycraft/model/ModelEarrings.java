package darkknight.jewelrycraft.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEarrings extends ModelBase
{
    // fields
    ModelRenderer MetalPart00;
    ModelRenderer MetalPart10;
    ModelRenderer MetalPart20;
    ModelRenderer Gem0;
    ModelRenderer Gem1;
    ModelRenderer MetalPart01;
    ModelRenderer MetalPart11;
    ModelRenderer MetalPart21;
    
    public ModelEarrings()
    {
        textureWidth = 32;
        textureHeight = 32;
        MetalPart00 = new ModelRenderer(this, 0, 0);
        MetalPart00.addBox(0F, 0F, 0F, 1, 3, 1);
        MetalPart00.setRotationPoint(4F, -4F, 0F);
        MetalPart00.setTextureSize(32, 32);
        MetalPart00.mirror = true;
        setRotation(MetalPart00, 0F, 0F, 0F);
        MetalPart10 = new ModelRenderer(this, 0, 0);
        MetalPart10.addBox(0F, 0F, 0F, 1, 1, 1);
        MetalPart10.setRotationPoint(4F, -3F, -1F);
        MetalPart10.setTextureSize(32, 32);
        MetalPart10.mirror = true;
        setRotation(MetalPart10, 0F, 0F, 0F);
        MetalPart20 = new ModelRenderer(this, 0, 0);
        MetalPart20.addBox(0F, 0F, 0F, 1, 1, 1);
        MetalPart20.setRotationPoint(4F, -3F, 1F);
        MetalPart20.setTextureSize(32, 32);
        MetalPart20.mirror = true;
        setRotation(MetalPart20, 0F, 0F, 0F);
        Gem0 = new ModelRenderer(this, 5, 0);
        Gem0.addBox(0F, 0F, 0F, 1, 1, 1);
        Gem0.setRotationPoint(4.5F, -3F, 0F);
        Gem0.setTextureSize(32, 32);
        Gem0.mirror = true;
        setRotation(Gem0, 0F, 0F, 0F);
        Gem1 = new ModelRenderer(this, 5, 0);
        Gem1.addBox(0F, 0F, 0F, 1, 1, 1);
        Gem1.setRotationPoint(-5.5F, -3F, 0F);
        Gem1.setTextureSize(32, 32);
        Gem1.mirror = true;
        setRotation(Gem1, 0F, 0F, 0F);
        MetalPart01 = new ModelRenderer(this, 0, 0);
        MetalPart01.addBox(0F, 0F, 0F, 1, 3, 1);
        MetalPart01.setRotationPoint(-5F, -4F, 0F);
        MetalPart01.setTextureSize(32, 32);
        MetalPart01.mirror = true;
        setRotation(MetalPart01, 0F, 0F, 0F);
        MetalPart11 = new ModelRenderer(this, 0, 0);
        MetalPart11.addBox(0F, 0F, 0F, 1, 1, 1);
        MetalPart11.setRotationPoint(-5F, -3F, -1F);
        MetalPart11.setTextureSize(32, 32);
        MetalPart11.mirror = true;
        setRotation(MetalPart11, 0F, 0F, 0F);
        MetalPart21 = new ModelRenderer(this, 0, 0);
        MetalPart21.addBox(0F, 0F, 0F, 1, 1, 1);
        MetalPart21.setRotationPoint(-5F, -3F, 1F);
        MetalPart21.setTextureSize(32, 32);
        MetalPart21.mirror = true;
        setRotation(MetalPart21, 0F, 0F, 0F);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        GL11.glPushMatrix();
//        GL11.glDisable(GL11.GL_LIGHTING);
        if ((int)f3 != -1){
            int ingotColor = (int)f3;
            int red = ingotColor >> 16 & 0xff;
            int green = ingotColor >> 8 & 0xff;
            int blue = ingotColor & 0xff;
            GL11.glColor3f((float)red / 255, (float)green / 255, (float)blue / 255);
            MetalPart00.render(f5);
            MetalPart10.render(f5);
            MetalPart20.render(f5);
            MetalPart01.render(f5);
            MetalPart11.render(f5);
            MetalPart21.render(f5);
        }
        if ((int)f4 != -1){
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_DST_COLOR);
            int gemColor = (int)f4;
            int red = gemColor >> 16 & 0xff;
            int green = gemColor >> 8 & 0xff;
            int blue = gemColor & 0xff;
            GL11.glColor3f((float)red / 255, (float)green / 255, (float)blue / 255);
            Gem0.render(f5);
            Gem1.render(f5);
            GL11.glDisable(GL11.GL_BLEND);
        }
//        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
    
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
    
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
    }
}
