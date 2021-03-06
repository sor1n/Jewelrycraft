/**
 * 
 */
package darkknight.jewelrycraft.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.network.PacketSendClientPlayerInfo;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
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
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getQuantity());
    }
    
    @Override
    public void updateEntityActionState()
    {
        if (ConfigHandler.HEARTS_DESPAWN)
            ++this.entityAge;
        this.setCanPickUpLoot(true);
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (ConfigHandler.HEARTS_DESPAWN && this.entityAge > ConfigHandler.HEART_DESPAWN_TIME)
            this.setDead();
    }
    
    protected void collideWithEntity(Entity entity)
    {
        super.collideWithEntity(entity);
        if (!this.worldObj.isRemote && entity instanceof EntityHeart && getType().equals(((EntityHeart)entity).getType())) {
            setQuantity(getQuantity() + ((EntityHeart)entity).getQuantity());
            entity.setDead();
        }
    }
    
    @Override
    public void onCollideWithPlayer(EntityPlayer player)
    {
        if (!this.worldObj.isRemote) {
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            if (playerInfo != null) {
                if (getType().equals("Red") && player.getHealth() < player.getMaxHealth()) {
                    float healAmount = player.getMaxHealth() - player.getHealth();
                    if (getQuantity() > healAmount) {
                        player.heal(healAmount);
                        this.setQuantity(getQuantity() - healAmount);
                    }else{
                        player.heal(getQuantity());
                        this.setDead();
                    }
                }else
                    if (getType().equals("White") && playerInfo.getFloat("WhiteHeart") > 0.1F) {
                    playerInfo.setFloat(getType() + "Heart", 0F);
                    if (player.getMaxHealth() < 40F) {
                        player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(player.getMaxHealth() + 2f);
                        player.setHealth(player.getHealth() + 2f);
                    }
                    JewelrycraftMod.netWrapper.sendTo(new PacketSendClientPlayerInfo(playerInfo), (EntityPlayerMP)player);
                    this.setDead();
                }else
                        if (!getType().equals("Red") && ((getType().equals("Black") && playerInfo.getFloat("BlackHeart") <= ConfigHandler.MAX_BLACK_HEARTS_PICKUP) || (getType().equals("Blue") && playerInfo.getFloat("BlueHeart") <= ConfigHandler.MAX_BLUE_HEARTS_PICKUP) || getType().equals("White"))) {
                    if (playerInfo.hasKey(getType() + "Heart"))
                        playerInfo.setFloat(getType() + "Heart", playerInfo.getFloat(getType() + "Heart") + getQuantity());
                    else playerInfo.setFloat(getType() + "Heart", getQuantity());
                    JewelrycraftMod.netWrapper.sendTo(new PacketSendClientPlayerInfo(playerInfo), (EntityPlayerMP)player);
                    this.setDead();
                }
            }
        }
    }
    
    public void onDeath(DamageSource source)
    {
        super.onDeath(source);
        if (source.getEntity() != null && source.getEntity() instanceof EntityPlayer)
            ((EntityPlayer)source.getEntity()).addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".heartKilled." + this.getType())));
    }
    
    @SideOnly (Side.CLIENT)
    public boolean canRenderOnFire()
    {
        return false;
    }
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, "Red");
        this.dataWatcher.addObject(17, 2f);
    }
    
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
        super.writeEntityToNBT(nbt);
        nbt.setString("Type", getType());
        nbt.setFloat("Quantity", getQuantity());
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
        super.readEntityFromNBT(nbt);
        setType(nbt.getString("Type"));
        setQuantity(nbt.getFloat("Quantity"));
    }
    
    public String getType()
    {
        return this.dataWatcher.getWatchableObjectString(16);
    }
    
    public void setType(String type)
    {
        this.dataWatcher.updateObject(16, type);
    }
    
    public Float getQuantity()
    {
        return this.dataWatcher.getWatchableObjectFloat(17);
    }
    
    public void setQuantity(Float qty)
    {
        this.dataWatcher.updateObject(17, qty);
    }
    
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return JewelrycraftUtil.HEART;
    }
    
    public boolean isEntityInvulnerable()
    {
        return false;
    }
    
    public boolean canPickUpLoot()
    {
        return false;
    }
}
