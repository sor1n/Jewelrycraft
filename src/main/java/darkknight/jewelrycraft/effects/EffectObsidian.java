package darkknight.jewelrycraft.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import darkknight.jewelrycraft.api.ModifierEffects;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

public class EffectObsidian extends ModifierEffects
{
    public EffectObsidian()
    {
        super(new ItemStack(Blocks.obsidian));
    }
    
    @Override
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {
        boolean exists = JewelryNBT.doesModifierExist(item, modifier);
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        if (jewelry instanceof ItemNecklace && exists){
            // Positive necklace
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 60, 2, true));
            // Negative necklace
            if (player.isInWater() && !player.capabilities.isCreativeMode){
                double slowAmount = 0.2D + (JewelryNBT.numberOfModifiers(item) - 1) * 0.05D;
                player.motionX *= slowAmount;
                player.motionY *= slowAmount;
                player.motionZ *= slowAmount;
                player.motionY = -0.5D;
                if (rand.nextInt(50) == 0) player.attackEntityFrom(DamageSourceList.weak, 2F);
            }
        }
        // Negative bracelet
        if (jewelry instanceof ItemBracelet && exists && playerInfo.hasKey("falls") && playerInfo.getInteger("falls") >= 300) player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 60, 1, true));
        // Negative ring
        if (jewelry instanceof ItemRing && exists && playerInfo.hasKey("strikes") && playerInfo.getInteger("strikes") >= 200){
            player.addPotionEffect(new PotionEffect(Potion.weakness.id, 60, 0, true));
            player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 60, 1, true));
        }
    }
    
    @Override
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        boolean exists = JewelryNBT.doesModifierExist(item, modifier);
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        if (jewelry instanceof ItemEarrings && exists && (source == DamageSource.anvil || source.damageType.equals("arrow"))){
            if (playerInfo.hasKey("protected")) playerInfo.setInteger("protected", playerInfo.getInteger("protected") + 1);
            else playerInfo.setInteger("protected", 1);
            // Positive && Negative earrings
            if (playerInfo.getInteger("protected") < 200) playerInfo.setBoolean("negateDamage", true);
            else player.attackEntityFrom(DamageSourceList.weak, player.getHealth() * 3F);
        }
        if (jewelry instanceof ItemBracelet && exists && source == DamageSource.fall){
            if (playerInfo.hasKey("falls")) playerInfo.setInteger("falls", playerInfo.getInteger("falls") + 1);
            else playerInfo.setInteger("falls", 1);
            // Positive bracelet
            if (playerInfo.getInteger("falls") < 300) playerInfo.setBoolean("negateDamage", true);
        }
    }
    
    public void onPlayerDead(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry)
    {
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        playerInfo.setInteger("falls", 0);
        playerInfo.setInteger("strikes", 0);
        playerInfo.setInteger("protected", 0);
    }
    
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        boolean exists = JewelryNBT.doesModifierExist(item, modifier);
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        NBTTagCompound enemyData = target.getEntityData();
        // Positive ring
        if (jewelry instanceof ItemRing && exists && playerInfo.getInteger("strikes") < 200 && !player.worldObj.isRemote){
            if (enemyData.getInteger("reAttacked") == 0){
                if (playerInfo.hasKey("strikes")) playerInfo.setInteger("strikes", playerInfo.getInteger("strikes") + 1);
                else playerInfo.setInteger("strikes", 1);
                // Negative ring
                enemyData.setInteger("reAttacked", enemyData.getInteger("reAttacked") + 1);
                target.attackEntityFrom(DamageSource.causePlayerDamage(player), amount * 2F);
                playerInfo.setBoolean("weakDamage", true);
            }
            if (enemyData.getInteger("reAttacked") == 1) enemyData.setInteger("reAttacked", 0);
        }
    }
}
