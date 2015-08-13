package darkknight.jewelrycraft.effects;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import darkknight.jewelrycraft.api.ModifierEffects;
import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

public class EffectBlazePowder extends ModifierEffects {
	public EffectBlazePowder() {
		super(new ItemStack(Items.blaze_powder));
	}

	@Override
	public void action(ItemStack item, EntityPlayer player, Item jewelry) {
		if (jewelry instanceof ItemNecklace) {
			// Positive for necklace
			if (player.isBurning() && rand.nextInt(JewelryNBT.numberOfModifiers(item)) == 0) player.extinguish();
			// Negative for necklace
			if (player.isInWater()) player.attackEntityFrom(DamageSource.drown, 1f + (JewelryNBT.numberOfModifiers(item) - 1) * 0.1F);
		}
		// Negative for bracelet
		if (jewelry instanceof ItemBracelet && player.isInWater()) {
			double slowAmount = 0.6D + (JewelryNBT.numberOfModifiers(item) - 1) * 0.05D;
			player.motionX *= slowAmount;
			player.motionY *= slowAmount;
			player.motionZ *= slowAmount;
			player.motionY -= (0.02D + (JewelryNBT.numberOfModifiers(item) - 1) * 0.005D);
			if (player.isCollidedHorizontally) player.motionY = 0.30000001192092896D;
		}
		// Negative for earrings
		if (jewelry instanceof ItemEarrings) {
			if (player.getAir() >= 300) player.setAir(player.getAir() / 2);
			else player.setAir(player.getAir() - JewelryNBT.numberOfModifiers(item));
		}
	}

	@Override
	public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount) {
		// Balanced for ring
		if (jewelry instanceof ItemRing && !player.isInWater() && rand.nextInt(JewelryNBT.numberOfModifiers(item)) == 0) target.setFire(2);
		return false;
	}

	public boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount) {
		NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
		if (jewelry instanceof ItemEarrings && rand.nextInt(4) == 0 && source == DamageSource.lava || source == DamageSource.inFire || source == DamageSource.onFire) {
			// Positive for earrings
			int stackSize = JewelryNBT.modifierSize(item, modifier);
			player.heal(stackSize * 0.05F - (JewelryNBT.numberOfModifiers(item) - 1) * 0.01F);
			return true;
		}
		// Positive for bracelet
		if (jewelry instanceof ItemBracelet) if (source == DamageSource.inFire || source == DamageSource.onFire || source == DamageSource.lava && player.worldObj.isMaterialInBB(AxisAlignedBB.getBoundingBox(player.boundingBox.minX, player.boundingBox.minY, player.boundingBox.minZ, player.boundingBox.maxX, player.boundingBox.maxY - 0.7, player.boundingBox.maxZ), Material.lava) && !player.worldObj.isMaterialInBB(AxisAlignedBB.getBoundingBox(player.boundingBox.minX, player.boundingBox.minY + 0.9, player.boundingBox.minZ, player.boundingBox.maxX, player.boundingBox.maxY, player.boundingBox.maxZ), Material.lava)) return true;
		return false;
	}
}
