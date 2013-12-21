package darkknight.jewelrycraft.renders;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.model.ModelJewlersCraftingBench;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityJewelrsCraftingTableRender extends TileEntitySpecialRenderer
{
    ModelJewlersCraftingBench modelTable = new ModelJewlersCraftingBench();
    String      texture     = "textures/tileentities/JewelrsCraftingBench.png";

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);

        ResourceLocation blockTexture = new ResourceLocation("jewelrycraft", texture);
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);
        Tessellator tessellator = Tessellator.instance;
        int block = te.getBlockMetadata();        
        TileEntityJewelrsCraftingTable jt = (TileEntityJewelrsCraftingTable)te;

        GL11.glPushMatrix();
        if (block == 0)
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        else if (block == 1){
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
        }
        else if (block == 2)
            GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
        else if (block == 3)
            GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);

        modelTable.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        //GL11.glTranslatef(0.05F, 0F, -0.5F);
        if (jt != null)
        {
            if (jt.hasJewel)
            {
                if(jt.jewel.getIconIndex().getIconName() != "")
                {
                    String domain = "";
                    if(jt.jewel.getIconIndex().getIconName().substring(0, jt.jewel.getIconIndex().getIconName().indexOf(":") + 1) != "")
                        domain = jt.jewel.getIconIndex().getIconName().substring(0, jt.jewel.getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
                    else
                        domain = "minecraft";
                    String texture = jt.jewel.getIconIndex().getIconName().substring(jt.jewel.getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
                    ResourceLocation lava = new ResourceLocation(domain, "textures/items/" + texture);
                    Minecraft.getMinecraft().renderEngine.bindTexture(lava);
                    jt.jewel.getIconIndex().getInterpolatedU(0);
                    double minu = jt.jewel.getIconIndex().getInterpolatedU(0);
                    double minv = jt.jewel.getIconIndex().getInterpolatedV(-64);
                    double maxu = jt.jewel.getIconIndex().getInterpolatedU(256);
                    double maxv = jt.jewel.getIconIndex().getInterpolatedV(256 - 64);
                    GL11.glPushMatrix();
                    GL11.glScalef(1f / 16f, 1f / 16f, 1f / 16f);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    if (jt.jewel.hasTagCompound())
                    {
                        if (jt.jewel.getTagCompound().hasKey("ingot"))
                        {
                            NBTTagCompound ingotNBT = (NBTTagCompound) jt.jewel.getTagCompound().getTag("ingot");
                            ItemStack ingotStack = new ItemStack(0, 0, 0);
                            ingotStack.readFromNBT(ingotNBT);
                            ItemRing.addMetal(jt.jewel, ingotStack);
                            int color = jt.jewel.getItem().getColorFromItemStack(jt.jewel, 0);
                            float red = (float)(color >> 16 & 255) / 255.0F;
                            float green = (float)(color >> 8 & 255) / 255.0F;
                            float blue = (float)(color & 255) / 255.0F;
                            if(!jt.jewel.getDisplayName().contains("Ingot")) GL11.glColor4f(red, green, blue, 1F);
                        }
                    }
                    tessellator.startDrawingQuads();
                    for(float f=0; f<=1; f+=0.1){
                        tessellator.addVertexWithUV(3, 9, -5+f, minu, minv);
                        tessellator.addVertexWithUV(-2.2, 9, -5+f, maxu, minv);
                        tessellator.addVertexWithUV(-2.2, 14, -5+f, maxu, maxv);
                        tessellator.addVertexWithUV(3, 14, -5+f, minu, maxv);

                        tessellator.addVertexWithUV(-3, 9, -5+f, minu, minv);
                        tessellator.addVertexWithUV(2.2, 9, -5+f, maxu, minv);
                        tessellator.addVertexWithUV(2.2, 14, -5+f, maxu, maxv);
                        tessellator.addVertexWithUV(-3, 14, -5+f, minu, maxv);
                    }
                    tessellator.draw();
                    GL11.glColor4f(1, 1F, 1F, 1.0F);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glPopMatrix();
                }
            }
            if (jt.hasEndItem)
            {
                if(jt.endItem.getIconIndex().getIconName() != "")
                {
                    String domain = "";
                    if(jt.endItem.getIconIndex().getIconName().substring(0, jt.endItem.getIconIndex().getIconName().indexOf(":") + 1) != "")
                        domain = jt.endItem.getIconIndex().getIconName().substring(0, jt.endItem.getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
                    else
                        domain = "minecraft";
                    String texture = jt.endItem.getIconIndex().getIconName().substring(jt.endItem.getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
                    ResourceLocation lava = new ResourceLocation(domain, "textures/items/" + texture);
                    Minecraft.getMinecraft().renderEngine.bindTexture(lava);
                    jt.endItem.getIconIndex().getInterpolatedU(0);
                    double minu = jt.endItem.getIconIndex().getInterpolatedU(0);
                    double minv = jt.endItem.getIconIndex().getInterpolatedV(-64);
                    double maxu = jt.endItem.getIconIndex().getInterpolatedU(256);
                    double maxv = jt.endItem.getIconIndex().getInterpolatedV(256 - 64);
                    GL11.glPushMatrix();
                    GL11.glScalef(1f / 16f, 1f / 16f, 1f / 16f);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    if (jt.endItem.hasTagCompound())
                    {
                        if (jt.endItem.getTagCompound().hasKey("ingot"))
                        {
                            NBTTagCompound ingotNBT = (NBTTagCompound) jt.endItem.getTagCompound().getTag("ingot");
                            ItemStack ingotStack = new ItemStack(0, 0, 0);
                            ingotStack.readFromNBT(ingotNBT);
                            ItemRing.addMetal(jt.endItem, ingotStack);
                            int color = jt.endItem.getItem().getColorFromItemStack(jt.endItem, 0);
                            float red = (float)(color >> 16 & 255) / 255.0F;
                            float green = (float)(color >> 8 & 255) / 255.0F;
                            float blue = (float)(color & 255) / 255.0F;
                            if(!jt.endItem.getDisplayName().contains("Ingot")) GL11.glColor4f(red, green, blue, 1F);
                        }
                    }
                    tessellator.startDrawingQuads();
                    for(float f=0; f<=1; f+=0.001){
                        tessellator.addVertexWithUV(3, 9, -5+f, minu, minv);
                        tessellator.addVertexWithUV(-2.2, 9, -5+f, maxu, minv);
                        tessellator.addVertexWithUV(-2.2, 14, -5+f, maxu, maxv);
                        tessellator.addVertexWithUV(3, 14, -5+f, minu, maxv);

                        tessellator.addVertexWithUV(-3, 9, -5+f, minu, minv);
                        tessellator.addVertexWithUV(2.2, 9, -5+f, maxu, minv);
                        tessellator.addVertexWithUV(2.2, 14, -5+f, maxu, maxv);
                        tessellator.addVertexWithUV(-3, 14, -5+f, minu, maxv);
                    }
                    tessellator.draw();
                    GL11.glColor4f(1, 1F, 1F, 1.0F);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glPopMatrix();
                }
            }
            if (jt.hasModifier)
            {
                if(jt.modifier.getIconIndex().getIconName() != "")
                {
                    String domain = "";
                    if(jt.modifier.getIconIndex().getIconName().substring(0, jt.modifier.getIconIndex().getIconName().indexOf(":") + 1) != "")
                        domain = jt.modifier.getIconIndex().getIconName().substring(0, jt.modifier.getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
                    else
                        domain = "minecraft";
                    String texture = jt.modifier.getIconIndex().getIconName().substring(jt.modifier.getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
                    ResourceLocation lava = new ResourceLocation(domain, "textures/items/" + texture);
                    Minecraft.getMinecraft().renderEngine.bindTexture(lava);
                    jt.modifier.getIconIndex().getInterpolatedU(0);
                    double minu = jt.modifier.getIconIndex().getInterpolatedU(-64);
                    double minv = jt.modifier.getIconIndex().getInterpolatedV(0);
                    double maxu = jt.modifier.getIconIndex().getInterpolatedU(256-64);
                    double maxv = jt.modifier.getIconIndex().getInterpolatedV(256);
                    GL11.glPushMatrix();
                    GL11.glScalef(1f / 16f, 1f / 16f, 1f / 16f);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    tessellator.startDrawingQuads();
                    for(float f=0; f<=1; f+=0.001){
                        tessellator.addVertexWithUV(8, 7.5, 3+f, minu, minv);
                        tessellator.addVertexWithUV(2.8, 7.5, 3+f, maxu, minv);
                        tessellator.addVertexWithUV(2.8, 13, 3+f, maxu, maxv);
                        tessellator.addVertexWithUV(8, 13, 3+f, minu, maxv);

                        tessellator.addVertexWithUV(2, 7.5, 3+f, minu, minv);
                        tessellator.addVertexWithUV(7.2, 7.5, 3+f, maxu, minv);
                        tessellator.addVertexWithUV(7.2, 13, 3+f, maxu, maxv);
                        tessellator.addVertexWithUV(2, 13, 3+f, minu, maxv);
                    }
                    tessellator.draw();
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
