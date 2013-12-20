package darkknight.jewelrycraft.renders;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.model.ModelMolder;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
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
                String name = me.mold.getDisplayName().substring(0, 1).toLowerCase() + me.mold.getDisplayName().trim().substring(1).replace(" M", "M");
                String texture = "textures/items/" + name + ".png";
                ResourceLocation lava = new ResourceLocation("jewelrycraft", texture);
                Minecraft.getMinecraft().renderEngine.bindTexture(lava);
                me.mold.getIconIndex().getInterpolatedU(0);
//                int decal = 0;
//                int decal2 = 0;
//                if (me.mold.getItemDamage() == 0)
//                    decal = 32;
//                else if (me.mold.getItemDamage() > 0)
//                    decal = 64;
                double minu = me.mold.getIconIndex().getInterpolatedU(0);
                double minv = me.mold.getIconIndex().getInterpolatedV(0);
                double maxu = me.mold.getIconIndex().getInterpolatedU(16);
                double maxv = me.mold.getIconIndex().getInterpolatedV(16);
                GL11.glPushMatrix();
                GL11.glScalef(1f / 16f, 1f / 16f, 1f / 16f);
                GL11.glDisable(GL11.GL_LIGHTING);
                
                for (float f = 0; f <= 2; f += 0.01)
                {
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(5, 21 + f, 5, minu, minv);
                    tessellator.addVertexWithUV(-5, 21 + f, 5, maxu, minv);
                    tessellator.addVertexWithUV(-5, 21 + f, -5, maxu, maxv);
                    tessellator.addVertexWithUV(5, 21 + f, -5, minu, maxv);
                    tessellator.draw();
                }
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
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
