package darkknight.jewelrycraft.container;

import java.awt.Desktop;
import java.net.URL;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;

public class GuiTabItems extends GuiTab
{

    public GuiTabItems(int id)
    {
        super("Items", id);
    }

    public ItemStack getIcon()
    {        
        return new ItemStack(ItemList.thiefGloves);
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
                text.add("  Shadow ingots are");
                text.add("obtained by smelting");
                text.add("shadow ore. They are");
                text.add("used in a few recipes");
                text.add("and an important key");
                text.add("for making some");
                text.add("jewellery work.");
                items.add(new ItemStack(BlockList.shadowOre));
                items.add(new ItemStack(ItemList.shadowIngot));
                Page.addSmeltingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text, items, x, y);
                break;
            case 2: 
                text.add("  These gloves allow");
                text.add("you to steal the trades");
                text.add("the pesky Testificates");
                text.add("have to offer.");
                text.add("  To use these simply");
                text.add("open their gui at least");
                text.add("once, then crouch and");
                items.add(new ItemStack(ItemList.thiefGloves));
                items.add(new ItemStack(ItemList.shadowIngot));
                items.add(null);
                items.add(new ItemStack(ItemList.shadowIngot));
                items.add(new ItemStack(Block.cloth, 1, 15));
                items.add(new ItemStack(ItemList.shadowIngot));
                items.add(new ItemStack(Block.cloth, 1, 15));
                items.add(new ItemStack(Block.cloth, 1, 15));
                items.add(new ItemStack(ItemList.shadowIngot));
                items.add(new ItemStack(Block.cloth, 1, 15));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 3: 
                text.add("right click on the them");
                text.add("to steal the trades.");
                text.add("A villager has 7 of the");
                text.add("same trade item. So, for");
                text.add("example, if he wants 2");
                text.add("emeralds in exchange");
                text.add("for 4 diamonds, you will");
                text.add("get 28 diamonds from");
                text.add("him, because 7*4=28.");
                text.add("However, if you have");
                text.add("traded with him before,");
                text.add("then he will have that");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 4: 
                text.add("many times less of the");
                text.add("item. This has a maximum");
                text.add("of 10 uses before it");
                text.add("breaks.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 5: 
                text.add("  In order to get the");
                text.add("ingot back from the");
                text.add("smelter you need a");
                text.add("mold for it. However,");
                text.add("this mold can't be used.");
                text.add("It is too soft. It needs");
                text.add("to be hardened in");
                text.add("order for it to be used.");
                items.add(new ItemStack(ItemList.clayMolds, 1, 0));
                items.add(new ItemStack(Item.clay));
                items.add(new ItemStack(Item.clay));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, items, x, y);
                break;
            case 6: 
                text.add("  By smelting the clay");
                text.add("mold you get a harder");
                text.add("version which can be");
                text.add("used to create ingots.");
                text.add("Simply right click with");
                text.add("this on a molder to");
                text.add("attach it and you're");
                text.add("ready to go.");
                items.add(new ItemStack(ItemList.clayMolds, 1, 0));
                items.add(new ItemStack(ItemList.molds, 1, 0));
                Page.addSmeltingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text, items, x, y);
                break;
            case 7: 
                text.add("  In order to create a");
                text.add("ring you need a mold");
                text.add("for it. However, this");
                text.add("one can't be used. It is");
                text.add("too soft. It needs to be");
                text.add("hardened in order for");
                text.add("it to be used.");
                items.add(new ItemStack(ItemList.clayMolds, 1, 1));
                items.add(null);
                items.add(new ItemStack(Item.clay));
                items.add(null);
                items.add(new ItemStack(Item.clay));
                items.add(null);
                items.add(new ItemStack(Item.clay));
                items.add(null);
                items.add(new ItemStack(Item.clay));
                items.add(null);
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 8: 
                text.add("  By smelting the clay");
                text.add("mold you get a harder");
                text.add("version which can be");
                text.add("used to create rings.");
                text.add("Simply right click with");
                text.add("this on a molder to");
                text.add("attach it and you're");
                text.add("ready to go.");
                items.add(new ItemStack(ItemList.clayMolds, 1, 1));
                items.add(new ItemStack(ItemList.molds, 1, 1));
                Page.addSmeltingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text, items, x, y);
                break;
            case 9: 
                text.add("  In order to create a");
                text.add("necklace you need a");
                text.add("mold for it. However,");
                text.add("this one can't be used.");
                text.add("It is too soft. It needs");
                text.add("to be hardened in");
                text.add("order for it to be used.");
                items.add(new ItemStack(ItemList.clayMolds, 1, 2));
                items.add(new ItemStack(Item.clay));
                items.add(null);
                items.add(new ItemStack(Item.clay));
                items.add(new ItemStack(Item.clay));
                items.add(null);
                items.add(new ItemStack(Item.clay));                
                items.add(null);
                items.add(new ItemStack(Item.clay));
                items.add(null);
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 10: 
                text.add("  By smelting the clay");
                text.add("mold you get a harder");
                text.add("version which can be");
                text.add("used to create");
                text.add("necklaces. Simply right");
                text.add("click with this on a");
                text.add("molder to attach it and");
                text.add("you're ready to go.");
                items.add(new ItemStack(ItemList.clayMolds, 1, 2));
                items.add(new ItemStack(ItemList.molds, 1, 2));
                Page.addSmeltingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text, items, x, y);
                break;
            case 11: 
                text.add("  Crystals don't do");
                text.add("much. They can be dyed");
                text.add("in any color and used");
                text.add("as jewels to create a");
                text.add("nice jewellery.");
                items.add(new ItemStack(ItemList.crystal, 1, 15));
                items.add(null);
                items.add(new ItemStack(Block.glass));
                items.add(null);
                items.add(new ItemStack(Block.glass));
                items.add(null);
                items.add(new ItemStack(Block.glass));
                items.add(null);
                items.add(new ItemStack(Block.glass));
                items.add(null);
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 12: 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values >= 15) values = 0;
                items.add(new ItemStack(ItemList.crystal, 1, values));
                items.add(new ItemStack(Item.dyePowder, 1, values));
                items.add(new ItemStack(ItemList.crystal, 1, 15));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, items, x, y);
                items.removeAll(items);
                items.add(new ItemStack(ItemList.crystal, 1, 15));
                items.add(new ItemStack(Item.dyePowder, 1, 15));
                items.add(new ItemStack(ItemList.crystal, 1, values));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop() + 60, true, text, items, x, y);
                break;
            case 13: 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values >= 3) values = 0;
                text.add("  It's this exact guide.");
                text.add("I don't even know why");
                text.add("you're reading this.");
                text.add("I added this recipe in");
                text.add("case you lose the");
                text.add("original. In case you");
                text.add("don't have it, I suggest");
                text.add("adding NEI so you can");
                items.add(new ItemStack(ItemList.guide));
                items.add(new ItemStack(ItemList.molds, 1, values));
                items.add(new ItemStack(Item.book));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, items, x, y);
                break;
            case 14: 
                String link = "HERE";
                if(x >= gui.getLeft() + 138 && x <= gui.getLeft() + 168 && y >= gui.getTop() + 98 && y <= gui.getTop() + 108) link = "§1HERE§0";
                text.add("see all the recipes.");
                text.add("Since you are reading");
                text.add("this, how about making");
                text.add("a youtube video");
                text.add("spotlighting this mod.");
                text.add("I'd really appreciate it.");
                text.add("After that you can");
                text.add("share it in the main");
                text.add("thread " + link + ".");
                text.add("  This mod was made by");
                text.add("DarkKnight (or sor1n");
                text.add("depending from where");
                text.add("you got this)");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            default:;
        }
    }

    public int getMaxPages()
    {
        return 13;
    }

    public void mouseClick(GuiGuide gui, int x, int y, int button) 
    {
        if(gui.page == 13 && x >= gui.getLeft() + 138 && x <= gui.getLeft() + 168 && y >= gui.getTop() + 98 && y <= gui.getTop() + 108)
        {
            try 
            {
                Desktop.getDesktop().browse(new URL("http://www.minecraftforum.net/topic/2210959-164smp-ssp-jewelrycraft-version-12/").toURI());
            }           
            catch (Exception e) {}
        }
    }

    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {        
    }

}
