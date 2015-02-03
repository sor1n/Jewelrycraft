package darkknight.jewelrycraft.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
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
            // Items
            GameRegistry.addRecipe(new ItemStack(ItemList.thiefGloves), "x x", "yxy", "yxy", 'x', ItemList.shadowIngot, 'y', new ItemStack(Blocks.wool, 1, 15));
            GameRegistry.addRecipe(new ItemStack(ItemList.clayMolds, 1, 0), "xx", 'x', Items.clay_ball);
            GameRegistry.addRecipe(new ItemStack(ItemList.clayMolds, 1, 1), " x ", "x x", " x ", 'x', Items.clay_ball);
            GameRegistry.addRecipe(new ItemStack(ItemList.clayMolds, 1, 2), "x x", "x x", " x ", 'x', Items.clay_ball);
            GameRegistry.addRecipe(new ItemStack(ItemList.clayMolds, 1, 3), "xxx", "x x", "xxx", 'x', Items.clay_ball);
            GameRegistry.addRecipe(new ItemStack(ItemList.clayMolds, 1, 4), "x x", 'x', Items.clay_ball);
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemList.crystal, 1, 15), " x ", "x x", " x ", 'x', Blocks.glass));
            for (int i = 0; i < 15; i++)
            {
                GameRegistry.addShapelessRecipe(new ItemStack(ItemList.crystal, 1, i), new Object[]
                { new ItemStack(ItemList.crystal, 1, 15), new ItemStack(Items.dye, 1, i) });
                GameRegistry.addShapelessRecipe(new ItemStack(ItemList.crystal, 1, 15), new Object[]
                { new ItemStack(ItemList.crystal, 1, i), new ItemStack(Items.dye, 1, 15) });
            }
            GameRegistry.addShapelessRecipe(new ItemStack(ItemList.shadowIngot, 9), new Object[]
            { new ItemStack(BlockList.shadowBlock) });
            GameRegistry.addShapelessRecipe(new ItemStack(ItemList.guide), new Object[]
            { new ItemStack(Items.book), new ItemStack(ItemList.molds, 1, 0) });
            GameRegistry.addShapelessRecipe(new ItemStack(ItemList.guide), new Object[]
            { new ItemStack(Items.book), new ItemStack(ItemList.molds, 1, 1) });
            GameRegistry.addShapelessRecipe(new ItemStack(ItemList.guide), new Object[]
            { new ItemStack(Items.book), new ItemStack(ItemList.molds, 1, 2) });
            GameRegistry.addShapelessRecipe(new ItemStack(ItemList.guide), new Object[]
            { new ItemStack(Items.book), new ItemStack(ItemList.molds, 1, 3) });
            GameRegistry.addShapelessRecipe(new ItemStack(ItemList.guide), new Object[]
            { new ItemStack(Items.book), new ItemStack(ItemList.molds, 1, 4) });
            
            // Blocks
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.molder), "x x", "xxx", 'x', Blocks.cobblestone));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.smelter), "xyx", "x x", "xzx", 'x', Blocks.cobblestone, 'y', Items.bucket, 'z', Items.lava_bucket));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.jewelCraftingTable), "xxx", "y y", "y y", 'x', Blocks.planks, 'y', Blocks.cobblestone));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.displayer, 2), " x ", "xxx", "yyy", 'x', Items.iron_ingot, 'y', Blocks.emerald_block));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockList.shadowBlock, 1), "xxx", "xxx", "xxx", 'x', ItemList.shadowIngot));
            GameRegistry.addRecipe(new ItemStack(BlockList.jewelAltar, 1), "sws", "bwb", "bbb", 's', Blocks.end_stone, 'w', new ItemStack(Blocks.wool, 1, 5), 'b', Blocks.nether_brick);
            
            // Smelting
            GameRegistry.addSmelting(BlockList.shadowOre, new ItemStack(ItemList.shadowIngot), 1.5f);
            GameRegistry.addSmelting(new ItemStack(ItemList.clayMolds, 1, 0), new ItemStack(ItemList.molds, 1, 0), 0.2F);
            GameRegistry.addSmelting(new ItemStack(ItemList.clayMolds, 1, 1), new ItemStack(ItemList.molds, 1, 1), 0.2F);
            GameRegistry.addSmelting(new ItemStack(ItemList.clayMolds, 1, 2), new ItemStack(ItemList.molds, 1, 2), 0.2F);
            GameRegistry.addSmelting(new ItemStack(ItemList.clayMolds, 1, 3), new ItemStack(ItemList.molds, 1, 3), 0.2F);
            GameRegistry.addSmelting(new ItemStack(ItemList.clayMolds, 1, 4), new ItemStack(ItemList.molds, 1, 4), 0.2F);
            
            isInitialized = true;
        }
    }
}
