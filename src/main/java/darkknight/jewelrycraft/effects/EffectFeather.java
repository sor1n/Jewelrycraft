package darkknight.jewelrycraft.effects;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import darkknight.jewelrycraft.api.ModifierEffects;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.potions.PotionList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

public class EffectFeather extends ModifierEffects
{
    public EffectFeather()
    {
        super(new ItemStack(Items.feather));
    }
    
    @Override
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        // Positive earrings
        if (jewelry instanceof ItemEarrings){
            AxisAlignedBB axisalignedbb = player.boundingBox.expand(1.0D, 1.0D, 1.0D);
            List list = player.worldObj.getEntitiesWithinAABB(EntityArrow.class, axisalignedbb);
            if (!player.worldObj.isRemote && list != null && !list.isEmpty()){
                Iterator iterator = list.iterator();
                while (iterator.hasNext()){
                    EntityArrow arrow = (EntityArrow)iterator.next();
                    if ((arrow.shootingEntity == null || !(arrow.shootingEntity.equals(player)) || arrow.canBePickedUp == 0) && rand.nextInt(2 + JewelryNBT.numberOfModifiers(item)) == 0) arrow.setDead();
                }
            }
        }
        if (jewelry instanceof ItemBracelet){
            // Positive bracelet
            if (player.motionY < 0) player.motionY *= (0.6D + (JewelryNBT.numberOfModifiers(item) - 1) * 0.03D);
            if (rand.nextInt(JewelryNBT.numberOfModifiers(item)) == 0) player.fallDistance = 0F;
            // Negative bracelet
            if (!player.isPotionActive(Potion.moveSlowdown) || player.getActivePotionEffect(Potion.moveSlowdown).getDuration() < 30) player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80 + JewelryNBT.numberOfModifiers(item) * 10, 1 + JewelryNBT.numberOfModifiers(item) / 4));
        }
    }
    
    @Override
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        NBTTagCompound enemyData = target.getEntityData();
        if (jewelry instanceof ItemRing && !player.worldObj.isRemote){
            if (enemyData.getInteger("reAttacked") == 0){
                // Negative ring
                enemyData.setInteger("reAttacked", enemyData.getInteger("reAttacked") + 1);
                target.attackEntityFrom(DamageSource.causePlayerDamage(player), amount / (2F + (JewelryNBT.numberOfModifiers(item) - 1) * 0.3F));
                // Positive ring
                if (rand.nextInt(2) == 0 && target instanceof EntityLivingBase) ((EntityLivingBase)target).addPotionEffect(new PotionEffect(PotionList.stun.id, (51 - JewelryNBT.numberOfModifiers(item))*2, 0, false));
                return true;
            }
            else enemyData.setInteger("reAttacked", 0);
        }
        return false;
    }
    
    @Override
    public boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        // Positive necklace
        if (jewelry instanceof ItemNecklace && rand.nextInt(3 + JewelryNBT.numberOfModifiers(item)) == 0 && source != DamageSourceList.weak && source != DamageSource.inFire && source != DamageSource.onFire && source != DamageSource.lava){
            player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GRAY + StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".effect.feather")));
            return true;
        }
        // Negative necklace
        if (jewelry instanceof ItemNecklace && (source == DamageSource.inFire || source == DamageSource.onFire || source == DamageSource.lava) && source != DamageSourceList.weak) player.attackEntityFrom(DamageSourceList.weak, amount * (3F + (JewelryNBT.numberOfModifiers(item) - 1) * 0.1F));
        // Negative earrings
        if (jewelry instanceof ItemEarrings && source.damageType.equals("arrow")) player.attackEntityFrom(DamageSourceList.weak, amount * (2F + (JewelryNBT.numberOfModifiers(item) - 1) * 0.1F));
        return false;
    }
}
