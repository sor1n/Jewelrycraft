/**
 * 
 */
package darkknight.jewelrycraft.potions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraft.potion.Potion;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @author Sorin
 *
 */
public class PotionList
{
    public static Potion stun;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        Potion[] potionTypes = null;

        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                    potionTypes = (Potion[])f.get(null);
                    final Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch (Exception e1) {
                System.err.println("Severe error, please report this to the mod author:");
                System.err.println(e1);
            }
        }

    }
    
    public static void init(FMLInitializationEvent e)
    {
        stun = new PotionStun(32, true, 0x000000);        
    }
    
    public static void postInit(FMLPostInitializationEvent e)
    {
        
    }    
}
