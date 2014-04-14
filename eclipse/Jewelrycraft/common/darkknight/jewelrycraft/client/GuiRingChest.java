package darkknight.jewelrycraft.client;

import org.lwjgl.opengl.GL11;

import darkknight.jewelrycraft.container.ContainerRingChest;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiRingChest extends GuiContainer
{
    public ContainerRingChest container;
    
    public GuiRingChest(ContainerRingChest container)
    {
        super(container);
        this.container = container;
        xSize = 176;
        ySize = 166;
    }
    
    @Override
    public void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY)
    {
        GL11.glColor3f(1, 1, 1);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("jewelrycraft", "textures/gui/chest_ring.png"));
        
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
    
    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        fontRendererObj.drawString("Linked Chest", 8, 6, 0x404040);
        fontRendererObj.drawString("Inventory", 8, 72, 0x404040);
    }
}
