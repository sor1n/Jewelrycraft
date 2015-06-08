package darkknight.jewelrycraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.item.ItemSpawnEgg;
import darkknight.jewelrycraft.util.Variables;

public class EntityList
{
    public static void preInit(FMLPreInitializationEvent e)
    {        
    	registerEntity(1, EntityHeart.class, "Heart", 0x000000, 0xFF0000); //Red
		ItemSpawnEgg.registerSpawnEgg(EntityHeart.class, "Heart", 2, 0x000000, 0xFFFFFF); // White
		ItemSpawnEgg.registerSpawnEgg(EntityHeart.class, "Heart", 3, 0x000000, 0x006BBD); // Blue
		ItemSpawnEgg.registerSpawnEgg(EntityHeart.class, "Heart", 4, 0x000000, 0x404040); // Black
		
    	registerEntity(5, EntityHalfHeart.class, "HalfHeart", 0x000000, 0xFF0000); // Red
		ItemSpawnEgg.registerSpawnEgg(EntityHalfHeart.class, "HalfHeart", 6, 0x000000, 0xFFFFFF); // White
		ItemSpawnEgg.registerSpawnEgg(EntityHalfHeart.class, "HalfHeart", 7, 0x000000, 0x006BBD); // Blue
		ItemSpawnEgg.registerSpawnEgg(EntityHalfHeart.class, "HalfHeart", 8, 0x000000, 0x404040); // Black
        
    }
	private static final void registerEntity(int id, Class<? extends Entity> entityClass, String name) {
		EntityRegistry.registerModEntity(entityClass, name, id, JewelrycraftMod.instance, 256, 1, true);
	}
    
	private static final void registerEntity(int id, Class<? extends EntityLiving> entityClass, String name, int eggBackgroundColor, int eggForegroundColor) {
		registerEntity(id, entityClass, name);
		ItemSpawnEgg.registerSpawnEgg(entityClass, name, id, eggBackgroundColor, eggForegroundColor);
	}
}
