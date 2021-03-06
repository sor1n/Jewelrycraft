package darkknight.jewelrycraft.client.gui;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.client.TabJewelry;
import darkknight.jewelrycraft.client.TabRegistry;
import darkknight.jewelrycraft.client.gui.container.ContainerJewelryTab;
import darkknight.jewelrycraft.events.KeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;

public class GuiJewelry extends GuiContainer
{
    ResourceLocation texture;
    
    /**
     * @param containerJewelryTab
     * @param texture
     */
    public GuiJewelry(ContainerJewelryTab containerJewelryTab, ResourceLocation texture)
    {
        super(containerJewelryTab);
        xSize = 194;
        ySize = 166;
        this.texture = texture;
    }
    
    /**
     * @param f
     * @param mouseX
     * @param mouseY
     */
    @Override
    public void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
    {
        GL11.glColor3f(1, 1, 1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GL11.glPushMatrix();
        GuiInventory.func_147046_a(guiLeft - 24, guiTop + 124, 60, (float)(guiLeft - 24) - mouseX, (float)(guiTop + 124 - 90) - mouseY, this.mc.thePlayer);
        GL11.glPopMatrix();
    }
    
    /**
     * @param mouseX
     * @param mouseY
     */
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {}
    
    /**
     * @param charecter
     * @param key
     */
    @Override
    protected void keyTyped(char charecter, int key)
    {
        super.keyTyped(charecter, key);
        if (key == KeyBindings.inventory.getKeyCode()) mc.thePlayer.closeScreen();
    }

    @Override
    public void initGui ()
    {
        super.initGui();
        int cornerX = guiLeft;
        int cornerY = guiTop;
        this.buttonList.clear();
        TabRegistry.updateTabValues(cornerX, cornerY, TabJewelry.class);
        TabRegistry.addTabsToList(this.buttonList);
    }
}
