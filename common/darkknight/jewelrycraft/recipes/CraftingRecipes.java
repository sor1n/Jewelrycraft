package darkknight.jewelrycraft.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
            GameRegistry.addRecipe(new ItemStack(ItemList.thiefGloves), "x x", "yxy", "yxy", 'x', ItemList.shadowIngot, 'y', new ItemStack(Block.cloth, 1, 15));
            GameRegistry.addRecipe(new ItemStack(ItemList.molds, 1, 0), "x", "y", 'x', Item.ingotGold, 'y', Block.cobblestone);
            GameRegistry.addRecipe(new ItemStack(ItemList.molds, 1, 1), "x x", "xyx", "x x", 'x', Item.ingotGold, 'y', Block.cobblestone);
            GameRegistry.addRecipe(new ItemStack(BlockList.molder), "x x", "xxx", 'x', Block.cobblestone);
            GameRegistry.addRecipe(new ItemStack(BlockList.smelter), "xyx", "x x", "xzx", 'x', Block.cobblestone, 'y', Item.bucketEmpty, 'z', Item.bucketLava);
            GameRegistry.addRecipe(new ItemStack(BlockList.jewelCraftingTable), "xxx", "y y", "y y", 'x', Block.planks, 'y', Block.cobblestone);
            GameRegistry.addSmelting(BlockList.shadowOre.blockID, new ItemStack(ItemList.shadowIngot), 1.5f);
            
            isInitialized = true;
        }
    }
}
