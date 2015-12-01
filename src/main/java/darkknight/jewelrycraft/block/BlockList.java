package darkknight.jewelrycraft.block;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.tileentity.TileEntityBlockShadow;
import darkknight.jewelrycraft.tileentity.TileEntityCrystal;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.tileentity.TileEntityMidasTouch;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntityMoltenMetal;
import darkknight.jewelrycraft.tileentity.TileEntityShadowEye;
import darkknight.jewelrycraft.tileentity.TileEntityShadowHand;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class BlockList
{
    public static Block shadowOre, smelter, molder, displayer, jewelCraftingTable, shadowBlock, shadowEye, handPedestal, shadowHand, midasTouchBlock, crystal;
    public static BlockMoltenMetal moltenMetal;
    public static Fluid moltenMetalFluid;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        shadowOre = new BlockJCOre().setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setBlockTextureName(Variables.MODID + ":oreShadow").setBlockName(Variables.MODID + ".oreShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
        smelter = new BlockSmelter().setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypePiston).setBlockName(Variables.MODID + ".smelter").setCreativeTab(JewelrycraftMod.jewelrycraft);
        molder = new BlockMolder(Material.rock).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypePiston).setBlockName(Variables.MODID + ".molder").setCreativeTab(JewelrycraftMod.jewelrycraft);
        displayer = new BlockDisplayer(Material.iron).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypeMetal).setBlockName(Variables.MODID + ".displayer").setCreativeTab(JewelrycraftMod.jewelrycraft);
        jewelCraftingTable = new BlockJewelrsCraftingTable(Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundTypePiston).setBlockName(Variables.MODID + ".jewelCraftingTable").setCreativeTab(JewelrycraftMod.jewelrycraft);
        shadowBlock = new BlockShadow().setHardness(5.0F).setResistance(7.0F).setStepSound(Block.soundTypeMetal).setBlockTextureName(Variables.MODID + ":blockShadow").setBlockName(Variables.MODID + ".blockShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
        shadowEye = new BlockShadowEye().setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypePiston).setBlockName(Variables.MODID + ".shadowEye").setCreativeTab(JewelrycraftMod.jewelrycraft);
        handPedestal = new BlockHandPedestal(Material.rock).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundTypePiston).setBlockName(Variables.MODID + ".handPedestal").setCreativeTab(JewelrycraftMod.jewelrycraft);
        shadowHand = new BlockShadowHand(Material.rock).setStepSound(Block.soundTypePiston).setBlockName(Variables.MODID + ".shadowHand").setCreativeTab(JewelrycraftMod.jewelrycraft).setBlockUnbreakable();
        midasTouchBlock = new BlockMidasTouch(Material.iron).setHardness(3.0F).setResistance(10.0F).setStepSound(Block.soundTypeMetal).setBlockName(Variables.MODID + ".midasTouchBlock");        
        moltenMetalFluid = new Fluid("metal.molten").setLuminosity(15).setDensity(3000).setTemperature(2000).setViscosity(6000);
        if (!FluidRegistry.registerFluid(moltenMetalFluid)) moltenMetalFluid = FluidRegistry.getFluid("metal.molten");
        moltenMetal = new BlockMoltenMetal(moltenMetalFluid, Material.lava);
        moltenMetalFluid.setBlock(moltenMetal);
        crystal = new BlockCrystal().setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeGlass).setBlockTextureName(Variables.MODID + ":blockCrystal").setBlockName(Variables.MODID + ".blockCrystal").setCreativeTab(JewelrycraftMod.jewelrycraft);
        
        GameRegistry.registerBlock(shadowOre, "shadowOre");
        GameRegistry.registerBlock(shadowBlock, "shadowBlock");
        GameRegistry.registerBlock(smelter, "Smelter");
        GameRegistry.registerBlock(molder, "Molder");
        GameRegistry.registerBlock(jewelCraftingTable, "jewelCraftingTable");
        GameRegistry.registerBlock(displayer, "Displayer");
        GameRegistry.registerBlock(shadowEye, "Shadow Eye");
        GameRegistry.registerBlock(handPedestal, "Stone Bricks Pedestal");
        GameRegistry.registerBlock(shadowHand, "Shadow Hand");
        GameRegistry.registerBlock(midasTouchBlock, "Midas Touch Block");
        GameRegistry.registerBlock(moltenMetal, "moltenMetalLiquid");
        GameRegistry.registerBlock(crystal, BlockItemCrystal.class, "crystalBlock");
        
        GameRegistry.registerTileEntity(TileEntitySmelter.class, Variables.MODID + ":smelter");
        GameRegistry.registerTileEntity(TileEntityMolder.class, Variables.MODID + ":molder");
        GameRegistry.registerTileEntity(TileEntityJewelrsCraftingTable.class, Variables.MODID + ":table");
        GameRegistry.registerTileEntity(TileEntityDisplayer.class, Variables.MODID + ":displayer");
        GameRegistry.registerTileEntity(TileEntityBlockShadow.class, Variables.MODID + ":blockShadow");
        GameRegistry.registerTileEntity(TileEntityShadowEye.class, Variables.MODID + ":shadowEye");
        GameRegistry.registerTileEntity(TileEntityHandPedestal.class, Variables.MODID + ":handPedestal");
        GameRegistry.registerTileEntity(TileEntityShadowHand.class, Variables.MODID + ":shadowHand");
        GameRegistry.registerTileEntity(TileEntityMidasTouch.class, Variables.MODID + ":midsaTouch");
        GameRegistry.registerTileEntity(TileEntityCrystal.class, Variables.MODID + ":crystalBlock");
        GameRegistry.registerTileEntity(TileEntityMoltenMetal.class, Variables.MODID + ":moltenMetalTE");
        
        OreDictionary.registerOre("oreShadow", new ItemStack(BlockList.shadowOre));
    }
}
