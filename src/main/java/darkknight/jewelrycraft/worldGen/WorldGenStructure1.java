/**
 * 
 */
package darkknight.jewelrycraft.worldGen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

/**
 * @author Sorin
 */
public class WorldGenStructure1 extends WorldGenerator
{
    public static final WeightedRandomItem[] items = new WeightedRandomItem[] {
        new WeightedRandomItem(new ItemStack(ItemList.thiefGloves), 7),
        new WeightedRandomItem(new ItemStack(Items.golden_apple), 5),
        new WeightedRandomItem(new ItemStack(Items.golden_apple, 1, 1), 1),
        new WeightedRandomItem(new ItemStack(ItemList.guide), 10),
        new WeightedRandomItem(new ItemStack(ItemList.shadowIngot), 15),
        new WeightedRandomItem(new ItemStack(BlockList.shadowEye), 2),
        new WeightedRandomItem(new ItemStack(Items.nether_star), 1),
        new WeightedRandomItem(new ItemStack(BlockList.shadowBlock), 2),
        new WeightedRandomItem(new ItemStack(BlockList.crystal), 16, 10)
        };
    
    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        for(int i = -2; i <= 2; i++)
            for(int j = -1; j <= 4; j++)
                for(int k = -2; k <= 2; k++)
                    world.setBlock(x + i, y + j, z + k, Blocks.air);
        
        for(int i = -2; i <= 2; i++)
            for(int k = -2; k <= 2; k++){
                world.setBlock(x + i, y - 1, z + k, Blocks.stonebrick);
                if (i % 2 == 0 && k % 2 == 0 && i != 0 && k != 0) world.setBlock(x + i, y, z + k, BlockList.crystal);
            }
        for(int i = -1; i <= 1; i++)
            for(int k = -1; k <= 1; k++)
                world.setBlock(x + i, y, z + k, Blocks.stone_slab, 5, 2);
        
        world.setBlock(x, y, z, Blocks.stonebrick);
        world.setBlock(x, y+1, z, BlockList.handPedestal, 6, 2);
        TileEntityHandPedestal pedestal = (TileEntityHandPedestal)world.getTileEntity(x, y+1, z);
        pedestal.setHeldItemStack(((WeightedRandomItem)WeightedRandom.getRandomItem(rand, items)).getItem(rand));
        
        return true;
    }
}
