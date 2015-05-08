package darkknight.jewelrycraft.curses;

import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CurseFlamingSoul extends Curse
{
    public CurseFlamingSoul(String name, int txtID, String pack)
    {
        super(name, txtID, pack);
    }
    
    public void attackedByPlayerAction(World world, EntityPlayer player, Entity target)
    {
        player.setFire(5);
    }
    
    public String getDescription()
    {
        return StatCollector.translateToLocal("curse." + Variables.MODID + ".flamingsoul.description");
    }
}
