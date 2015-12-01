package darkknight.jewelrycraft.client;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 * @author TinkersCOnstruct
 */
@SideOnly(Side.CLIENT)
public abstract class AbstractTab extends GuiButton
{
    ResourceLocation texture = new ResourceLocation(Variables.MODID, "textures/gui/hearts.png");
    ItemStack renderStack;
    RenderItem itemRenderer = new RenderItem();

    public AbstractTab(int id, int posX, int posY, ItemStack renderStack)
    {
        super(id, posX, posY, 18, 18, "");
        this.renderStack = renderStack;
    }

    @Override
    public void drawButton (Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int xOffset = this.enabled ? 0 : 8;

            mc.renderEngine.bindTexture(this.texture);
            this.drawTexturedModalRect(this.xPosition, yPosition, 144 + xOffset, 32, 18, 18);

            GL11.glPushMatrix();
            RenderHelper.enableGUIStandardItemLighting();
            this.zLevel = 100.0F;
            this.itemRenderer.zLevel = 100.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            this.itemRenderer.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, renderStack, xPosition + 1, yPosition + 1);
            this.itemRenderer.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, renderStack, xPosition + 1, yPosition + 1);
            GL11.glDisable(GL11.GL_LIGHTING);
            this.itemRenderer.zLevel = 0.0F;
            this.zLevel = 0.0F;
            RenderHelper.disableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean mousePressed (Minecraft mc, int mouseX, int mouseY)
    {
        boolean inWindow = this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        if (inWindow) this.onTabClicked();
        return inWindow;
    }

    public abstract void onTabClicked ();

    public abstract boolean shouldAddToList ();
}