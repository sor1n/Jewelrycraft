package darkknight.jewelrycraft.curses;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.item.render.MaskRender;
import darkknight.jewelrycraft.util.Variables;

public class CursePentagram extends Curse
{
    ResourceLocation curse = new ResourceLocation(Variables.MODID, "textures/gui/curses.png");
    float rot = 0F;
    
    public CursePentagram(int id, String name, int text)
    {
        super(id, name, text);
    }
    
    @Override
    public void attackedByPlayerAction(World world, EntityPlayer player, Entity target)
    {}
    
    public void playerRender(EntityPlayer player, RenderPlayerEvent.Specials.Post event)
    {
        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        Tessellator tessellator = Tessellator.instance;
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        texturemanager.bindTexture(curse);
        GL11.glRotatef(rot, 0F, 1F, 0F);
        GL11.glScalef(0.1F, 0.1F, 0.1F);
        GL11.glTranslatef(-16F, 15F, -16F);
        GL11.glRotatef(90F, 1F, 0F, 0F);
        rot+=3F;
        if (rot > 360F) rot = 0F;
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        int x = 0;
        int y = 0;
        int u = 32 * 7;
        int v = 0;
        int width = 32;
        int height = 32;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + height), (double)0, (double)((float)(u + 0) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), (double)0, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + 0), (double)0, (double)((float)(u + width) * f), (double)((float)(v + 0) * f1));
        tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)0, (double)((float)(u + 0) * f), (double)((float)(v + 0) * f1));
        tessellator.draw();
        GL11.glPopMatrix();
    }
    
    public String getDescription()
    {
        return "The Dark Lord has misterious ways of showing you his love.";
    }
}
