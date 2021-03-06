package darkknight.jewelrycraft.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelBiped - Either Mojang or a mod author Created using Tabula 4.1.1
 */
public class ModelBracelet extends ModelBase
{
    public ModelRenderer metal1;
    public ModelRenderer metal2;
    public ModelRenderer metal3;
    public ModelRenderer metal4;
    public ModelRenderer gem1;
    public ModelRenderer gem2;
    public ModelRenderer gem3;
    public ModelRenderer gem4;
    public ModelRenderer gem5;
    public ModelRenderer gem6;
    public ModelRenderer gem7;
    public ModelRenderer gem8;
    public ModelRenderer gem9;
    
    public ModelBracelet()
    {
        this.textureWidth = 16;
        this.textureHeight = 16;
        this.metal4 = new ModelRenderer(this, 0, 8);
        this.metal4.setRotationPoint(-5.0F, 12.0F, -2.0F);
        this.metal4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 4, 0.0F);
        this.gem4 = new ModelRenderer(this, 0, 0);
        this.gem4.setRotationPoint(-3.0F, 12.8F, -3.5F);
        this.gem4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.setRotateAngle(gem4, 0.0F, 0.008901179185171082F, 0.0F);
        this.gem3 = new ModelRenderer(this, 0, 0);
        this.gem3.setRotationPoint(-4.8F, 12.2F, -3.5F);
        this.gem3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.gem2 = new ModelRenderer(this, 0, 0);
        this.gem2.setRotationPoint(-1.5F, 12.8F, -3.5F);
        this.gem2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.gem1 = new ModelRenderer(this, 0, 0);
        this.gem1.setRotationPoint(0.3F, 12.2F, -3.5F);
        this.gem1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.metal1 = new ModelRenderer(this, 0, 8);
        this.metal1.setRotationPoint(-5.0F, 12.0F, -3.0F);
        this.metal1.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.gem6 = new ModelRenderer(this, 0, 0);
        this.gem6.setRotationPoint(-4.3F, 12.7F, -3.5F);
        this.gem6.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.gem7 = new ModelRenderer(this, 0, 0);
        this.gem7.setRotationPoint(-3.8F, 12.7F, -3.5F);
        this.gem7.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.gem8 = new ModelRenderer(this, 0, 0);
        this.gem8.setRotationPoint(-0.2F, 12.7F, -3.5F);
        this.gem8.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.metal2 = new ModelRenderer(this, 0, 8);
        this.metal2.setRotationPoint(-5.0F, 12.0F, 2.0F);
        this.metal2.addBox(0.0F, 0.0F, 0.0F, 6, 2, 1, 0.0F);
        this.gem5 = new ModelRenderer(this, 0, 0);
        this.gem5.setRotationPoint(-2.5F, 12.3F, -3.5F);
        this.gem5.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
        this.metal3 = new ModelRenderer(this, 0, 8);
        this.metal3.setRotationPoint(0.0F, 12.0F, -2.0F);
        this.metal3.addBox(0.0F, 0.0F, 0.0F, 1, 2, 4, 0.0F);
        this.gem9 = new ModelRenderer(this, 0, 0);
        this.gem9.setRotationPoint(-0.7F, 12.7F, -3.5F);
        this.gem9.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
    }
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
//        GL11.glDisable(GL11.GL_LIGHTING);
        if ((int)f3 != -1){
            int ingotColor = (int)f3;
            int red = (ingotColor >> 16) & 0xff;
            int green = (ingotColor >> 8) & 0xff;
            int blue = ingotColor & 0xff;
            GL11.glColor3f((float)red / 255, (float)green / 255, (float)blue / 255);
            this.metal1.render(f5);
            this.metal2.render(f5);
            this.metal3.render(f5);
            this.metal4.render(f5);
        }
        if ((int)f4 != -1){
            int gemColor = (int)f4;
            int red = gemColor >> 16 & 0xff;
            int green = gemColor >> 8 & 0xff;
            int blue = gemColor & 0xff;
            GL11.glColor3f((float)red / 255, (float)green / 255, (float)blue / 255);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_DST_COLOR);
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem4.offsetX, this.gem4.offsetY, this.gem4.offsetZ);
            GL11.glTranslatef(this.gem4.rotationPointX * f5, this.gem4.rotationPointY * f5, this.gem4.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem4.offsetX, -this.gem4.offsetY, -this.gem4.offsetZ);
            GL11.glTranslatef(-this.gem4.rotationPointX * f5, -this.gem4.rotationPointY * f5, -this.gem4.rotationPointZ * f5);
            this.gem4.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem3.offsetX, this.gem3.offsetY, this.gem3.offsetZ);
            GL11.glTranslatef(this.gem3.rotationPointX * f5, this.gem3.rotationPointY * f5, this.gem3.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem3.offsetX, -this.gem3.offsetY, -this.gem3.offsetZ);
            GL11.glTranslatef(-this.gem3.rotationPointX * f5, -this.gem3.rotationPointY * f5, -this.gem3.rotationPointZ * f5);
            this.gem3.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem2.offsetX, this.gem2.offsetY, this.gem2.offsetZ);
            GL11.glTranslatef(this.gem2.rotationPointX * f5, this.gem2.rotationPointY * f5, this.gem2.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem2.offsetX, -this.gem2.offsetY, -this.gem2.offsetZ);
            GL11.glTranslatef(-this.gem2.rotationPointX * f5, -this.gem2.rotationPointY * f5, -this.gem2.rotationPointZ * f5);
            this.gem2.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem1.offsetX, this.gem1.offsetY, this.gem1.offsetZ);
            GL11.glTranslatef(this.gem1.rotationPointX * f5, this.gem1.rotationPointY * f5, this.gem1.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem1.offsetX, -this.gem1.offsetY, -this.gem1.offsetZ);
            GL11.glTranslatef(-this.gem1.rotationPointX * f5, -this.gem1.rotationPointY * f5, -this.gem1.rotationPointZ * f5);
            this.gem1.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem6.offsetX, this.gem6.offsetY, this.gem6.offsetZ);
            GL11.glTranslatef(this.gem6.rotationPointX * f5, this.gem6.rotationPointY * f5, this.gem6.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem6.offsetX, -this.gem6.offsetY, -this.gem6.offsetZ);
            GL11.glTranslatef(-this.gem6.rotationPointX * f5, -this.gem6.rotationPointY * f5, -this.gem6.rotationPointZ * f5);
            this.gem6.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem7.offsetX, this.gem7.offsetY, this.gem7.offsetZ);
            GL11.glTranslatef(this.gem7.rotationPointX * f5, this.gem7.rotationPointY * f5, this.gem7.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem7.offsetX, -this.gem7.offsetY, -this.gem7.offsetZ);
            GL11.glTranslatef(-this.gem7.rotationPointX * f5, -this.gem7.rotationPointY * f5, -this.gem7.rotationPointZ * f5);
            this.gem7.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem8.offsetX, this.gem8.offsetY, this.gem8.offsetZ);
            GL11.glTranslatef(this.gem8.rotationPointX * f5, this.gem8.rotationPointY * f5, this.gem8.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem8.offsetX, -this.gem8.offsetY, -this.gem8.offsetZ);
            GL11.glTranslatef(-this.gem8.rotationPointX * f5, -this.gem8.rotationPointY * f5, -this.gem8.rotationPointZ * f5);
            this.gem8.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem5.offsetX, this.gem5.offsetY, this.gem5.offsetZ);
            GL11.glTranslatef(this.gem5.rotationPointX * f5, this.gem5.rotationPointY * f5, this.gem5.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem5.offsetX, -this.gem5.offsetY, -this.gem5.offsetZ);
            GL11.glTranslatef(-this.gem5.rotationPointX * f5, -this.gem5.rotationPointY * f5, -this.gem5.rotationPointZ * f5);
            this.gem5.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem9.offsetX, this.gem9.offsetY, this.gem9.offsetZ);
            GL11.glTranslatef(this.gem9.rotationPointX * f5, this.gem9.rotationPointY * f5, this.gem9.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.gem9.offsetX, -this.gem9.offsetY, -this.gem9.offsetZ);
            GL11.glTranslatef(-this.gem9.rotationPointX * f5, -this.gem9.rotationPointY * f5, -this.gem9.rotationPointZ * f5);
            this.gem9.render(f5);
            GL11.glPopMatrix();
            GL11.glDisable(GL11.GL_BLEND);
        }
//        GL11.glEnable(GL11.GL_LIGHTING);
    }
    
    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
