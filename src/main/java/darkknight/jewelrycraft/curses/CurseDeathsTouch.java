package darkknight.jewelrycraft.curses;

import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class CurseDeathsTouch extends Curse
{
    public CurseDeathsTouch(String name, int txtID, String pack)
    {
        super(name, txtID, pack);
    }
    
    @Override
    public boolean attackedByPlayerActionCancelable(LivingAttackEvent event, World world, EntityPlayer player, Entity target)
    {
        if (!world.isRemote) target.attackEntityFrom(DamageSource.wither, event.ammount);
        return true;
    }
    
    public String getDescription()
    {
        return StatCollector.translateToLocal("curse." + Variables.MODID + ".deathsTouch.description");
    }
    
	@Override
	public String getDisplayName() 
	{
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".deathsTouch");
	}

    @Override
    public boolean canCurseBeActivated()
    {
        return ConfigHandler.CURSE_DEATHS_TOUCH;
    }
	
}
