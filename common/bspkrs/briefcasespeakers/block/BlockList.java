package bspkrs.briefcasespeakers.block;

import bspkrs.briefcasespeakers.BriefcaseSpeakersMod;
import bspkrs.briefcasespeakers.config.ConfigHandler;
import bspkrs.briefcasespeakers.tileentity.TileEntitySmelter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockList
{
    public static Block    shadowOre;
    public static Block    smelter;
    public static Block    molder;
    public static Block    jewelCraftingTable;
    
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            shadowOre = new BlockBase(ConfigHandler.idShadowOre, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("briefcasespeakers.oreShadow").setCreativeTab(BriefcaseSpeakersMod.shadowspkrs);
            smelter = new BlockSmelter(ConfigHandler.idSmelter, Material.iron).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("briefcasespeakers.smelter").setCreativeTab(BriefcaseSpeakersMod.shadowspkrs);
            molder = new BlockMolder(ConfigHandler.idMolder, Material.iron).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("briefcasespeakers.molder").setCreativeTab(BriefcaseSpeakersMod.shadowspkrs);
            jewelCraftingTable = new BlockBase(ConfigHandler.idJewelCraftingTable, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("briefcasespeakers.jewelCraftingTable").setCreativeTab(BriefcaseSpeakersMod.shadowspkrs);
            
            GameRegistry.registerBlock(shadowOre, "shadowOre");
            GameRegistry.registerBlock(smelter, "Smelter");
            GameRegistry.registerBlock(molder, "Molder");
            GameRegistry.registerBlock(jewelCraftingTable, "jewelCraftingTable");
            
            GameRegistry.registerTileEntity(TileEntitySmelter.class, "30");
        }
    }
}
