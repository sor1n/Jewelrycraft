package darkknight.jewelrycraft.curses;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import darkknight.jewelrycraft.util.Variables;

public class CurseList
{
    private static Curse rotten;
    private static Curse flaming;
    private static Curse blind;
    private static Curse greed;
    private static Curse infamy;
    private static Curse midasTouch;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        rotten = new CurseRottenHeart(0, Variables.MODNAME + ":" + "Rotten Heart", 0);
        flaming = new CurseFlamingSoul(1, Variables.MODNAME + ":" + "Flaming Soul", 0);
        greed = new CurseGreed(2, Variables.MODNAME + ":" + "Greed", 0);
        blind = new CurseBlind(3, Variables.MODNAME + ":" + "Blind", 0);
        infamy = new CurseInfamy(4, Variables.MODNAME + ":" + "Infamy", 0);
        midasTouch = new CurseMidasTouch(5, Variables.MODNAME + ":" + "Midas Touch", 0);
    }
}
