package darkknight.jewelrycraft.entities;

import net.minecraft.entity.Entity;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.util.Variables;

public class EntityList
{
    public static void preInit(FMLPreInitializationEvent e)
    {        
        EntityRegistry.registerModEntity(EntityHeart.class, "Heart", 1, JewelrycraftMod.instance, 40, 3, false);
        EntityRegistry.registerModEntity(EntityHalfHeart.class, "HalfHeart", 2, JewelrycraftMod.instance, 40, 3, false);
    }
}
