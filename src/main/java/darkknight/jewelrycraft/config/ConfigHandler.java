package darkknight.jewelrycraft.config;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.util.Variables;

public class ConfigHandler
{
    public static Configuration config;
    public static final ConfigHandler INSTANCE = new ConfigHandler();    
    public static final String[] categories = {"Timers", "Village Generation", "Misc", "World Generation"};
    
    public static int INGOT_COOLING_TIME;
    public static int INGOT_MELTING_TIME;
    public static int GEM_PLACEMENT_TIME;
    public static int RITUAL_TIME;
    public static int HEART_DESPAWN_TIME;
    
    public static boolean GENERATE_VILLAGE_NETHERSTAR;
    public static boolean CAN_FURNACE_GENERATE_INGOTS;
    public static int MAX_VILLAGE_JEWELERS;
    public static int JEWELER_WEIGHT;
    public static int INGOT_CHEST_MIN;
    public static int INGOT_CHEST_MAX;
    public static int INGOT_CHEST_MAX_STACK;
    public static int GEM_CHEST_MIN;
    public static int GEM_CHEST_MAX;
    public static int FURNACE_MIN_INGOT_STACK;
    public static int FURNACE_MAX_INGOT_STACK;

    public static boolean CRYSTAL_GLOW;
    public static boolean HEARTS_DESPAWN;
    public static boolean JEWELRY_INFO;
    
    public static boolean CURSES_ENABLED = true;
    public static boolean CURSE_ROTTEN_HEART = true;
    public static boolean CURSE_FLAMING_SOUL = true;
    public static boolean CURSE_GREED = true;
    public static boolean CURSE_BLIND = true;
    public static boolean CURSE_INFAMY = true;
    public static boolean CURSE_MIDAS_TOUCH = true;
    public static boolean CURSE_RABBIT_PAW = true;
    public static boolean CURSE_PENTAGRAM = true;
    public static boolean CURSE_VAMPIRE_HUNGER = true;
    public static boolean CURSE_HUMBLE_BUNDLE = true;
    
    public static boolean ENABLE_WORLD_GEN = true;
    public static boolean ORE_GEN = true;
    public static boolean CRYSTAL_GEN = true;
    public static boolean STRUCTURE_1_GEN = true;
    public static boolean STRUCTURE_2_GEN = true;
    public static boolean STRUCTURE_3_GEN = true;
    public static boolean STRUCTURE_4_GEN = true;
    public static boolean STRUCTURE_5_GEN = true;
    
