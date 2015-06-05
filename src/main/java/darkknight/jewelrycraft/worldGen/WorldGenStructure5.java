/**
 * 
 */
package darkknight.jewelrycraft.worldGen;

import java.io.IOException;
import java.util.Random;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.item.ItemMoltenMetalBucket;
import darkknight.jewelrycraft.network.PacketSendLiquidData;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.DungeonHooks;

/**
 * @author Sorin
 */
public class WorldGenStructure5 extends WorldGenerator
{
    public boolean generate(World world, BiomeGenBase biome, Random rand, int x, int y, int z)
    {
        Block slab = Blocks.stone_slab;
        Block stair = Blocks.stone_brick_stairs;
        Block block = Blocks.stonebrick;
        int metadata = 0, slabMeta = 5;        
        if (biome == BiomeGenBase.desert || biome == BiomeGenBase.desertHills){
            stair = Blocks.sandstone_stairs;
            block = Blocks.sandstone;
            metadata = 2;
            slabMeta = 1;
        }
        for(int i = -2; i <= 2; i++)
            for(int j = 0; j <= 3; j++)
                for(int k = -3; k <= -1; k++)
                    world.setBlock(x + i, y + j, z + k, Blocks.air);
        for(int i = -5; i <= -3; i++)
            for(int k = -3; k <= 2; k++)
                world.setBlock(x + i, y, z + k, Blocks.air);
        for(int i = -2; i <= 2; i++)
            for(int k = -3; k <= -1; k++)
                world.setBlock(x + i, y, z + k, block, metadata, 1);
        for(int i = 1; i <= 2; i++)
            for(int k = -3; k <= -1; k++)
                world.setBlock(x + i, y + 1, z + k, block, metadata, 1);
        for(int i = -5; i <= -3; i++)
            for(int k = -3; k <= 2; k++)
                world.setBlock(x + i, y - 1, z + k, block, metadata, 1);
        for(int i = -5; i <= -3; i++)
            for(int k = -3; k <= 2; k++)
                if ((i != -4 || k <= -3 || k >= 2) && !(i == -3 && k == -2)) world.setBlock(x + i, y, z + k, slab, slabMeta, 2);
        world.setBlock(x - 3, y, z - 1, stair, 0, 2);
        world.setBlock(x - 3, y, z - 3, stair, 0, 2);
        world.setBlock(x - 2, y + 1, z - 3, slab, slabMeta, 2);
        world.setBlock(x - 2, y + 1, z - 1, slab, slabMeta, 2);
        world.setBlock(x - 1, y + 1, z - 3, slab, slabMeta, 2);
        world.setBlock(x - 1, y + 1, z - 1, slab, slabMeta, 2);
        world.setBlock(x, y + 1, z - 1, stair, 0, 2);
        world.setBlock(x, y + 1, z - 3, stair, 0, 2);
        world.setBlock(x + 1, y + 2, z - 3, slab, slabMeta, 2);
        world.setBlock(x + 1, y + 2, z - 1, slab, slabMeta, 2);
        for(int k = -3; k <= -1; k++)
            world.setBlock(x + 2, y + 2, z + k, block, metadata, 1);
        if (rand.nextInt(5) == 0){
            ItemStack stack = new ItemStack(ItemList.bucket);
            JewelryNBT.addMetal(stack, new ItemStack(Items.gold_ingot));
            try{
                if (stack != null && JewelryNBT.ingot(stack) != null){
                    if (!world.isRemote) world.func_147480_a(x, y, z, true);
                    int color = ItemMoltenMetalBucket.color(stack, 1);
                    JewelrycraftMod.saveData.setString((x + 1) + " " + (y + 2) + " " + (z - 2) + " " + world.provider.dimensionId, Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) + ":" + JewelryNBT.ingot(stack).getItemDamage() + ":" + color);
                    JewelrycraftMod.netWrapper.sendToAll(new PacketSendLiquidData(world.provider.dimensionId, x + 1, y + 2, z - 2, Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()), JewelryNBT.ingot(stack).getItemDamage(), color));
                    world.setBlock(x + 1, y + 2, z - 2, BlockList.moltenMetal, 0, 3);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }else if (rand.nextBoolean()) world.setBlock(x + 1, y + 2, z - 2, Blocks.lava, 0, 3);
        else world.setBlock(x + 1, y + 2, z - 2, Blocks.water, 0, 3);
        return true;
    }
    
    @Override
    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        return generate(world, BiomeGenBase.plains, rand, x, y, z);
    }
}
