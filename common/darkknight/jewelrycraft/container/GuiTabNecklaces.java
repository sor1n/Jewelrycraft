package darkknight.jewelrycraft.container;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class GuiTabNecklaces extends GuiTab
{
    int jValues;
    public GuiTabNecklaces(int id)
    {
        super("Necklaces", id);
        jValues = 0;
    }
    
    public ItemStack getIcon()
    {        
        ItemStack it = new ItemStack(ItemList.necklace);
        JewelryNBT.addMetal(it, new ItemStack(Item.ingotIron));
        JewelryNBT.addJewel(it, new ItemStack(Block.blockRedstone)); 
        return it;
    }

    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {        
        ArrayList<String> text = new ArrayList<String>();
        ArrayList<ItemStack> jewels = new ArrayList<ItemStack>();
        ItemStack item = new ItemStack(ItemList.necklace);
        int xPos = (page%2==0)?107:-35;
        switch(page)
        {    
            case 1: 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, new ItemStack(Item.enderPearl));

                text.add("§2Jewel: §0Ender Pearl");
                text.add("§2Modifier: §0None");
                text.add("§2Ingot: §0Any");
                text.add("  This ring teleports");
                text.add("you and anyone around ");
                text.add("in any location from the");
                text.add("same dimension. Just");
                text.add("right click once in a");
                text.add("location to se the ");
                text.add("position. Then right ");
                text.add("click any time you want");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 2: 
                text.add("to teleport there.");
                text.add("§4§nAltar Effect");
                text.add("  This teleports anyone");
                text.add("or anything that steps");
                text.add("or goes near the altar");
                text.add("to the necklaces");
                text.add("coordonates, as long");
                text.add("as that is in the same");
                text.add("dimension.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 3: 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, new ItemStack(Item.enderPearl));
                JewelryNBT.addModifier(item, new ItemStack(Item.bed));

                text.add("§2Jewel: §0Ender Pearl");
                text.add("§2Modifier: §0Bed");
                text.add("§2Ingot: §0Any");
                text.add("  Just like the other");
                text.add("necklace this teleports");
                text.add("you and anybody close");
                text.add("to you to the set");
                text.add("destination. The only");
                text.add("difference is that you");
                text.add("can travel between");
                text.add("dimensions with this.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 4: 
                text.add("§4§nAltar Effect");
                text.add("  This teleports anyone");
                text.add("or anything that step");
                text.add("on or around the altar");
                text.add("to the set location, with");
                text.add("the benefit of it being");
                text.add("capable of");
                text.add("inter-dimensional");
                text.add("travels.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 5:                 
                jewels.add(null);
                jewels.add(new ItemStack(Item.diamond));
                jewels.add(new ItemStack(Item.emerald));
                jewels.add(new ItemStack(Item.netherStar));
                
                if(del == 0) { values++; jValues++;}
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;
                if(jValues > jewels.size() - 1) jValues = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, jewels.get(jValues));
                JewelryNBT.addModifier(item, new ItemStack(Item.blazePowder));

                text.add("§2Jewel: §0None, Diamond");
                text.add("Emerald or Nether Star");
                text.add("§2Modifier: §0Blaze Powder");
                text.add("§2Ingot: §0Any");
                text.add("  This necklace gives");
                text.add("you and those around");
                text.add("you §4Fire Resistance§0");
                text.add("when activated and in");
                text.add("your inventory. To");
                text.add("deactivate it simply");
                text.add("right click with it.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 6: 
                text.add("Depending on the jewel");
                text.add("used, you and the");
                text.add("others get §4Fire");
                text.add("§4Resistance§0 I if you");
                text.add("haven't got any jewel,");
                text.add("II for Diamond,");
                text.add("III for Emerald and");
                text.add("VIII for Nether Star.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 7:                 
                jewels.add(null);
                jewels.add(new ItemStack(Item.diamond));
                jewels.add(new ItemStack(Item.emerald));
                jewels.add(new ItemStack(Item.netherStar));
                
                if(del == 0) { values++; jValues++;}
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;
                if(jValues > jewels.size() - 1) jValues = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, jewels.get(jValues));
                JewelryNBT.addModifier(item, new ItemStack(Item.sugar));

                text.add("§2Jewel: §0None, Diamond");
                text.add("Emerald or Nether Star");
                text.add("§2Modifier: §0Sugar");
                text.add("§2Ingot: §0Any");
                text.add("  This necklace gives");
                text.add("you and those around");
                text.add("you §4Speed§0 when");
                text.add("activated and in your");
                text.add("inventory. To");
                text.add("deactivate it simply");
                text.add("right click with it.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 8: 
                text.add("Depending on the jewel");
                text.add("used, you and the");
                text.add("others get §4Speed§0 I");
                text.add("if you haven't got any");
                text.add("jewel, II for Diamond,");
                text.add("III for Emerald and");
                text.add("VIII for Nether Star.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 9:                 
                jewels.add(null);
                jewels.add(new ItemStack(Item.diamond));
                jewels.add(new ItemStack(Item.emerald));
                jewels.add(new ItemStack(Item.netherStar));
                
                if(del == 0) { values++; jValues++;}
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;
                if(jValues > jewels.size() - 1) jValues = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, jewels.get(jValues));
                JewelryNBT.addModifier(item, new ItemStack(Item.pickaxeIron));

                text.add("§2Jewel: §0None, Diamond");
                text.add("Emerald or Nether Star");
                text.add("§2Modifier: §0Iron Pickaxe");
                text.add("§2Ingot: §0Any");
                text.add("  This necklace gives");
                text.add("you and those around");
                text.add("you §4Haste§0 when");
                text.add("activated and in your");
                text.add("inventory. To");
                text.add("deactivate it simply");
                text.add("right click with it.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 10: 
                text.add("Depending on the jewel");
                text.add("used, you and the");
                text.add("others get §4Haste§0 I");
                text.add("if you haven't got any");
                text.add("jewel, II for Diamond,");
                text.add("III for Emerald and");
                text.add("VIII for Nether Star.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 11:                 
                jewels.add(null);
                jewels.add(new ItemStack(Item.diamond));
                jewels.add(new ItemStack(Item.emerald));
                jewels.add(new ItemStack(Item.netherStar));
                
                if(del == 0) { values++; jValues++;}
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;
                if(jValues > jewels.size() - 1) jValues = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, jewels.get(jValues));
                JewelryNBT.addModifier(item, new ItemStack(Item.feather));

                text.add("§2Jewel: §0None, Diamond");
                text.add("Emerald or Nether Star");
                text.add("§2Modifier: §0Feather");
                text.add("§2Ingot: §0Any");
                text.add("  This necklace gives");
                text.add("you and those around");
                text.add("you §4Jump Boost§0 when");
                text.add("activated and in your");
                text.add("inventory. To");
                text.add("deactivate it simply");
                text.add("right click with it.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 12: 
                text.add("Depending on the jewel");
                text.add("used, you and the");
                text.add("others get §4Jump Boost");
                text.add("I if you haven't got any");
                text.add("jewel, II for Diamond,");
                text.add("III for Emerald and");
                text.add("VIII for Nether Star.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 13:                 
                jewels.add(null);
                jewels.add(new ItemStack(Item.diamond));
                jewels.add(new ItemStack(Item.emerald));
                jewels.add(new ItemStack(Item.netherStar));
                
                if(del == 0) { values++; jValues++;}
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;
                if(jValues > jewels.size() - 1) jValues = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, jewels.get(jValues));
                JewelryNBT.addModifier(item, new ItemStack(Item.potion, 1, 8270));

                text.add("§2Jewel: §0None, Diamond");
                text.add("Emerald or Nether Star");
                text.add("§2Modifier: §08min Potion of");
                text.add("Invisibility");
                text.add("§2Ingot: §0Any");
                text.add("  This necklace gives");
                text.add("you and those around");
                text.add("you §4Invisibility§0 when");
                text.add("activated and in your");
                text.add("inventory. To");
                text.add("deactivate it simply");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 14: 
                text.add("right click with it.");
                text.add("Depending on the jewel");
                text.add("used, you and the");
                text.add("others get §4Invisibility§0 I");
                text.add("if you haven't got any");
                text.add("jewel, II for Diamond,");
                text.add("III for Emerald and");
                text.add("VIII for Nether Star.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 15:                 
                jewels.add(null);
                jewels.add(new ItemStack(Item.diamond));
                jewels.add(new ItemStack(Item.emerald));
                jewels.add(new ItemStack(Item.netherStar));
                
                if(del == 0){values++; jValues++;}
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;
                if(jValues > jewels.size() - 1) jValues = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, jewels.get(jValues));
                JewelryNBT.addModifier(item, new ItemStack(Item.dyePowder, 1, 15));

                text.add("§2Jewel: §0None, Diamond");
                text.add("Emerald or Nether Star");
                text.add("§2Modifier: §0Bone Meal");
                text.add("§2Ingot: §0Any");
                text.add("  This hydrates the");
                text.add("farm blocks under you");
                text.add("in a defined area. If");
                text.add("you right click with this,");
                text.add("it will help plants in");
                text.add("that area grow faster.");
                text.add("The are it affects is");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 16: 
                text.add("determined by the jewel");
                text.add("used. For none, the");
                text.add("area is a block, diamond");
                text.add("is 3x3, emerald 5x5 and");
                text.add("nether star 11x11.");
                text.add("§4§nAltar Effect");
                text.add("  In the altar, this");
                text.add("has the ability to");
                text.add("hydrate any tilted land");
                text.add("and speed up the");
                text.add("growth of plants in a");
                text.add("7x3x7 area.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 17: 
                JewelryNBT.addMetal(item, new ItemStack(ItemList.shadowIngot));
                JewelryNBT.addJewel(item, new ItemStack(Item.netherStar));
                JewelryNBT.addModifier(item, new ItemStack(Item.pickaxeDiamond));

                text.add("§2Jewel: §0Nether Star");
                text.add("§2Modifier: §0Diamond Pick");
                text.add("§2Ingot: §0Shadow Ingot");
                text.add("  This will break all");
                text.add("blocks in a 3x3x1 area.");
                text.add("Just right click on a");
                text.add("block and let the mining");
                text.add("begin.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
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
