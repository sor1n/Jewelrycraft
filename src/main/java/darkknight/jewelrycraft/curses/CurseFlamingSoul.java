package darkknight.jewelrycraft.curses;

import darkknight.jewelrycraft.api.Curse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
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
        return "Is it me or is it getting hot in here?";
    }
}
