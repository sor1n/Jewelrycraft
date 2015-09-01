package darkknight.jewelrycraft.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.random.WeightedRandomCurse;
import darkknight.jewelrycraft.worldGen.Generation;
import darkknight.jewelrycraft.worldGen.WorldGenStructure;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.oredict.OreDictionary;

public class JewelrycraftUtil {
	public static ArrayList<ItemStack> objects = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> gem = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> jewelry = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> metal = new ArrayList<ItemStack>();
	public static ArrayList<ItemStack> ores = new ArrayList<ItemStack>();
	public static HashMap<ItemStack, ItemStack> oreToIngot = new HashMap<ItemStack, ItemStack>();
	public static HashMap<ItemStack, Integer> colors = new HashMap<ItemStack, Integer>();
	public static ArrayList<String> jamcraftPlayers = new ArrayList<String>();
	private static ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public static ArrayList<WorldGenStructure> structures = new ArrayList<WorldGenStructure>();
	public static Random rand = new Random();
	public static EnumCreatureAttribute HEART;

	/**
	 * Adds gems and jewelry to their appropriate list
	 */
	public static void addStuff() {
		// Jewels
		for (int i = 0; i < 16; i++)
			gem.add(new ItemStack(BlockList.crystal, 1, i));
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
		for (Object item : GameData.getItemRegistry()) {
			if (Loader.isModLoaded("Mantle") && ((Item) item).getUnlocalizedName().equals("Mantle:item.mantle.manual")) continue;
			try {
				if (item != null && (Item) item != null && ((Item) item).getHasSubtypes() && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
					((Item) item).getSubItems((Item) item, null, items);
				}
				else objects.add(new ItemStack((Item) item));
				if (!items.isEmpty()) objects.addAll(items);
				items.removeAll(items);
			}
			catch (Exception e) {
				JewelrycraftMod.logger.info("Error, tried to add subtypes of item " + ((Item) item).getUnlocalizedName() + "\nItem is not added in the list.");
			}
		}
		// Structures
		try {
			for (Field f : Generation.class.getDeclaredFields()) {
				Object obj = f.get(null);
				if (obj instanceof WorldGenStructure) structures.add((WorldGenStructure) obj);
			}
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void generateColors() {
		for (Object item : GameData.getItemRegistry()) {
			if (Loader.isModLoaded("Mantle") && ((Item) item).getUnlocalizedName().equals("Mantle:item.mantle.manual")) continue;
			try {
				if (item != null && (Item) item != null && ((Item) item).getHasSubtypes() && FMLCommonHandler.instance().getSide() == Side.CLIENT) {
					((Item) item).getSubItems((Item) item, null, items);
				}
				else {
					ItemStack it = new ItemStack((Item) item);
					colors.put(it, color(it, 0));
				}
				if (!items.isEmpty()) for (ItemStack it : items)
					colors.put(it, color(it, 0));
				items.removeAll(items);
			}
			catch (Exception e) {
				JewelrycraftMod.logger.info("Error, tried to add color of the item " + ((Item) item).getUnlocalizedName() + " but it failed.");
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static int getColor(ItemStack item) {
		if (Item.getIdFromItem(item.getItem()) == Block.getIdFromBlock(Blocks.stained_glass) || Item.getIdFromItem(item.getItem()) == Block.getIdFromBlock(Blocks.stained_hardened_clay) || Item.getIdFromItem(item.getItem()) == Block.getIdFromBlock(Blocks.wool) || Item.getIdFromItem(item.getItem()) == Block.getIdFromBlock(Blocks.carpet)) item.setItemDamage(15 - item.getItemDamage());
		for (ItemStack stack : colors.keySet())
			if (item != null && item.getItem() != null && stack.getItem() != null && item.getItem().equals(stack.getItem()) && item.getItemDamage() == stack.getItemDamage()) return colors.get(stack);
		return 0xFFFFFF;
	}

	@SideOnly(Side.CLIENT)
	public static int color(ItemStack stack, int pass) throws IOException {
		IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
		ResourceLocation ingot;
		BufferedImage icon;
		if (stack != null && Item.getIdFromItem(stack.getItem()) > 0 && stack.getIconIndex() != null && stack.getItem().getColorFromItemStack(stack, pass) == 16777215) {
			try {
				ingot = getLocation(stack);
			}
			catch (Exception e) {
				ingot = new ResourceLocation("textures/items/apple.png");
			}
			icon = ImageIO.read(rm.getResource(ingot).getInputStream());
			int height = icon.getHeight();
			int width = icon.getWidth();
			Map m = new HashMap();
			for (int i = 0; i < width; i++)
				for (int j = 0; j < height; j++) {
					int rgb = icon.getRGB(i, j);
					int red = rgb >> 16 & 0xff;
					int green = rgb >> 8 & 0xff;
					int blue = rgb & 0xff;
					int[] rgbArr = { red, green, blue };
					int Cmax = Math.max(red, Math.max(green, blue));
					int Cmin = Math.min(red, Math.min(green, blue));
					if (!isGray(rgbArr)) m.put(rgb, (Cmax + Cmin) / 2);
				}
			return getMostCommonColour(m);
		}
		else return stack.getItem().getColorFromItemStack(stack, pass);
	}

	public static ResourceLocation getLocation(ItemStack item) {
		String domain = "";
		String texture;
		IIcon itemIcon = item.getItem().getIcon(item, 0);
		String iconName = itemIcon.getIconName();
		if (iconName.substring(0, iconName.indexOf(":") + 1) != "") domain = iconName.substring(0, iconName.indexOf(":") + 1).replace(":", " ").trim();
		else domain = "minecraft";
		texture = iconName.substring(iconName.lastIndexOf(":") + 1) + ".png";
		ResourceLocation textureLocation = null;
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		if (texturemanager.getResourceLocation(item.getItemSpriteNumber()).toString().contains("items")) textureLocation = new ResourceLocation(domain.toLowerCase(), "textures/items/" + texture);
		else textureLocation = new ResourceLocation(domain.toLowerCase(), "textures/blocks/" + texture);
		return textureLocation;
	}

	@SideOnly(Side.CLIENT)
	public static int getMostCommonColour(Map map) {
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) o1).getValue()).compareTo(((Map.Entry) o2).getValue());
			}
		});
		Map.Entry me = (Map.Entry) list.get(list.size() - 1);
		for (int i = 0; i < list.size(); i++) {
			float alpha = Float.valueOf(list.get(i).toString().split("=")[1]);
			if (alpha < 180) me = (Map.Entry) list.get(i);
		}
		int rgb = (Integer) me.getKey();
		return rgb;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isGray(int[] rgbArr) {
		int rgbSum = rgbArr[0] + rgbArr[1] + rgbArr[2];
		if (rgbSum > 0 && rgbSum < 256 * 3) return false;
		return true;
	}

