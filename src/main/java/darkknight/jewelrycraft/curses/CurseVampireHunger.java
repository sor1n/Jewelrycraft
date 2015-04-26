/**
 * 
 */
package darkknight.jewelrycraft.curses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import darkknight.jewelrycraft.api.Curse;

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
        if(player.shouldHeal() && rand.nextInt(5) == 0) player.heal(1F);
    }
}
