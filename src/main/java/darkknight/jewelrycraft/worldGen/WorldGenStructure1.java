/**
 * 
 */
package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.random.WeightedRandomItem;
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

/**
 * @author Sorin
 */
public class WorldGenStructure1 extends WorldGenStructure
{
    public static final WeightedRandomItem[] items = new WeightedRandomItem[]{new WeightedRandomItem(new ItemStack(ItemList.thiefGloves), 10), new WeightedRandomItem(new ItemStack(Items.golden_apple), 8), new WeightedRandomItem(new ItemStack(Items.golden_apple, 1, 1), 1), new WeightedRandomItem(new ItemStack(ItemList.guide), 20), new WeightedRandomItem(new ItemStack(ItemList.shadowIngot), 25), new WeightedRandomItem(new ItemStack(BlockList.shadowEye), 2), new WeightedRandomItem(new ItemStack(Items.nether_star), 1), new WeightedRandomItem(new ItemStack(BlockList.shadowBlock), 2), new WeightedRandomItem(new ItemStack(BlockList.crystal), 16, 20)};
    
    public boolean generate(World world, BiomeGenBase biome, Random rand, int x, int y, int z)
    {
        Block block = Blocks.stonebrick;
        int metadata = 0, slabMeta = 5;        
        if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills){
            block = Blocks.sandstone;
            metadata = 2;
            slabMeta = 1;
        }
        for(int i = -2; i <= 2; i++)
            for(int j = -1; j <= 4; j++)
                for(int k = -2; k <= 2; k++)
                    world.setBlock(x + i, y + j, z + k, Blocks.air);
        for(int i = -2; i <= 2; i++)
            for(int k = -2; k <= 2; k++){
                world.setBlock(x + i, y - 1, z + k, block, metadata, 2);
                if (i % 2 == 0 && k % 2 == 0 && i != 0 && k != 0) world.setBlock(x + i, y, z + k, BlockList.crystal);
            }
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlock(x + i, y, z + k, Blocks.stone_slab, slabMeta, 2);
        world.setBlock(x, y, z, block, metadata, 2);
        world.setBlock(x, y + 1, z, BlockList.handPedestal, 6, 2);
        TileEntityHandPedestal pedestal = (TileEntityHandPedestal)world.getTileEntity(x, y + 1, z);
        pedestal.setHeldItemStack(((WeightedRandomItem)WeightedRandom.getRandomItem(rand, items)).getItem(rand));
        return true;
    }
    
    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        return generate(world, BiomeGenBase.plains, rand, x, y, z);
    }
    
    public int structureNo()
    {
        return 1;
    }
}
