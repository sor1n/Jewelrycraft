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
        createEntity(EntityHeart.class, Variables.MODID + ".Heart", 0xFF0000, 0xFF0000, false);
        createEntity(EntityHalfHeart.class, Variables.MODID + ".Half-Heart", 0x000000, 0xFF0000, false);
    }

    public static void createEntity(Class<? extends Entity> entity, String entityName, int solidColor, int spotColor, boolean hasSpawnEgg)
    {
        int randomID = EntityRegistry.findGlobalUniqueEntityId();
        if(hasSpawnEgg) EntityRegistry.registerGlobalEntityID(entity, entityName, randomID, solidColor, spotColor);
        else EntityRegistry.registerGlobalEntityID(entity, entityName, randomID);
        EntityRegistry.registerModEntity(entity, entityName, randomID, JewelrycraftMod.instance, 40, 3, true);
    }
}