	public static WeightedRandomCurse[] getCurses(World world, EntityPlayer player, Random random) {
		WeightedRandomCurse[] curses = new WeightedRandomCurse[Curse.availableCurses.size()];
		for (int c = 0; c < Curse.availableCurses.size(); c++)
			curses[c] = new WeightedRandomCurse(Curse.availableCurses.get(c), Curse.availableCurses.get(c).weight(world, player, random));
		return curses;
	}

	/**
	 * Adds curse points to a player
	 * 
	 * @param player the player to add the points to
	 * @param points amount of curse points
	 */
	public static void addCursePoints(EntityPlayer player, int points) {
		NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
		playerInfo.setInteger("cursePoints", playerInfo.hasKey("cursePoints") ? (playerInfo.getInteger("cursePoints") + points) : points);
		playerInfo.setBoolean("playerCursePointsChanged", true);
	}

	public static int getCursePoints(EntityPlayer player) {
		NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
		return playerInfo.getInteger("cursePoints");
	}

	/**
	 * Adds the UUID's of the jamcrafters in a list (+ special people)
	 */
	public static void jamcrafters() {
		jamcraftPlayers.add("d3214311-7550-4c9c-a372-d9292c10b8a6"); //allout58
		jamcraftPlayers.add("a690119f-c4a2-4bd6-a99d-d63679abb328"); //ChewBaker
		jamcraftPlayers.add("de7c9903-51fa-4a24-88cd-48faf122ca36"); //domi1819
		jamcraftPlayers.add("70aeb298-3a7b-46da-a393-ab10df9359f2"); //founderio
		jamcraftPlayers.add("6fbe603c-14bf-4085-afdd-abe592c26e7c"); //GerbShert
		jamcraftPlayers.add("b0d21306-36bf-4d85-84df-a956d183c45a"); //isomgirls6
		jamcraftPlayers.add("1733a31f-01f9-4f4d-82aa-7de30ca810d3"); //TH3N00B
		jamcraftPlayers.add("4833eacf-1d94-49a7-9f89-4cf88d69dcf9"); //Joban
		jamcraftPlayers.add("718cf671-9084-4e78-b91f-033e80aa11bf"); //KJ4IPS
		jamcraftPlayers.add("bea5e0c4-85c4-454d-a081-e1eaae6895ee"); //Mitchellbrine
		jamcraftPlayers.add("7ecf3e2f-fedf-4f7e-8d24-6731d078db4f"); //MrComputerGhost
		jamcraftPlayers.add("1b11ad3a-f0ca-4695-a019-2d7e5d83a5fd"); //Resinresin
		jamcraftPlayers.add("3ec9ac58-2f1b-4d3f-b4eb-3b875da877ae"); //sci4me
		jamcraftPlayers.add("cf9fa23f-205e-4eed-aba3-9f2848cd6a4d"); //OnyxDarkKnight
		jamcraftPlayers.add("91880caa-b032-48e3-bfe8-c2c7ed31824e"); //theminecoder
		jamcraftPlayers.add("8d0b3804-f71c-4219-897b-8c315448ea7c"); //YSPilot
		jamcraftPlayers.add("bbb87dbe-690f-4205-bdc5-72ffb8ebc29d"); //direwolf20
	}

