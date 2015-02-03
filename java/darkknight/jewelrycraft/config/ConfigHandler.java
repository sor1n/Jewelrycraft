package darkknight.jewelrycraft.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler
{
    public static Configuration config;
    public static int ingotCoolingTime = 100;
    public static int ingotMeltingTime = 1500;
    public static int jewelryCraftingTime = 200;
    
    private static boolean isInitialized = false;
    public static boolean generateVillageNetherstar = false;
    public static boolean canFurnacesGenerateIngots = true;
    public static int maxVillageJewelers = 1;
    public static int jewelerWeight = 30;
    public static int ingotChestMin = 1;
    public static int ingotChestMax = 4;
    public static int ingotChestMaxStack = 2;
    public static int jewelsChestMin = 2;
    public static int jewelsChestMax = 5;
    public static int furnacesIngotStackMin = 2;
    public static int furnacesIngotStackMax = 5;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            config = new Configuration(new File(e.getModConfigurationDirectory(), "JewelryCraftv2.0.cfg"));
            
            config.load();
            
            ingotCoolingTime = config.get("Timers", "Molder Ingot Cooling Time", ingotCoolingTime, "This sets the number of ticks you need to wait before the mold is cooled.").getInt();
            ingotMeltingTime = config.get("Timers", "Ingot Melting Time", ingotMeltingTime, "This sets the number of ticks you need to wait before an ingot is completely smelted.").getInt();
            jewelryCraftingTime = config.get("Timers", "Jewelry Crafting Time", jewelryCraftingTime, "This sets the number of ticks it takes for a jewel to be modified.").getInt();
            
            generateVillageNetherstar = config.get("Village Generation", "Netherstar Generation", generateVillageNetherstar, "If set to true Nether Stars will be able to generate in Jewelers chests.").getBoolean(generateVillageNetherstar);
            canFurnacesGenerateIngots = config.get("Village Generation", "Furnace Ingots Generation", canFurnacesGenerateIngots, "If set to true jewelers will generate ingots in furnaces.").getBoolean(canFurnacesGenerateIngots);
            
            maxVillageJewelers = config.get("Village Generation", "Maximum Jewelers", maxVillageJewelers, "Sets how many jewelers can be in a village.").getInt();
            jewelerWeight = config.get("Village Generation", "Jewelers Weight", jewelerWeight, "Chance of getting a jeweler in a village. The higher the value, the higher the chance.").getInt();
            
            ingotChestMin = config.get("Village Generation", "Ingot Chest Min", ingotChestMin, "Minimum number of ingots that can be found in a chest from the Jeweler. (It's the chest from the back part)").getInt();
            ingotChestMax = config.get("Village Generation", "Ingot Chest Max", ingotChestMax, "Maximum number of ingots that can be found in a chest from the Jeweler. (It's the chest from the back part)").getInt();
            ingotChestMaxStack = config.get("Village Generation", "Ingot Chest Max Stack", ingotChestMaxStack, "Maximum number of the stack the ingots can be. For example: if set to 2 and ingots have a chance of generating, you have a chance of getting a stack of max 2 ingots in a chest.").getInt();
            jewelsChestMin = config.get("Village Generation", "Jewelers Chest Min", jewelsChestMin, "Determines the minimum nuber of jewels/modifiers that can be generated in the front chests of a Jeweler.").getInt();
            jewelsChestMax = config.get("Village Generation", "Jewelers Chest Max", jewelsChestMax, "Determines the maximum nuber of jewels/modifiers that can be generated in the front chests of a Jeweler.").getInt();
            furnacesIngotStackMin = config.get("Village Generation", "Ingot Furnace Min", furnacesIngotStackMin, "Determines the minimum number of ingots that can generate in a furnace.").getInt();
            furnacesIngotStackMax = config.get("Village Generation", "Ingot Furnace Max", furnacesIngotStackMax, "Determines the maximum number of ingots that can generate in a furnace.").getInt();
            
            config.save();
            
            isInitialized = true;
        }
    }
}
