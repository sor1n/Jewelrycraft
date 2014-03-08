package darkknight.jewelrycraft.worldGen.village;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import cpw.mods.fml.common.registry.VillagerRegistry.IVillageTradeHandler;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class JCTrades implements IVillageTradeHandler
{

    public JCTrades()
    {
        super();
    }

    @Override
    public void manipulateTradesForVillager (EntityVillager villager, MerchantRecipeList recipeList, Random random)
    {
        if (villager.getProfession() == 3000)
        {
            ItemStack ingredient = null;
            ItemStack ingredient2 = null;
            ItemStack result;

            int type = random.nextInt(12);
            switch(type)
            {
                case 0:
                {
                    result = JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size()));
                    result.stackSize = 5 + random.nextInt(8);
                    ingredient = new ItemStack(Item.emerald, 2 + random.nextInt(2));
                    if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 2 + random.nextInt(2));
                    break;
                }
                case 1:
                {
                    result = new ItemStack(ItemList.molds, 5 + random.nextInt(7), random.nextInt(2)); 
                    ingredient = new ItemStack(Item.emerald, 1 + random.nextInt(1));
                    if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 1 + random.nextInt(2));
                    break;
                }
                case 2:
                {
                    result = new ItemStack(ItemList.thiefGloves); 
                    ingredient = new ItemStack(Item.emerald, 16 + random.nextInt(8));
                    if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 8 + random.nextInt(4));
                    break;
                }
                case 3:
                {
                    result = new ItemStack(BlockList.displayer, 1 + random.nextInt(6)); 
                    ingredient = new ItemStack(Item.emerald, 3 + random.nextInt(8));
                    if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 3 + random.nextInt(8));
                    break;
                }
                case 4:
                {
                    result = new ItemStack(BlockList.jewelCraftingTable); 
                    ingredient = new ItemStack(Item.emerald, 1 + random.nextInt(2));
                    if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 1 + random.nextInt(2));
                    break;
                }
                case 5:
                {
                    result = new ItemStack(BlockList.shadowOre, 1 + random.nextInt(16)); 
                    ingredient = new ItemStack(Item.emerald, 3 + random.nextInt(4));
                    if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 3 + random.nextInt(4));
                    break;
                }
                case 6:
                {
                    result = new ItemStack(BlockList.molder, 5 + random.nextInt(5)); 
                    ingredient = new ItemStack(Item.emerald, 1 + random.nextInt(1));
                    if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 1 + random.nextInt(1));
                    break;
                }
                case 7:
                {
                    result = new ItemStack(BlockList.smelter); 
                    ingredient = new ItemStack(Item.emerald, 1 + random.nextInt(2));
                    if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 1 + random.nextInt(2));
                    break;
                }
                case 8:
                {
                    int end = random.nextInt(JewelrycraftUtil.modifiers.size());
                    result = JewelrycraftUtil.modifiers.get(end);
                    if(JewelrycraftUtil.modifiers.size() - 1 - end >= 3)
                    {
                        result.stackSize = 1 + random.nextInt(JewelrycraftUtil.modifiers.size() - end);
                        int value = end;
                        if(value > 64) value = 64;
                        ingredient = new ItemStack(Item.emerald, 3 + random.nextInt(value));
                        if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 4 + random.nextInt(value));
                    }
                    else
                    {
                        result.stackSize = 1 + random.nextInt(7);
                        ingredient = new ItemStack(Item.emerald, result.stackSize/2 + 1 + random.nextInt(7));
                        ingredient2 = new ItemStack(Item.emerald, result.stackSize/2 + 1 + random.nextInt(4));                        
                    }
                    if(result.getMaxStackSize()<=1) result.stackSize = 1;
                    break;
                }
                case 9:
                {
                    int end = random.nextInt(JewelrycraftUtil.jewel.size());
                    result = JewelrycraftUtil.jewel.get(end);
                    result.stackSize = 1 + random.nextInt(JewelrycraftUtil.jewel.size() - end);
                    if(JewelrycraftUtil.jewel.size() - 1 - end >= 1)
                    {
                        int value = end;
                        if(value > 64) value = 64;
                        ingredient = new ItemStack(Item.emerald, 2 + random.nextInt(value));
                        if(random.nextBoolean()) ingredient2 = new ItemStack(Item.emerald, 2 + random.nextInt(value));
                    }
                    else
                    {
                        ingredient = new ItemStack(Block.blockEmerald, 16 + random.nextInt(32));
                        ingredient2 = new ItemStack(Block.blockEmerald, 8 + random.nextInt(48));                        
                    }
                    break;
                }
                default: 
                {
                    result = new ItemStack(ItemList.ring, 1, 0);
                    JewelryNBT.addMetal(result, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())));
                    JewelryNBT.addModifier(result, JewelrycraftUtil.modifiers.get(random.nextInt(JewelrycraftUtil.modifiers.size())));
                    JewelryNBT.addJewel(result, JewelrycraftUtil.jewel.get(random.nextInt(JewelrycraftUtil.jewel.size())));
                    if(JewelryNBT.isModifierEffectType(result)) JewelryNBT.addMode(result, "Activated");
                    if(JewelryNBT.isJewelX(result, new ItemStack(Item.netherStar)) && JewelryNBT.isModifierX(result, new ItemStack(Item.book))) 
                        JewelryNBT.addMode(result, "Disenchant");
                    ingredient = new ItemStack(Item.emerald, 16 + random.nextInt(20));
                    ingredient2 = new ItemStack(Block.blockEmerald, 5 + random.nextInt(5));
                }
            }

            recipeList.addToListWithCheck(new MerchantRecipe(ingredient, ingredient2, result));
        }
    }
}