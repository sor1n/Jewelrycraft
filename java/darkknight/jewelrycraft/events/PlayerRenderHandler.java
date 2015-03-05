package darkknight.jewelrycraft.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.curses.Curse;
import darkknight.jewelrycraft.entities.renders.RenderHelper;
import darkknight.jewelrycraft.lib.Reference;
import darkknight.jewelrycraft.model.ModelMask;
import darkknight.jewelrycraft.tileentity.renders.MaskRender;
import darkknight.jewelrycraft.util.PlayerUtils;

public class PlayerRenderHandler
{
    ModelMask maskModel = new ModelMask();
    MaskRender mask = new MaskRender();
    
    @SubscribeEvent
    public void renderScreen(RenderPlayerEvent.Specials.Post event)
    {
        EntityPlayer player = event.entityPlayer;
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
        if (playerInfo.getInteger(Reference.MODNAME + ":" + "Greed") >= 0){
            float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * event.partialRenderTick;
            float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * event.partialRenderTick;
            float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * event.partialRenderTick;
            GL11.glPushMatrix();
            GL11.glRotatef(yawOffset, 0, -1, 0);
            GL11.glRotatef(yaw - 90, 0, 1, 0);
            GL11.glRotatef(pitch, 0, 0, -1);
            GL11.glRotatef(90F, 0, 1F, 0F);
            RenderHelper.translateToHeadLevel(player);
            GL11.glScalef(1.6f, 1.6f, 1.6f);
            GL11.glTranslatef(-0.25F, -0.25F, -0.25F);
            mask.doRender(event.entityPlayer, 0F, 0F, 0F, 0F, 0F);
            GL11.glPopMatrix();
        }
    }
}
