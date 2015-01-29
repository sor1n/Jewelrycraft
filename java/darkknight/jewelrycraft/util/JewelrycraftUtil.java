package darkknight.jewelrycraft.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.lib.Reference;

public class JewelrycraftUtil
{
    public static ArrayList<ItemStack> modifiers = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> gem = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> jewelry = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> metal = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
    public static HashMap<String, Integer> curses = new HashMap<String, Integer>();
    public static HashMap<Item, ItemStack> oreToIngot = new HashMap<Item, ItemStack>();
    public static ArrayList<String> jamcraftPlayers = new ArrayList<String>();
    public static Random rand = new Random();
    
    public static void addStuff()
    {
        // Modifiers
        modifiers.add(new ItemStack(Blocks.chest));
        modifiers.add(new ItemStack(Blocks.torch));
        modifiers.add(new ItemStack(Items.book));
        modifiers.add(new ItemStack(Items.dye, 1, 15));
        modifiers.add(new ItemStack(Items.bone));
        modifiers.add(new ItemStack(Items.sugar));
        modifiers.add(new ItemStack(Items.feather));
        modifiers.add(new ItemStack(Items.bed));
        modifiers.add(new ItemStack(Items.iron_pickaxe));
        modifiers.add(new ItemStack(Items.redstone));
        modifiers.add(new ItemStack(Items.diamond_pickaxe));
        modifiers.add(new ItemStack(Items.blaze_powder));
        modifiers.add(new ItemStack(Items.ender_eye));
        modifiers.add(new ItemStack(Items.potionitem, 1, 8270));
        
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
        
        // Curses
        curses.put(Reference.MODNAME + ":" + "Blind", 0);
        curses.put(Reference.MODNAME + ":" + "Weak", 1);
        curses.put(Reference.MODNAME + ":" + "Anemic", 2);
        curses.put(Reference.MODNAME + ":" + "Scared", 3);
        curses.put(Reference.MODNAME + ":" + "Brave", 4);
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
    
    public static ArrayList<ItemStack> addRandomModifiers()
    {
        ArrayList<ItemStack> list = new ArrayList<ItemStack>();
        for(int i = 0; i < new Random().nextInt(modifiers.size()); i++)
        {
            list.add(modifiers.get(new Random().nextInt(modifiers.size())));
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
                
                if ((nextStack.getItem().getUnlocalizedName().toLowerCase().contains("ingot") || nextStack.getItem().getUnlocalizedName().toLowerCase().contains("alloy")) && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("powder") && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("dust") && !nextStack.getItem().getUnlocalizedName().toLowerCase().contains("block") && !metal.contains(nextStack)){
                    metal.add(nextStack);
                    if(OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")) != null){
                    	ores.addAll(OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")));
                    	Iterator<ItemStack> ores = OreDictionary.getOres(OreDictionary.getOreNames()[index].replace("ingot", "ore")).iterator();
                    	while(ores.hasNext())
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
        Iterator<ItemStack> i = modifiers.iterator();
        
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
