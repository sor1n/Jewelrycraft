package darkknight.jewelrycraft.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;

public class CraftingRecipes
{
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            //Items
            GameRegistry.addRecipe(new ItemStack(ItemList.thiefGloves), "x x", "yxy", "yxy", 'x', ItemList.shadowIngot, 'y', new ItemStack(Block.cloth, 1, 15));
            GameRegistry.addRecipe(new ItemStack(ItemList.clayMolds, 1, 0), "xx", 'x', Item.clay);
            GameRegistry.addRecipe(new ItemStack(ItemList.clayMolds, 1, 1), " x ", "x x", " x ", 'x', Item.clay);
            GameRegistry.addRecipe(new ItemStack(ItemList.clayMolds, 1, 2), "x x", "x x", " x ", 'x', Item.clay);
            GameRegistry.addRecipe(new ItemStack(ItemList.crystal, 1, 15), " x ", "x x", " x ", 'x', Block.glass);
            for(int i=0; i < 15; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ItemList.crystal, 1, i), new Object[]{new ItemStack(ItemList.crystal, 1, 15), new ItemStack(Item.dyePowder, 1, i)});
                GameRegistry.addShapelessRecipe(new ItemStack(ItemList.crystal, 1, 15), new Object[]{new ItemStack(ItemList.crystal, 1, i), new ItemStack(Item.dyePowder, 1, 15)});
            }
            
            //Blocks
            GameRegistry.addRecipe(new ItemStack(BlockList.molder), "x x", "xxx", 'x', Block.cobblestone);
            GameRegistry.addRecipe(new ItemStack(BlockList.smelter), "xyx", "x x", "xzx", 'x', Block.cobblestone, 'y', Item.bucketEmpty, 'z', Item.bucketLava);
            GameRegistry.addRecipe(new ItemStack(BlockList.jewelCraftingTable), "xxx", "y y", "y y", 'x', Block.planks, 'y', Block.cobblestone);
            GameRegistry.addRecipe(new ItemStack(BlockList.displayer, 2), " x ", "xxx", "yyy", 'x', Item.ingotIron, 'y', Block.blockEmerald);
            GameRegistry.addRecipe(new ItemStack(BlockList.shadowBlock, 1), "xxx", "xxx", "xxx", 'x', ItemList.shadowIngot);
            GameRegistry.addRecipe(new ItemStack(BlockList.jewelAltar, 1), "sws", "bwb", "bbb", 's', Block.whiteStone, 'w', new ItemStack(Block.cloth, 1, 5), 'b', Block.netherBrick);
            
            //Smelting
            GameRegistry.addSmelting(BlockList.shadowOre.blockID, new ItemStack(ItemList.shadowIngot), 1.5f);            
            FurnaceRecipes.smelting().addSmelting(ItemList.clayMolds.itemID, 0, new ItemStack(ItemList.molds, 1, 0), 0.2F);
            FurnaceRecipes.smelting().addSmelting(ItemList.clayMolds.itemID, 1, new ItemStack(ItemList.molds, 1, 1), 0.2F);
            FurnaceRecipes.smelting().addSmelting(ItemList.clayMolds.itemID, 2, new ItemStack(ItemList.molds, 1, 2), 0.2F);
            
            isInitialized = true;
        }
    }
}
