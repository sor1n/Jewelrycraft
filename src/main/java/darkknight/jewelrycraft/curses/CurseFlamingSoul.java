package darkknight.jewelrycraft.curses;

import darkknight.jewelrycraft.api.Curse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CurseFlamingSoul extends Curse
{
    public CurseFlamingSoul(String name, int txtID, String pack)
    {
        super(name, txtID, pack);
    }
    
    @Override
    public void action(World world, EntityPlayer player)
    {
        if (!player.isBurning() && rand.nextInt(20) == 0) player.setFire(5);
    }
    
    public String getDescription()
    {
        return "Is it me or is it getting hot in here?";
    }
}
