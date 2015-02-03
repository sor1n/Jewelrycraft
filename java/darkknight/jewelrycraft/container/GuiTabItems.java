package darkknight.jewelrycraft.container;

import java.awt.Desktop;
import java.net.URL;
import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

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
        String text = "";
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        int xPos = (page % 2 == 0) ? 107 : -35;
        switch (page)
        {
            case 1:
                text = "Shadow ingots are obtained by smelting shadow ore. They are used in a few recipes and an important key for making some jewellery work.";
                items.add(new ItemStack(BlockList.shadowOre));
                items.add(new ItemStack(ItemList.shadowIngot));
                Page.addSmeltingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text, items, x, y);
                break;
            case 2:
                text = "These gloves give you the chance to steal the trades those pesky Testificates have to offer. To use these simply open their gui at least once, then Crouch and";
                items.add(new ItemStack(ItemList.thiefGloves));
                items.add(new ItemStack(ItemList.shadowIngot));
                items.add(null);
                items.add(new ItemStack(ItemList.shadowIngot));
                items.add(new ItemStack(Blocks.wool, 1, 15));
                items.add(new ItemStack(ItemList.shadowIngot));
                items.add(new ItemStack(Blocks.wool, 1, 15));
                items.add(new ItemStack(Blocks.wool, 1, 15));
                items.add(new ItemStack(ItemList.shadowIngot));
                items.add(new ItemStack(Blocks.wool, 1, 15));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 3:
                text = "right click on the them to hopefully steal the trades. If you traded with him before, then you have a chance of getting the traded emeralds back as well. This has a maximum of 10 uses before it breaks.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 4:
                text = "In order to get the ingot back from the smelter you need a mold for it. However, this mold can't be used. It is too soft. It needs to be hardened in order for it to be used.";
                items.add(new ItemStack(ItemList.clayMolds, 1, 0));
                items.add(new ItemStack(Items.clay_ball));
                items.add(new ItemStack(Items.clay_ball));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, items, x, y);
                break;
            case 5:
                text = "To create a ring you need a mold for it. However, this one is too soft to be used. It needs to be hardened in order for it to be used.";
                items.add(new ItemStack(ItemList.clayMolds, 1, 1));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 6:
                text = "To create a necklace you need a mold for it. However, this one can't be used. It is too soft. It needs to be hardened in order for it to be used.";
                items.add(new ItemStack(ItemList.clayMolds, 1, 2));
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 7:
                text = "To create a bracelet you need a mold for it. However, this one can't be used. It is too soft. It needs to be hardened in order for it to be used.";
                items.add(new ItemStack(ItemList.clayMolds, 1, 3));
                items.add(new ItemStack(Items.clay_ball));
                items.add(new ItemStack(Items.clay_ball));
                items.add(new ItemStack(Items.clay_ball));
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(new ItemStack(Items.clay_ball));
                items.add(new ItemStack(Items.clay_ball));
                items.add(new ItemStack(Items.clay_ball));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 8:
                text = "To create a necklace you need a mold for it. However, this one can't be used. It is too soft. It needs to be hardened in order for it to be used.";
                items.add(new ItemStack(ItemList.clayMolds, 1, 4));
                items.add(null);
                items.add(null);
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(new ItemStack(Items.clay_ball));
                items.add(null);
                items.add(null);
                items.add(null);
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 9:
                if (del == 0) values++;
                del++;
                if (del >= 300) del = 0;
                if (values > 4) values = 0;
                text = "By smelting a clay mold you get a harder version which can be used to create jewellery. Simply right click with this on a molder to attach it and you're ready to go.";
                items.add(new ItemStack(ItemList.clayMolds, 1, values));
                items.add(new ItemStack(ItemList.molds, 1, values));
                Page.addSmeltingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text, items, x, y);
                break;
            case 10:
                text = "Crystals don't do much as of yet. They can be dyed in any color and used as gems to create a nice jewellery.";
                items.add(new ItemStack(ItemList.crystal, 1, 15));
                items.add(null);
                items.add(new ItemStack(Blocks.glass));
                items.add(null);
                items.add(new ItemStack(Blocks.glass));
                items.add(null);
                items.add(new ItemStack(Blocks.glass));
                items.add(null);
                items.add(new ItemStack(Blocks.glass));
                items.add(null);
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, items, x, y);
                break;
            case 11:
                if (del == 0) values++;
                del++;
                if (del >= 300) del = 0;
                if (values >= 15) values = 0;
                items.add(new ItemStack(ItemList.crystal, 1, values));
                items.add(new ItemStack(Items.dye, 1, values));
                items.add(new ItemStack(ItemList.crystal, 1, 15));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, items, x, y);
                items.removeAll(items);
                items.add(new ItemStack(ItemList.crystal, 1, 15));
                items.add(new ItemStack(Items.dye, 1, 15));
                items.add(new ItemStack(ItemList.crystal, 1, values));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop() + 60, true, text, items, x, y);
                break;
            case 12:
                if (del == 0) values++;
                del++;
                if (del >= 300) del = 0;
                if (values > 4) values = 0;
                text = "It's this exact guide. I don't even know why you're reading this. I added this recipe in case you lose the original. Even if this is more helpful than NEI, I do suggest";
                items.add(new ItemStack(ItemList.guide));
                items.add(new ItemStack(ItemList.molds, 1, values));
                items.add(new ItemStack(Items.book));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, items, x, y);
                break;
            case 13:
                String link = "HERE";
                if (x >= gui.getLeft() && x <= gui.getLeft() + 30 && y >= gui.getTop() + 104 && y <= gui.getTop() + 124) link = EnumChatFormatting.DARK_BLUE + "HERE" + EnumChatFormatting.BLACK;
                text = "installing it so you can see all the recipes. Since you are reading this, how about making a youtube video spotlighting this mod. I'd really appreciate it. After that you can share it in the main thread " + link + "." + " This mod was made by DarkKnight (or sor1n, depends";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 14:
                text = "where you got this mod from) and the help of domi1819 and bspkrs.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 15:
                ItemStack item = new ItemStack(ItemList.bucket); 
                if (del == 0) values++;
                del++;
                if (del >= 300) del = 0;
                if (values > JewelrycraftUtil.metal.size() - 1) values = 0;               
                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values).copy());
                text = "These buckets contain molten metal. To obtain one simply Right Click a full Smelter to get a bucket. You can pour the metal, other than that it has no use. You can place the molten metal back in a Smelter by Right Clicking one with it.";
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, item, text, 40f, 0, 0, true, 45, 10);
                break;
            default:
                ;
        }
    }
    
    public int getMaxPages()
    {
        return 15;
    }
    
    public void mouseClick(GuiGuide gui, int x, int y, int button)
    {
        if (gui.page == 13 && x >= gui.getLeft() && x <= gui.getLeft() + 30 && y >= gui.getTop() + 104 && y <= gui.getTop() + 124)
        {
            try
            {
                Desktop.getDesktop().browse(new URL("http://www.minecraftforum.net/topic/2210959-164smp-ssp-jewelrycraft-version-12/").toURI());
            }
            catch (Exception e)
            {
            }
        }
    }
    
    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {
    }
    
}
