/**
 * 
 */
package darkknight.jewelrycraft.potions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
import net.minecraft.potion.Potion;

/**
 * @author Sorin
 * Code for extending Potion list from Mithion
 * SOurce: https://github.com/Mithion/ArsMagica2/blob/master/src/main/java/am2/buffs/BuffList.java
 */
public class PotionList
{
    public static Potion stun;
    private static int potionDefaultOffset = 0;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
    }
    
    private static void setFinalStatic(Field field, Object newValue) throws Exception
    {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }
    
    private static void extendPotionsArray() throws Exception
    {
        JewelrycraftMod.logger.info("Extending potions array");
        JewelrycraftMod.logger.info("Injecting potions starting from index " + Potion.potionTypes.length);
        potionDefaultOffset = Potion.potionTypes.length;
        setPotionArrayLength(255);
    }
    
    private static void setPotionArrayLength(int length) throws Exception
    {
        if (length <= Potion.potionTypes.length)
            return;
        Potion[] potions = new Potion[length];
        for(int i = 0; i < Potion.potionTypes.length; ++i){
            potions[i] = Potion.potionTypes[i];
        }
        Field field = null;
        Field[] fields = Potion.class.getDeclaredFields();
        for(Field f: fields){
            if (f.getType().equals(Potion[].class)) {
                field = f;
                break;
            }
        }
        setFinalStatic(field, potions);
    }
    
    public static void init(FMLInitializationEvent e)
    {
        try{
            extendPotionsArray();
        }
        catch(Throwable t){
            JewelrycraftMod.logger.error("Failed to extend potion ids!");
            t.printStackTrace();
        }
        stun = new PotionStun(potionDefaultOffset + 0, true, 0x000000);
    }
    
    public static void postInit(FMLPostInitializationEvent e)
    {}
}
