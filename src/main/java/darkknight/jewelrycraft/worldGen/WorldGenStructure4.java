/**
 * 
 */
package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import darkknight.jewelrycraft.block.BlockList;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author Sorin
 */
public class WorldGenStructure4 extends WorldGenStructure
{
    public boolean generate(World world, BiomeGenBase biome, Random rand, int x, int y, int z)
    {
        Block stair = Blocks.stone_brick_stairs;
        Block block = Blocks.stonebrick;
        int metadata = 0, slabMeta = 5;        
        if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills){
            stair = Blocks.sandstone_stairs;
            block = Blocks.sandstone;
            metadata = 2;
            slabMeta = 1;
        }
        for(int i = -1; i <= 1; i++)
            for(int j = 0; j <= 3; j++)
                for(int k = -1; k <= 1; k++)
                    world.setBlock(x + i, y + j, z + k, Blocks.air);
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlock(x + i, y, z + k, Blocks.stone_slab, slabMeta, 2);
        world.setBlock(x, y, z, block, metadata, 2);
        world.setBlock(x, y, z - 1, stair, 3, 1);
        world.setBlock(x, y, z + 1, stair, 2, 1);
        world.setBlock(x - 1, y, z, stair, 1, 1);
        world.setBlock(x + 1, y, z, stair, 0, 1);
        world.setBlock(x, y + 1, z - 1, stair, 7, 1);
        world.setBlock(x, y + 1, z + 1, stair, 6, 1);
        world.setBlock(x - 1, y + 1, z, stair, 5, 1);
        world.setBlock(x + 1, y + 1, z, stair, 4, 1);
        world.setBlock(x, y + 1, z, BlockList.shadowBlock);    
        slabMeta = 5;
        if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills) slabMeta = 1;
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                if (i == 0 || k == 0) world.setBlock(x + i, y + 2, z + k, Blocks.stone_slab, slabMeta, 2);
        world.setBlock(x, y + 2, z, block, metadata, 2);
        world.setBlock(x, y + 3, z, BlockList.crystal);
        return true;
    }
    
    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        return generate(world, BiomeGenBase.plains, rand, x, y, z);
    }
    
    public int structureNo()
    {
        return 4;
    }
}
