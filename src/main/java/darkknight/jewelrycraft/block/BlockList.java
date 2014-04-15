package darkknight.jewelrycraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.tileentity.TileEntityAltar;
import darkknight.jewelrycraft.tileentity.TileEntityBlockShadow;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;

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
	public static BlockMoltenMetal moltenMetal;
	public static Fluid moltenMetalFluid;

	private static boolean isInitialized = false;

	public static void preInit(FMLPreInitializationEvent e)
	{
		if (!isInitialized)
		{
			shadowOre = new BlockJCOre().setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setBlockTextureName("jewelrycraft:oreShadow").setBlockName("Jewelrycraft.oreShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
			glow = new BlockGlow().setBlockName("Jewelrycraft.glow").setLightLevel(1F);
			smelter = new BlockSmelter(Material.rock).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypeStone).setBlockName("Jewelrycraft.smelter").setCreativeTab(JewelrycraftMod.jewelrycraft);
			molder = new BlockMolder(Material.rock).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypeStone).setBlockName("Jewelrycraft.molder").setCreativeTab(JewelrycraftMod.jewelrycraft);
			displayer = new BlockDisplayer(Material.iron).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypeMetal).setBlockName("Jewelrycraft.displayer").setCreativeTab(JewelrycraftMod.jewelrycraft);
			jewelCraftingTable = new BlockJewelrsCraftingTable(Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypeStone).setBlockName("Jewelrycraft.jewelCraftingTable").setCreativeTab(JewelrycraftMod.jewelrycraft);
			shadowBlock = new BlockShadow().setHardness(5.0F).setResistance(7.0F).setStepSound(Block.soundTypeMetal).setBlockTextureName("jewelrycraft:blockShadow").setBlockName("Jewelrycraft.blockShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
			jewelAltar = new BlockJewelAltar().setHardness(5.0F).setResistance(2.0F).setStepSound(Block.soundTypeMetal).setBlockTextureName("jewelrycraft:altar").setBlockName("Jewelrycraft.altar").setCreativeTab(JewelrycraftMod.jewelrycraft);

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

	        moltenMetalFluid = new Fluid("metal.molten").setLuminosity(15).setDensity(3000).setTemperature(2000).setViscosity(6000);
	        if (!FluidRegistry.registerFluid(moltenMetalFluid)) moltenMetalFluid = FluidRegistry.getFluid("metal.molten");
			moltenMetal = new BlockMoltenMetal(moltenMetalFluid, Material.lava);
			GameRegistry.registerBlock(moltenMetal, "moltenMetalLiquid");

			isInitialized = true;
		}
	}
}
