package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.common.IWorldGenerator;
import darkknight.jewelrycraft.block.BlockList;

public class Generation implements IWorldGenerator
{
    public static WorldGenStructure1 STRUCTURE_1 = new WorldGenStructure1();
    public static WorldGenStructure2 STRUCTURE_2 = new WorldGenStructure2();
    public static WorldGenStructure3 STRUCTURE_3 = new WorldGenStructure3();
    public static WorldGenStructure4 STRUCTURE_4 = new WorldGenStructure4();
    public static WorldGenStructure5 STRUCTURE_5 = new WorldGenStructure5();
    
    // public static WorldGenerator STRUCTURE_6 = new WorldGenStructure6();
    // public static WorldGenerator STRUCTURE_7 = new WorldGenStructure7();
    // public static WorldGenerator STRUCTURE_8 = new WorldGenStructure8();
    // public static WorldGenerator STRUCTURE_9 = new WorldGenStructure9();
    // public static WorldGenerator STRUCTURE_10 = new WorldGenStructure10();
    // public static WorldGenerator STRUCTURE_11 = new WorldGenStructure11();
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        switch(world.provider.dimensionId)
        {
            case -1:
                generateNether(world, random, chunkX << 4, chunkZ << 4);
                break;
            case 0:
                generateSurface(world, random, chunkX << 4, chunkZ << 4);
                break;
            case 1:
                generateEnd(world, random, chunkX << 4, chunkZ << 4);
                break;
        }
    }
    
    private void generateEnd(World world, Random random, int i, int j)
    {}
    
    private void generateSurface(World world, Random random, int i, int j)
    {
        generateShadowOre(world, random, i, j);
        generateCrystals(world, random, i, j);
        generateStructure1(world, random, i, j);
        generateStructure2(world, random, i, j);
        generateStructure3(world, random, i, j);
        generateStructure4(world, random, i, j);
        generateStructure5(world, random, i, j);
    }
    
    private void generateNether(World world, Random random, int i, int j)
    {}
    
    private void generateShadowOre(World world, Random random, int i, int j)
    {
        for(int k = 0; k < 1; k++){
            int x = i + random.nextInt(16);
            int y = 5 + random.nextInt(4);
            int z = j + random.nextInt(16);
            if (world.getBlock(x, y, z) == Blocks.stone) world.setBlock(x, y, z, BlockList.shadowOre);
            int randX = random.nextInt(2), randY = random.nextInt(1), randZ = random.nextInt(2);
            if (random.nextInt(3) == 0 && world.getBlock(x + randX, y + randY, z + randZ) == Blocks.stone) world.setBlock(x + randX, y + randY, z + randZ, BlockList.shadowOre);
        }
    }
    
    private void generateCrystals(World world, Random random, int i, int j)
    {
        for(int k = 0; k < 16; k++){
            int x = i + random.nextInt(12);
            int y = 5 + random.nextInt(64);
            int z = j + random.nextInt(12);
            for(int r = 0; r < 12; r++){
                int randX = random.nextInt(4);
                int randY = random.nextInt(2);
                int randZ = random.nextInt(4);
                if (world.getBlock(x + randX, y + randY - 1, z + randZ) == Blocks.stone && world.getBlock(x + randX, y + randY, z + randZ) == Blocks.air) world.setBlock(x + randX, y + randY, z + randZ, BlockList.crystal, random.nextInt(16), 2);
            }
        }
    }
    
    private void generateStructure1(World world, Random random, int i, int j)
    {
        BiomeGenBase biomeBase = world.getBiomeGenForCoords(i, j);
        int x = i + random.nextInt(16);
        int y = random.nextInt(100);
        int z = j + random.nextInt(16);
        if (world.getBlock(x, y, z) == Blocks.air && (world.getBlock(x, y - 1, z) == Blocks.stone || world.getBlock(x, y - 1, z) == Blocks.sand || world.getBlock(x, y - 1, z) == Blocks.grass)) STRUCTURE_1.generate(world, biomeBase, random, x, y, z);
    }
    
    private void generateStructure2(World world, Random random, int i, int j)
    {
        BiomeGenBase biomeBase = world.getBiomeGenForCoords(i, j);
        if (random.nextInt(3) == 0){
            int x = i + random.nextInt(16);
            int y = random.nextInt(100);
            int z = j + random.nextInt(16);
            if (world.getBlock(x, y, z) == Blocks.stone) STRUCTURE_2.generate(world, biomeBase, random, x, y, z);
        }
    }
    
    private void generateStructure3(World world, Random random, int i, int j)
    {
        BiomeGenBase biomeBase = world.getBiomeGenForCoords(i, j);
        if (random.nextInt(3) == 0){
            int x = i + random.nextInt(16);
            int y = random.nextInt(100);
            int z = j + random.nextInt(16);
            if (world.getBlock(x, y, z) == Blocks.stone) STRUCTURE_3.generate(world, biomeBase, random, x, y, z);
        }
    }
    
    private void generateStructure4(World world, Random random, int i, int j)
    {
        BiomeGenBase biomeBase = world.getBiomeGenForCoords(i, j);
        if (random.nextInt(5) == 0){
            int x = i + random.nextInt(16);
            int y = random.nextInt(100);
            int z = j + random.nextInt(16);
            if (world.getBlock(x, y, z) == Blocks.air && (world.getBlock(x, y - 1, z) == Blocks.stone || world.getBlock(x, y - 1, z) == Blocks.sand || world.getBlock(x, y - 1, z) == Blocks.grass)) STRUCTURE_4.generate(world, biomeBase, random, x, y, z);
        }
    }
    
    private void generateStructure5(World world, Random random, int i, int j)
    {
        BiomeGenBase biomeBase = world.getBiomeGenForCoords(i, j);
        if (random.nextInt(3) == 0){
            int x = i + random.nextInt(16);
            int y = random.nextInt(100);
            int z = j + random.nextInt(16);
            if (world.getBlock(x, y, z) == Blocks.stone) STRUCTURE_5.generate(world, biomeBase, random, x, y, z);
        }
    }
}
