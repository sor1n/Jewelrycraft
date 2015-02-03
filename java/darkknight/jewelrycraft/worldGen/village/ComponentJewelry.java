package darkknight.jewelrycraft.worldGen.village;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.StructureVillagePieces.Start;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.item.ItemMolds;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class ComponentJewelry extends StructureVillagePieces.House1
{
    private int averageGroundLevel = -1;
    
    public ComponentJewelry()
    {
    }
    
    public ComponentJewelry(Start par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super();
        this.coordBaseMode = par5;
        this.boundingBox = par4StructureBoundingBox;
    }
    
    @SuppressWarnings("rawtypes")
    public static ComponentJewelry buildComponent(Start villagePiece, List pieces, Random random, int p1, int p2, int p3, int p4, int p5)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p1, p2, p3, 0, 0, 0, 11, 5, 12, p4);
        return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(pieces, structureboundingbox) == null ? new ComponentJewelry(villagePiece, p5, random, structureboundingbox, p4) : null;
    }
    
    /**
     * second Part of Structure generating, this for example places Spiderwebs,
     * Mob Spawners, it closes Mineshafts at the end, it adds Fences...
     */
    public boolean addComponentParts(World world, Random random, StructureBoundingBox sbb)
    {
        if (this.averageGroundLevel < 0)
        {
            this.averageGroundLevel = this.getAverageGroundLevel(world, sbb);
            
            if (this.averageGroundLevel < 0) { return true; }
            
            this.boundingBox.offset(0, this.averageGroundLevel - this.boundingBox.maxY + 3, 0);
        }
        
        /**
         * arguments: (World worldObj, StructureBoundingBox structBB, int minX,
         * int minY, int minZ, int maxX, int maxY, int maxZ, int placeBlockId,
         * int replaceBlockId, boolean alwaysreplace)
         */
        this.fillWithBlocks(world, sbb, 0, 0, 6, 10, 5, 11, Block.getBlockById(0), Block.getBlockById(0), false);
        this.fillWithBlocks(world, sbb, 2, 0, 0, 8, 5, 5, Block.getBlockById(0), Block.getBlockById(0), false);
        // Pillars
        this.fillWithBlocks(world, sbb, 2, 0, 0, 2, 3, 0, Blocks.log, Blocks.log, false);
        this.fillWithBlocks(world, sbb, 2, 0, 3, 2, 3, 3, Blocks.log, Blocks.log, false);
        this.fillWithBlocks(world, sbb, 8, 0, 0, 8, 3, 0, Blocks.log, Blocks.log, false);
        this.fillWithBlocks(world, sbb, 8, 0, 3, 8, 3, 3, Blocks.log, Blocks.log, false);
        
        // Walls
        this.fillWithBlocks(world, sbb, 2, 0, 1, 2, 3, 2, Blocks.planks, Blocks.planks, false);
        this.fillWithBlocks(world, sbb, 2, 0, 4, 2, 3, 5, Blocks.planks, Blocks.planks, false);
        this.fillWithBlocks(world, sbb, 8, 0, 1, 8, 3, 2, Blocks.planks, Blocks.planks, false);
        this.fillWithBlocks(world, sbb, 8, 0, 4, 8, 3, 5, Blocks.planks, Blocks.planks, false);
        this.fillWithBlocks(world, sbb, 3, 0, 0, 7, 3, 0, Blocks.planks, Blocks.planks, false);
        
        this.fillWithBlocks(world, sbb, 0, 0, 6, 10, 3, 6, Blocks.cobblestone, Blocks.cobblestone, false);
        this.fillWithBlocks(world, sbb, 0, 0, 11, 10, 3, 11, Blocks.cobblestone, Blocks.cobblestone, false);
        this.fillWithBlocks(world, sbb, 0, 0, 6, 0, 3, 11, Blocks.cobblestone, Blocks.cobblestone, false);
        this.fillWithBlocks(world, sbb, 10, 0, 6, 10, 3, 11, Blocks.cobblestone, Blocks.cobblestone, false);
        
        // Roof
        for (int i = 3; i <= 7; i++)
            for (int j = 1; j <= 5; j++)
                this.placeBlockAtCurrentPosition(world, Blocks.wooden_slab, 2, i, 4, j, sbb);
        
        for (int i = 3; i <= 7; i++)
            for (int j = 6; j <= 6; j++)
                this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 0, i, 4, j, sbb);
        
        for (int i = 1; i <= 9; i++)
            for (int j = 7; j <= 10; j++)
                this.placeBlockAtCurrentPosition(world, Blocks.stone_slab, 3, i, 4, j, sbb);
        
        for (int i = 2; i <= 8; i++)
            this.placeBlockAtCurrentPosition(world, Blocks.double_wooden_slab, 2, i, 4, 0, sbb);
        
        for (int i = 1; i <= 5; i++)
        {
            this.placeBlockAtCurrentPosition(world, Blocks.double_wooden_slab, 2, 2, 4, i, sbb);
            this.placeBlockAtCurrentPosition(world, Blocks.double_wooden_slab, 2, 8, 4, i, sbb);
        }
        
        for (int i = 0; i <= 2; i++)
        {
            this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, i, 4, 6, sbb);
            this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, i + 8, 4, 6, sbb);
        }
        
        for (int i = 7; i <= 11; i++)
        {
            this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 0, 4, i, sbb);
            this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 10, 4, i, sbb);
        }
        
        for (int i = 0; i <= 10; i++)
            this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, i, 4, 11, sbb);
        
        // Base
        for (int i = 2; i <= 8; i++)
            for (int j = 0; j <= 5; j++)
                this.placeBlockAtCurrentPosition(world, Blocks.planks, 1, i, 0, j, sbb);
        this.fillWithBlocks(world, sbb, 0, 0, 6, 10, 0, 11, Blocks.stonebrick, Blocks.stonebrick, false);
        
        for (int i = 6; i <= 10; i++)
            this.placeBlockAtCurrentPosition(world, Blocks.double_stone_slab, 0, 5, 0, i, sbb);
        
        for (int i = 7; i <= 10; i++)
        {
            this.placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 1, 0, i, sbb);
            this.placeBlockAtCurrentPosition(world, Blocks.stonebrick, 3, 9, 0, i, sbb);
        }
        
        // Decorations
        this.placeDoorAtCurrentPosition(world, sbb, random, 6, 1, 0, this.getMetadataWithOffset(Blocks.wooden_door, 1));
        this.placeDoorAtCurrentPosition(world, sbb, random, 5, 1, 6, this.getMetadataWithOffset(Blocks.wooden_door, 1));
        
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 3, 2, 0, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 4, 2, 0, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 1, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 4, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 2, 2, 5, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 8, 2, 1, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 8, 2, 2, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 8, 2, 4, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.glass_pane, 0, 8, 2, 5, sbb);
        
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 6, 3, 1, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 3, 3, 3, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 7, 3, 3, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 3, 5, sbb);
        
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 3, 7, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 5, 3, 10, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 3, 8, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 1, 3, 9, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 9, 3, 8, sbb);
        this.placeBlockAtCurrentPosition(world, Blocks.torch, 0, 9, 3, 9, sbb);
        
        int bgCarpetColor = random.nextInt(16);
        
        for (int i = 4; i <= 7; i++)
            for (int j = 1; j <= 5; j++)
                this.placeBlockAtCurrentPosition(world, Blocks.carpet, bgCarpetColor, i, 1, j, sbb);
        
        generateChest(world, 3, 1, 1, 0, random, sbb, ConfigHandler.jewelsChestMin, ConfigHandler.jewelsChestMax);
        generateDisplayer(world, 3, 1, 2, (coordBaseMode == 0 || coordBaseMode == 2) ? 1 : 2, random, sbb);
        placeBlockAtCurrentPosition(world, BlockList.jewelCraftingTable, (coordBaseMode == 0 || coordBaseMode == 2) ? 1 : 2, 3, 1, 3, sbb);
        generateDisplayer(world, 3, 1, 4, (coordBaseMode == 0 || coordBaseMode == 2) ? 1 : 2, random, sbb);
        generateChest(world, 3, 1, 5, 0, random, sbb, ConfigHandler.jewelsChestMin, ConfigHandler.jewelsChestMax);
        
        generateFurnace(world, 1, 1, 7, 0, random, sbb, ConfigHandler.furnacesIngotStackMin, ConfigHandler.furnacesIngotStackMax, ConfigHandler.canFurnacesGenerateIngots);
        generateFurnace(world, 1, 2, 7, 0, random, sbb, ConfigHandler.furnacesIngotStackMin, ConfigHandler.furnacesIngotStackMax, ConfigHandler.canFurnacesGenerateIngots);
        generateFurnace(world, 1, 3, 7, 0, random, sbb, ConfigHandler.furnacesIngotStackMin, ConfigHandler.furnacesIngotStackMax, ConfigHandler.canFurnacesGenerateIngots);
        generateFurnace(world, 1, 1, 10, 0, random, sbb, ConfigHandler.furnacesIngotStackMin, ConfigHandler.furnacesIngotStackMax, ConfigHandler.canFurnacesGenerateIngots);
        generateFurnace(world, 1, 2, 10, 0, random, sbb, ConfigHandler.furnacesIngotStackMin, ConfigHandler.furnacesIngotStackMax, ConfigHandler.canFurnacesGenerateIngots);
        generateFurnace(world, 1, 3, 10, 0, random, sbb, ConfigHandler.furnacesIngotStackMin, ConfigHandler.furnacesIngotStackMax, ConfigHandler.canFurnacesGenerateIngots);
        
        generateSmelter(world, 1, 1, 8, (coordBaseMode == 0 || coordBaseMode == 2) ? 1 : 2, random, sbb, random.nextBoolean());
        generateSmelter(world, 1, 1, 9, (coordBaseMode == 0 || coordBaseMode == 2) ? 1 : 2, random, sbb, random.nextBoolean());
        
        generateMolder(world, 2, 1, 8, (coordBaseMode == 0 || coordBaseMode == 2) ? 1 : 2, random, sbb, random.nextBoolean(), random.nextBoolean());
        generateMolder(world, 2, 1, 9, (coordBaseMode == 0 || coordBaseMode == 2) ? 1 : 2, random, sbb, random.nextBoolean(), random.nextBoolean());
        
        if(random.nextBoolean()) generateIngotChest(world, 9, 1, 7, 0, random, sbb, ConfigHandler.ingotChestMin, ConfigHandler.ingotChestMax, Blocks.chest, ConfigHandler.ingotChestMaxStack);
        else generateOresChest(world, 9, 1, 7, 0, random, sbb, ConfigHandler.ingotChestMin, ConfigHandler.ingotChestMax, Blocks.chest, ConfigHandler.ingotChestMaxStack);
        if(random.nextBoolean()) generateIngotChest(world, 9, 1, 8, 0, random, sbb, ConfigHandler.ingotChestMin, ConfigHandler.ingotChestMax, Blocks.chest, ConfigHandler.ingotChestMaxStack);
        else generateOresChest(world, 9, 1, 8, 0, random, sbb, ConfigHandler.ingotChestMin, ConfigHandler.ingotChestMax, Blocks.chest, ConfigHandler.ingotChestMaxStack);
        if(random.nextBoolean()) generateIngotChest(world, 9, 1, 9, 0, random, sbb, ConfigHandler.ingotChestMin, ConfigHandler.ingotChestMax, Blocks.trapped_chest, ConfigHandler.ingotChestMaxStack);
        else generateOresChest(world, 9, 1, 9, 0, random, sbb, ConfigHandler.ingotChestMin, ConfigHandler.ingotChestMax, Blocks.trapped_chest, ConfigHandler.ingotChestMaxStack);
        if(random.nextBoolean()) generateIngotChest(world, 9, 1, 10, 0, random, sbb, ConfigHandler.ingotChestMin, ConfigHandler.ingotChestMax, Blocks.trapped_chest, ConfigHandler.ingotChestMaxStack);
        else generateOresChest(world, 9, 1, 10, 0, random, sbb, ConfigHandler.ingotChestMin, ConfigHandler.ingotChestMax, Blocks.trapped_chest, ConfigHandler.ingotChestMaxStack);
            
        for (int l = 0; l < 6; ++l)
        {
            for (int i1 = 2; i1 < 9; ++i1)
            {
                this.clearCurrentPositionBlocksUpwards(world, i1, 9, l, sbb);
                this.func_151554_b(world, Blocks.cobblestone, 0, i1, -1, l, sbb);
            }
        }
        
        for (int l = 6; l < 12; ++l)
        {
            for (int i1 = 0; i1 < 11; ++i1)
            {
                this.clearCurrentPositionBlocksUpwards(world, i1, 9, l, sbb);
                this.func_151554_b(world, Blocks.cobblestone, 0, i1, -1, l, sbb);
            }
        }
        
        this.spawnVillagers(world, sbb, 3, 1, 3, 1);
        
        return true;
    }
    
    public void generateChest(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, int min, int max)
    {
        int i1 = this.getXWithOffset(i, k);
        int j1 = this.getYWithOffset(j);
        int k1 = this.getZWithOffset(i, k);
        int t = random.nextInt(max - min + 1) + min;
        this.placeBlockAtCurrentPosition(world, Blocks.chest, metadata, i, j, k, sbb);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(i1, j1, k1);
        while (chest != null && t > 0)
        {
            ItemStack jewels = JewelrycraftUtil.gem.get(random.nextInt(JewelrycraftUtil.gem.size()));
            chest.func_145976_a("Jeweler's Chest");
            if (jewels.getItem() == Items.nether_star && ConfigHandler.generateVillageNetherstar) chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), jewels);
            else if (random.nextBoolean() && jewels.getItem() != Items.nether_star) chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), jewels);
            t--;
        }
    }
    
    public void generateIngotChest(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, int min, int max, Block chestB, int randomAmount)
    {
        int i1 = this.getXWithOffset(i, k);
        int j1 = this.getYWithOffset(j);
        int k1 = this.getZWithOffset(i, k);
        int t = random.nextInt(max - min + 1) + min;
        this.placeBlockAtCurrentPosition(world, chestB, metadata, i, j, k, sbb);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(i1, j1, k1);
        while (chest != null && t > 0)
        {
            chest.func_145976_a("Ingot Chest");
            int metalID = random.nextInt(JewelrycraftUtil.metal.size());
            ItemStack metal = JewelrycraftUtil.metal.get(metalID).copy();
            metal.stackSize = 2 + random.nextInt(randomAmount);
            if (random.nextBoolean()) chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), metal);
            t--;
        }
    }
    
    public void generateOresChest(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, int min, int max, Block chestB, int randomAmount)
    {
        int i1 = this.getXWithOffset(i, k);
        int j1 = this.getYWithOffset(j);
        int k1 = this.getZWithOffset(i, k);
        int t = random.nextInt(max - min + 1) + min;
        this.placeBlockAtCurrentPosition(world, chestB, metadata, i, j, k, sbb);
        TileEntityChest chest = (TileEntityChest) world.getTileEntity(i1, j1, k1);
        while (chest != null && t > 0)
        {
            chest.func_145976_a("Ores Chest");
            int oreID = random.nextInt(JewelrycraftUtil.ores.size());
            ItemStack ores = JewelrycraftUtil.ores.get(oreID).copy();
            ores.stackSize = 2 + random.nextInt(randomAmount);
            if (random.nextBoolean()) chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), ores);
            t--;
        }
    }
    
    public void generateDisplayer(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb)
    {
        int i1 = this.getXWithOffset(i, k);
        int j1 = this.getYWithOffset(j);
        int k1 = this.getZWithOffset(i, k);
        placeBlockAtCurrentPosition(world, BlockList.displayer, metadata, i, j, k, sbb);
        TileEntityDisplayer displayer = (TileEntityDisplayer) world.getTileEntity(i1, j1, k1);
        if (displayer != null)
        {
            Item[] jewels = {ItemList.ring, ItemList.necklace};
            ItemStack jewel = new ItemStack(jewels[random.nextInt(jewels.length)]);
            JewelryNBT.addMetal(jewel, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())));
            JewelryNBT.addModifiers(jewel, JewelrycraftUtil.addRandomModifiers(random.nextInt(4)));
            JewelryNBT.addGem(jewel, JewelrycraftUtil.gem.get(random.nextInt(JewelrycraftUtil.gem.size())));
            displayer.object = jewel;
            displayer.quantity = 1;
            displayer.hasObject = true;
        }
    }
    
    public void generateSmelter(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, boolean isEmpty)
    {
        int i1 = this.getXWithOffset(i, k);
        int j1 = this.getYWithOffset(j);
        int k1 = this.getZWithOffset(i, k);
        placeBlockAtCurrentPosition(world, BlockList.smelter, metadata, i, j, k, sbb);
        TileEntitySmelter smelter = (TileEntitySmelter) world.getTileEntity(i1, j1, k1);
        if (smelter != null && !isEmpty)
        {
            int metal = random.nextInt(JewelrycraftUtil.metal.size());
            smelter.moltenMetal = JewelrycraftUtil.metal.get(metal).copy();
            smelter.hasMoltenMetal = true;
            int quantity = random.nextInt(9);
            switch(quantity)
            {
                case 0: smelter.quantity = 0.1f;
                case 1: smelter.quantity = 0.2f;
                case 2: smelter.quantity = 0.3f;
                case 3: smelter.quantity = 0.4f;
                case 4: smelter.quantity = 0.5f;
                case 5: smelter.quantity = 0.6f;
                case 6: smelter.quantity = 0.7f;
                case 7: smelter.quantity = 0.8f;
                case 8: smelter.quantity = 0.9f;
                default: smelter.quantity = 0.1f;
            }
        }
    }
    
    public void generateMolder(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, boolean hasMold, boolean hasStuff)
    {
        int i1 = this.getXWithOffset(i, k);
        int j1 = this.getYWithOffset(j);
        int k1 = this.getZWithOffset(i, k);
        placeBlockAtCurrentPosition(world, BlockList.molder, metadata, i, j, k, sbb);
        TileEntityMolder molder = (TileEntityMolder) world.getTileEntity(i1, j1, k1);
        if (molder != null)
        {
            if (hasMold)
            {
                int meta = random.nextInt(ItemMolds.moldsItemNames.length + 1);
                molder.mold = new ItemStack(ItemList.molds, 1, meta);
                molder.hasMold = true;
                if (hasStuff)
                {
                    ItemStack ring = new ItemStack(ItemList.ring);
                    JewelryNBT.addMetal(ring, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())).copy());
                    ItemStack necklace = new ItemStack(ItemList.necklace);
                    JewelryNBT.addMetal(necklace, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())).copy());
                    if (meta == 0) molder.jewelBase = JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size()));
                    else if (meta == 1) molder.jewelBase = ring;
                    else molder.jewelBase = necklace;
                    molder.hasJewelBase = true;
                }
            }
        }
    }
    
    public void generateFurnace(World world, int i, int j, int k, int metadata, Random random, StructureBoundingBox sbb, int min, int max, boolean hasMetal)
    {
        int i1 = this.getXWithOffset(i, k);
        int j1 = this.getYWithOffset(j);
        int k1 = this.getZWithOffset(i, k);
        placeBlockAtCurrentPosition(world, Blocks.furnace, metadata, i, j, k, sbb);
        TileEntityFurnace furnace = (TileEntityFurnace) world.getTileEntity(i1, j1, k1);
        if (furnace != null)
        {
            if (random.nextBoolean()) furnace.setInventorySlotContents(1, new ItemStack(Items.coal, 1 + random.nextInt(16)));
            if (hasMetal)
            {
                int metalID = random.nextInt(JewelrycraftUtil.metal.size());
                ItemStack metal = JewelrycraftUtil.metal.get(metalID).copy();
                metal.stackSize = random.nextInt(max - min + 1) + min;
                furnace.setInventorySlotContents(2, metal);
            }
        }
    }
    
    /**
     * Returns the villager type to spawn in this component, based on the number
     * of villagers already spawned.
     */
    protected int getVillagerType(int par1)
    {
        return 3000;
    }
}