package darkknight.jewelrycraft.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderPlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.model.ModelMask;
import darkknight.jewelrycraft.tileentity.renders.MaskRender;

public class PlayerRenderHandler
{
    ModelMask maskModel = new ModelMask();
    MaskRender mask = new MaskRender();
    
    @SubscribeEvent
    public void renderScreen(RenderPlayerEvent.Pre event)
    {
//        if (event.entityPlayer.equals(Minecraft.getMinecraft().thePlayer)) mask.doRender(event.entityPlayer, 0F, 0F, 0F, event.entityPlayer.rotationPitch, event.entityPlayer.rotationYawHead);
        // mask.renderTileEntityAt(null, event.x, event.entity.rotationPitch, event.entity.rotationYawHead, 0F);
    }
}
