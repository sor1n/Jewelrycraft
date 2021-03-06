/**
 * 
 */
package darkknight.jewelrycraft.curses;

import java.util.ArrayList;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

/**
 * @author Sorin
 *
 */
public class CurseHumbleBundle extends Curse
{
    protected CurseHumbleBundle(String name, int txtID, String texturepack)
    {
        super(name, txtID, texturepack);
    }
    
    @Override
    public void entityDropItems(EntityPlayer player, Entity target, ArrayList<EntityItem> drops)
    {
        for(EntityItem item: drops){
            ItemStack drop = item.getEntityItem().copy();
            target.entityDropItem(drop, 0.5F);
        }
    }
    
    public void onBlockItemsDrop(EntityPlayer player, BlockEvent.HarvestDropsEvent event)
    {
        for(ItemStack item: event.drops){
            ItemStack drop = item.copy();
            if(drop.getItem() != Item.getItemFromBlock(event.block)) dropItem(event.world, event.x, event.y, event.z, drop);
        }
    }
    
    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.11000000298023224D;
        world.spawnEntityInWorld(entityitem);
    }
    
    public String getDescription()
    {
        return StatCollector.translateToLocal("curse." + Variables.MODID + ".humblebundle.description");
    }

	@Override
	public String getDisplayName() 
	{
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".humblebundle");
	}

    @Override
    public boolean canCurseBeActivated()
    {
        return ConfigHandler.CURSE_HUMBLE_BUNDLE;
    }
}
