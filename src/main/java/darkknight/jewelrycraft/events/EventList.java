/**
 * 
 */
package darkknight.jewelrycraft.events;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.gui.GuiHandler;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.effects.EffectsList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.worldGen.Generation;

public class EventList
{
    public static void preInit(FMLPreInitializationEvent e)
    {
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
        FMLCommonHandler.instance().bus().register(new EventCommonHandler());
        BucketHandler.INSTANCE.buckets.put(BlockList.moltenMetal, ItemList.bucket);
        JewelrycraftMod.proxy.preInit();
    }
    
    public static void init(FMLInitializationEvent e)
    {
        GameRegistry.registerWorldGenerator(new Generation(), 0);
        JewelrycraftMod.proxy.init();
        new GuiHandler(); 
        FMLCommonHandler.instance().bus().register(ConfigHandler.INSTANCE);
    }
    
    public static void postInit(FMLPostInitializationEvent e)
    {
        JewelrycraftUtil.addMetals();
        JewelrycraftUtil.jamcrafters();
        EffectsList.postInit(e);
        DamageSourceList.postInit(e);
        JewelrycraftMod.proxy.postInit();
        JewelrycraftUtil.addStuff();   
    }
}
