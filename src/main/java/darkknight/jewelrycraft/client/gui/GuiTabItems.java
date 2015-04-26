package darkknight.jewelrycraft.client.gui;

import java.awt.Desktop;
import java.net.URL;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.Page;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class GuiTabItems extends GuiTab
{
    
    /**
     * @param id
     */
    public GuiTabItems(int id)
    {
        super("Items", id);
    }
    
    /**
     * @return
     */
    @Override
    public ItemStack getIcon()
    {
        return new ItemStack(ItemList.thiefGloves);
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
                text = "Shadow ingots are obtained by smelting shadow ore. They are used in a few recipes and an important key for making some jewelry work.";
                Page.addSmeltingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text, x, y, true, new ItemStack(BlockList.shadowOre), new ItemStack(ItemList.shadowIngot));
                break;
            case 2:
                text = "These gloves give you the chance to steal the trades those pesky Testificates have to offer. To use these simply open their gui at least once, then Crouch and";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(ItemList.thiefGloves), new ItemStack(ItemList.shadowIngot), null, new ItemStack(ItemList.shadowIngot), new ItemStack(Blocks.wool, 1, 15), new ItemStack(ItemList.shadowIngot), new ItemStack(Blocks.wool, 1, 15), new ItemStack(Blocks.wool, 1, 15), new ItemStack(ItemList.shadowIngot), new ItemStack(Blocks.wool, 1, 15));
                break;
            case 3:
                text = "right click on the them to hopefully steal the trades. If you traded with him before, then you have a chance of getting the traded emeralds back as well. This has a maximum of 10 uses before it breaks.";
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 4:
                text = "In order to get the ingot back from the smelter you need a mold for it. However, this mold can't be used. It is too soft. It needs to be hardened in order for it to be used.";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, x, y, true, new ItemStack(ItemList.clayMolds, 1, 0), new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball));
                break;
            case 5:
                text = "To create a ring you need a mold for it. However, this one is too soft to be used. It needs to be hardened in order for it to be used.";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(ItemList.clayMolds, 1, 1), null, new ItemStack(Items.clay_ball), null, new ItemStack(Items.clay_ball), null, new ItemStack(Items.clay_ball), null, new ItemStack(Items.clay_ball), null);
                break;
            case 6:
                text = "To create a necklace you need a mold for it. However, this one can't be used. It is too soft. It needs to be hardened in order for it to be used.";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(ItemList.clayMolds, 1, 2), new ItemStack(Items.clay_ball), null, new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball), null, new ItemStack(Items.clay_ball), null, new ItemStack(Items.clay_ball), null);
                break;
            case 7:
                text = "To create a bracelet you need a mold for it. However, this one can't be used. It is too soft. It needs to be hardened in order for it to be used.";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(ItemList.clayMolds, 1, 3), new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball), null, new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball), new ItemStack(Items.clay_ball));
                break;
            case 8:
                text = "To create a necklace you need a mold for it. However, this one can't be used. It is too soft. It needs to be hardened in order for it to be used.";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(ItemList.clayMolds, 1, 4), null, null, null, new ItemStack(Items.clay_ball), null, new ItemStack(Items.clay_ball), null, null, null);
                break;
            case 9:
                if (del == 0) values++;
                del++;
                if (del >= 300) del = 0;
                if (values > 4) values = 0;
                text = "By smelting a clay mold you get a harder version which can be used to create jewelry. Simply right click with this on a molder to attach it and you're ready to go.";
                Page.addSmeltingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text, x, y, true, new ItemStack(ItemList.clayMolds, 1, values), new ItemStack(ItemList.molds, 1, values));
                break;
            case 10:
                text = "Crystals don't do much as of yet. They can be dyed in any color and used as gems to create a nice jewelry.";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), false, text, x, y, true, new ItemStack(ItemList.crystal, 1, 15), null, new ItemStack(Blocks.glass), null, new ItemStack(Blocks.glass), null, new ItemStack(Blocks.glass), null, new ItemStack(Blocks.glass), null);
                break;
            case 11:
                if (del == 0) values++;
                del++;
                if (del >= 300) del = 0;
                if (values >= 15) values = 0;
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, x, y, true, new ItemStack(ItemList.crystal, 1, values), new ItemStack(Items.dye, 1, values), new ItemStack(ItemList.crystal, 1, 15));
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop() + 60, true, text, x, y, true, new ItemStack(ItemList.crystal, 1, 15), new ItemStack(Items.dye, 1, 15), new ItemStack(ItemList.crystal, 1, values));
                break;
            case 12:
                if (del == 0) values++;
                del++;
                if (del >= 300) del = 0;
                if (values > 4) values = 0;
                text = "It's this exact guide. I don't even know why you're reading this. I added this recipe in case you lose the original. Even if this is more helpful than NEI, I do suggest";
                Page.addCraftingRecipeTextPage(gui, gui.getLeft() + xPos, gui.getTop(), true, text, x, y, true, new ItemStack(ItemList.guide), new ItemStack(ItemList.molds, 1, values), new ItemStack(Items.book));
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
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, item, text, 40f, 0, 0, true, 45, 10, true);
                break;
            case 16:
                text = "This item is a creative only item! Right click it while in creative mode to open a GUI. Place a piece of jewelery inside the slot, select what you want to add, then click on 'Add Items'. If you selected Modifiers, you can select multiple";
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop() - 5, new ItemStack(ItemList.jewelryModifier), text, 40f, 0, 0, true, 45, 10, true);
                break;
            case 17:
                text = "items at once. The 'Item' button is to add an Item to a Golden Object, which can not be obtained normally. This tool can be really useful, especially for those who want to test the mod and can't wait for the normal processes to finish (Smelter, Jewelers Table, Ritual).";
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
        return 17;
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
        if (gui.page == 13 && x >= gui.getLeft() && x <= gui.getLeft() + 30 && y >= gui.getTop() + 104 && y <= gui.getTop() + 124) try{
            Desktop.getDesktop().browse(new URL("http://www.minecraftforum.net/topic/2210959-164smp-ssp-jewelrycraft-version-12/").toURI());
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
