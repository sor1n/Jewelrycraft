package darkknight.jewelrycraft.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * ModelMask - OnyxDarkKnight
 * Created using Tabula 4.1.1
 */
public class ModelMask extends ModelBase {
    public ModelRenderer shape1;
    public ModelRenderer shape2;
    public ModelRenderer shape3;
    public ModelRenderer shape4;
    public ModelRenderer shape5;
    public ModelRenderer shape6;
    public ModelRenderer shape7;
    public ModelRenderer shape8;
    public ModelRenderer shape9;

    public ModelMask() {
        this.textureWidth = 64;
        this.textureHeight = 64;
        this.shape6 = new ModelRenderer(this, 41, 4);
        this.shape6.setRotationPoint(11.0F, 0.0F, 3.0F);
        this.shape6.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.shape7 = new ModelRenderer(this, 41, 4);
        this.shape7.setRotationPoint(-3.0F, 0.0F, 3.0F);
        this.shape7.addBox(0.0F, 0.0F, 0.0F, 1, 15, 1, 0.0F);
        this.shape4 = new ModelRenderer(this, 31, 2);
        this.shape4.setRotationPoint(-2.0F, 0.0F, 2.0F);
        this.shape4.addBox(0.0F, 0.0F, 0.0F, 1, 17, 1, 0.0F);
        this.shape1 = new ModelRenderer(this, 0, 0);
        this.shape1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.shape1.addBox(0.0F, 0.0F, 0.0F, 9, 19, 1, 0.0F);
        this.shape9 = new ModelRenderer(this, 46, 8);
        this.shape9.setRotationPoint(12.0F, 1.0F, 4.0F);
        this.shape9.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1, 0.0F);
        this.shape5 = new ModelRenderer(this, 36, 2);
        this.shape5.setRotationPoint(10.0F, 0.0F, 2.0F);
        this.shape5.addBox(0.0F, 0.0F, 0.0F, 1, 17, 1, 0.0F);
        this.shape8 = new ModelRenderer(this, 46, 8);
        this.shape8.setRotationPoint(-4.0F, 1.0F, 4.0F);
        this.shape8.addBox(0.0F, 0.0F, 0.0F, 1, 11, 1, 0.0F);
        this.shape3 = new ModelRenderer(this, 26, 1);
        this.shape3.setRotationPoint(9.0F, 0.0F, 1.0F);
        this.shape3.addBox(0.0F, 0.0F, 0.0F, 1, 18, 1, 0.0F);
        this.shape2 = new ModelRenderer(this, 21, 1);
        this.shape2.setRotationPoint(-1.0F, 0.0F, 1.0F);
        this.shape2.addBox(0.0F, 0.0F, 0.0F, 1, 18, 1, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        this.shape6.render(f5);
        this.shape7.render(f5);
        this.shape4.render(f5);
        this.shape1.render(f5);
        this.shape9.render(f5);
        this.shape5.render(f5);
        this.shape8.render(f5);
        this.shape3.render(f5);
        this.shape2.render(f5);
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
