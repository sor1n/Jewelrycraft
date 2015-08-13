/**
 * 
 */
package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * @author Sorin
 */
public class WorldGenStructure extends WorldGenerator 
{
	public boolean generate(World world, BiomeGenBase biome, Random rand, int x, int y, int z) 
	{
		return false;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		return false;
	}
}
