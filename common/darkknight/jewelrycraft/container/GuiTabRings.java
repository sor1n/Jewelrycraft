package darkknight.jewelrycraft.container;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class GuiTabRings extends GuiTab
{
    int jValues;
    public GuiTabRings(int id)
    {
        super("Rings", id);
        jValues = 0;
    }
    
    public ItemStack getIcon()
    {        
        ItemStack it = new ItemStack(ItemList.ring);
        JewelryNBT.addMetal(it, new ItemStack(Item.ingotGold));
        JewelryNBT.addJewel(it, new ItemStack(Item.enderPearl)); 
        return it;
    }

    @Override
    public void drawBackground(GuiGuide gui, int x, int y, int page)
    {        
        ArrayList<String> text = new ArrayList<String>();
        ArrayList<ItemStack> jewels = new ArrayList<ItemStack>();
        ItemStack item = new ItemStack(ItemList.ring);
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
                text.add("  This ring allows you");
                text.add("to teleport in any");
                text.add("location from the same");
                text.add("dimension. Simply right");
                text.add("click once in a location");
                text.add("to se the position. Then");
                text.add("right click any time you");
                text.add("want to teleport there.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 2: 
                text.add("§4§nAltar Effect");
                text.add("  If this ring is placed");
                text.add("in the altar and if the");
                text.add("ring has coordonates");
                text.add("memorized, then anyone");
                text.add("who steps on the block");
                text.add("will get teleported in");
                text.add("that location, as long");
                text.add("as it is in the same");
                text.add("dimension. This works");
                text.add("for other entities as");
                text.add("well, not just players.");
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
                text.add("ring that had only an");
                text.add("ender pearl as a jewel,");
                text.add("by adding a bed as a");
                text.add("modifier to it you can");
                text.add("travel between");
                text.add("dimensions.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 4: 
                text.add("§4§nAltar Effect");
                text.add("  Just like the other");
                text.add("one, when in the altar");
                text.add("if somebody steps on");
                text.add("the block they get");
                text.add("teleported in that spot.");
                text.add("The only difference is");
                text.add("that you can teleport");
                text.add("between dimesnions with");
                text.add("this. It works for mobs");
                text.add("and other entities as");
                text.add("well.");
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
                text.add("  This ring grants you");
                text.add("§4Fire Resistance§0 when");
                text.add("activated and in your");
                text.add("inventory. To deactivate");
                text.add("it simply right click with");
                text.add("it. Depending on the");
                text.add("jewel you used, you");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 6: 
                text.add("get §4Fire Resistance§0 I");
                text.add("if you haven't got any");
                text.add("jewel, II for Diamond,");
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
                text.add("  This ring grants you");
                text.add("§4Speed§0 when activated");
                text.add("and in your inventory.");
                text.add("To deactivate it simply");
                text.add("right click with it.");
                text.add("Depending on the");
                text.add("jewel you used, you");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 8: 
                text.add("get §4Speed§0 I if you");
                text.add("haven't got any jewel,");
                text.add("II for Diamond,");
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
                text.add("  This ring grants you");
                text.add("§4Haste§0 when activated");
                text.add("and in your inventory.");
                text.add("To deactivate it simply");
                text.add("right click with it.");
                text.add("Depending on the");
                text.add("jewel you used, you");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 10: 
                text.add("get §4Haste§0 I if you");
                text.add("haven't got any jewel,");
                text.add("II for Diamond,");
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
                text.add("  This ring grants you");
                text.add("§4Jump Boost§0 when");
                text.add("activated and in your");
                text.add("inventory, as well as");
                text.add("remove any fall damage.");
                text.add("To deactivate it simply");
                text.add("right click with it.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 12: 
                text.add("Depending on the jewel");
                text.add("you used, you get");
                text.add("§4Jump Boost§0 I if you");
                text.add("haven't got any jewel,");
                text.add("II for Diamond,");
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
                text.add("  This ring grants you");
                text.add("§4Invisibility§0 when");
                text.add("activated and in your");
                text.add("inventory, as well as");
                text.add("remove any fall damage.");
                text.add("To deactivate it simply");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 14: 
                text.add("right click with it.");
                text.add("Depending on the jewel");
                text.add("you used, you get");
                text.add("§4Invisibility§0 I if you");
                text.add("haven't got any jewel,");
                text.add("II for Diamond,");
                text.add("III for Emerald and");
                text.add("VIII for Nether Star.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 15:                 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, new ItemStack(Item.netherStar));
                JewelryNBT.addModifier(item, new ItemStack(Item.book));

                text.add("§2Jewel: §0Nether Star");
                text.add("§2Modifier: §0Book");
                text.add("§2Ingot: §0Any");
                text.add("  This has the power");
                text.add("to §5Enchant§0, §5Disenchant§0");
                text.add("or even §5Transfer");
                text.add("§5Enchantments§0. To");
                text.add("change its mode simply");
                text.add("right click it. However,");
                text.add("this ring only works");
                text.add("when it is being held.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 16: 
                text.add("§5§nDisenchanting§0");
                text.add(" If the ring is held");
                text.add("and an enchanted item");
                text.add("is placed beside it in");
                text.add("the hotbar, the ring");
                text.add("will remove all the");
                text.add("enchantments from the");
                text.add("item and store them in");
                text.add("enchanted books, which");
                text.add("are placed in your");
                text.add("inventory. Be careful");
                text.add("however, because if");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 17: 
                text.add("there is no room in");
                text.add("your inventory for the");
                text.add("books, the enchantments");
                text.add("will go in the void with");
                text.add("no way of recovering");
                text.add("them. Unfortunately,");
                text.add("disenchanting is not");
                text.add("free. It requires 2");
                text.add("levels of experience");
                text.add("per ench and some of");
                text.add("your blood. It will also");
                text.add("damage the item a bit.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 18: 
                text.add("§5§nEnchanting§0");
                text.add(" This mode allows you");
                text.add("to give a random");
                text.add("enchantment to an item");
                text.add("that can hold");
                text.add("enchantments in the");
                text.add("first place. However,");
                text.add("each enchanting comes");
                text.add("with a cost. The player");
                text.add("is required to have at");
                text.add("least 1 level of xp.");
                text.add("The higher the number");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 19: 
                text.add("the better, as the level");
                text.add("of the enchantment will");
                text.add("be equal with the amount");
                text.add("of levels, but this value");
                text.add("can never go over 6.");
                text.add("This gives allows people");
                text.add("to get an enchantment");
                text.add("of a higher value than");
                text.add("normal (5 being the");
                text.add("limit normally).");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 20: 
                text.add("§5§nEnchantment Transfer§0");
                text.add(" This mode is very");
                text.add("special as it lets you");
                text.add("transfer enchantments");
                text.add("from an item or block to");
                text.add("another, not caring if");
                text.add("the item/block can");
                text.add("actually hold enchants.");
                text.add("Just place the ench");
                text.add("item on the left of the");
                text.add("ring and to the right of");
                text.add("the ring, the item you");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 21: 
                text.add("want to transfer the");
                text.add("enchs to. The ring can");
                text.add("only transfer an ench");
                text.add("at a time, after each");
                text.add("transfer it requiring a");
                text.add("cooldown.");
                text.add(" §4Again, this ring only");
                text.add("§4works when it is in the");
                text.add("§4players hotbar and");
                text.add("§4being held!");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 22:                 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, new ItemStack(Item.enderPearl));
                JewelryNBT.addModifier(item, new ItemStack(Block.chest));

                text.add("§2Jewel: §0Ender Pearl");
                text.add("§2Modifier: §0Chest");
                text.add("§2Ingot: §0Any");
                text.add("  This ring can link");
                text.add("to any chest and");
                text.add("access its inventory.");
                text.add("To link to a chest just");
                text.add("crouch and right click");
                text.add("on the one you want.");
                text.add("Then right click again");
                text.add("to open that inventory.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 23: 
                text.add("This only works if you");
                text.add("are in range to the");
                text.add("chest. You can go");
                text.add("about 128 blocks");
                text.add("before it stops");
                text.add("working. After that");
                text.add("a message will be");
                text.add("displayed saying the");
                text.add("amount of blocks you");
                text.add("need to be closer in");
                text.add("order for it to work");
                text.add("again.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 24:                 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, new ItemStack(Block.obsidian));
                JewelryNBT.addModifier(item, new ItemStack(Item.eyeOfEnder));

                text.add("§2Jewel: §0Obsidian");
                text.add("§2Modifier: §0Eye of Ender");
                text.add("§2Ingot: §0Any");
                text.add("  This ring is connected");
                text.add("to your ender chest.");
                text.add("Just right click it");
                text.add("anywhere to open the");
                text.add("ender chest gui.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 25:                 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, new ItemStack(Item.netherStar));
                JewelryNBT.addModifier(item, new ItemStack(Block.chest));

                text.add("§2Jewel: §0Nether Star");
                text.add("§2Modifier: §0Chest");
                text.add("§2Ingot: §0Any");
                text.add("  This ring can store");
                text.add("any entity in it. To do");
                text.add("that right click an");
                text.add("to store it in the ring");
                text.add("(crouch and right click");
                text.add("if right clicking the");
                text.add("entity opens a GUI,");
                text.add("such as villagers).");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 26: 
                text.add("Right click again on the");
                text.add("ground to release the");
                text.add("entity in that spot.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 27:                 
                if(del == 0){values++; jValues++;}
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;
                if(jValues > JewelrycraftUtil.jewel.size() - 1) jValues = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, JewelrycraftUtil.jewel.get(jValues));
                JewelryNBT.addModifier(item, new ItemStack(Item.dyePowder, 1, 15));

                text.add("§2Jewel: §0Any");
                text.add("§2Modifier: §0Bone Meal");
                text.add("§2Ingot: §0Any");
                text.add("  While having it in");
                text.add("the inventory it will");
                text.add("hydrate any farmland");
                text.add("you step on. If you");
                text.add("right click with this");
                text.add("on a plant, it will");
                text.add("speed up the growth a");
                text.add("bit. Better keep right");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 28: 
                text.add("clicking if you want to");
                text.add("see an actual effect.");
                text.add("§4§nAltar Effect");
                text.add("  In the altar, the ring");
                text.add("has the ability to");
                text.add("hydrate any tilted land");
                text.add("and speed up the");
                text.add("growth of plants in a");
                text.add("3x3x3 area.");
                Page.addTextPage(gui, gui.getLeft() + xPos, gui.getTop(), text);
                break;
            case 29:                 
                if(del == 0) values++;
                del++;
                if(del >= 300) del = 0;
                if(values > JewelrycraftUtil.metal.size() - 1) values = 0;

                JewelryNBT.addMetal(item, JewelrycraftUtil.metal.get(values));
                JewelryNBT.addJewel(item, new ItemStack(Item.enderPearl));
                JewelryNBT.addModifier(item, new ItemStack(Item.pickaxeDiamond));

                text.add("§2Jewel: §0Ender Pearl");
                text.add("§2Modifier: §0Diamond Pick");
                text.add("§2Ingot: §0Any");
                text.add("  You can right click");
                text.add("any block (or crouch");
                text.add("right click) to store");
                text.add("that block inside it.");
                text.add("Right click on the");
                text.add("ground to place it");
                text.add("there. It can also");
                text.add("create golems/withers.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            case 30: 
                JewelryNBT.addMetal(item, new ItemStack(ItemList.shadowIngot));
                JewelryNBT.addJewel(item, new ItemStack(Item.netherStar));
                JewelryNBT.addModifier(item, new ItemStack(Item.pickaxeDiamond));

                text.add("§2Jewel: §0Nether Star");
                text.add("§2Modifier: §0Diamond Pick");
                text.add("§2Ingot: §0Shadow Ingot");
                text.add("  Right clicking with");
                text.add("this on any block will");
                text.add("cause that block to");
                text.add("break instantly.");
                Page.addImageTextPage(gui, gui.getLeft() + xPos, gui.getTop(), item, text, 50f, 0, -10, false, 45, 0);
                break;
            default:;
        }
    }

    public int getMaxPages()
    {
        return 30;
    }

    @Override
    public void drawForeground(GuiGuide gui, int x, int y, int page)
    {        
    }

}
