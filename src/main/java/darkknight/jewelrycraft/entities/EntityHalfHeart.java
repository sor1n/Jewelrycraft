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
    
    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.updateObject(17, 1f);
    }
    
    @Override
    public void onCollideWithPlayer(EntityPlayer player)
    {
        super.onCollideWithPlayer(player);
    }
}
