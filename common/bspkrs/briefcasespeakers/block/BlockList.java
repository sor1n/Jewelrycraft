package bspkrs.briefcasespeakers.block;

import bspkrs.briefcasespeakers.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class BlockList
{
    public static Block    shadowOre;
    
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            shadowOre = new Block(ConfigHandler.idShadowOre, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("briefcasespeakers.oreShadow").setTextureName("briefcasespeakers:shadowOre");
        }
    }
}
