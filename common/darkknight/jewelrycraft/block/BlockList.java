package darkknight.jewelrycraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.renders.TileEntityMolderRender;
import darkknight.jewelrycraft.renders.TileEntitySmelterRender;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;

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
            shadowOre = new BlockBase(ConfigHandler.idShadowOre, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("jewelrycraft.oreShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
            smelter = new BlockSmelter(ConfigHandler.idSmelter, Material.iron).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("jewelrycraft.smelter").setCreativeTab(JewelrycraftMod.jewelrycraft);
            molder = new BlockMolder(ConfigHandler.idMolder, Material.iron).setHardness(5.0F).setResistance(6.0F).setStepSound(Block.soundMetalFootstep).setUnlocalizedName("jewelrycraft.molder").setCreativeTab(JewelrycraftMod.jewelrycraft);
            jewelCraftingTable = new BlockBase(ConfigHandler.idJewelCraftingTable, Material.rock).setHardness(3.0F).setResistance(5.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("jewelrycraft.jewelCraftingTable").setCreativeTab(JewelrycraftMod.jewelrycraft);
            
            GameRegistry.registerBlock(shadowOre, "shadowOre");
            GameRegistry.registerBlock(smelter, "Smelter");
            GameRegistry.registerBlock(molder, "Molder");
            GameRegistry.registerBlock(jewelCraftingTable, "jewelCraftingTable");
            
            GameRegistry.registerTileEntity(TileEntitySmelter.class, "30");
            GameRegistry.registerTileEntity(TileEntityMolder.class, "31");
            
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmelter.class, new TileEntitySmelterRender());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMolder.class, new TileEntityMolderRender());
        }
    }
}
