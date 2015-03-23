package darkknight.jewelrycraft.curses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.entities.EntityHalfHeart;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;

public class CurseInfamy extends Curse
{
    public CurseInfamy(int id, String name, int text)
    {
        super(id, name, text);
    }
    
    @Override
    public void attackedByPlayerAction(World world, EntityPlayer player, Entity target)
    {
        if (rand.nextInt(5) == 0 && !world.isRemote && !(target instanceof EntityMob) && target instanceof EntityLiving && !(target instanceof EntityHeart) && !(target instanceof EntityHalfHeart) && target.canAttackWithItem()){
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            if (playerInfo.getFloat("BlackHeart") < 20F) playerInfo.setFloat("BlackHeart", playerInfo.getFloat("BlackHeart") + 1.0F);
            if (player.getMaxHealth() >= 3F){
                player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(player.getMaxHealth() - 1.0F);
                player.setHealth(player.getHealth() - 1.0F);
            }
            JewelrycraftUtil.addCursePoints(player, 10);
        }
    }
    
    public String getDescription()
    {
        return "What have you done?!";
    }
}
