package darkknight.jewelrycraft.effects;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import darkknight.jewelrycraft.api.ModifierEffects;

public class EffectsList
{
    private static ModifierEffects blazePowder, enderEye, feather, enderPearl, obsidian;
    
    /**
     * @param e
     */
    public static void postInit(FMLPostInitializationEvent e)
    {
        blazePowder = new EffectBlazePowder();
        enderEye = new EffectEnderEye();
        feather = new EffectFeather();
        enderPearl = new EffectEnderPearl();
        obsidian = new EffectObsidian();
    }
}
