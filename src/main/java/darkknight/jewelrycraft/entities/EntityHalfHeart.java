package darkknight.jewelrycraft.entities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

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