    public void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile(),true);
        config.load();
        syncConfigs();
    }
    
    private void syncConfigs()
    {
        INGOT_COOLING_TIME = config.getInt("Molder Ingot Cooling Time", categories[0], 100, 5, Integer.MAX_VALUE, "This sets the number of ticks you need to wait before the mold is cooled.");
        INGOT_MELTING_TIME = config.getInt("Ingot Melting Time", categories[0], 1500, 5, Integer.MAX_VALUE, "This sets the number of ticks you need to wait before an ingot is completely smelted.");
        GEM_PLACEMENT_TIME = config.getInt( "Jewelry Crafting Time", categories[0], 200, 5, Integer.MAX_VALUE, "This sets the number of ticks it takes for a jewel to be modified.");
        RITUAL_TIME = config.getInt( "Ritual Time", categories[0], 1000, 5, Integer.MAX_VALUE, "This sets the number of ticks it takes for the ritual to end.");
        HEART_DESPAWN_TIME = config.getInt( "Hearts Despawn Time", categories[0], 900, 20, Integer.MAX_VALUE, "This sets the number of ticks it takes for hearts to despawn, 20=1 second");
        
        GENERATE_VILLAGE_NETHERSTAR = config.getBoolean("Netherstar Generation", categories[1], false, "If set to true Nether Stars will be able to generate in Jewelers chests.");
        CAN_FURNACE_GENERATE_INGOTS = config.getBoolean("Furnace Ingots Generation", categories[1], true, "If set to true jewelers will generate ingots in furnaces.");
        
        MAX_VILLAGE_JEWELERS = config.getInt("Maximum Jewelers", categories[1], 1, 0, Integer.MAX_VALUE, "Sets how many jewelers can be in a village.");
        JEWELER_WEIGHT = config.getInt("Jewelers Weight", categories[1], 30, 0, Integer.MAX_VALUE, "Chance of getting a jeweler in a village. The higher the value, the higher the chance.");
        INGOT_CHEST_MIN = config.getInt("Ingot Chest Min", categories[1], 1, 0, Integer.MAX_VALUE, "Minimum number of ingots that can be found in a chest from the Jeweler. (It's the chest from the back part)");
        INGOT_CHEST_MAX = config.getInt("Ingot Chest Max", categories[1], 4, 0, Integer.MAX_VALUE, "Maximum number of ingots that can be found in a chest from the Jeweler. (It's the chest from the back part)");
        INGOT_CHEST_MAX_STACK = config.getInt("Ingot Chest Max Stack", categories[1], 2, 0, Integer.MAX_VALUE, "Maximum number of the stack the ingots can be. For example: if set to 2 and ingots have a chance of generating, you have a chance of getting a stack of max 2 ingots in a chest.");
        GEM_CHEST_MIN = config.getInt("Jewelers Chest Min", categories[1], 2, 0, Integer.MAX_VALUE, "Determines the minimum nuber of jewels/modifiers that can be generated in the front chests of a Jeweler.");
        GEM_CHEST_MAX = config.getInt("Jewelers Chest Max", categories[1], 5, 0, Integer.MAX_VALUE, "Determines the maximum nuber of jewels/modifiers that can be generated in the front chests of a Jeweler.");
        FURNACE_MIN_INGOT_STACK = config.getInt("Ingot Furnace Min", categories[1], 2, 0, Integer.MAX_VALUE, "Determines the minimum number of ingots that can generate in a furnace.");
        FURNACE_MAX_INGOT_STACK = config.getInt("Ingot Furnace Max", categories[1], 5, 0, Integer.MAX_VALUE, "Determines the maximum number of ingots that can generate in a furnace.");
        
        CRYSTAL_GLOW = config.getBoolean("Crystal Glow", categories[2], false, "If true, then crystal will slowly glow (can cause lag)");
        HEARTS_DESPAWN = config.getBoolean("Hearts Despawn", categories[2], true, "If false, then Hearts and Half-hearts will no longer despawn");
        JEWELRY_INFO = config.getBoolean("Jewelry Info", categories[2], true, "If false, then extra info won't be show when hovering over a jewelery.");
        
        ENABLE_WORLD_GEN = config.getBoolean("World Generation", categories[3], true, "If false, nothing will generate (this includes ore)");
        ORE_GEN = config.getBoolean("Ore Generation", categories[3], true, "If false, ores won't generate");
        CRYSTAL_GEN = config.getBoolean("Crystal Generation", categories[3], true, "If false, crystals won't generate");
        STRUCTURE_1_GEN = config.getBoolean("Structure 1 Generation", categories[3], true, "If false, structure no.1 won't generate");
        STRUCTURE_2_GEN = config.getBoolean("Structure 2 Generation", categories[3], true, "If false, structure no.2 won't generate");
        STRUCTURE_3_GEN = config.getBoolean("Structure 3 Generation", categories[3], true, "If false, structure no.3 won't generate");
        STRUCTURE_4_GEN = config.getBoolean("Structure 4 Generation", categories[3], true, "If false, structure no.4 won't generate");
        STRUCTURE_5_GEN = config.getBoolean("Structure 5 Generation", categories[3], true, "If false, structure no.5 won't generate");
                
//        CURSES_ENABLED = config.getBoolean("Curses", categories[3], true, "If set to false curses will be deactivated.");
//        CURSE_ROTTEN_HEART = config.getBoolean("Rotten Heart", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_FLAMING_SOUL = config.getBoolean("Flaming Soul", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_GREED = config.getBoolean("Greed", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_BLIND = config.getBoolean("Blind", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_INFAMY = config.getBoolean("Infamy", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_MIDAS_TOUCH = config.getBoolean("Midas Touch", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_RABBIT_PAW = config.getBoolean("Rabbit's Paw", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_PENTAGRAM = config.getBoolean("Pentagram", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_VAMPIRE_HUNGER = config.getBoolean("Vampire Hunger", categories[3], true, "If set to false this curse will be deactivated.");
//        CURSE_HUMBLE_BUNDLE = config.getBoolean("Humble Bundle", categories[3], true, "If set to false this curse will be deactivated.");
        if (config.hasChanged()) config.save();
    }
    
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equals(Variables.MODID)) syncConfigs();
    }
}
