package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.block.BlockMoltenMetal;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author Sorin
 */
public class WorldGenStructure2 extends WorldGenStructure {
	public boolean generate(World world, BiomeGenBase biome, Random rand, int x, int y, int z) {
		Block block = Blocks.stonebrick;
		Block stair = Blocks.stone_brick_stairs;
		int metadata = 0, slabMeta = 5;
		if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills) {
			block = Blocks.sandstone;
			stair = Blocks.sandstone_stairs;
			metadata = 2;
		}
		for (int i = -1; i <= 1; i++)
			for (int k = -1; k <= 1; k++)
				world.setBlock(x + i, y, z + k, Blocks.air);
		for (int i = -1; i <= 1; i++)
			for (int k = -1; k <= 1; k++)
				world.setBlock(x + i, y - 1, z + k, block, metadata, 2);
		for (int i = -1; i <= 1; i++)
			for (int k = -1; k <= 1; k++)
				world.setBlock(x + i, y, z + k, stair);
		for (int i = -1; i <= 1; i++)
			for (int k = -1; k <= 1; k++)
				world.setBlockMetadataWithNotify(x + i, y, z + k, (k == -1) ? 3 : (k == 0) ? (i == 1) ? 0 : 1 : 2, 2);
		world.setBlock(x, y, z, Blocks.air);
		ItemStack stack = new ItemStack(ItemList.bucket);
		JewelryNBT.addMetal(stack, JewelrycraftUtil.metal.get(rand.nextInt(JewelrycraftUtil.metal.size())));
		if (stack != null && JewelryNBT.ingot(stack) != null) {
			if (!world.isRemote) world.func_147480_a(x, y, z, true);
			world.setBlock(x, y, z, BlockList.moltenMetal, 0, 3);
			if(BlockMoltenMetal.getTileEntity(world, x, y, z) != null) BlockMoltenMetal.getTileEntity(world, x, y, z).setMetal(JewelryNBT.ingot(stack));
		}
		return true;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		return generate(world, BiomeGenBase.plains, rand, x, y, z);
	}
}
