package darkknight.jewelrycraft.worldGen;

import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

/**
 * @author Sorin
 *
 */
public class ChestGeneration
{
    static Item[] jewelry = new Item[]{ItemList.ring, ItemList.necklace, ItemList.bracelet, ItemList.earrings};
    static Random random = new Random();
    public static void preInit(FMLPreInitializationEvent e)
    {
        addItemToDifferentPlaces(new WeightedRandomChestContent(new ItemStack(ItemList.thiefGloves), 1, 1, 2), true, true, false, false, true);
        addItemToDifferentPlaces(new WeightedRandomChestContent(new ItemStack(ItemList.guide), 1, 1, 7), true, true, true, true, true, false, true, true);
        addVillageBlacksmithLoot(new WeightedRandomChestContent(new ItemStack(ItemList.shadowIngot), 1, 4, 5));
        for(int i = 0; i < 16; i++) addItemToDifferentPlaces(new WeightedRandomChestContent(new ItemStack(BlockList.crystal, 1, i), 1, 4, 4), true, true, true, true);
        
        ItemStack special = new ItemStack(jewelry[random.nextInt(4)]);
        int randValue = random.nextInt(4);
        if(JewelrycraftUtil.metal.size() > 0) JewelryNBT.addMetal(special, JewelrycraftUtil.metal.get(random.nextInt(JewelrycraftUtil.metal.size())));
        if(JewelrycraftUtil.objects.size() > 0) JewelryNBT.addModifiers(special, JewelrycraftUtil.addRandomModifiers(randValue));
        if(JewelrycraftUtil.gem.size() > 0) JewelryNBT.addGem(special, JewelrycraftUtil.gem.get(random.nextInt(JewelrycraftUtil.gem.size())));
        addItemToDifferentPlaces(new WeightedRandomChestContent(special, 1, 1, 1), true, true, true, true);
    }
    
    /**
     * The booleans determine in which places should the items be added. The order is like so:
     * <p><ul>
     * <li> Dungeon
     * <li> Stronhold
     * <li> Pyramid
     * <li> Mineshaft
     * <li> Village Blacksmith
     * <li> Dispenser
     * <li> Bonus Chest
     * <li> Stronghold Library
     * </ul><p>
     */
    public static void addItemToDifferentPlaces(WeightedRandomChestContent item, Boolean ... options)
    {
        if(options.length > 0 && options[0]) addDungeonLoot(item);
        if(options.length > 1 && options[1]) addStrongholdLoot(item);
        if(options.length > 2 && options[2]) addPyramidLoot(item);
        if(options.length > 3 && options[3]) addMineshaftLoot(item);
        if(options.length > 4 && options[4]) addVillageBlacksmithLoot(item);
        if(options.length > 5 && options[5]) addDispenserLoot(item);
        if(options.length > 6 && options[6]) addBonusChestLoot(item);
        if(options.length > 7 && options[7]) addStrongholdLibraryLoot(item);
    }
    
    public static void addDungeonLoot(WeightedRandomChestContent item)
    {
        ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, item);
    }
    
    public static void addStrongholdLoot(WeightedRandomChestContent item)
    {
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CORRIDOR, item);
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_CROSSING, item);
    }
    
    public static void addPyramidLoot(WeightedRandomChestContent item)
    {
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_DESERT_CHEST, item);
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_CHEST, item);
    }
    
    public static void addMineshaftLoot(WeightedRandomChestContent item)
    {
        ChestGenHooks.addItem(ChestGenHooks.MINESHAFT_CORRIDOR, item);
    }
        
    public static void addVillageBlacksmithLoot(WeightedRandomChestContent item)
    {
        ChestGenHooks.addItem(ChestGenHooks.VILLAGE_BLACKSMITH, item);
    }
    
    public static void addDispenserLoot(WeightedRandomChestContent item)
    {
        ChestGenHooks.addItem(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER, item);
    }
    
    public static void addBonusChestLoot(WeightedRandomChestContent item)
    {
        ChestGenHooks.addItem(ChestGenHooks.BONUS_CHEST, item);
    }
    
    public static void addStrongholdLibraryLoot(WeightedRandomChestContent item)
    {
        ChestGenHooks.addItem(ChestGenHooks.STRONGHOLD_LIBRARY, item);
    }
}
