package darkknight.jewelrycraft.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelShadowHand extends ModelBase
{
    //fields
    ModelRenderer finger1;
    ModelRenderer finger2;
    ModelRenderer finger3;
    ModelRenderer finger4;
    ModelRenderer finger5;
    ModelRenderer finger6;
    ModelRenderer finger7;
    ModelRenderer finger8;
    ModelRenderer finger9;
    ModelRenderer finger10;
    ModelRenderer Base;
    ModelRenderer Base2;
    ModelRenderer Base3;
    ModelRenderer Palm;
    
    public ModelShadowHand()
    {
        textureWidth = 64;
        textureHeight = 32;
        
        finger1 = new ModelRenderer(this, 0, 0);
        finger1.addBox(-1.5F, -4.5F, -9F, 2, 4, 3);
        finger1.setRotationPoint(0F, 10F, 0F);
        finger1.setTextureSize(64, 32);
        finger1.mirror = true;
        setRotation(finger1, -0.2094395F, 0.7853982F, 0F);
        finger2 = new ModelRenderer(this, 0, 0);
        finger2.addBox(-2.5F, -4.5F, -9F, 2, 4, 3);
        finger2.setRotationPoint(0F, 10F, 0F);
        finger2.setTextureSize(64, 32);
        finger2.mirror = true;
        setRotation(finger2, -0.2094395F, 0.0872665F, 0F);
        finger3 = new ModelRenderer(this, 0, 0);
        finger3.addBox(0.5F, -4.5F, -9F, 2, 4, 3);
        finger3.setRotationPoint(0F, 10F, 0F);
        finger3.setTextureSize(64, 32);
        finger3.mirror = true;
        setRotation(finger3, -0.2094395F, -0.0872665F, 0F);
        finger4 = new ModelRenderer(this, 0, 0);
        finger4.addBox(-0.5F, -4.5F, -9F, 2, 4, 3);
        finger4.setRotationPoint(0F, 10F, 0F);
        finger4.setTextureSize(64, 32);
        finger4.mirror = true;
        setRotation(finger4, -0.2094395F, -0.7853982F, 0F);
        finger5 = new ModelRenderer(this, 0, 0);
        finger5.addBox(-1F, -4.5F, 5F, 2, 4, 3);
        finger5.setRotationPoint(0F, 10F, 0F);
        finger5.setTextureSize(64, 32);
        finger5.mirror = true;
        setRotation(finger5, 0.2094395F, 0F, 0F);
        finger6 = new ModelRenderer(this, 0, 0);
        finger6.addBox(-1.5F, -0.5F, -9F, 2, 3, 5);
        finger6.setRotationPoint(0F, 10F, 0F);
        finger6.setTextureSize(64, 32);
        finger6.mirror = true;
        setRotation(finger6, -0.2094395F, 0.7853982F, 0F);
        finger7 = new ModelRenderer(this, 0, 0);
        finger7.addBox(-2.5F, -0.5F, -9F, 2, 3, 5);
        finger7.setRotationPoint(0F, 10F, 0F);
        finger7.setTextureSize(64, 32);
        finger7.mirror = true;
        setRotation(finger7, -0.2094395F, 0.0872665F, 0F);
        finger8 = new ModelRenderer(this, 0, 0);
        finger8.addBox(0.5F, -0.5F, -9F, 2, 3, 5);
        finger8.setRotationPoint(0F, 10F, 0F);
        finger8.setTextureSize(64, 32);
        finger8.mirror = true;
        setRotation(finger8, -0.2094395F, -0.0872665F, 0F);
        finger9 = new ModelRenderer(this, 0, 0);
        finger9.addBox(-0.5F, -0.5F, -9F, 2, 3, 5);
        finger9.setRotationPoint(0F, 10F, 0F);
        finger9.setTextureSize(64, 32);
        finger9.mirror = true;
        setRotation(finger9, -0.2094395F, -0.7853982F, 0F);
        finger10 = new ModelRenderer(this, 0, 0);
        finger10.addBox(-1F, -0.5F, 3F, 2, 3, 5);
        finger10.setRotationPoint(0F, 10F, 0F);
        finger10.setTextureSize(64, 32);
        finger10.mirror = true;
        setRotation(finger10, 0.2094395F, 0F, 0F);
        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-3F, 6F, -3F, 6, 2, 6);
        Base.setRotationPoint(0F, 16F, 0F);
        Base.setTextureSize(64, 32);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);
        Base2 = new ModelRenderer(this, 0, 15);
        Base2.addBox(-2F, 0F, -2F, 4, 6, 4);
        Base2.setRotationPoint(0F, 16F, 0F);
        Base2.setTextureSize(64, 32);
        Base2.mirror = true;
        setRotation(Base2, 0F, 0F, 0F);
        Base3 = new ModelRenderer(this, 28, 0);
        Base3.addBox(-3F, -4F, -3F, 6, 4, 6);
        Base3.setRotationPoint(0F, 16F, 0F);
        Base3.setTextureSize(64, 32);
        Base3.mirror = true;
        setRotation(Base3, 0F, 0F, 0F);
        Palm = new ModelRenderer(this, 0, 0);
        Palm.addBox(-4F, -8F, -4F, 8, 4, 8);
        Palm.setRotationPoint(0F, 16F, 0F);
        Palm.setTextureSize(64, 32);
        Palm.mirror = true;
        setRotation(Palm, 0F, 0F, 0F);
    }
    
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        switch ((int)f)
        {
            default: 
                GL11.glTranslatef(0.15f, 0.0f, 0.0f);
                GL11.glTranslatef(0.0f, 0.25f, 0.0f);
                GL11.glTranslatef(0.0f, 0.0f, 0.06f);
                GL11.glRotatef(35f, 0, 0, 1);
                GL11.glRotatef(-15f, 1, 0, 1);
                break;
            //case 1: GL11.glRotatef(10f, 0, 0, 1);
        }
        finger1.render(f5);
        GL11.glPopMatrix();
        finger2.render(f5);
        finger3.render(f5);
        finger4.render(f5);
        finger5.render(f5);
        finger6.render(f5);
        finger7.render(f5);
        finger8.render(f5);
        finger9.render(f5);
        finger10.render(f5);
        Base.render(f5);
        Base2.render(f5);
        Base3.render(f5);
        Palm.render(f5);
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
