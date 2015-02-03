package darkknight.jewelrycraft.renders;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.model.ModelMolder;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.util.JewelryNBT;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TileEntityMolderRender extends TileEntitySpecialRenderer
{
    ModelMolder modelMolder = new ModelMolder();
    
    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        TileEntityMolder me = (TileEntityMolder) te;
        String texture = "textures/tileentities/Molder.png";
        ResourceLocation blockTexture = new ResourceLocation("jewelrycraft", texture);
        Minecraft.getMinecraft().renderEngine.bindTexture(blockTexture);
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        try
        {
            int block = me.getBlockMetadata();
            if (block == 1) GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
            else if (block == 2){
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
            }
            else if (block == 3){
                GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(180F, 1.0F, 0.0F, 1.0F);
            }
        }
        catch (Exception e)
        {
        }
        modelMolder.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
        if (me != null)
        {
            if (me.hasMold)
            {
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                EntityItem entityitem = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, me.mold);
                entityitem.getEntityItem().stackSize = 1;
                entityitem.hoverStart = 0.0F;
                GL11.glTranslatef(0F, 1.312F, -0.25F);
                GL11.glScalef(1.25F, 1.0F, 1.25F);
                GL11.glRotatef(90F, 1F, 0F, 0f);
                RenderItem.renderInFrame = true;
                if (entityitem != null) RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
            if (me.hasJewelBase && me.jewelBase.getIconIndex() != null && me.jewelBase.getIconIndex().getIconName() != "")
            {
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                EntityItem entityitem = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, me.jewelBase);
                entityitem.getEntityItem().stackSize = 1;
                entityitem.hoverStart = 0.0F;
                GL11.glTranslatef(0F, 1.312F, -0.25F);
                GL11.glScalef(1.25F, 1.0F, 1.25F);
                GL11.glRotatef(90F, 1F, 0F, 0f);
                RenderItem.renderInFrame = true;
                if (entityitem != null) RenderManager.instance.renderEntityWithPosYaw(entityitem, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
                RenderItem.renderInFrame = false;
                GL11.glColor4f(1, 1F, 1F, 1.0F);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
            }
            if (me.hasMoltenMetal && me.moltenMetal != null && me.moltenMetal != new ItemStack(Item.getItemById(0), 0, 0))
            {
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_LIGHTING);
                if (JewelrycraftMod.fancyRender)
                {
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(1, 1, 0, 0);
                }
                ItemStack metal = new ItemStack(ItemList.metal);
                ItemStack ingot = me.moltenMetal.copy();
                if (Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.stained_glass) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.stained_hardened_clay) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.wool) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.carpet)) ingot.setItemDamage(15 - ingot.getItemDamage());
                JewelryNBT.addMetal(metal, ingot);
                EntityItem moltenMetal = new EntityItem(te.getWorldObj(), 0.0D, 0.0D, 0.0D, metal);
                moltenMetal.getEntityItem().stackSize = 1;
                moltenMetal.hoverStart = 0.0F;
                
                GL11.glTranslatef(-0F, 1.33f - 0.005f * me.quantity, -0.29F);
                GL11.glScalef(1.1F, 1.0F, 1.4F);
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
