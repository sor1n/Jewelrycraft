package darkknight.jewelrycraft.client;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.container.ContainerJewelryTab;
import darkknight.jewelrycraft.container.ContainerRingChest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiJewelry extends GuiContainer
{    
    public GuiJewelry(ContainerJewelryTab containerJewelryTab)
    {
        super(containerJewelryTab);
        xSize = 194;
        ySize = 166;
    }
    
    @Override
    public void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
    {
        GL11.glColor3f(1, 1, 1);        
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("jewelrycraft", "textures/gui/jewelry_tab.png"));        
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
    
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    }
}
