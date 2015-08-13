/**
 * 
 */
package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.DungeonHooks;

/**
 * @author Sorin
 */
public class WorldGenStructure3 extends WorldGenStructure
{
    public boolean generate(World world, BiomeGenBase biome, Random rand, int x, int y, int z)
    {
        Block slab = Blocks.stone_slab;
        Block stair = Blocks.stone_brick_stairs;
        int slabMeta = 13;        
        if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills){
            stair = Blocks.sandstone_stairs;
            slabMeta = 9;
        }
        for(int i = -1; i <= 1; i++)
            for(int j = -1; j <= 2; j++)
                for(int k = -1; k <= 1; k++)
                    world.setBlock(x + i, y + j, z + k, Blocks.air);
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlock(x + i, y - 1, z + k, slab, slabMeta, 1);
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlock(x + i, y, z + k, stair);
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlockMetadataWithNotify(x + i, y, z + k, (k == -1) ? 3 : (k == 0) ? (i == 1) ? 0 : 1 : 2, 2);
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlock(x + i, y + 1, z + k, stair);
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlockMetadataWithNotify(x + i, y + 1, z + k, (k == -1) ? 7 : (k == 0) ? (i == 1) ? 4 : 5 : 6, 2);
        slabMeta = 5;
        if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills) slabMeta = 1;
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlock(x + i, y + 2, z + k, slab, slabMeta, 2);
        world.setBlock(x, y, z, Blocks.mob_spawner);
        world.setBlock(x, y + 1, z, Blocks.mob_spawner);
        for(int l = 0; l < 2; l++){
            TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getTileEntity(x, y + l, z);
            if (tileentitymobspawner != null) tileentitymobspawner.func_145881_a().setEntityName(DungeonHooks.getRandomDungeonMob(rand));
            else System.err.println("Failed to fetch mob spawner entity at (" + x + ", " + (y + l) + ", " + z + ")");
        }
        return true;
    }
    
    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        return generate(world, BiomeGenBase.plains, rand, x, y, z);
    }
}
