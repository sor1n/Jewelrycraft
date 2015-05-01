package darkknight.jewelrycraft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.item.ItemList;

public class JewelrycraftUtil
{
    public static ArrayList<ItemStack> objects = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> gem = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> jewelry = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> metal = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
    public static HashMap<Item, ItemStack> oreToIngot = new HashMap<Item, ItemStack>();
    public static ArrayList<String> jamcraftPlayers = new ArrayList<String>();
    private static ArrayList<ItemStack> items = new ArrayList<ItemStack>();
    public static Random rand = new Random();
    
    /**
     * Adds gems and jewelry to their appropriate list
     */
    public static void addStuff()
    {
        // Jewels
        for(int i = 0; i < 16; i++)
            gem.add(new ItemStack(ItemList.crystal, 1, i));
        gem.add(new ItemStack(Blocks.redstone_block));
        gem.add(new ItemStack(Blocks.lapis_block));
        gem.add(new ItemStack(Blocks.obsidian));
        gem.add(new ItemStack(Items.diamond));
        gem.add(new ItemStack(Items.emerald));
        gem.add(new ItemStack(Items.ender_pearl));
        gem.add(new ItemStack(Items.nether_star));
        // Jewelry
        jewelry.add(new ItemStack(ItemList.ring));
        jewelry.add(new ItemStack(ItemList.necklace));
        jewelry.add(new ItemStack(ItemList.bracelet));
        jewelry.add(new ItemStack(ItemList.earrings));
        for(Object item: GameData.getItemRegistry()){
            if (Loader.isModLoaded("Mantle") && ((Item)item).getUnlocalizedName().equals("Mantle:item.mantle.manual")) continue;
            try{
                if (item != null && (Item)item != null && ((Item)item).getHasSubtypes() && FMLCommonHandler.instance().getSide() == Side.CLIENT){
                    ((Item)item).getSubItems((Item)item, null, items);
                }else objects.add(new ItemStack((Item)item));
                if (!items.isEmpty()) objects.addAll(items);
                items.removeAll(items);
            }
            catch(Exception e){
                JewelrycraftMod.logger.info("Error, tried to add subtypes of item " + ((Item)item).getUnlocalizedName() + "\nItem is not added in the list.");
            }
        }
    }
    
    /**
     * Adds curse points to a player
     * 
     * @param player the player to add the points to
     * @param points amount of curse points
     */
    public static void addCursePoints(EntityPlayer player, int points)
    {
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        playerInfo.setInteger("cursePoints", playerInfo.hasKey("cursePoints") ? (playerInfo.getInteger("cursePoints") + points) : points);
        playerInfo.setBoolean("playerCursePointsChanged", true);
    }
    
    public static int getCursePoints(EntityPlayer player)
    {
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        return playerInfo.getInteger("cursePoints");
    }
    
    /**
     * Adds the UUID's of the jamcrafters in a list
     */
    public static void jamcrafters()
    {
        jamcraftPlayers.add("d3214311-7550-4c9c-a372-d9292c10b8a6");
        jamcraftPlayers.add("a690119f-c4a2-4bd6-a99d-d63679abb328");
        jamcraftPlayers.add("de7c9903-51fa-4a24-88cd-48faf122ca36");
        jamcraftPlayers.add("70aeb298-3a7b-46da-a393-ab10df9359f2");
        jamcraftPlayers.add("6fbe603c-14bf-4085-afdd-abe592c26e7c");
        jamcraftPlayers.add("b0d21306-36bf-4d85-84df-a956d183c45a");
        jamcraftPlayers.add("1733a31f-01f9-4f4d-82aa-7de30ca810d3");
        jamcraftPlayers.add("4833eacf-1d94-49a7-9f89-4cf88d69dcf9");
        jamcraftPlayers.add("718cf671-9084-4e78-b91f-033e80aa11bf");
        jamcraftPlayers.add("bea5e0c4-85c4-454d-a081-e1eaae6895ee");
        jamcraftPlayers.add("7ecf3e2f-fedf-4f7e-8d24-6731d078db4f");
        jamcraftPlayers.add("1b11ad3a-f0ca-4695-a019-2d7e5d83a5fd");
        jamcraftPlayers.add("3ec9ac58-2f1b-4d3f-b4eb-3b875da877ae");
        jamcraftPlayers.add("cf9fa23f-205e-4eed-aba3-9f2848cd6a4d");
        jamcraftPlayers.add("91880caa-b032-48e3-bfe8-c2c7ed31824e");
        jamcraftPlayers.add("8d0b3804-f71c-4219-897b-8c315448ea7c");
        jamcraftPlayers.add("bbb87dbe-690f-4205-bdc5-72ffb8ebc29d");
    }
    
    /**
     * Adds a random amount of modifiers to a list
     * 
     * @param randValue maximum number of modifiers
     * @return a list containing the random modifiers
     */
    public static ArrayList<ItemStack> addRandomModifiers(int randValue)
    {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for(int i = 0; i < 2 + randValue; i++){
            ItemStack item = objects.get(new Random().nextInt(objects.size()));
            item.stackSize = 1 + new Random().nextInt(2);
            list.add(item);
        }
        return list;
    }
    
    /**
     * Links ores with their appropriate ingot
     */
    public static void addMetals()
    {
        int index = 0;
        while (index < OreDictionary.getOreNames().length){
            Iterator<ItemStack> i = OreDictionary.getOres(OreDictionary.getOreNames()[index]).iterator();
            while (i.hasNext()){
                ItemStack nextStack = i.next();
                if ((nextStack.getItem().getUnlocalizedName().toLowerCase().contains("ingot") || nextStack.getItem().getUnlocalizedName().toLowerCase().contains("alloy")) && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("powder") && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("dust") && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("block") && !metal.contains(nextStack)){
                    metal.add(nextStack);
                    if (OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")) != null){
                        ores.addAll(OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")));
                        Iterator<ItemStack> ores = OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")).iterator();
                        while (ores.hasNext()){
                            ItemStack ore = ores.next();
                            oreToIngot.put(ore.getItem(), nextStack);
                        }
                    }
                }
            }
            index++;
        }
    }
    
    /**
     * Checks to see if the specified item is a gem
     * 
     * @param item ItemStack containing the item
     * @return is the item a gem
     */
    public static boolean isGem(ItemStack item)
    {
        Iterator<ItemStack> i = gem.iterator();
        while (i.hasNext()){
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    /**
     * Checks to see if the specified item is a metal
     * 
     * @param item ItemStack containing the item
     * @return is the item a metal
     */
    public static boolean isMetal(ItemStack item)
    {
        Iterator<ItemStack> i = metal.iterator();
        while (i.hasNext()){
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    /**
     * Checks to see if the specified item is a piece of jewelry
     * 
     * @param item ItemStack containing the item
     * @return is the item a piece of jewelry
     */
    public static boolean isJewelry(ItemStack item)
    {
        Iterator<ItemStack> i = jewelry.iterator();
        while (i.hasNext()){
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    /**
     * Checks to see if the specified item is an ore
     * 
     * @param item ItemStack containing the item
     * @return is the item an ore
     */
    public static boolean isOre(ItemStack item)
    {
        Iterator<ItemStack> i = ores.iterator();
        while (i.hasNext()){
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    /**
     * Gets the ingot from the ore
     * 
     * @param ore the ore
     * @return the ingot
     */
    public static ItemStack getIngotFromOre(Item ore)
    {
        return oreToIngot.get(ore);
    }
}
