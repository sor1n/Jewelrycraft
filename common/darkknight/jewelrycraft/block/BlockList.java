package darkknight.jewelrycraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.tileentity.*;

public class BlockList
{
    public static Block    shadowOre;
    public static Block    glow;
    public static Block    smelter;
    public static Block    molder;
    public static Block    displayer;
    public static Block    jewelCraftingTable;
    public static Block    shadowBlock;
    public static Block    jewelAltar;
    
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            shadowOre = new Block(ConfigHandler.idShadowOre, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setTextureName("jewelrycraft:oreShadow").setUnlocalizedName("Jewelrycraft.oreShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
            glow = new BlockGlow(ConfigHandler.idGlow).setUnlocalizedName("Jewelrycraft.glow").setLightValue(1F);
            smelter = new BlockSmelter(ConfigHandler.idSmelter, Material.rock).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("Jewelrycraft.smelter").setCreativeTab(JewelrycraftMod.jewelrycraft);
            molder = new BlockMolder(ConfigHandler.idMolder, Material.rock).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("Jewelrycraft.molder").setCreativeTab(JewelrycraftMod.jewelrycraft);
            displayer = new BlockDisplayer(ConfigHandler.idDisplayer, Material.iron).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("Jewelrycraft.displayer").setCreativeTab(JewelrycraftMod.jewelrycraft);
            jewelCraftingTable = new BlockJewelrsCraftingTable(ConfigHandler.idJewelCraftingTable, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("Jewelrycraft.jewelCraftingTable").setCreativeTab(JewelrycraftMod.jewelrycraft);
            shadowBlock = new BlockShadow(ConfigHandler.idShadowBlock).setHardness(5.0F).setResistance(7.0F).setStepSound(Block.soundMetalFootstep).setTextureName("jewelrycraft:blockShadow").setUnlocalizedName("Jewelrycraft.blockShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
            jewelAltar = new BlockJewelAltar(ConfigHandler.idAltar).setHardness(5.0F).setResistance(2.0F).setStepSound(Block.soundMetalFootstep).setTextureName("jewelrycraft:altar").setUnlocalizedName("Jewelrycraft.altar").setCreativeTab(JewelrycraftMod.jewelrycraft);
            
            GameRegistry.registerBlock(shadowOre, "shadowOre");
            GameRegistry.registerBlock(shadowBlock, "shadowBlock");
            GameRegistry.registerBlock(smelter, "Smelter");
            GameRegistry.registerBlock(molder, "Molder");
            GameRegistry.registerBlock(jewelCraftingTable, "jewelCraftingTable");
            GameRegistry.registerBlock(displayer, "Displayer");
            GameRegistry.registerBlock(jewelAltar, "Altar");
            
            GameRegistry.registerTileEntity(TileEntitySmelter.class, "30");
            GameRegistry.registerTileEntity(TileEntityMolder.class, "31");
            GameRegistry.registerTileEntity(TileEntityJewelrsCraftingTable.class, "32");
            GameRegistry.registerTileEntity(TileEntityDisplayer.class, "33");
            GameRegistry.registerTileEntity(TileEntityBlockShadow.class, "34");
            GameRegistry.registerTileEntity(TileEntityAltar.class, "35");
            
            isInitialized = true;
        }
    }
}
