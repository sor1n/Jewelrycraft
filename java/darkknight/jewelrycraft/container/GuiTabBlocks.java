package darkknight.jewelrycraft.container;

import java.awt.Desktop;
import java.net.URL;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;

public class GuiTabBlocks extends GuiTab
{
    
    /**
     * @param id
     */
    public GuiTabBlocks(int id)
    {
        super("Blocks", id);
    }
    
    /**
     * @return
     */
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(BlockList.jewelAltar);
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param page
     */
    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {
        String text = "";
        int xPos = page % 2 == 0 ? 107 : -35;
        switch(page)
        {
            case 1:
                text = "This ore is extremely rare and can be found only between Y-level 5 and 8. It can only be mined using a diamond pickaxe.";
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), new ItemStack(BlockList.shadowOre), text, 90f, true);
                break;
            case 2:
                text = "The Shadow Block is crafted using 9 shadow ingots. Magicians believed it held the ability to merge with the shadows. It becomes more transparent as it";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(BlockList.shadowBlock), new ItemStack(ItemList.shadowIngot), new ItemStack(ItemList.shadowIngot), new ItemStack(ItemList.shadowIngot), new ItemStack(ItemList.shadowIngot), new ItemStack(ItemList.shadowIngot), new ItemStack(ItemList.shadowIngot), new ItemStack(ItemList.shadowIngot), new ItemStack(ItemList.shadowIngot), new ItemStack(ItemList.shadowIngot));
                break;
            case 3:
                text = "gets darker. If a comparator is attached to it, the output strength will be equal to the value of darkness it is in.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 4:
                text = "The smelter is one of the first blocks needed to get started with Jewelrycraft. Requiring just some cobble and a couple buckets. It is required in order to";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(BlockList.smelter), new ItemStack(Blocks.cobblestone), new ItemStack(Items.bucket), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.cobblestone), null, new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Items.lava_bucket), new ItemStack(Blocks.cobblestone));
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
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(BlockList.molder), new ItemStack(Blocks.cobblestone), null, new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.cobblestone));
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
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(BlockList.jewelCraftingTable), new ItemStack(Blocks.planks, 1, values), new ItemStack(Blocks.planks, 1, values), new ItemStack(Blocks.planks, 1, values), new ItemStack(Blocks.cobblestone), null, new ItemStack(Blocks.cobblestone), new ItemStack(Blocks.cobblestone), null, new ItemStack(Blocks.cobblestone));
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
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(BlockList.jewelAltar), new ItemStack(Blocks.end_stone), new ItemStack(Blocks.wool, 1, 5), new ItemStack(Blocks.end_stone), new ItemStack(Blocks.nether_brick), new ItemStack(Blocks.wool, 1, 5), new ItemStack(Blocks.nether_brick), new ItemStack(Blocks.nether_brick), new ItemStack(Blocks.nether_brick), new ItemStack(Blocks.nether_brick));
                break;
            case 14:
                text = "Right Click to retreive the jewellery.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 15:
                text = "The Storage Displayer, as the name suggests, can store a large amount (Up to: " + Integer.MAX_VALUE + ") of a  single item/block placed in it. It will";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(BlockList.displayer), null, new ItemStack(Items.iron_ingot), null, new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Blocks.emerald_block), new ItemStack(Blocks.emerald_block), new ItemStack(Blocks.emerald_block));
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
            case 19:
                text = "This mysterious shaped block is used in the ritual. The acient ones claimed it had the power to travel through worlds. They would use these to offer";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(BlockList.handPedestal), new ItemStack(Blocks.cobblestone_wall), new ItemStack(Blocks.cobblestone_wall), new ItemStack(Blocks.cobblestone_wall), null, new ItemStack(Blocks.stonebrick), null, new ItemStack(Blocks.stone_slab, 1, 5), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stone_slab, 1, 5));
                break;
            case 20:
                text = "sacrifices to 'The Dark One' in exchange for unimaginable powers.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 21:
                text = "The Cursed Eye is an ancient artifact also known as 'The Dark One's Eye'. It is part of the sacrifice ritual the ancient ones talk about. Be careful";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(BlockList.shadowEye), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stained_hardened_clay, 1, 15), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stained_hardened_clay, 1, 15), new ItemStack(Items.ender_eye), new ItemStack(Blocks.stained_hardened_clay, 1, 15), new ItemStack(Blocks.stonebrick), new ItemStack(Blocks.stained_hardened_clay, 1, 15), new ItemStack(Blocks.stonebrick));
                break;
            case 22:
                text = "though, for He sees everything. To see how to create the ritual look in the Ritual tab.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            default:
                ;
        }
    }
    
    /**
     * @return
     */
    @Override
    public int getMaxPages()
    {
        return 21;
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param button
     */
    @Override
    public void mouseClick(GuiGuide gui, int x, int y, int button)
    {
        if (gui.page == 21 && x >= gui.getLeft() + 130 && x <= gui.getLeft() + 160 && y >= gui.getTop() + 40 && y <= gui.getTop() + 50) try{
            Desktop.getDesktop().browse(new URL("http://imgur.com/a/Zk0LW").toURI());
        }
        catch(Exception e){}
    }
    
    /**
     * @param gui
     * @param x
     * @param y
     * @param page
     */
    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {}
}
