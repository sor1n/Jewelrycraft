/**
 * 
 */
package darkknight.jewelrycraft.curses;

import java.util.ArrayList;

import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CurseRabbitsPaw extends Curse
{
    public CurseRabbitsPaw(String name, int txtID, String pack)
    {
        super(name, txtID, pack);
    }
    
    @Override
    public void entityDropItems(EntityPlayer player, Entity target, ArrayList<EntityItem> drops)
    {
        for(EntityItem item: drops){
            ItemStack drop = item.getEntityItem().copy();
            drop.stackSize = this.rand.nextInt(4);            
            if (drop.stackSize > 0) target.entityDropItem(drop, 0.5F);
        }
    }
    
    public void entityDeathAction(World world, EntityLivingBase target, EntityPlayer player)
    {
        if (rand.nextInt(3) == 0) world.spawnEntityInWorld(new EntityXPOrb(world, target.posX, target.posY, target.posZ, 1 + rand.nextInt(40)));
    }
    
    public int luck()
    {
        return 10;
    }
    
    public String getDescription()
    {
        return StatCollector.translateToLocal("curse." + Variables.MODID + ".rabbitspaw.description");
    }
    
	@Override
	public String getDisplayName() 
	{
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".rabbitspaw");
	}

    @Override
    public boolean canCurseBeActivated()
    {
        return ConfigHandler.CURSE_RABBIT_PAW;
    }
}
