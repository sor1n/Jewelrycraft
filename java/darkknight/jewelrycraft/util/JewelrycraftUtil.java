package darkknight.jewelrycraft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.lib.Reference;

public class JewelrycraftUtil
{
    public static ArrayList<ItemStack> objects = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> gem = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> jewelry = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> metal = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
    public static HashMap<String, Integer> curseValues = new HashMap<String, Integer>();
    public static HashMap<Integer, String> curseNames = new HashMap<Integer, String>();
    public static HashMap<Integer, String> availableCurseNames = new HashMap<Integer, String>();
    public static HashMap<String, Integer> availableCurseValues = new HashMap<String, Integer>();
    public static HashMap<Item, ItemStack> oreToIngot = new HashMap<Item, ItemStack>();
    public static ArrayList<String> jamcraftPlayers = new ArrayList<String>();
    public static Random rand = new Random();
    
    public static void addStuff()
    {
        // Modifiers
        for(Object item: GameData.getItemRegistry())
        {
            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
            ((Item)item).getSubItems((Item)item, null, items);
            objects.addAll(items);
        }
        
        // Jewels
        for (int i = 0; i < 16; i++)
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
        
        // Curses
        addCurse(Reference.MODNAME + ":" + "Blind", 0);
        addCurse(Reference.MODNAME + ":" + "Weak", 1);
        addCurse(Reference.MODNAME + ":" + "Anemic", 2);
        addCurse(Reference.MODNAME + ":" + "Scared", 3);
        addCurse(Reference.MODNAME + ":" + "Brave", 4);
    }
    
    public static void jamcrafters()
    {
        jamcraftPlayers.add("allout58");
        jamcraftPlayers.add("ChewBaker");
        jamcraftPlayers.add("domi1819");
        jamcraftPlayers.add("founderio");
        jamcraftPlayers.add("Ironhammer354");
        jamcraftPlayers.add("isomgirls6");
        jamcraftPlayers.add("jmjmjm439");
        jamcraftPlayers.add("Joban");
        jamcraftPlayers.add("KJ4IPS");
        jamcraftPlayers.add("Mitchellbrine");
        jamcraftPlayers.add("MrComputerGhost");
        jamcraftPlayers.add("MrKol999");
        jamcraftPlayers.add("Resinresin");
        jamcraftPlayers.add("sci4me");
        jamcraftPlayers.add("sor1n");
        jamcraftPlayers.add("theminecoder");
        jamcraftPlayers.add("YSPilot");
        jamcraftPlayers.add("direwolf20");
    }
    
    public static void addCurse(String name, int val)
    {
        curseValues.put(name, val);
        curseNames.put(val, name);
        availableCurseValues.put(name, val);
        availableCurseNames.put(val, name);
    }
    
    public static ArrayList<ItemStack> addRandomModifiers(int randValue)
    {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for (int i = 0; i < 2 + randValue; i++)
        {
            ItemStack item = objects.get(new Random().nextInt(objects.size()));
            item.stackSize = 1 + new Random().nextInt(2);
            list.add(item);
        }
        return list;
    }
    
    public static void addMetals()
    {
        int index = 0;
        while (index < OreDictionary.getOreNames().length)
        {
            Iterator<ItemStack> i = OreDictionary.getOres(OreDictionary.getOreNames()[index]).iterator();
            
            while (i.hasNext())
            {
                ItemStack nextStack = i.next();
                
                if ((nextStack.getItem().getUnlocalizedName().toLowerCase().contains("ingot") || nextStack.getItem().getUnlocalizedName().toLowerCase().contains("alloy")) && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("powder") && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("dust") && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("block") && !metal.contains(nextStack))
                {
                    metal.add(nextStack);
                    if (OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")) != null)
                    {
                        ores.addAll(OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")));
                        Iterator<ItemStack> ores = OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")).iterator();
                        while (ores.hasNext())
                        {
                            ItemStack ore = ores.next();
                            oreToIngot.put(ore.getItem(), nextStack);
                        }
                    }
                }
            }
            index++;
        }
    }
    
    public static boolean isModifier(ItemStack item)
    {
        Iterator<ItemStack> i = objects.iterator();
        
        while (i.hasNext())
        {
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    public static boolean isGem(ItemStack item)
    {
        Iterator<ItemStack> i = gem.iterator();
        
        while (i.hasNext())
        {
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    public static boolean isMetal(ItemStack item)
    {
        Iterator<ItemStack> i = metal.iterator();
        
        while (i.hasNext())
        {
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    public static boolean isJewelry(ItemStack item)
    {
        Iterator<ItemStack> i = jewelry.iterator();
        
        while (i.hasNext())
        {
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    public static boolean isOre(ItemStack item)
    {
        Iterator<ItemStack> i = ores.iterator();
        
        while (i.hasNext())
        {
            ItemStack temp = i.next();
            if (temp.getItem() == item.getItem() && temp.getItemDamage() == item.getItemDamage()) return true;
        }
        return false;
    }
    
    public static ItemStack getIngotFromOre(Item ore)
    {
        return oreToIngot.get(ore);
    }
}
