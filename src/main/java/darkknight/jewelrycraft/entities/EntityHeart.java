/**
 * 
 */
package darkknight.jewelrycraft.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * @author Sorin
 */
public class EntityHeart extends EntityLiving
{
    public EntityHeart(World world)
    {
        super(world);
        this.setSize(0.4F, 0.4F);
    }
    
    public boolean isEntityInvulnerable()
    {
        return true;
    }
    
    protected boolean canDespawn()
    {
        return false;
    }
    
    @Override
    public void onCollideWithPlayer(EntityPlayer player)
    {
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        if (getType() == "Red" && player.getHealth() < player.getMaxHealth()){
            player.heal(2f);
            this.setDead();
        }
        else if (getType() != "Red"){
            playerInfo.setFloat(getType() + "Heart", playerInfo.getFloat(getType() + "Heart") + 2.0F);
            JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
            this.setDead();
        }
    }
    
    @Override
    protected void updateEntityActionState()
    {
    }
    
    @SideOnly(Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, "Red");
    }
    
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setString("Type", getType());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        setType(nbt.getString("Type"));
    }

    public String getType()
    {
        return this.dataWatcher.getWatchableObjectString(16);
    }

    public void setType(String type)
    {
        this.dataWatcher.updateObject(16, type);
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

}
