package darkknight.jewelrycraft.renders;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class ItemRender implements IItemRenderer
{
    TileEntitySpecialRenderer render;
    public TileEntity entity;
    ModelBase model;
    
    public ItemRender(TileEntitySpecialRenderer render, TileEntity entity, ModelBase model)
    {
        this.entity = entity;
        this.render = render;
        this.model = model;
    }
    
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }
    
    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }
    
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data)
    {
        if (type == IItemRenderer.ItemRenderType.ENTITY) {
            GL11.glRotatef(180f, 0f, 1f, 0f);
            GL11.glTranslatef(-0.5f, -0.5f, -0.4f);
        }
        this.render.renderTileEntityAt(this.entity, 0.0D, 0.0D, 0.0D, 0.0F);
    }
    
}
