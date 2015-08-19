package darkknight.jewelrycraft.curses;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.Variables;

public class CurseList {
	public static Curse rotten;
	public static Curse flaming;
	public static Curse blind;
	public static Curse greed;
	public static Curse infamy;
	public static Curse midasTouch;
	public static Curse rabbitsPaw;
	public static Curse pentagram;
	public static Curse vampireHunger;
	public static Curse humbleBundle;
	public static Curse deathsTouch;
	public static Curse antichrist;
	public static Curse moneyEqualsPower;

	public static void preInit(FMLPreInitializationEvent e) {
		rotten = new CurseRottenHeart(Variables.MODNAME + ":" + "Rotten Heart", 0, Variables.MODID + "_curses_0");
		flaming = new CurseFlamingSoul(Variables.MODNAME + ":" + "Flaming Soul", 1, Variables.MODID + "_curses_0");
		greed = new CurseGreed(Variables.MODNAME + ":" + "Greed", 2, Variables.MODID + "_curses_0");
		blind = new CurseBlind(Variables.MODNAME + ":" + "Blind", 3, Variables.MODID + "_curses_0");
		infamy = new CurseInfamy(Variables.MODNAME + ":" + "Infamy", 4, Variables.MODID + "_curses_0");
		midasTouch = new CurseMidasTouch(Variables.MODNAME + ":" + "Midas Touch", 5, Variables.MODID + "_curses_0");
		rabbitsPaw = new CurseRabbitsPaw(Variables.MODNAME + ":" + "Rabbit's Paw", 6, Variables.MODID + "_curses_0");
		pentagram = new CursePentagram(Variables.MODNAME + ":" + "Pentagram", 7, Variables.MODID + "_curses_0");
		vampireHunger = new CurseVampireHunger(Variables.MODNAME + ":" + "Vampire Hunger", 8, Variables.MODID + "_curses_0");
		humbleBundle = new CurseHumbleBundle(Variables.MODNAME + ":" + "Humble Bundle", 9, Variables.MODID + "_curses_0");
		deathsTouch = new CurseDeathsTouch(Variables.MODNAME + ":" + "Deaths Touch", 10, Variables.MODID + "_curses_0");
		// antichrist = new CurseMidasTouch(Variables.MODNAME + ":" + "Antichrist", 11, Variables.MODID + "_curses_0");
		// moneyEqualsPower = new CurseMidasTouch(Variables.MODNAME + ":" + "Money Equals Power", 12, Variables.MODID + "_curses_0");
	}
}
