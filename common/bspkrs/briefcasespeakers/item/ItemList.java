package bspkrs.briefcasespeakers.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import bspkrs.briefcasespeakers.config.ConfigHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ItemList
{
    public static Item     thiefGloves;
    public static Item     briefcaseSpeakers;
    public static Item     speaker;
    public static Item     remote;
    public static Item     shadowIngot;
    
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            thiefGloves = new ItemThiefGloves(ConfigHandler.idThiefGloves).setUnlocalizedName("briefcasespeakers.thiefGloves").setCreativeTab(CreativeTabs.tabCombat);
            speaker = new ItemBase(ConfigHandler.idSpeaker).setUnlocalizedName("briefcasespeakers.speakers").setCreativeTab(CreativeTabs.tabMisc);
            remote = new ItemRemote(ConfigHandler.idRemote).setUnlocalizedName("briefcasespeakers.remote").setCreativeTab(CreativeTabs.tabMisc);
            shadowIngot = new ItemBase(ConfigHandler.idShadowIngot).setUnlocalizedName("briefcasespeakers.ingotShadow").setCreativeTab(CreativeTabs.tabMaterials);

        }
    }
}
