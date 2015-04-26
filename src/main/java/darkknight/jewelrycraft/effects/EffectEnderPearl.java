package darkknight.jewelrycraft.effects;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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

public class EffectEnderPearl extends ModifierEffects
{
    public EffectEnderPearl()
    {
        super(new ItemStack(Items.ender_pearl));
    }
    
    @Override
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {
        boolean exists = JewelryNBT.doesModifierExist(item, modifier);
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        if (jewelry instanceof ItemEarrings && exists){
            AxisAlignedBB axisalignedbb = player.boundingBox.expand(2.0D, 2.0D, 2.0D);
            List list = player.worldObj.getEntitiesWithinAABB(EntityArrow.class, axisalignedbb);
            if (!player.worldObj.isRemote && list != null && !list.isEmpty()){
                Iterator iterator = list.iterator();
                while (iterator.hasNext()){
                    EntityArrow arrow = (EntityArrow)iterator.next();
                    if (arrow.shootingEntity == null || !(arrow.shootingEntity.equals(player)))
                    // Negative earrings
                    if (rand.nextInt(30) == 0){
                        arrow.worldObj.createExplosion(new EntityTNTPrimed(arrow.worldObj), arrow.posX, arrow.posY, arrow.posZ, 2F, true);
                        arrow.setDead();
                    }
                    // Positive earrings
                    else arrow.setPosition(arrow.posX + rand.nextInt(16) - rand.nextInt(16), arrow.posY + rand.nextInt(16), arrow.posZ + rand.nextInt(16) - rand.nextInt(16));
                }
            }
        }
        // Negative Necklace
        if (jewelry instanceof ItemNecklace && exists) player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60, -10, true));
        // Negative bracelet
        if (jewelry instanceof ItemBracelet && exists && player.isInWater()) player.setPositionAndUpdate(player.posX + rand.nextInt(16) - rand.nextInt(16), player.posY + rand.nextInt(4), player.posZ + rand.nextInt(16) - rand.nextInt(16));
    }
    
    @Override
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        boolean exists = JewelryNBT.doesModifierExist(item, modifier);
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        // Positive Necklace
        if (jewelry instanceof ItemNecklace && exists && source.getEntity() != null) source.getEntity().attackEntityFrom(source, amount);
        // Positive bracelet
        if (jewelry instanceof ItemBracelet && exists && !player.worldObj.isRemote){
            int id = player.worldObj.provider.dimensionId;
            if (player.getHealth() <= 6F) if (player.getBedLocation(id) != null) player.setPositionAndUpdate(player.getBedLocation(id).posX, player.getBedLocation(id).posY, player.getBedLocation(id).posZ);
            else player.setPositionAndUpdate(player.worldObj.getSpawnPoint().posX, player.worldObj.getSpawnPoint().posY, player.worldObj.getSpawnPoint().posZ);
        }
    }
    
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        boolean exists = JewelryNBT.doesModifierExist(item, modifier);
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        NBTTagCompound enemyData = target.getEntityData();
        if (jewelry instanceof ItemRing && exists){
            // Negative ring
            if (target instanceof EntityEnderman) player.addPotionEffect(new PotionEffect(Potion.weakness.id, 400, 2, true));
            // Positive ring
            else target.setPosition(target.posX + rand.nextInt(16) - rand.nextInt(16), target.posY + rand.nextInt(4), target.posZ + rand.nextInt(16) - rand.nextInt(16));
        }
    }
    
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        return false;
    }
}
