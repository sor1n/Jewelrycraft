package darkknight.jewelrycraft.renders;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.model.ModelMolder;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityMolderRender extends TileEntitySpecialRenderer
{
    ModelMolder modelMolder = new ModelMolder();
    String      texture     = "textures/tileentities/Molder.png";

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        TileEntityMolder me = (TileEntityMolder) te;

        ResourceLocation blockTexture = new ResourceLocation("jewelrycraft", texture);
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);
        Tessellator tessellator = Tessellator.instance;

        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        modelMolder.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        if (me != null)
        {
            if (me.hasMold)
            {
//                String name = me.mold.getDisplayName().substring(0, 1).toLowerCase() + me.mold.getDisplayName().trim().substring(1).replace(" M", "M");
//                String texture = "textures/items/" + name + ".png";
//                ResourceLocation lava = new ResourceLocation("jewelrycraft", texture);
//                Minecraft.getMinecraft().renderEngine.bindTexture(lava);
//                double minu = me.mold.getIconIndex().getInterpolatedU(16D);
//                double minv = me.mold.getIconIndex().getInterpolatedV(-96D);
//                double maxu = me.mold.getIconIndex().getInterpolatedU(16.0D * 256D);
//                double maxv = me.mold.getIconIndex().getInterpolatedV(-96.0D * 256D);
//                GL11.glDisable(GL11.GL_LIGHTING);
//                GL11.glScalef(1f / 16f, 1f / 16f, 1f / 16f);
//                GL11.glRotatef(180F, 0F, 1F, 0F);
                //GL11.glRotatef(90F, 1, 0F, 0F);
//                GL11.glEnable(GL11.GL_LIGHTING);
//                for (float f = 0; f <= 2; f += 0.01)
//                {
//                    tessellator.startDrawingQuads();
//                    tessellator.addVertexWithUV(5, 21 + f, 5, minu, minv);
//                    tessellator.addVertexWithUV(-5, 21 + f, 5, maxu, minv);
//                    tessellator.addVertexWithUV(-5, 21 + f, -5, maxu, maxv);
//                    tessellator.addVertexWithUV(5, 21 + f, -5, minu, maxv);
//                    tessellator.draw();
//                }
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                EntityItem entityitem = new EntityItem(te.worldObj, 0.0D, 0.0D, 0.0D, me.mold);
                entityitem.getEntityItem().stackSize = 1;
                entityitem.hoverStart = 0.0F;
                GL11.glTranslatef(0F, 1.312F, -0.25F);
                GL11.glScalef(1.25F, 1.0F, 1.25F);
                GL11.glRotatef(90F, 1F, 0F, 0f);
                RenderItem.renderInFrame = true;
                RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
            if (me.hasJewelBase)
            {
                if(me.jewelBase.getIconIndex().getIconName() != "")
                {
//                    String domain = "";
//                    if(me.jewelBase.getIconIndex().getIconName().substring(0, me.jewelBase.getIconIndex().getIconName().indexOf(":") + 1) != "")
//                        domain = me.jewelBase.getIconIndex().getIconName().substring(0, me.jewelBase.getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
//                    else
//                        domain = "minecraft";
//                    String texture = me.jewelBase.getIconIndex().getIconName().substring(me.jewelBase.getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
//                    ResourceLocation lava = new ResourceLocation(domain, "textures/items/" + texture);
//                    Minecraft.getMinecraft().renderEngine.bindTexture(lava);
//                    double minu = me.jewelBase.getIconIndex().getInterpolatedU(16D);
//                    double minv = me.jewelBase.getIconIndex().getInterpolatedV(-96D);
//                    double maxu = me.jewelBase.getIconIndex().getInterpolatedU(16.0D * 256D);
//                    double maxv = me.jewelBase.getIconIndex().getInterpolatedV(-96.0D * 256D);
                    
//                    GL11.glScalef(1f / 16f, 1f / 16f, 1f / 16f);
//                    GL11.glRotatef(180F, 0F, 1F, 0F);
//                    int color = me.jewelBase.getItem().getColorFromItemStack(me.jewelBase, 0);
//                    float red = (float)(color >> 16 & 255) / 255.0F;
//                    float green = (float)(color >> 8 & 255) / 255.0F;
//                    float blue = (float)(color & 255) / 255.0F;
//                    if(!me.jewelBase.getDisplayName().contains("Ingot")) GL11.glColor4f(red, green, blue, 1F);
//                    for(float f = 0; f <= 0.3; f+=0.01)
//                    {
//                        tessellator.startDrawingQuads();
//                        tessellator.addVertexWithUV(5, 20.8 + f, 5, minu, minv);
//                        tessellator.addVertexWithUV(-5, 20.8 + f, 5, maxu, minv);
//                        tessellator.addVertexWithUV(-5, 20.8 + f, -5, maxu, maxv);
//                        tessellator.addVertexWithUV(5, 20.8 + f, -5, minu, maxv);
//                        tessellator.draw();
//                    }
                    GL11.glPushMatrix();
                    GL11.glDisable(GL11.GL_LIGHTING);
                    ItemRing.addMetal(me.jewelBase, me.ringMetal);
                    EntityItem entityitem = new EntityItem(te.worldObj, 0.0D, 0.0D, 0.0D, me.jewelBase);
                    entityitem.getEntityItem().stackSize = 1;
                    entityitem.hoverStart = 0.0F;
                    GL11.glTranslatef(0F, 1.312F, -0.25F);
                    GL11.glScalef(1.25F, 1.0F, 1.25F);
                    GL11.glRotatef(90F, 1F, 0F, 0f);
                    RenderItem.renderInFrame = true;
                    RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    RenderItem.renderInFrame = false;
                    GL11.glColor4f(1, 1F, 1F, 1.0F);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glPopMatrix();
                }
            }
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    public void adjustLightFixture(World world, int i, int j, int k, Block block)
    {
        Tessellator tess = Tessellator.instance;
        float brightness = block.getBlockBrightness(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
    }

}
