package darkknight.jewelrycraft.model;

import com.pau101.util.CubicBezier;
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

/**
 * @author Paul Fulham (pau101)
 */
public class ModelHandPedestal extends ModelBase
{
    protected ModelRenderer plinth;
    protected ModelRenderer base;
    protected ModelRenderer shaft;
    private ModelRenderer palm;
    private ModelRenderer[] fingers;
    private ModelRenderer thumb;
    private ItemStackModelRenderer heldItemStack;
    private CubicBezier easeInOut;
    
    /**
     * @param resetResourceLocation
     */
    public ModelHandPedestal(ResourceLocation resetResourceLocation)
    {
        initPedestalRenderers();
        plinth.setRotationPoint(0, 16, 0);
        plinth.addChild(base);
        base.addChild(shaft);
        initHandModelRenderers(resetResourceLocation);
        initCubicBeziers();
    }
    
    /**
     * 
     */
    protected void initPedestalRenderers()
    {
        plinth = createModelRenderer(0, 0).addBox(-6, 4, -6, 12, 4, 12);
        base = createModelRenderer(0, 0).addBox(-4, 0, -4, 8, 4, 8);
        shaft = createModelRenderer(0, 0).addBox(-3, -4, -3, 6, 4, 6);
    }
    
    /**
     * 
     */
    private void initCubicBeziers()
    {
        easeInOut = new CubicBezier(0.4F, 0, 0.6F, 1);
    }
    
    /**
     * @param textureOffsetX
     * @param textureOffsetY
     * @return
     */
    protected final ModelRenderer createModelRenderer(int textureOffsetX, int textureOffsetY)
    {
        ModelRenderer modelRenderer = new ModelRenderer(this, textureOffsetX, textureOffsetY);
        modelRenderer.setTextureSize(textureWidth, textureHeight);
        return modelRenderer;
    }
    
    /**
     * @return
     */
    private ModelRenderer createPhalanges()
    {
        return createModelRenderer(0, 0).addBox(-1, -1.5F, 0, 2, 3, 5).addBox(-1, -5.5F, 2, 2, 4, 3);
    }
    
    /**
     * @param resetResourceLocation
     */
    private void initHandModelRenderers(ResourceLocation resetResourceLocation)
    {
        palm = createModelRenderer(0, 0).addBox(-4, -8, -4, 8, 4, 8);
        shaft.addChild(palm);
        int fingerCount = 4;
        fingers = new ModelRenderer[fingerCount];
        for(int i = 0; i < fingerCount; i++){
            ModelRenderer phalanges = createPhalanges();
            float theta = (i / (float)fingerCount - 0.5F + 1F / fingerCount / 2) * ((float)Math.PI * 0.6F);
            phalanges.rotateAngleY = theta;
            phalanges.setRotationPoint(MathHelper.sin(theta) * 5, -5.75F, MathHelper.cos(theta) * 5 - 1);
            fingers[i] = phalanges;
            palm.addChild(phalanges);
        }
        thumb = createPhalanges();
        thumb.rotateAngleY = (float)Math.PI;
        thumb.rotationPointY = -5.75F;
        thumb.rotationPointZ = -3;
        palm.addChild(thumb);
        heldItemStack = new ItemStackModelRenderer(this, resetResourceLocation);
        heldItemStack.rotateAngleZ = (float)Math.PI;
        heldItemStack.rotateAngleX = (float)(Math.PI / 2);
        heldItemStack.setRotationPoint(0, -8.5F, -3.5F);
        palm.addChild(heldItemStack);
    }
    
    /**
     * @param pedestal
     * @param partialRenderTicks
     * @param scale
     */
    public void render(TileEntityHandPedestal pedestal, float partialRenderTicks, float scale)
    {
        handleHeldItemStack(pedestal.getHeldItemStack());
        float gripScale = pedestal.getGripScale();
        float grip = easeInOut.eval(pedestal.getGrip(partialRenderTicks)) * gripScale;
        float rotateAngleX = (float)(grip * 75 * Math.PI / 180 + (1 - grip) * 10 * Math.PI / 180);
        float rotateAngleZ = (float)(grip * 20 * Math.PI / 180);
        for(int i = 0; i < fingers.length; i++){
            ModelRenderer phalanges = fingers[i];
            phalanges.rotateAngleX = rotateAngleX;
            phalanges.rotateAngleZ = rotateAngleZ * (2F / (fingers.length - 1) * i - 1);
        }
        thumb.rotateAngleX = (float)(grip * 60 * Math.PI / 180 + (1 - grip) * 10 * Math.PI / 180);
        thumb.rotateAngleZ = rotateAngleZ;
        plinth.render(scale);
    }
    
    /**
     * @param itemStack
     */
    private void handleHeldItemStack(ItemStack itemStack)
    {
        heldItemStack.setItemStack(itemStack);
        if (itemStack != null){
            Item item = itemStack.getItem();
            if (item instanceof ItemBlock){
                heldItemStack.rotateAngleX = 0;
                heldItemStack.rotationPointZ = 0;
            }else{
                heldItemStack.rotateAngleX = (float)(Math.PI / 2);
                heldItemStack.rotationPointZ = -3.5F;
            }
        }
    }
}