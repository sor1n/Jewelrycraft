/**
 * 
 */
package darkknight.jewelrycraft.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * @author Sorin
 */
public class PotionStun extends PotionBase {
	protected PotionStun(int id, boolean isBad, int color) {
		super(id, isBad, color);
		this.setPotionName(Variables.MODID + ".potion.stun");
	}

	public void action(EntityLivingBase entity) {
		entity.motionX *= 0D;
		entity.motionZ *= 0D;
		entity.motionY *= 0D;
		entity.isSwingInProgress = false;
		entity.moveForward = 0F;
		entity.moveStrafing = 0F;
		entity.setAIMoveSpeed(0F);
		entity.limbSwing = 0F;
		entity.limbSwingAmount = 0F;
		entity.swingProgressInt = 0;
		entity.rotationPitch = entity.prevRotationPitch;
		entity.rotationYaw = entity.prevRotationYaw;
		entity.worldObj.spawnParticle("spell", entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ, 0.0D, 0.3D, 0.0D);
		if (entity.getActivePotionEffect(PotionList.stun).getDuration() == 0) entity.removePotionEffect(PotionList.stun.id);
	}
}
