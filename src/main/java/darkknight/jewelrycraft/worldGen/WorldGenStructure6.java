/**
 * 
 */
package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.DungeonHooks;

/**
 * @author Sorin
 */
public class WorldGenStructure6 extends WorldGenerator
{
    public boolean generate(World world, BiomeGenBase biome, Random rand, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        return generate(world, BiomeGenBase.plains, rand, x, y, z);
    }
}
