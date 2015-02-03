package darkknight.jewelrycraft.worldGen.village;

import java.util.Random;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.item.ItemMolds;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class JCTrades implements IVillageTradeHandler
{
    
    public JCTrades()
    {
        super();
    }
    
    @Override
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
        if (villager.getProfession() == 3000)
        {
            ItemStack ingredient = null;
            ItemStack ingredient2 = null;
            ItemStack result;
            
            int type = random.nextInt(12);
            switch (type)
            {
                case 0:
                {
                    result = JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size()));
                    result.stackSize = 5 + random.nextInt(5);
                    ingredient = new ItemStack(Items.emerald, 2 + random.nextInt(7));
                    if (random.nextBoolean()) ingredient2 = new ItemStack(Items.emerald, 2 + random.nextInt(2));
                    break;
                }
                case 1:
                {
                    result = new ItemStack(ItemList.molds, 5 + random.nextInt(7), random.nextInt(ItemMolds.moldsItemNames.length));
                    ingredient = new ItemStack(Items.emerald, 1);
                    if (random.nextBoolean()) ingredient2 = new ItemStack(Items.emerald, 1 + random.nextInt(2));
                    break;
                }
                case 2:
                {
                    int number = random.nextInt(3);
                    result = new ItemStack(BlockList.displayer, 1 + number);
                    ingredient = new ItemStack(Blocks.emerald_block, 2 + number*3 + random.nextInt(2));
                    ingredient2 = new ItemStack(Items.emerald, 3 + number + random.nextInt(8));
                    break;
                }
                case 3:
                {
                    result = new ItemStack(BlockList.jewelCraftingTable);
                    ingredient = new ItemStack(Items.emerald, 1 + random.nextInt(2));
                    if (random.nextBoolean()) ingredient2 = new ItemStack(Items.emerald, 1 + random.nextInt(2));
                    break;
                }
                case 4:
                {
                    result = new ItemStack(BlockList.shadowOre, 1 + random.nextInt(6));
                    ingredient = new ItemStack(Items.emerald, 3 + random.nextInt(4));
                    if (random.nextBoolean()) ingredient2 = new ItemStack(Items.emerald, 3 + random.nextInt(4));
                    break;
                }
                case 5:
                {
                    result = new ItemStack(BlockList.molder, 5 + random.nextInt(5));
                    ingredient = new ItemStack(Items.emerald, 1);
                    if (random.nextBoolean()) ingredient2 = new ItemStack(Items.emerald, 1);
                    break;
                }
                case 6:
                {
                    result = new ItemStack(BlockList.smelter);
                    ingredient = new ItemStack(Items.emerald, 1 + random.nextInt(2));
                    if (random.nextBoolean()) ingredient2 = new ItemStack(Items.emerald, 1 + random.nextInt(2));
                    break;
                }
                case 7:
                {
                    int end = random.nextInt(JewelrycraftUtil.gem.size());
                    result = JewelrycraftUtil.gem.get(end);
                    result.stackSize = 1 + random.nextInt(JewelrycraftUtil.gem.size() - end);
                    if (JewelrycraftUtil.gem.size() - 1 - end >= 1)
                    {
                        int value = end;
                        if (value > 64) value = 64;
                        ingredient = new ItemStack(Items.emerald, 2 + random.nextInt(value));
                        if (random.nextBoolean()) ingredient2 = new ItemStack(Items.emerald, 2 + random.nextInt(value));
                    }
                    else
                    {
                        ingredient = new ItemStack(Blocks.emerald_block, 16 + random.nextInt(32));
                        ingredient2 = new ItemStack(Blocks.emerald_block, 8 + random.nextInt(48));
                    }
                    break;
                }
                case 8:
                {
                    result = JewelrycraftUtil.ores.get(random.nextInt(JewelrycraftUtil.ores.size()));
                    result.stackSize = 3 + random.nextInt(3);
                    ingredient = new ItemStack(Items.emerald, 2 + random.nextInt(5));
                    if (random.nextBoolean()) ingredient2 = new ItemStack(Items.emerald, 2 + random.nextInt(6));
                    break;
                }
                case 9:
                {
                    result = new ItemStack(ItemList.guide, 1);
                    ingredient = new ItemStack(Items.emerald, 1);
                    break;
                }
                default:
                {
                    result = new ItemStack(ItemList.ring, 1, 0);
                    int randValue = random.nextInt(4);
                    JewelryNBT.addMetal(result, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())));
                    JewelryNBT.addModifiers(result, JewelrycraftUtil.addRandomModifiers(randValue));
                    JewelryNBT.addGem(result, JewelrycraftUtil.gem.get(random.nextInt(JewelrycraftUtil.gem.size())));
                    ingredient = new ItemStack(Items.emerald, 16 + random.nextInt(20));
                    ingredient2 = new ItemStack(Blocks.emerald_block, 2 + randValue);
                }
            }
            
            recipeList.addToListWithCheck(new MerchantRecipe(ingredient, ingredient2, result));
        }
    }
}