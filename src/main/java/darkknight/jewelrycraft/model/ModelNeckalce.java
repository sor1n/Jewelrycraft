package darkknight.jewelrycraft.model;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelNeckalce - OnyxDarkKnight Created using Tabula 4.1.1
 */
public class ModelNeckalce extends ModelBase
{
    public ModelRenderer neck1;
    public ModelRenderer neck2;
    public ModelRenderer neck3;
    public ModelRenderer neck4;
    public ModelRenderer neck5;
    public ModelRenderer neck6;
    public ModelRenderer neck7;
    public ModelRenderer neck8;
    public ModelRenderer neck9;
    public ModelRenderer gem;
    
    public ModelNeckalce()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.neck1 = new ModelRenderer(this, 0, 0);
        this.neck1.setRotationPoint(2.0F, 0.0F, -2.5F);
        this.neck1.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.neck7 = new ModelRenderer(this, 0, 0);
        this.neck7.setRotationPoint(-1.0F, 3.5F, -2.5F);
        this.neck7.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.neck8 = new ModelRenderer(this, 0, 0);
        this.neck8.setRotationPoint(0.7F, 3.5F, -2.5F);
        this.neck8.addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.0F);
        this.neck3 = new ModelRenderer(this, 0, 0);
        this.neck3.setRotationPoint(-2.0F, 1.5F, -2.5F);
        this.neck3.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.neck6 = new ModelRenderer(this, 0, 0);
        this.neck6.setRotationPoint(-1.5F, 2.5F, -2.5F);
        this.neck6.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.neck9 = new ModelRenderer(this, 0, 0);
        this.neck9.setRotationPoint(-0.75F, 4.0F, -2.5F);
        this.neck9.addBox(0.0F, 0.0F, 0.0F, 3, 3, 1, 0.0F);
        this.neck4 = new ModelRenderer(this, 0, 0);
        this.neck4.setRotationPoint(1.5F, 1.5F, -2.5F);
        this.neck4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
        this.neck2 = new ModelRenderer(this, 0, 0);
        this.neck2.setRotationPoint(-2.5F, 0.0F, -2.5F);
        this.neck2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
        this.gem = new ModelRenderer(this, 9, 0);
        this.gem.setRotationPoint(-0.5F, 4.25F, -2.75F);
        this.gem.addBox(0.0F, 0.0F, 0.0F, 4, 4, 1, 0.0F);
        this.neck5 = new ModelRenderer(this, 0, 0);
        this.neck5.setRotationPoint(1.0F, 2.5F, -2.5F);
        this.neck5.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
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
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck1.offsetX, this.neck1.offsetY, this.neck1.offsetZ);
            GL11.glTranslatef(this.neck1.rotationPointX * f5, this.neck1.rotationPointY * f5, this.neck1.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck1.offsetX, -this.neck1.offsetY, -this.neck1.offsetZ);
            GL11.glTranslatef(-this.neck1.rotationPointX * f5, -this.neck1.rotationPointY * f5, -this.neck1.rotationPointZ * f5);
            this.neck1.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck7.offsetX, this.neck7.offsetY, this.neck7.offsetZ);
            GL11.glTranslatef(this.neck7.rotationPointX * f5, this.neck7.rotationPointY * f5, this.neck7.rotationPointZ * f5);
            GL11.glScaled(0.3D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck7.offsetX, -this.neck7.offsetY, -this.neck7.offsetZ);
            GL11.glTranslatef(-this.neck7.rotationPointX * f5, -this.neck7.rotationPointY * f5, -this.neck7.rotationPointZ * f5);
            this.neck7.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck8.offsetX, this.neck8.offsetY, this.neck8.offsetZ);
            GL11.glTranslatef(this.neck8.rotationPointX * f5, this.neck8.rotationPointY * f5, this.neck8.rotationPointZ * f5);
            GL11.glScaled(0.3D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck8.offsetX, -this.neck8.offsetY, -this.neck8.offsetZ);
            GL11.glTranslatef(-this.neck8.rotationPointX * f5, -this.neck8.rotationPointY * f5, -this.neck8.rotationPointZ * f5);
            this.neck8.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck3.offsetX, this.neck3.offsetY, this.neck3.offsetZ);
            GL11.glTranslatef(this.neck3.rotationPointX * f5, this.neck3.rotationPointY * f5, this.neck3.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck3.offsetX, -this.neck3.offsetY, -this.neck3.offsetZ);
            GL11.glTranslatef(-this.neck3.rotationPointX * f5, -this.neck3.rotationPointY * f5, -this.neck3.rotationPointZ * f5);
            this.neck3.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck6.offsetX, this.neck6.offsetY, this.neck6.offsetZ);
            GL11.glTranslatef(this.neck6.rotationPointX * f5, this.neck6.rotationPointY * f5, this.neck6.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck6.offsetX, -this.neck6.offsetY, -this.neck6.offsetZ);
            GL11.glTranslatef(-this.neck6.rotationPointX * f5, -this.neck6.rotationPointY * f5, -this.neck6.rotationPointZ * f5);
            this.neck6.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck9.offsetX, this.neck9.offsetY, this.neck9.offsetZ);
            GL11.glTranslatef(this.neck9.rotationPointX * f5, this.neck9.rotationPointY * f5, this.neck9.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck9.offsetX, -this.neck9.offsetY, -this.neck9.offsetZ);
            GL11.glTranslatef(-this.neck9.rotationPointX * f5, -this.neck9.rotationPointY * f5, -this.neck9.rotationPointZ * f5);
            this.neck9.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck4.offsetX, this.neck4.offsetY, this.neck4.offsetZ);
            GL11.glTranslatef(this.neck4.rotationPointX * f5, this.neck4.rotationPointY * f5, this.neck4.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck4.offsetX, -this.neck4.offsetY, -this.neck4.offsetZ);
            GL11.glTranslatef(-this.neck4.rotationPointX * f5, -this.neck4.rotationPointY * f5, -this.neck4.rotationPointZ * f5);
            this.neck4.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck2.offsetX, this.neck2.offsetY, this.neck2.offsetZ);
            GL11.glTranslatef(this.neck2.rotationPointX * f5, this.neck2.rotationPointY * f5, this.neck2.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck2.offsetX, -this.neck2.offsetY, -this.neck2.offsetZ);
            GL11.glTranslatef(-this.neck2.rotationPointX * f5, -this.neck2.rotationPointY * f5, -this.neck2.rotationPointZ * f5);
            this.neck2.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.neck5.offsetX, this.neck5.offsetY, this.neck5.offsetZ);
            GL11.glTranslatef(this.neck5.rotationPointX * f5, this.neck5.rotationPointY * f5, this.neck5.rotationPointZ * f5);
            GL11.glScaled(0.5D, 0.5D, 0.5D);
            GL11.glTranslatef(-this.neck5.offsetX, -this.neck5.offsetY, -this.neck5.offsetZ);
            GL11.glTranslatef(-this.neck5.rotationPointX * f5, -this.neck5.rotationPointY * f5, -this.neck5.rotationPointZ * f5);
            this.neck5.render(f5);
            GL11.glPopMatrix();
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
            GL11.glTranslatef(this.gem.offsetX, this.gem.offsetY, this.gem.offsetZ);
            GL11.glTranslatef(this.gem.rotationPointX * f5, this.gem.rotationPointY * f5, this.gem.rotationPointZ * f5);
            GL11.glScaled(0.25D, 0.25D, 0.25D);
            GL11.glTranslatef(-this.gem.offsetX, -this.gem.offsetY, -this.gem.offsetZ);
            GL11.glTranslatef(-this.gem.rotationPointX * f5, -this.gem.rotationPointY * f5, -this.gem.rotationPointZ * f5);
            this.gem.render(f5);
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
