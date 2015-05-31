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
        createEntity(EntityHeart.class, Variables.MODID + ".Heart");
        createEntity(EntityHalfHeart.class, Variables.MODID + ".Half-Heart");
    }

    public static void createEntity(Class<? extends Entity> entity, String entityName)
    {
        int randomID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerModEntity(entity, entityName, randomID, JewelrycraftMod.instance, 40, 3, false);
    }
}
