package darkknight.jewelrycraft.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * ModelRing - OnyxDarkKnight Created using Tabula 4.1.1
 */
public class ModelRing extends ModelBase
{
    public ModelRenderer ring1;
    public ModelRenderer ring2;
    public ModelRenderer ring3;
    public ModelRenderer gem;
    public ModelRenderer gem1;
    public ModelRenderer gem2;
    
    public ModelRing()
    {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.ring1 = new ModelRenderer(this, 0, 0);
        this.ring1.setRotationPoint(-8.6F, 11.4F, -2.15F);
        this.ring1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.ring3 = new ModelRenderer(this, 0, 0);
        this.ring3.setRotationPoint(-8.5F, 11.4F, -2.15F);
        this.ring3.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.gem = new ModelRenderer(this, 5, 0);
        this.gem.setRotationPoint(-8.5F, 11.5F, -2.25F);
        this.gem.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
        this.gem1 = new ModelRenderer(this, 6, 0);
        this.gem1.setRotationPoint(-8.45F, 11.45F, -2.25F);
        this.gem1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.ring2 = new ModelRenderer(this, 0, 0);
        this.ring2.setRotationPoint(-8.3F, 11.4F, -2.15F);
        this.ring2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
        this.gem2 = new ModelRenderer(this, 6, 0);
        this.gem2.setRotationPoint(-8.45F, 11.6F, -2.25F);
        this.gem2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
    }
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
//      GL11.glEnable(GL11.GL_LIGHTING);
        if ((int)f3 != -1){
            int ingotColor = (int)f3;
            int red = (ingotColor >> 16) & 0xff;
            int green = (ingotColor >> 8) & 0xff;
            int blue = ingotColor & 0xff;
            GL11.glColor3f((float)red / 255, (float)green / 255, (float)blue / 255);
            GL11.glPushMatrix();
            GL11.glTranslatef(this.ring1.offsetX, this.ring1.offsetY, this.ring1.offsetZ);
            GL11.glTranslatef(this.ring1.rotationPointX * f5, this.ring1.rotationPointY * f5, this.ring1.rotationPointZ * f5);
            GL11.glScaled(0.1D, 0.3D, 0.3D);
            GL11.glTranslatef(-this.ring1.offsetX, -this.ring1.offsetY, -this.ring1.offsetZ);
            GL11.glTranslatef(-this.ring1.rotationPointX * f5, -this.ring1.rotationPointY * f5, -this.ring1.rotationPointZ * f5);
            this.ring1.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.ring2.offsetX, this.ring2.offsetY, this.ring2.offsetZ);
            GL11.glTranslatef(this.ring2.rotationPointX * f5, this.ring2.rotationPointY * f5, this.ring2.rotationPointZ * f5);
            GL11.glScaled(0.1D, 0.3D, 0.3D);
            GL11.glTranslatef(-this.ring2.offsetX, -this.ring2.offsetY, -this.ring2.offsetZ);
            GL11.glTranslatef(-this.ring2.rotationPointX * f5, -this.ring2.rotationPointY * f5, -this.ring2.rotationPointZ * f5);
            this.ring2.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.ring3.offsetX, this.ring3.offsetY, this.ring3.offsetZ);
            GL11.glTranslatef(this.ring3.rotationPointX * f5, this.ring3.rotationPointY * f5, this.ring3.rotationPointZ * f5);
            GL11.glScaled(0.2D, 0.3D, 0.1D);
            GL11.glTranslatef(-this.ring3.offsetX, -this.ring3.offsetY, -this.ring3.offsetZ);
            GL11.glTranslatef(-this.ring3.rotationPointX * f5, -this.ring3.rotationPointY * f5, -this.ring3.rotationPointZ * f5);
            this.ring3.render(f5);
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
            GL11.glScaled(0.1D, 0.1D, 0.1D);
            GL11.glTranslatef(-this.gem.offsetX, -this.gem.offsetY, -this.gem.offsetZ);
            GL11.glTranslatef(-this.gem.rotationPointX * f5, -this.gem.rotationPointY * f5, -this.gem.rotationPointZ * f5);
            this.gem.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem1.offsetX, this.gem1.offsetY, this.gem1.offsetZ);
            GL11.glTranslatef(this.gem1.rotationPointX * f5, this.gem1.rotationPointY * f5, this.gem1.rotationPointZ * f5);
            GL11.glScaled(0.1D, 0.05D, 0.1D);
            GL11.glTranslatef(-this.gem1.offsetX, -this.gem1.offsetY, -this.gem1.offsetZ);
            GL11.glTranslatef(-this.gem1.rotationPointX * f5, -this.gem1.rotationPointY * f5, -this.gem1.rotationPointZ * f5);
            this.gem1.render(f5);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef(this.gem2.offsetX, this.gem2.offsetY, this.gem2.offsetZ);
            GL11.glTranslatef(this.gem2.rotationPointX * f5, this.gem2.rotationPointY * f5, this.gem2.rotationPointZ * f5);
            GL11.glScaled(0.1D, 0.05D, 0.1D);
            GL11.glTranslatef(-this.gem2.offsetX, -this.gem2.offsetY, -this.gem2.offsetZ);
            GL11.glTranslatef(-this.gem2.rotationPointX * f5, -this.gem2.rotationPointY * f5, -this.gem2.rotationPointZ * f5);
            this.gem2.render(f5);
            GL11.glPopMatrix();
            GL11.glDisable(GL11.GL_BLEND);
        }
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
