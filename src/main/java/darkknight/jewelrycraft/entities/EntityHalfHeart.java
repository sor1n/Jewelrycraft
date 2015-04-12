/**
 * 
 */
package darkknight.jewelrycraft.entities;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

/**
 * @author Sorin
 */
public class EntityHalfHeart extends EntityHeart
{
    public EntityHalfHeart(World world)
    {
        super(world);
    }
    
    @Override
    public void onCollideWithPlayer(EntityPlayer player)
    {
        if (!player.worldObj.isRemote){
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            if (getType().equals("Red") && player.getHealth() < player.getMaxHealth()){
                player.heal(1f);
                this.setDead();
            }else if (getType().equals("White") && playerInfo.getFloat("WhiteHeart") > 0.1F){
                playerInfo.setFloat(getType() + "Heart", 0F);
                player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(player.getMaxHealth() + 2f);
                this.setDead();
            }else if (!getType().equals("Red")){
                playerInfo.setFloat(getType() + "Heart", playerInfo.getFloat(getType() + "Heart") + 1.0F);
                this.setDead();
            }
        }
        else JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
    }
}
