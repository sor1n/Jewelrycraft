package darkknight.jewelrycraft.worldGen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import darkknight.jewelrycraft.config.ConfigHandler;

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
    {}
    
    private void generateSurface(World world, Random random, int i, int j)
    {
        for (int k = 1; k < 4; k++)
        {
            int x = i + random.nextInt(16);
            int y = 5 + random.nextInt(4);
            int z = j + random.nextInt(16);
            world.setBlock(x, y, z, ConfigHandler.idShadowOre);
            System.out.println(x + " " + y + " " + z);
        }
    }
    
    private void generateNether(World world, Random random, int i, int j)
    {}
}
