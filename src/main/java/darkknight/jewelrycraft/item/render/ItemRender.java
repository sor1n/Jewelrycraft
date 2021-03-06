package darkknight.jewelrycraft.item.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.util.JewelryNBT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.src.FMLRenderAccessLibrary;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ItemRender implements IItemRenderer
{
    TileEntitySpecialRenderer render;
    public TileEntity entity;
    ModelBase model;
    private RenderBlocks renderBlocksIr = new RenderBlocks();
    private Minecraft mc = Minecraft.getMinecraft();
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private float tran = 0F;
    
    /**
     * @param render
     * @param entity
     * @param model
     */
    public ItemRender(TileEntitySpecialRenderer render, TileEntity entity, ModelBase model)
    {
        this.entity = entity;
        this.render = render;
        this.model = model;
    }
    
    public ItemRender()
    {}
    
    /**
     * @param item
     * @param type
     * @return
     */
    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return true;
    }
    
    /**
     * @param type
     * @param item
     * @param helper
     * @return
     */
    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        return true;
    }
    
    /**
     * @param type
     * @param item
     * @param data
     */
    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object ... data)
    {
        if (item != null) {
            if (render != null && entity != null && JewelryNBT.item(item) == null) {
                if (type == IItemRenderer.ItemRenderType.ENTITY) {
                    GL11.glRotatef(180f, 0f, 1f, 0f);
                    GL11.glTranslatef(-0.5f, -0.5f, -0.4f);
                }
                render.renderTileEntityAt(entity, 0.0D, 0.0D, 0.0D, 0.0F);
            }else
                if (JewelryNBT.item(item) != null) {
                GL11.glPushMatrix();
                GL11.glColor3f(1F, 1F, 0F);
                if (Item.getItemById(Integer.valueOf(item.getTagCompound().getTag("target").toString().split(",")[0].substring(4).replace("s", ""))) != null)
                    renderItem(Minecraft.getMinecraft().thePlayer, JewelryNBT.item(item), 0, type);
                else renderItem(Minecraft.getMinecraft().thePlayer, new ItemStack(Blocks.end_portal), 0, type);
                GL11.glPopMatrix();
            }else{
                GL11.glPushMatrix();
                GL11.glColor3f(1F, 1F, 0F);
                renderItem(Minecraft.getMinecraft().thePlayer, new ItemStack(Blocks.end_portal), 0, type);
                GL11.glPopMatrix();
            }
        }
    }
    
    public void renderItem(EntityLivingBase entity, ItemStack itemStack, int meta, ItemRenderType type)
    {
        GL11.glPushMatrix();
        TextureManager texturemanager = this.mc.getTextureManager();
        if (itemStack != null) {
            Item item = itemStack.getItem();
            Block block = Block.getBlockFromItem(item);
            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemStack, type);
            if (customRenderer != null) {
                texturemanager.bindTexture(texturemanager.getResourceLocation(itemStack.getItemSpriteNumber()));
                if (type.equals(type.EQUIPPED))
                    GL11.glTranslatef(0.7F, 0.55F, 0.55F);
                ForgeHooksClient.renderEquippedItem(type, customRenderer, renderBlocksIr, entity, itemStack);
            }else
                if (itemStack.getItemSpriteNumber() == 0 && item instanceof ItemBlock && RenderBlocks.renderItemIn3d(block.getRenderType())) {
                texturemanager.bindTexture(texturemanager.getResourceLocation(0));
                if (type.equals(type.EQUIPPED))
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                if (itemStack != null && block != null && block.getRenderBlockPass() != 0) {
                    GL11.glDepthMask(false);
                    renderBlockAsItem(block, itemStack.getItemDamage(), 1.0F);
                    GL11.glDepthMask(true);
                }else{
                    renderBlockAsItem(block, itemStack.getItemDamage(), 1.0F);
                }
            }else{
                IIcon iicon = itemStack.getIconIndex();
                if (iicon == null) {
                    GL11.glPopMatrix();
                    return;
                }
                texturemanager.bindTexture(texturemanager.getResourceLocation(itemStack.getItemSpriteNumber()));
                TextureUtil.func_152777_a(false, false, 1.0F);
                Tessellator tessellator = Tessellator.instance;
                float f = iicon.getMinU();
                float f1 = iicon.getMaxU();
                float f2 = iicon.getMinV();
                float f3 = iicon.getMaxV();
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                float f6 = 1.6F;
                GL11.glScalef(f6, f6, f6);
                if (type.equals(type.ENTITY)) {
                    GL11.glTranslatef(0.0F, 0.0265F, 0.0F);
                    GL11.glRotatef(-30F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(45F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(0.625F, 0.625F, 0.625F);
                }
                if (!type.equals(type.EQUIPPED_FIRST_PERSON)) {
                    GL11.glRotatef(45f, 0f, 1f, 0f);
                    GL11.glRotatef(180f, 0f, 1f, 0f);
                    GL11.glRotatef(30f, 1f, 0f, 0f);
                    if (type.equals(type.EQUIPPED)) {
                        GL11.glRotatef(35f, 1f, 0f, 0f);
                        GL11.glTranslatef(0F, -0.15F, -0.6F);
                    }
                    GL11.glTranslatef(-0.5F, -0.5F, 0.0F);
                }else
                    if (type.equals(type.EQUIPPED_FIRST_PERSON)) {
                    GL11.glTranslatef(-0.35F, 0.4F, 0.93F);
                    GL11.glRotatef(45f, 0f, 1f, 0f);
                    GL11.glRotatef(-25f, 0f, 0f, 1f);
                }
                if (itemStack.getItem().requiresMultipleRenderPasses()) {
                    for(int x = 0; x < itemStack.getItem().getRenderPasses(itemStack.getItemDamage()); x++){
                        iicon = itemStack.getItem().getIcon(itemStack, x);
                        f = iicon.getMinU();
                        f1 = iicon.getMaxU();
                        f2 = iicon.getMinV();
                        f3 = iicon.getMaxV();
                        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
                    }
                }else{
                    ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);
                }
                GL11.glDepthFunc(GL11.GL_EQUAL);
                renderShine(tessellator, true);
                GL11.glPushMatrix();
                float f8 = 0.325F;
                GL11.glScalef(f8, f8, f8);
                float f9 = (float)(Minecraft.getSystemTime() % 30000L) / 30000.0F * 8.0F;
                GL11.glTranslatef(-f9, 0.0F, 0.0F);
                GL11.glRotatef(30.0F, 0.0F, 0.0F, 1.0F);
                ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
                GL11.glPopMatrix();
                GL11.glMatrixMode(GL11.GL_MODELVIEW);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                texturemanager.bindTexture(texturemanager.getResourceLocation(itemStack.getItemSpriteNumber()));
                TextureUtil.func_147945_b();
            }
        }
        GL11.glPopMatrix();
    }
    
    public void renderBlockAsItem(Block block, int damage, float luminacy)
    {
        Tessellator tessellator = Tessellator.instance;
        if (block == Blocks.dispenser || block == Blocks.dropper || block == Blocks.furnace)
            damage = 3;
        int j;
        float f1;
        float f2;
        GL11.glColor4f(1F, 1F, 0F, 1.0F);
        j = block.getRenderType();
        renderBlocksIr.setRenderBoundsFromBlock(block);
        int k;
        if (j != 0 && j != 31 && j != 39 && j != 16 && j != 26) {
            GL11.glColor4f(1F, 1F, 0F, 1.0F);
            if (j == 13) {
                // Cactus
                block.setBlockBoundsForItemRender();
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                f1 = 0.0625F;
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                renderBlocksIr.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 0));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                renderBlocksIr.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 1));
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, -1.0F);
                tessellator.addTranslation(0.0F, 0.0F, f1);
                renderBlocksIr.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 2));
                tessellator.addTranslation(0.0F, 0.0F, -f1);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 0.0F, 1.0F);
                tessellator.addTranslation(0.0F, 0.0F, -f1);
                renderBlocksIr.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 3));
                tessellator.addTranslation(0.0F, 0.0F, f1);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                tessellator.addTranslation(f1, 0.0F, 0.0F);
                renderBlocksIr.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 4));
                tessellator.addTranslation(-f1, 0.0F, 0.0F);
                tessellator.draw();
                tessellator.startDrawingQuads();
                tessellator.setNormal(1.0F, 0.0F, 0.0F);
                tessellator.addTranslation(-f1, 0.0F, 0.0F);
                renderBlocksIr.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 5));
                tessellator.addTranslation(f1, 0.0F, 0.0F);
                tessellator.draw();
                GL11.glTranslatef(0.0625F, 0.0F, 0.0625F);
                GL11.glScalef(0.9375F - 0.0625F, 1F, 0.9375F - 0.0625F);
                shinyBlock(tessellator, false);
            }else
                if (j == 22) {
                // Chest
                GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                TileEntityRendererChestHelper.instance.renderChest(block, damage, luminacy);
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                GL11.glTranslatef(0.0625F, 0.0F, 0.0625F);
                GL11.glScalef(0.9375F - 0.0625F, 0.875F, 0.9375F - 0.0625F);
                shinyBlock(tessellator, false);
            }else
                    if (j == 10) {
                // Stairs
                for(k = 0; k < 2; ++k){
                    if (k == 0) {
                        renderBlocksIr.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
                    }
                    if (k == 1) {
                        renderBlocksIr.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
                    }
                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderBlocksIr.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderBlocksIr.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderBlocksIr.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderBlocksIr.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
            }else
                        if (j == 27) {
                // Dragon Egg
                k = 0;
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tessellator.startDrawingQuads();
                for(int l = 0; l < 8; ++l){
                    byte b0 = 0;
                    byte b1 = 1;
                    if (l == 0) {
                        b0 = 2;
                    }
                    if (l == 1) {
                        b0 = 3;
                    }
                    if (l == 2) {
                        b0 = 4;
                    }
                    if (l == 3) {
                        b0 = 5;
                        b1 = 2;
                    }
                    if (l == 4) {
                        b0 = 6;
                        b1 = 3;
                    }
                    if (l == 5) {
                        b0 = 7;
                        b1 = 5;
                    }
                    if (l == 6) {
                        b0 = 6;
                        b1 = 2;
                    }
                    if (l == 7) {
                        b0 = 3;
                    }
                    float f5 = (float)b0 / 16.0F;
                    float f6 = 1.0F - (float)k / 16.0F;
                    float f7 = 1.0F - (float)(k + b1) / 16.0F;
                    k += b1;
                    renderBlocksIr.setRenderBounds((double)(0.5F - f5), (double)f7, (double)(0.5F - f5), (double)(0.5F + f5), (double)f6, (double)(0.5F + f5));
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderBlocksIr.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 0));
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderBlocksIr.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 1));
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderBlocksIr.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 2));
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderBlocksIr.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 3));
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 4));
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 5));
                }
                tessellator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                renderBlocksIr.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }else
                            if (j == 11) {
                // Fence
                for(k = 0; k < 4; ++k){
                    f2 = 0.125F;
                    if (k == 0) {
                        renderBlocksIr.setRenderBounds((double)(0.5F - f2), 0.0D, 0.0D, (double)(0.5F + f2), 1.0D, (double)(f2 * 2.0F));
                    }
                    if (k == 1) {
                        renderBlocksIr.setRenderBounds((double)(0.5F - f2), 0.0D, (double)(1.0F - f2 * 2.0F), (double)(0.5F + f2), 1.0D, 1.0D);
                    }
                    f2 = 0.0625F;
                    if (k == 2) {
                        renderBlocksIr.setRenderBounds((double)(0.5F - f2), (double)(1.0F - f2 * 3.0F), (double)(-f2 * 2.0F), (double)(0.5F + f2), (double)(1.0F - f2), (double)(1.0F + f2 * 2.0F));
                    }
                    if (k == 3) {
                        renderBlocksIr.setRenderBounds((double)(0.5F - f2), (double)(0.5F - f2 * 3.0F), (double)(-f2 * 2.0F), (double)(0.5F + f2), (double)(0.5F - f2), (double)(1.0F + f2 * 2.0F));
                    }
                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderBlocksIr.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderBlocksIr.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderBlocksIr.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderBlocksIr.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
                renderBlocksIr.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }else
                                if (j == 21) {
                // Fence Gate
                for(k = 0; k < 3; ++k){
                    f2 = 0.0625F;
                    if (k == 0) {
                        renderBlocksIr.setRenderBounds((double)(0.5F - f2), 0.30000001192092896D, 0.0D, (double)(0.5F + f2), 1.0D, (double)(f2 * 2.0F));
                    }
                    if (k == 1) {
                        renderBlocksIr.setRenderBounds((double)(0.5F - f2), 0.30000001192092896D, (double)(1.0F - f2 * 2.0F), (double)(0.5F + f2), 1.0D, 1.0D);
                    }
                    f2 = 0.0625F;
                    if (k == 2) {
                        renderBlocksIr.setRenderBounds((double)(0.5F - f2), 0.5D, 0.0D, (double)(0.5F + f2), (double)(1.0F - f2), 1.0D);
                    }
                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderBlocksIr.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 0));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderBlocksIr.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 1));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderBlocksIr.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 2));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderBlocksIr.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 3));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 4));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSide(block, 5));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
            }else
                                    if (j == 32) {
                // Wall
                for(k = 0; k < 2; ++k){
                    if (k == 0) {
                        renderBlocksIr.setRenderBounds(0.0D, 0.0D, 0.3125D, 1.0D, 0.8125D, 0.6875D);
                    }
                    if (k == 1) {
                        renderBlocksIr.setRenderBounds(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D);
                    }
                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderBlocksIr.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 0, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderBlocksIr.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 1, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderBlocksIr.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 2, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderBlocksIr.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 3, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 4, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 5, damage));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
                renderBlocksIr.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }else
                                        if (j == 35) {
                // Anvil
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                renderBlocksIr.renderBlockAnvilOrient((BlockAnvil)block, 0, 0, 0, damage << 2, true);
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }else
                                            if (j == 34) {
                // Beacon
                for(k = 0; k < 3; ++k){
                    if (k == 0) {
                        renderBlocksIr.setRenderBounds(0.125D, 0.0D, 0.125D, 0.875D, 0.1875D, 0.875D);
                        renderBlocksIr.setOverrideBlockTexture(renderBlocksIr.getBlockIcon(Blocks.obsidian));
                    }else
                        if (k == 1) {
                        renderBlocksIr.setRenderBounds(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.875D, 0.8125D);
                        renderBlocksIr.setOverrideBlockTexture(renderBlocksIr.getBlockIcon(Blocks.beacon));
                    }else
                            if (k == 2) {
                        renderBlocksIr.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                        renderBlocksIr.setOverrideBlockTexture(renderBlocksIr.getBlockIcon(Blocks.glass));
                    }
                    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, -1.0F, 0.0F);
                    renderBlocksIr.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 0, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 1.0F, 0.0F);
                    renderBlocksIr.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 1, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, -1.0F);
                    renderBlocksIr.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 2, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(0.0F, 0.0F, 1.0F);
                    renderBlocksIr.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 3, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 4, damage));
                    tessellator.draw();
                    tessellator.startDrawingQuads();
                    tessellator.setNormal(1.0F, 0.0F, 0.0F);
                    renderBlocksIr.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 5, damage));
                    tessellator.draw();
                    GL11.glTranslatef(0.5F, 0.5F, 0.5F);
                }
                renderBlocksIr.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
                renderBlocksIr.clearOverrideBlockTexture();
                GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                shinyBlock(tessellator, false);
            }else{
                try{
                    FMLRenderAccessLibrary.renderInventoryBlock(renderBlocksIr, block, damage, j);
                }
                catch(Exception e){
                    JewelrycraftMod.logger.error("Something went wrong with rendering the item");
                    e.printStackTrace();
                }
            }
        }else{
            if (j == 16) {
                damage = 1;
            }
            block.setBlockBoundsForItemRender();
            renderBlocksIr.setRenderBoundsFromBlock(block);
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1.0F, 0.0F);
            renderBlocksIr.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 0, damage));
            tessellator.draw();
            GL11.glColor4f(1F, 1F, 0F, 1.0F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderBlocksIr.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 1, damage));
            tessellator.draw();
            GL11.glColor4f(1F, 1F, 0F, 1.0F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1.0F);
            renderBlocksIr.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 2, damage));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderBlocksIr.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 3, damage));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1.0F, 0.0F, 0.0F);
            renderBlocksIr.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 4, damage));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderBlocksIr.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderBlocksIr.getBlockIconFromSideAndMetadata(block, 5, damage));
            tessellator.draw();
            GL11.glTranslatef((float)block.getBlockBoundsMinX(), (float)block.getBlockBoundsMinY(), (float)block.getBlockBoundsMinZ());
            GL11.glScalef((float)block.getBlockBoundsMaxX() - (float)block.getBlockBoundsMinX(), (float)block.getBlockBoundsMaxY() - (float)block.getBlockBoundsMinY(), (float)block.getBlockBoundsMaxZ() - (float)block.getBlockBoundsMinZ());
            shinyBlock(tessellator, false);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
    }
    
    public void renderShine(Tessellator tessellator, boolean autoAnimate)
    {
        TextureManager texturemanager = this.mc.getTextureManager();
        texturemanager.bindTexture(RES_ITEM_GLINT);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(768, 1, 1, 0);
        GL11.glMatrixMode(GL11.GL_TEXTURE);
        GL11.glPushMatrix();
        float f8 = 0.325F;
        GL11.glScalef(f8, f8, f8);
        if (autoAnimate)
            GL11.glTranslatef(17F, 0.0F, 0.0F);
        else{
            GL11.glTranslatef(tran, 0.0F, 0.0F);
            tran += 0.0004F;
            if (tran >= 360F)
                tran = 0F;
        }
        GL11.glRotatef(-30.0F, 0.0F, 0.0F, 1.0F);
        GL11.glColor3f(1F, 1F, 0F);
        ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.001F);
        GL11.glPopMatrix();
    }
    
    public void shinyBlock(Tessellator tessellator, boolean autoAnimate)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0F, 0.0F, -0.001F);
        renderShine(tessellator, autoAnimate);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(1.0F, 0.0F, 1.0001F);
        GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
        renderShine(tessellator, autoAnimate);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.001F, 0.0F, 0.0F);
        GL11.glRotatef(90.0F, 0.0F, -1.0F, 0.0F);
        renderShine(tessellator, autoAnimate);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(1.001F, 0.0F, 1.0F);
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        renderShine(tessellator, autoAnimate);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.001F, 0.0F);
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        renderShine(tessellator, autoAnimate);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
        renderShine(tessellator, autoAnimate);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glPopMatrix();
    }
}
