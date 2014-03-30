package darkknight.jewelrycraft.container;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
        ArrayList<String> text = new ArrayList<String>();
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        int xPos = (page%2==0)?107:-35;
        switch(page)
        {    
            case 1: 
                text.add("  This ore is extremely");
                text.add("rare and can be found");
                text.add("only between Y level 5");
                text.add("and 8. It can only be");
                text.add("mined using a diamond");
                text.add("pickaxe.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), new ItemStack(BlockList.shadowOre), text, 90f);
                break;
            case 2: 
                text.add("  The Shadow Block is");
                text.add("crafted using 9 shadow");
                text.add("ingots. It has been");
                text.add("discovered that it");
                text.add("poseses abnormal");
                text.add("properties in the");
                text.add("shadow. The darker it");
                items.add(new ItemStack(BlockList.shadowBlock));
                for(int i = 1; i <= 9; i++) items.add(new ItemStack(ItemList.shadowIngot));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 3: 
                text.add("is, the more");
                text.add("transparent it will be,");
                text.add("until it becomes");
                text.add("walkable through. If a");
                text.add("comparator is attached");
                text.add("to it, the output");
                text.add("strength will be equal");
                text.add("to the value of");
                text.add("darkness it is in.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 4: 
                text.add("  The smelter is one of");
                text.add("the first blocks needed");
                text.add("to get started with");
                text.add("Jewelrycraft. Requiring");
                text.add("just some cobble and");
                text.add("a couple buckets, it's");
                text.add("the most important");
                items.add(new ItemStack(BlockList.smelter));
                items.add(new ItemStack(Block.cobblestone));
                items.add(new ItemStack(Item.bucketEmpty));
                items.add(new ItemStack(Block.cobblestone));
                items.add(new ItemStack(Block.cobblestone));
                items.add(null);
                items.add(new ItemStack(Block.cobblestone));
                items.add(new ItemStack(Block.cobblestone));
                items.add(new ItemStack(Item.bucketLava));
                items.add(new ItemStack(Block.cobblestone));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 5: 
                text.add("block as it can melt");
                text.add("ingots which can be");
                text.add("made into pieces of");
                text.add("jewellery, like rings");
                text.add("or necklaces. To use");
                text.add("the block all you need");
                text.add("to do is right click");
                text.add("on it with any ingot.");
                text.add("If left clicked while");
                text.add("smelting, a message");
                text.add("will appear saying the");
                text.add("percentage it is done.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 6: 
                text.add("If left clicked when");
                text.add("it's done smelting,");
                text.add("a message will be");
                text.add("displayed, mentioning");
                text.add("the contents of the");
                text.add("block.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 7: 
                text.add("  The molder is a key");
                text.add("piece in creating");
                text.add("jewellery. You need");
                text.add("to pour the molten");
                text.add("metal out of the");
                text.add("smelter somewhere.");
                text.add("That somewhere is the");
                
                items.add(new ItemStack(BlockList.molder));
                items.add(new ItemStack(Block.cobblestone));
                items.add(null);
                items.add(new ItemStack(Block.cobblestone));
                for(int i = 1; i <= 3; i++) items.add(new ItemStack(Block.cobblestone));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 8: 
                text.add("molder. But before");
                text.add("pouring the molten");
                text.add("metal in it, you must");
                text.add("first add a mold.");
                text.add("You can do that by");
                text.add("simply right clicking");
                text.add("the block with the");
                text.add("mold of your choice.");
                text.add("If you want to get the");
                text.add("mold out, simply crouch");
                text.add("and right click it with");
                text.add("an empty hand.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 9: 
                text.add("  Once you have a mold");
                text.add("inside, left click on");
                text.add("the smelter and wait");
                text.add("for the metal to cool");
                text.add("down. When it's done,");
                text.add("left click on the");
                text.add("molder to get the");
                text.add("jewellery. §4Be aware");
                text.add("§4that this block must be");
                text.add("§4placed directly in front");
                text.add("§4of the smelter,");
                text.add("§4otherwise it won't work!");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 10: 
                text.add("  Your jewellery on");
                text.add("their own don't do");
                text.add("much. They need to be");
                text.add("modified a bit and the");
                text.add("only way to do that is");
                text.add("by using this block.");
                text.add("Simply right click the");
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values >= 4) values = 0;
                items.add(new ItemStack(BlockList.jewelCraftingTable));
                for(int i = 1; i <= 3; i++)items.add(new ItemStack(Block.planks, 1, values));
                items.add(new ItemStack(Block.cobblestone));
                items.add(null);
                items.add(new ItemStack(Block.cobblestone));
                items.add(new ItemStack(Block.cobblestone));
                items.add(null);
                items.add(new ItemStack(Block.cobblestone));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 11: 
                text.add("block while holding the");
                text.add("jewellery to place it in.");
                text.add("After that just add in");
                text.add("a jewel or a modifier,");
                text.add("or even both, to the");
                text.add("block. To do that simply");
                text.add("right click with them on");
                text.add("the block. Once it's");
                text.add("done modifying, left");
                text.add("click on it to retrieve");
                text.add("the modified item. If");
                text.add("you wish to know how");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 12: 
                text.add("much is left before the");
                text.add("transformation is done,");
                text.add("simply left click on the");
                text.add("table in the process.");
                text.add("  A list with all the");
                text.add("possible modifiers is");
                text.add("located in a separate");
                text.add("tab.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 13: 
                text.add("  This block can store");
                text.add("any jewellery in it");
                text.add("and activate their");
                text.add("effects as it were a");
                text.add("player. However, it");
                text.add("does not work with");
                text.add("everything. You can");
                items.add(new ItemStack(BlockList.jewelAltar));
                items.add(new ItemStack(Block.whiteStone));
                items.add(new ItemStack(Block.cloth, 1, 5));
                items.add(new ItemStack(Block.whiteStone));
                items.add(new ItemStack(Block.netherBrick));
                items.add(new ItemStack(Block.cloth, 1, 5));
                items.add(new ItemStack(Block.netherBrick));
                items.add(new ItemStack(Block.netherBrick));
                items.add(new ItemStack(Block.netherBrick));
                items.add(new ItemStack(Block.netherBrick));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 14: 
                text.add("find out which jewellery");
                text.add("works by looking in");
                text.add("their apropriate tab.");
                text.add("  Each item will have a");
                text.add("note where it is");
                text.add("mentioned their effect");
                text.add("when placed in this");
                text.add("block.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 15: 
                text.add("  The Storage");
                text.add("Displayer, as the");
                text.add("name suggests, can");
                text.add("store a large amount");
                text.add("of a single item/block");
                text.add("placed in it. This will");
                text.add("display all possible");
                items.add(new ItemStack(BlockList.displayer));
                items.add(null);
                items.add(new ItemStack(Item.ingotIron));
                items.add(null);
                items.add(new ItemStack(Item.ingotIron));
                items.add(new ItemStack(Item.ingotIron));
                items.add(new ItemStack(Item.ingotIron));
                items.add(new ItemStack(Block.blockEmerald));
                items.add(new ItemStack(Block.blockEmerald));
                items.add(new ItemStack(Block.blockEmerald));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 16: 
                text.add("infromation about the");
                text.add("object in it, such as");
                text.add("the name, durability,");
                text.add("enchantments and many");
                text.add("more. Below the name");
                text.add("is shown the amount");
                text.add("stored. To store");
                text.add("something in it simply");
                text.add("right click with that");
                text.add("object on it and the");
                text.add("whole amount of items");
                text.add("or blocks will be");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 17: 
                text.add("immediately stored");
                text.add("inside. To retrieve");
                text.add("a single item just");
                text.add("left click the block.");
                text.add("If you wish to get");
                text.add("a whole stack, just");
                text.add("crouch and left click.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            default:;
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
