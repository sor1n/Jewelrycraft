package darkknight.jewelrycraft.worldGen;

import java.util.Random;


import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;
import darkknight.jewelrycraft.block.BlockList;

public class Generation implements IWorldGenerator
{

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
    {
        switch(world.provider.dimensionId){
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 0:
                generateSurface(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16);
                break;
            }        
    }
    
    private void generateEnd(World world, Random random, int i, int j) {}

    private void generateSurface(World world, Random random, int i, int j) 
    {
        for(int k = 0; k < 1; k++) {
            int x = i + random.nextInt(16);
            int y = random.nextInt(5);
            int z = j + random.nextInt(16);
            (new WorldGenMinable(BlockList.shadowOre.blockID, 1)).generate(world, random, x, y, z);
        }
    }

    private void generateNether(World world, Random random, int i, int j) {}

}
