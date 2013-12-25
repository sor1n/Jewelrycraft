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
    public static Item     clayMolds;
    public static Item     ring;
    
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            thiefGloves = new ItemThiefGloves(ConfigHandler.idThiefGloves - 256).setUnlocalizedName("Jewelrycraft.thiefGloves").setTextureName("jewelrycraft:thiefGloves").setCreativeTab(JewelrycraftMod.jewelrycraft);
            shadowIngot = new Item(ConfigHandler.idShadowIngot - 256).setUnlocalizedName("Jewelrycraft.ingotShadow").setTextureName("jewelrycraft:ingotShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
            molds = new ItemMolds(ConfigHandler.idMolds - 256).setUnlocalizedName("Jewelrycraft.mold").setTextureName("mold").setCreativeTab(JewelrycraftMod.jewelrycraft);
            clayMolds = new ItemClayMolds(ConfigHandler.idClayMolds - 256).setUnlocalizedName("Jewelrycraft.mold").setTextureName("mold").setCreativeTab(JewelrycraftMod.jewelrycraft);
            ring = new ItemRing(ConfigHandler.idRing - 256).setUnlocalizedName("Jewelrycraft.ring").setTextureName("jewelrycraft:ring").setCreativeTab(JewelrycraftMod.jewelrycraft);
            
            isInitialized = true;
        }
    }
}
