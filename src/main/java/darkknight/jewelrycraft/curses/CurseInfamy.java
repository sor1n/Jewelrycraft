package darkknight.jewelrycraft.curses;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.entities.EntityHalfHeart;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.entities.renders.RenderHelper;
import darkknight.jewelrycraft.item.render.MaskRender;
import darkknight.jewelrycraft.network.PacketSendClientPlayerInfo;
import darkknight.jewelrycraft.network.PacketSendServerPlayersInfo;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class CurseInfamy extends Curse
{
    public CurseInfamy(String name, int txtID, String pack)
    {
        super(name, txtID, pack);
    }
    
    @Override
    public void attackedByPlayerAction(LivingAttackEvent event, World world, EntityPlayer player, Entity target)
    {
        if (rand.nextInt(5) == 0 && !world.isRemote && !(target instanceof EntityMob) && target instanceof EntityLiving && !(target instanceof EntityHeart) && !(target instanceof EntityHalfHeart) && target.canAttackWithItem()){
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            playerInfo.setFloat("BlackHeart", playerInfo.getFloat("BlackHeart") + 1.0F);
            if (player.getMaxHealth() >= 3F){
                player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(player.getMaxHealth() - 1.0F);
                player.setHealth(player.getHealth() - 1.0F);
            }
            JewelrycraftUtil.addCursePoints(player, 10);
			JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
			JewelrycraftMod.netWrapper.sendTo(new PacketSendClientPlayerInfo(playerInfo), (EntityPlayerMP)player);
        }
    }
    
    @Override
    public void playerRender(EntityPlayer player, RenderPlayerEvent.Specials.Post event)
    {
        MaskRender mask = new MaskRender();
        float yaw = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * event.partialRenderTick;
        float yawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * event.partialRenderTick;
        float pitch = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * event.partialRenderTick;
        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
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
    
    public String getDescription()
    {
        return StatCollector.translateToLocal("curse." + Variables.MODID + ".infamy.description");
    }
    
	@Override
	public String getDisplayName() 
	{
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".infamy");
	}

    @Override
    public boolean canCurseBeActivated()
    {
        return ConfigHandler.CURSE_INFAMY;
    }
}
