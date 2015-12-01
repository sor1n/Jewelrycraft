/**
 * 
 */
package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.random.WeightedRandomItem;
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author Sorin
 */
public class WorldGenStructure6 extends WorldGenStructure {
	public boolean generate(World world, BiomeGenBase biome, Random rand, int x, int y, int z) {
		int randBlock, randBlockMeta;
		Block block;
		for (int i = -2; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				for (int k = -2; k <= 1; k++)
					world.setBlock(x + i, y + j, z + k, Blocks.air);
		for (int i = -2; i <= 1; i++)
			for (int k = -2; k <= 1; k++) {
				randBlock = rand.nextInt(4);
				switch (randBlock) {
				case 0:
					block = Blocks.cobblestone;
					randBlockMeta = 0;
					break;
				case 1:
					block = Blocks.stonebrick;
					randBlockMeta = 2;
					break;
				case 2:
					block = Blocks.stonebrick;
					randBlockMeta = 0;
					break;
				default:
					block = Blocks.cobblestone;
					randBlockMeta = 0;
					break;
				}
				world.setBlock(x + i, y - 1, z + k, block, randBlockMeta, 2);
			}
		int crystalCol = rand.nextInt(15);
		world.setBlock(x - 2, y, z - 2, BlockList.crystal, 0, 2);
		world.setBlock(x - 2, y, z + 1, BlockList.crystal, 1 + crystalCol, 2);
		world.setBlock(x + 1, y, z - 2, BlockList.crystal, 1 + crystalCol, 2);
		world.setBlock(x + 1, y, z + 1, BlockList.crystal, 0, 2);
		world.setBlock(x - 1, y, z - 1, Blocks.skull, 1, 2);
		TileEntity tileentity = world.getTileEntity(x - 1, y, z - 1);
		if (tileentity != null && tileentity instanceof TileEntitySkull) {
			((TileEntitySkull) tileentity).func_152107_a(rand.nextInt(50) == 0 ? 1 : 0);
			((TileEntitySkull) tileentity).func_145903_a(MathHelper.floor_double((double) (rand.nextInt(361) * 16.0F / 360.0F) + 0.5D) & 15);
			((BlockSkull) Blocks.skull).func_149965_a(world, x - 1, y, z - 1, (TileEntitySkull) tileentity);
		}
		world.setBlock(x + 1, y, z - 1, BlockList.jewelCraftingTable, 3, 2);
		TileEntity jewelersTable = world.getTileEntity(x + 1, y, z - 1);
		Item[] jewelry = new Item[] { ItemList.ring, ItemList.necklace, ItemList.bracelet, ItemList.earrings };
		if (jewelersTable != null && jewelersTable instanceof TileEntityJewelrsCraftingTable) {
			if (rand.nextBoolean()) ((TileEntityJewelrsCraftingTable) jewelersTable).setGemItemStack(JewelrycraftUtil.gem.get(rand.nextInt(JewelrycraftUtil.gem.size())));
			else if (rand.nextBoolean()) {
				ItemStack result = new ItemStack(jewelry[rand.nextInt(4)], 1, 0);
				if (JewelrycraftUtil.metal.size() > 0) JewelryNBT.addMetal(result, JewelrycraftUtil.metal.get(rand.nextInt(JewelrycraftUtil.metal.size())));
				((TileEntityJewelrsCraftingTable) jewelersTable).setJewelryItemStack(result);
			}
		}
		world.setBlock(x - 1, y, z + 1, BlockList.handPedestal, 0, 2);
		TileEntity pedestal = world.getTileEntity(x - 1, y, z + 1);
		if (pedestal != null && pedestal instanceof TileEntityHandPedestal) ((TileEntityHandPedestal) pedestal).setHeldItemStack(((WeightedRandomItem) WeightedRandom.getRandomItem(rand, WorldGenStructure1.items)).getItem(rand));
		world.setBlock(x - 1, y + 1, z + 1, BlockList.shadowEye, 0, 2);
		return true;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		return generate(world, BiomeGenBase.plains, rand, x, y, z);
	}
    
    public int structureNo()
    {
        return 6;
    }
}
