package darkknight.jewelrycraft.entities.renders;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class RenderHelper
{
    public static void rotateIfSneaking(EntityPlayer player)
    {
        if (player.isSneaking()) applySneakingRotation();
    }
    
    public static void applySneakingRotation()
    {
        GL11.glRotatef(28.64789F, 1.0F, 0.0F, 0.0F);
    }
    
    public static void translateToHeadLevel(EntityPlayer player)
    {
        GL11.glTranslated(0, (player != Minecraft.getMinecraft().thePlayer ? 1.62F : 0F) - player.getDefaultEyeHeight() + (player.isSneaking() ? 0.0625 : 0), 0);
    }
}