	/**
	 * Adds a random amount of modifiers to a list
	 * 
	 * @param randValue maximum number of modifiers
	 * @return a list containing the random modifiers
	 */
	public static ArrayList<ItemStack> addRandomModifiers(int randValue) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		for (int i = 0; i < 2 + randValue; i++) {
			ItemStack item = objects.get(new Random().nextInt(objects.size()));
			item.stackSize = 1 + new Random().nextInt(2);
			list.add(item);
		}
		return list;
	}

	/**
	 * Links ores with their appropriate ingot
	 */
	public static void addMetals() {
		int index = 0;
		while (index < OreDictionary.getOreNames().length) {
			Iterator<ItemStack> i = OreDictionary.getOres(OreDictionary.getOreNames()[index]).iterator();
			while (i.hasNext()) {
				ItemStack nextStack = i.next();
				String stackName = nextStack.getItem().getUnlocalizedName().toLowerCase();
				if ((stackName.contains("ingot") || stackName.contains("alloy")) && !metal.contains(nextStack)) metal.add(nextStack);
				if (nextStack.getItem().getUnlocalizedName().toLowerCase().contains("ore") && !ores.contains(nextStack)) {
					ItemStack ingot = FurnaceRecipes.smelting().getSmeltingResult(nextStack);
					if (ingot != null && (ingot.getItem().getUnlocalizedName().toLowerCase().contains("ingot") || ingot.getItem().getUnlocalizedName().toLowerCase().contains("alloy"))) {
						ores.add(nextStack);
						oreToIngot.put(nextStack, ingot);
						JewelrycraftMod.logger.info(nextStack + " Adding " + nextStack.getDisplayName() + " with damage value " + nextStack.getItemDamage() + " and with " + nextStack.stackSize + " in stack");
						JewelrycraftMod.logger.info(ingot + " Adding ingot " + ingot.getDisplayName() + " with damage value " + ingot.getItemDamage() + " and with " + ingot.stackSize + " in stack\n");
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
	public static boolean isGem(ItemStack item) {
		Iterator<ItemStack> i = gem.iterator();
		while (i.hasNext()) {
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
	public static boolean isMetal(ItemStack item) {
		Iterator<ItemStack> i = metal.iterator();
		while (i.hasNext()) {
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
	public static boolean isJewelry(ItemStack item) {
		Iterator<ItemStack> i = jewelry.iterator();
		while (i.hasNext()) {
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
	public static boolean isOre(ItemStack item) {
		Iterator<ItemStack> i = ores.iterator();
		while (i.hasNext()) {
			ItemStack temp = i.next();
			if (temp.getItem().equals(item.getItem()) && temp.getItemDamage() == item.getItemDamage()) return true;
		}
		return false;
	}

	/**
	 * Gets the ingot from the ore
	 * 
	 * @param ore the ore
	 * @return the ingot
	 */
	public static ItemStack getIngotFromOre(ItemStack ore) {
		for (ItemStack ores : JewelrycraftUtil.oreToIngot.keySet())
			if (ores.getItem().equals(ore.getItem()) && ores.getItemDamage() == ore.getItemDamage()) return oreToIngot.get(ores);
		return null;
	}

	/**
	 * This determines whether the player unlocked an achievement or not.
	 * 
	 * @param player The player to unlock the achievement
	 * @param achievement The achievement to be unlocked
	 * @return True or False depending if the player did unlock the achievement or not
	 */
	public static boolean AchievemtUnlocked(EntityPlayer player, Achievement achievement) {
		return ((EntityPlayerMP) player).func_147099_x().hasAchievementUnlocked(achievement);
	}
}
