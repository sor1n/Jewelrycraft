package bspkrs.briefcasespeakers.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import bspkrs.briefcasespeakers.tileentity.TileEntityBriefcaseSpeakers;

public class TileEntityRenderBriefcaseSpeakers extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_110638_a = new ResourceLocation("textures/entity/sign.png");
    
    /** The ModelSign instance used by the TileEntitySignRenderer */
    private final ModelBriefcaseSpeakers  modelBS        = new ModelBriefcaseSpeakers();
    
    public void renderTileEntityBriefcaseSpeakersAt(TileEntityBriefcaseSpeakers tebs, double par2, double par4, double par6, float par8)
    {
        Block block = tebs.getBlockType();
        GL11.glPushMatrix();
        float f1 = 0.6666667F;
        float f2 = 0.0F;
        
        int i = tebs.getBlockMetadata();
        
        if (i == 2)
        {
            f2 = 180.0F;
        }
        
        if (i == 4)
        {
            f2 = 90.0F;
        }
        
        if (i == 5)
        {
            f2 = -90.0F;
        }
        
        GL11.glTranslatef((float) par2 + 0.5F, (float) par4 + 0.75F * f1, (float) par6 + 0.5F);
        GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
        
        this.bindTexture(field_110638_a);
        GL11.glPushMatrix();
        GL11.glScalef(f1, -f1, -f1);
        this.modelBS.renderBriefcaseSpeakers(1.0f);
        GL11.glPopMatrix();
        FontRenderer fontrenderer = this.getFontRenderer();
        f2 = 0.016666668F * f1;
        GL11.glTranslatef(0.0F, 0.5F * f1, 0.07F * f1);
        GL11.glScalef(f2, -f2, f2);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f2);
        GL11.glDepthMask(false);
        
        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
    {
        this.renderTileEntityBriefcaseSpeakersAt((TileEntityBriefcaseSpeakers) par1TileEntity, par2, par4, par6, par8);
    }
}
