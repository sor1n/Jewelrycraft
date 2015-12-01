/**
 * 
 */
package darkknight.jewelrycraft.worldGen.village;

import java.io.IOException;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.VillagerRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.world.gen.structure.MapGenStructureIO;

public class VillageHandler
{
    public static void preInit(FMLPreInitializationEvent e) throws IOException
    {
        VillagerRegistry.instance().registerVillagerId(3000);
        VillagerRegistry.instance().registerVillageTradeHandler(3000, new JCTrades());
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageJewelryHandler());
        try{
            MapGenStructureIO.func_143031_a(ComponentJewelry.class, Variables.MODID + ":Jewelry");
        }
        catch(Throwable e2){
            JewelrycraftMod.logger.error("Error registering Jewelrycraft Structures with Vanilla Minecraft: this is expected in versions earlier than 1.7.10");
        }
    }
}
