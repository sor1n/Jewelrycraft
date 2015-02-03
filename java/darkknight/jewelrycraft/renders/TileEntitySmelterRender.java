package darkknight.jewelrycraft.renders;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.model.ModelSmelter;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;
import darkknight.jewelrycraft.util.JewelryNBT;

public class TileEntitySmelterRender extends TileEntitySpecialRenderer
{
    ModelSmelter modelSmelter = new ModelSmelter();
    
    public static final float p = 1 / 16, p3 = 3 * p, p13 = 13 * p, p15 = 15 * p;
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        String texture = "textures/tileentities/Smelter.png";
        ResourceLocation blockTexture = new ResourceLocation("jewelrycraft", texture);
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);
        TileEntitySmelter st = (TileEntitySmelter) te;
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        try
        {
            int block = te.getBlockMetadata();
            if (block == 1) GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            else if (block == 2)
            {
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
            }
            else if (block == 3)
            {
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
            }
        }
        catch (Exception e)
        {
        }
        modelSmelter.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        
        if (scale != 0)
        {
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_LIGHTING);
            if (JewelrycraftMod.fancyRender)
            {
                GL11.glEnable(GL11.GL_BLEND);
                OpenGlHelper.glBlendFunc(1, 1, 0, 0);
            }
            EntityItem entityitem = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, new ItemStack(Blocks.lava, 1, 1));
            entityitem.hoverStart = 0.0F;
            GL11.glTranslatef(-0F, 1.25F, -0.345F);
            GL11.glScalef(1.2F, 1.0F, 1.7F);
            GL11.glRotatef(90F, 1F, 0F, 0f);
            RenderItem.renderInFrame = true;
            int i = 15728880;
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
            RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
            RenderItem.renderInFrame = false;
            if (JewelrycraftMod.fancyRender) GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        
        if (st != null)
        {
            if (st.hasMetal && st.metal != null && st.metal.getItem() != null)
            {
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                EntityItem metal = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, st.metal);
                metal.getEntityItem().stackSize = 1;
                metal.hoverStart = 0.0F;
                
                GL11.glRotatef(-50F, 1F, 0F, 0F);
                GL11.glRotatef(-50F, 0F, 0F, 1F);
                GL11.glRotatef(180F, 1F, 0F, 0F);
                GL11.glScalef(0.5F, 0.5F, 0.5F);
                GL11.glTranslatef(-0.9F, -0.9F, -1.6F);
                if (RenderManager.instance.options.fancyGraphics) RenderManager.instance.renderEntityWithPosYaw(metal, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                else
                {
                    RenderManager.instance.options.fancyGraphics = true;
                    RenderManager.instance.renderEntityWithPosYaw(metal, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                    RenderManager.instance.options.fancyGraphics = false;
                }
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
            if (st.hasMoltenMetal && st.moltenMetal != null && st.moltenMetal.getItem() != null)
            {
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                if (JewelrycraftMod.fancyRender)
                {
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(1, 1, 0, 0);
                }
                ItemStack metal = new ItemStack(ItemList.metal);
                ItemStack ingot = st.moltenMetal.copy();
                if (Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.stained_glass) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.stained_hardened_clay) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.wool) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.carpet)) ingot.setItemDamage(15 - ingot.getItemDamage());
                JewelryNBT.addMetal(metal, ingot);
                EntityItem moltenMetal = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, metal);
                moltenMetal.getEntityItem().stackSize = 1;
                moltenMetal.hoverStart = 0.0F;
                
                GL11.glTranslatef(-0F, 1.00f - .4F * st.quantity, -0.14F);
                GL11.glScalef(0.71F, 1F, 0.84F);
                GL11.glRotatef(90F, 1F, 0F, 0f);
                RenderItem.renderInFrame = true;
                RenderManager.instance.renderEntityWithPosYaw(moltenMetal, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;
                if (JewelrycraftMod.fancyRender) GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
        }
        GL11.glPopMatrix();
    }
}
