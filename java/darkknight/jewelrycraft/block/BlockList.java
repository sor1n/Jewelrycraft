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
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntityShadowEye;
import darkknight.jewelrycraft.tileentity.TileEntityShadowHand;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;

public class BlockList
{
    public static Block shadowOre, glow, smelter, molder, displayer, jewelCraftingTable, shadowBlock, shadowEye, jewelAltar, handPedestal, shadowHand;
    public static BlockMoltenMetal moltenMetal;
    public static Fluid moltenMetalFluid;
    
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            shadowOre = new BlockJCOre().setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setBlockTextureName("jewelrycraft:oreShadow").setBlockName("Jewelrycraft.oreShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
            glow = new BlockGlow().setBlockName("Jewelrycraft.glow").setLightLevel(1F);
            smelter = new BlockSmelter().setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypePiston).setBlockName("Jewelrycraft.smelter").setCreativeTab(JewelrycraftMod.jewelrycraft);
            molder = new BlockMolder(Material.rock).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypePiston).setBlockName("Jewelrycraft.molder").setCreativeTab(JewelrycraftMod.jewelrycraft);
            displayer = new BlockDisplayer(Material.iron).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypeMetal).setBlockName("Jewelrycraft.displayer").setCreativeTab(JewelrycraftMod.jewelrycraft);
            jewelCraftingTable = new BlockJewelrsCraftingTable(Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setBlockName("Jewelrycraft.jewelCraftingTable").setCreativeTab(JewelrycraftMod.jewelrycraft);
            shadowBlock = new BlockShadow().setHardness(5.0F).setResistance(7.0F).setStepSound(Block.soundTypeMetal).setBlockTextureName("jewelrycraft:blockShadow").setBlockName("Jewelrycraft.blockShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
            jewelAltar = new BlockJewelAltar().setHardness(5.0F).setResistance(2.0F).setStepSound(Block.soundTypeMetal).setBlockTextureName("jewelrycraft:altar").setBlockName("Jewelrycraft.altar").setCreativeTab(JewelrycraftMod.jewelrycraft);
            shadowEye = new BlockShadowEye().setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypePiston).setBlockName("Jewelrycraft.shadowEye").setCreativeTab(JewelrycraftMod.jewelrycraft);
            handPedestal = new BlockHandPedestal(Material.rock).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypePiston).setBlockName("Jewelrycraft.handPedestal").setCreativeTab(JewelrycraftMod.jewelrycraft);
            shadowHand = new BlockShadowHand(Material.rock).setStepSound(Block.soundTypePiston).setBlockName("Jewelrycraft.shadowHand").setCreativeTab(JewelrycraftMod.jewelrycraft).setBlockUnbreakable();
            
            GameRegistry.registerBlock(shadowOre, "shadowOre");
            GameRegistry.registerBlock(shadowBlock, "shadowBlock");
            GameRegistry.registerBlock(smelter, "Smelter");
            GameRegistry.registerBlock(molder, "Molder");
            GameRegistry.registerBlock(jewelCraftingTable, "jewelCraftingTable");
            GameRegistry.registerBlock(displayer, "Displayer");
            GameRegistry.registerBlock(jewelAltar, "Altar");
            GameRegistry.registerBlock(shadowEye, "Shadow Eye");
            GameRegistry.registerBlock(handPedestal, "Stone Bricks Pedestal");
            GameRegistry.registerBlock(shadowHand, "Shadow Hand");
            
            GameRegistry.registerTileEntity(TileEntitySmelter.class, "jewelrycraft:smelter");
            GameRegistry.registerTileEntity(TileEntityMolder.class, "jewelrycraft:molder");
            GameRegistry.registerTileEntity(TileEntityJewelrsCraftingTable.class, "jewelrycraft:table");
            GameRegistry.registerTileEntity(TileEntityDisplayer.class, "jewelrycraft:displayer");
            GameRegistry.registerTileEntity(TileEntityBlockShadow.class, "jewelrycraft:blockShadow");
            GameRegistry.registerTileEntity(TileEntityAltar.class, "jewelrycraft:altar");
            GameRegistry.registerTileEntity(TileEntityShadowEye.class, "jewelrycraft:shadowEye");
            GameRegistry.registerTileEntity(TileEntityHandPedestal.class, "jewelrycraft:handPedestal");
            GameRegistry.registerTileEntity(TileEntityShadowHand.class, "jewelrycraft:shadowHand");
            
            moltenMetalFluid = new Fluid("metal.molten").setLuminosity(15).setDensity(3000).setTemperature(2000).setViscosity(6000);
            if (!FluidRegistry.registerFluid(moltenMetalFluid)) moltenMetalFluid = FluidRegistry.getFluid("metal.molten");
            moltenMetal = new BlockMoltenMetal(moltenMetalFluid, Material.lava);
            GameRegistry.registerBlock(moltenMetal, "moltenMetalLiquid");
            
            isInitialized = true;
        }
    }
}
