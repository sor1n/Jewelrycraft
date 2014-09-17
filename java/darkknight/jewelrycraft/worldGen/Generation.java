package darkknight.jewelrycraft.worldGen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import darkknight.jewelrycraft.block.BlockList;

public class Generation implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        switch (world.provider.dimensionId)
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
    {
    }
    
    private void generateSurface(World world, Random random, int i, int j)
    {
        for (int k = 0; k < 1; k++)
        {
            int x = i + random.nextInt(16);
            int y = 5 + random.nextInt(4);
            int z = j + random.nextInt(16);
            if (world.getBlock(x, y, z) == Blocks.stone) world.setBlock(x, y, z, BlockList.shadowOre);
            int randX = random.nextInt(2), randY = random.nextInt(1), randZ = random.nextInt(2);
            if (random.nextInt(3) == 0 && world.getBlock(x + randX, y + randY, z + randZ) == Blocks.stone) world.setBlock(x + randX, y + randY, z + randZ, BlockList.shadowOre);
        }
    }
    
    private void generateNether(World world, Random random, int i, int j)
    {
    }
}
