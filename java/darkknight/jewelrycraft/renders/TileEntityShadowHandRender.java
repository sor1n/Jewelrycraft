package darkknight.jewelrycraft.renders;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.model.ModelShadowHand;
import darkknight.jewelrycraft.tileentity.TileEntityShadowHand;

public class TileEntityShadowHandRender extends TileEntitySpecialRenderer
{
    ModelShadowHand model = new ModelShadowHand();
    String texture = "textures/tileentities/ShadowHand.png";
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        TileEntityShadowHand tile = (TileEntityShadowHand) te;
        
        ResourceLocation blockTexture = new ResourceLocation("jewelrycraft", texture);
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);
        
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        try
        {
            int block = tile.getBlockMetadata();
            if (block == 0) GL11.glRotatef(180F, 0.0F, 1.0F, 0.0F);
            else if (block == 1) GL11.glRotatef(225F, 0.0F, 1.0F, 0.0F);
            else if (block == 2) GL11.glRotatef(270F, 0.0F, 1.0F, 0.0F);
            else if (block == 3) GL11.glRotatef(315F, 0.0F, 1.0F, 0.0F);
            else if (block == 4) GL11.glRotatef(0F, 0.0F, 1.0F, 0.0F);
            else if (block == 5) GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
            else if (block == 6) GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            else if (block == 7) GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
        }
        catch (Exception e)
        {
        }
        if (tile != null) model.render((Entity) null, tile.grip, 0, 0, 0.0F, 0.0F, 0.0625F);
        if (tile != null && tile.hasObject && tile.object != null && tile.object != new ItemStack(Item.getItemById(0), 0, 0))
        {
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            EntityItem entityitem = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, tile.object);
            entityitem.hoverStart = 0.0F;
            tile.object.stackSize = 1;
            GL11.glTranslatef(0.0F, 0.4F, 0.2F);
            GL11.glRotatef(180F, 1F, 0F, 0F);
            GL11.glRotatef(90F, 1F, 0F, 0F);
            if (RenderManager.instance.options.fancyGraphics) RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            else
            {
                GL11.glRotatef(180F, 0F, 1F, 0F);
                RenderManager.instance.options.fancyGraphics = true;
                RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderManager.instance.options.fancyGraphics = false;
            }
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
    
    public void adjustLightFixture(World world, int i, int j, int k, Block block)
    {
        Tessellator tess = Tessellator.instance;
        float brightness = block.getLightOpacity(world, i, j, k);
        int skyLight = world.getLightBrightnessForSkyBlocks(i, j, k, 0);
        int modulousModifier = skyLight % 65536;
        int divModifier = skyLight / 65536;
        tess.setColorOpaque_F(brightness, brightness, brightness);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) modulousModifier, divModifier);
    }
}