package darkknight.jewelrycraft.curses;

import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CurseRottenHeart extends Curse
{
    public CurseRottenHeart(String name, int txtID, String pack)
    {
        super(name, txtID, pack);
    }
    
    @Override
    public void action(World world, EntityPlayer player)
    {
        if (!player.isPotionActive(Potion.poison) || player.getActivePotionEffect(Potion.poison).getDuration() < 30) player.addPotionEffect(new PotionEffect(Potion.poison.id, 80));
    }
    
    public String getDescription()
    {
        return StatCollector.translateToLocal("curse." + Variables.MODID + ".rottenheart.description");
    }
    
	@Override
	public String getDisplayName() 
	{
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".rottenheart");
	}
    
    @Override
    public boolean canCurseBeActivated(World world)
    {
        return world.getWorldInfo().isHardcoreModeEnabled() ? false : ConfigHandler.CURSE_ROTTEN_HEART;
    }
}
