package darkknight.jewelrycraft.item;

import net.minecraft.item.Item;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.config.ConfigHandler;

public class ItemList
{
    public static Item     thiefGloves;
    public static Item     shadowIngot;
    public static Item     molds;
    
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            thiefGloves = new ItemThiefGloves(ConfigHandler.idThiefGloves).setUnlocalizedName("jewelrycraft.thiefGloves").setCreativeTab(JewelrycraftMod.jewelrycraft);
            shadowIngot = new ItemBase(ConfigHandler.idShadowIngot).setUnlocalizedName("jewelrycraft.ingotShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
            molds = new ItemMolds(ConfigHandler.idMolds).setUnlocalizedName("jewelrycraft.mold").setTextureName("Mold").setCreativeTab(JewelrycraftMod.jewelrycraft);
        }
    }
}
