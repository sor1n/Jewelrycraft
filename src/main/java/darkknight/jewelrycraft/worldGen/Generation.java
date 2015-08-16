package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import cpw.mods.fml.common.IWorldGenerator;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.config.ConfigHandler;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;

public class Generation implements IWorldGenerator {
	public static WorldGenStructure STRUCTURE_1 = new WorldGenStructure1();
	public static WorldGenStructure STRUCTURE_2 = new WorldGenStructure2();
	public static WorldGenStructure STRUCTURE_3 = new WorldGenStructure3();
	public static WorldGenStructure STRUCTURE_4 = new WorldGenStructure4();
	public static WorldGenStructure STRUCTURE_5 = new WorldGenStructure5();
	public static WorldGenStructure STRUCTURE_6 = new WorldGenStructure6();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1:
			generateNether(world, random, chunkX << 4, chunkZ << 4);
			break;
		case 0:
			generateSurface(world, random, chunkX << 4, chunkZ << 4);
			break;
		case 1:
			generateEnd(world, random, chunkX << 4, chunkZ << 4);
			break;
		default:
			generateSurface(world, random, chunkX << 4, chunkZ << 4);
			break;
		}
	}

	private void generateEnd(World world, Random random, int i, int j) {}

	private void generateSurface(World world, Random random, int i, int j) {
		if (ConfigHandler.ENABLE_WORLD_GEN) {
			if (!world.getWorldInfo().getTerrainType().equals(WorldType.FLAT)) {
				if (ConfigHandler.ORE_GEN) generateShadowOre(world, random, i, j);
				if (ConfigHandler.CRYSTAL_GEN) generateCrystals(world, random, i, j);
				if (ConfigHandler.STRUCTURES[0]) generateStructureOverground(STRUCTURE_1, world, random, i, j, 6, false, true);
				if (ConfigHandler.STRUCTURES[1]) generateStructureUnderground(STRUCTURE_2, world, random, i, j, 5, false, true);
				if (ConfigHandler.STRUCTURES[2]) generateStructureUnderground(STRUCTURE_3, world, random, i, j, 7, false, true);
				if (ConfigHandler.STRUCTURES[3]) generateStructureOverground(STRUCTURE_4, world, random, i, j, 10, false, true);
				if (ConfigHandler.STRUCTURES[4]) generateStructureUnderground(STRUCTURE_5, world, random, i, j, 6, false, true);
				if (ConfigHandler.STRUCTURES[5]) generateStructureOverground(STRUCTURE_6, world, random, i, j, 10, false, true);
			}
		}
	}

	private void generateNether(World world, Random random, int i, int j) {}

	private void generateShadowOre(World world, Random random, int i, int j) {
		for (int k = 0; k < 1; k++) {
			int x = i + random.nextInt(16);
			int y = 5 + random.nextInt(4);
			int z = j + random.nextInt(16);
			if (world.getBlock(x, y, z) == Blocks.stone) world.setBlock(x, y, z, BlockList.shadowOre);
			int randX = random.nextInt(2), randY = random.nextInt(1), randZ = random.nextInt(2);
			if (random.nextInt(3) == 0 && world.getBlock(x + randX, y + randY, z + randZ) == Blocks.stone) world.setBlock(x + randX, y + randY, z + randZ, BlockList.shadowOre);
		}
	}

	private void generateCrystals(World world, Random random, int i, int j) {
		for (int k = 0; k < 16; k++) {
			int x = i + random.nextInt(12);
			int y = 5 + random.nextInt(64);
			int z = j + random.nextInt(12);
			for (int r = 0; r < 12; r++) {
				int randX = random.nextInt(4);
				int randY = random.nextInt(2);
				int randZ = random.nextInt(4);
				if (world.getBlock(x + randX, y + randY - 1, z + randZ) == Blocks.stone && world.getBlock(x + randX, y + randY, z + randZ) == Blocks.air) world.setBlock(x + randX, y + randY, z + randZ, BlockList.crystal, random.nextInt(16), 2);
			}
		}
	}

	private void generateStructureUnderground(WorldGenStructure structure, World world, Random random, int i, int j, int maxAttempts, boolean noOfAttempts, boolean chanceOfSpawning) {
		BiomeGenBase biomeBase = world.getBiomeGenForCoords(i, j);
		if (noOfAttempts) {
			for (int k = 0; k < maxAttempts; k++) {
				int x = i + random.nextInt(16);
				int y = random.nextInt(64);
				int z = j + random.nextInt(16);
				if (world.getBlock(x, y, z) == Blocks.stone) structure.generate(world, biomeBase, random, x, y, z);
			}
		}
		else if (chanceOfSpawning) {
			if (random.nextInt(maxAttempts) == 0) {
				int x = i + random.nextInt(16);
				int y = random.nextInt(64);
				int z = j + random.nextInt(16);
				if (world.getBlock(x, y, z) == Blocks.stone) structure.generate(world, biomeBase, random, x, y, z);
			}
		}
	}

	private void generateStructureOverground(WorldGenStructure structure, World world, Random random, int i, int j, int maxAttempts, boolean noOfAttempts, boolean chanceOfSpawning) {
		BiomeGenBase biomeBase = world.getBiomeGenForCoords(i, j);
		if (noOfAttempts) {
			for (int k = 0; k < maxAttempts; k++) {
				int x = i + random.nextInt(16);
				int y = (world.getChunkHeightMapMinimum(i, j) > 0 ? world.getChunkHeightMapMinimum(i, j) : world.getChunkFromChunkCoords(i, j).heightMapMinimum - 16) + random.nextInt(4);
				int z = j + random.nextInt(16);
				if (world.getBlock(x, y, z) == Blocks.air && (world.getBlock(x, y - 1, z) == Blocks.stone || world.getBlock(x, y - 1, z) == Blocks.sand || world.getBlock(x, y - 1, z) == Blocks.grass)) structure.generate(world, biomeBase, random, x, y, z);
			}
		}
		else if (chanceOfSpawning) {
			if (random.nextInt(maxAttempts) == 0) {
				int x = i + random.nextInt(16);
				int y = (world.getChunkHeightMapMinimum(i, j) > 0 ? world.getChunkHeightMapMinimum(i, j) : world.getChunkFromChunkCoords(i, j).heightMapMinimum - 16) + random.nextInt(4);
				int z = j + random.nextInt(16);
				if (world.getBlock(x, y, z) == Blocks.air && (world.getBlock(x, y - 1, z) == Blocks.stone || world.getBlock(x, y - 1, z) == Blocks.sand || world.getBlock(x, y - 1, z) == Blocks.grass)) {
					structure.generate(world, biomeBase, random, x, y, z);
					System.out.println(x + " " + z);
				}
			}
		}
	}
}
