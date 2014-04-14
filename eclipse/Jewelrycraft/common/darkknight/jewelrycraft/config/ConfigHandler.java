package darkknight.jewelrycraft.config;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler
{
    private static Configuration config;    
    public static int            ingotCoolingTime     = 200;
    public static int            ingotMeltingTime     = 1500;
    public static int            jewelryCraftingTime  = 2000;
    
    private static boolean       isInitialized        = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            config = new Configuration(e.getSuggestedConfigurationFile());
            
            config.load();
            
            ingotCoolingTime = config.get("timers", "Molder Ingot Cooling Time", ingotCoolingTime).getInt();
            ingotMeltingTime = config.get("timers", "Ingot Melting Time", ingotMeltingTime).getInt();
            jewelryCraftingTime = config.get("timers", "Jewelry Crafting Time", jewelryCraftingTime).getInt();
            
            config.save();
            
            isInitialized = true;
        }
    }
}
