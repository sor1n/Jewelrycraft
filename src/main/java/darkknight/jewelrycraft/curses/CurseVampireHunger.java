/**
 * 
 */
package darkknight.jewelrycraft.curses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.Variables;

/**
 * @author Sorin
 *
 */
public class CurseVampireHunger extends Curse
{
    protected CurseVampireHunger(String name, int txtID, String texturepack)
    {
        super(name, txtID, texturepack);
    }
    
    public void attackedByPlayerAction(World world, EntityPlayer player, Entity target)
    {
        if(player.shouldHeal() && rand.nextBoolean()) player.heal(1F);
    }
    
    public void action(World world, EntityPlayer player)
    {
        if(!player.capabilities.isCreativeMode && world.isDaytime() && !world.isRaining() && world.canBlockSeeTheSky(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY + 1F), MathHelper.floor_double(player.posZ)))
        	player.setFire(8);
    }
    
    public String getDescription()
    {
        return StatCollector.translateToLocal("curse." + Variables.MODID + ".vampirehunger.description");
    }
    
	@Override
	public String getDisplayName() 
	{
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".vampirehunger");
	}

    @Override
    public boolean canCurseBeActivated(World world)
    {
        return ConfigHandler.CURSE_VAMPIRE_HUNGER;
    }
}
