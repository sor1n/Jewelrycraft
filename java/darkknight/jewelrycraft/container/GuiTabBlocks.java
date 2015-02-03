package darkknight.jewelrycraft.container;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;

public class GuiTabBlocks extends GuiTab
{
    
    public GuiTabBlocks(int id)
    {
        super("Blocks", id);
    }
    
    public ItemStack getIcon()
    {
        return new ItemStack(BlockList.jewelAltar);
    }
    
    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {
        String text = "";
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        int xPos = (page % 2 == 0) ? 107 : -35;
        switch (page)
        {
            case 1:
                text = "This ore is extremely rare and can be found only between Y-level 5 and 8. It can only be mined using a diamond pickaxe.";
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), new ItemStack(BlockList.shadowOre), text, 90f);
                break;
            case 2:
                text = "The Shadow Block is crafted using 9 shadow ingots. Magicians believed it held the ability to merge with the shadows. It becomes more transparent as it";
                items.add(new ItemStack(BlockList.shadowBlock));
                for (int i = 1; i <= 9; i++)
                    items.add(new ItemStack(ItemList.shadowIngot));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 3:
                text = "gets darker. If a comparator is attached to it, the output strength will be equal to the value of darkness it is in.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 4:
                text = "The smelter is one of the first blocks needed to get started with Jewelrycraft. Requiring just some cobble and a couple buckets. It is required in order to";
                items.add(new ItemStack(BlockList.smelter));
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(new ItemStack(Items.bucket));
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(null);
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(new ItemStack(Items.lava_bucket));
                items.add(new ItemStack(Blocks.cobblestone));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 5:
                text = "melt ingots or even ores which can be made into rings, necklaces, bracelets or earrings. To use the block all you need to do is right click on it with any ore or ingot. It can melt multimple ingots/ores at a time. Crouch (default: Shift) + Right Click will remove all items";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 6:
                text = "added. If right clicked when done smelting, it will say what the block contains.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 7:
                text = "The molder is a key piece in creating jewellery. You need to pour the molten metal out of the smelter somewhere. That somewhere is the";
                
                items.add(new ItemStack(BlockList.molder));
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(null);
                items.add(new ItemStack(Blocks.cobblestone));
                for (int i = 1; i <= 3; i++)
                    items.add(new ItemStack(Blocks.cobblestone));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 8:
                text = "molder. But before pouring the molten metal in it, you must first add a mold. You can do that by simply right clicking the block with the mold of your choice. If you want to get the mold out, simply crouch and Right Click it with an empty hand. Once you";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 9:
                text = "have a mold inside, left click on the smelter and wait for the metal to cool down. When it's done, left click on the molder to get the jewellery. Be aware that this block must be placed directly in front of the smelter, otherwise it won't work!";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 10:
                text = "This table allows you to add a gem to a piece of jewellery. Right click the block while holding a jewellery to add it in. Then do the same with a gem (you";
                if (del == 0) values++;
                del++;
                if (del >= 300) del = 0;
                if (values >= 4) values = 0;
                items.add(new ItemStack(BlockList.jewelCraftingTable));
                for (int i = 1; i <= 3; i++)
                    items.add(new ItemStack(Blocks.planks, 1, values));
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(null);
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(new ItemStack(Blocks.cobblestone));
                items.add(null);
                items.add(new ItemStack(Blocks.cobblestone));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 11:
                text = "can find a list with all possible gems in this guide). Crouch + Right Click to retreive placed items. Left Click the block to see the progress the crafting has made. Once the crafting is done, Left Click the block to get the item. You are able to recraft a";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 12:
                text = "jewellery by readding the modified version to this block and adding a different gem to it. Once the crafting is done, the current gem will be replaced by the new one. There is also a 50% chance that you will get back the old gem.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 13:
                text = "This block can store any jewellery in it and activate their effects as it were a player. To do that simply right click the block with a jewellery. Crouch +";
                items.add(new ItemStack(BlockList.jewelAltar));
                items.add(new ItemStack(Blocks.end_stone));
                items.add(new ItemStack(Blocks.wool, 1, 5));
                items.add(new ItemStack(Blocks.end_stone));
                items.add(new ItemStack(Blocks.nether_brick));
                items.add(new ItemStack(Blocks.wool, 1, 5));
                items.add(new ItemStack(Blocks.nether_brick));
                items.add(new ItemStack(Blocks.nether_brick));
                items.add(new ItemStack(Blocks.nether_brick));
                items.add(new ItemStack(Blocks.nether_brick));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 14:
                text = "Right Click to retreive the jewellery.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 15:
                text = "The Storage Displayer, as the name suggests, can store a large amount (Up to: " + Integer.MAX_VALUE + ") of a  single item/block placed in it. It will";
                items.add(new ItemStack(BlockList.displayer));
                items.add(null);
                items.add(new ItemStack(Items.iron_ingot));
                items.add(null);
                items.add(new ItemStack(Items.iron_ingot));
                items.add(new ItemStack(Items.iron_ingot));
                items.add(new ItemStack(Items.iron_ingot));
                items.add(new ItemStack(Blocks.emerald_block));
                items.add(new ItemStack(Blocks.emerald_block));
                items.add(new ItemStack(Blocks.emerald_block));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 16:
                text = "display all possible infromation about the object in it, such as the name, durability, enchantments etc. To store something in it simply right click with that object on it and the whole amount of items or blocks you are holding will be immediately stored inside.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 17:
                text = "If a displayer already contains an item and you have that in your inventory, you can simply hold right click on it with an empty hand to add all of your items of that type from your inventory. To retrieve a single item just left click the block. If you wish to";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 18:
                text = "get a whole stack, Crouch + Left Click on it. In creative mode you can simply hold Right Click on a displayer containing an object and it will add 64 of it with every right click, without it being in your inventory.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            default:
                ;
        }
    }
    
    public int getMaxPages()
    {
        return 17;
    }
    
    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {
    }
    
}
