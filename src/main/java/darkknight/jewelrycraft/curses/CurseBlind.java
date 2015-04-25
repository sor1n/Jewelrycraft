package darkknight.jewelrycraft.curses;

import darkknight.jewelrycraft.api.Curse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CurseBlind extends Curse
{
    public CurseBlind(String name, int txtID, String pack)
    {
        super(name, txtID, pack);
    }
    
    @Override
    public void action(World world, EntityPlayer player)
    {
        if (!player.isPotionActive(Potion.blindness) || player.getActivePotionEffect(Potion.blindness).getDuration() < 30) player.addPotionEffect(new PotionEffect(Potion.blindness.id, 60));
    }
    
    public String getDescription()
    {
        return "You see the light slowly fading in front of you";
    }
}
