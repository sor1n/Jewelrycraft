package darkknight.jewelrycraft.item;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockList;

public class ItemList
{
    public static Item thiefGloves;
    public static Item shadowIngot, ingot2, ingot3, ingot4, ingot5, ingot6, ingot7;
    public static Item molds;
    public static Item clayMolds;
    public static Item crystal;
    public static ItemRing ring;
    public static ItemNecklace necklace;
    public static Item guide;
    public static ItemMoltenMetalBucket bucket;
    public static ItemMoltenMetal metal;
    
    private static boolean isInitialized = false;
    
    public static void preInit(FMLPreInitializationEvent e)
    {
        if (!isInitialized)
        {
            thiefGloves = new ItemThiefGloves().setUnlocalizedName("Jewelrycraft.thiefGloves").setTextureName("jewelrycraft:thiefGloves").setCreativeTab(JewelrycraftMod.jewelrycraft);
            shadowIngot = new Item().setUnlocalizedName("Jewelrycraft.ingotShadow").setTextureName("jewelrycraft:ingotShadow").setCreativeTab(JewelrycraftMod.jewelrycraft);
            molds = new ItemMolds().setUnlocalizedName("Jewelrycraft.mold").setTextureName("Mold").setCreativeTab(JewelrycraftMod.jewelrycraft);
            clayMolds = new ItemClayMolds().setUnlocalizedName("Jewelrycraft.mold").setTextureName("Mold").setCreativeTab(JewelrycraftMod.jewelrycraft);
            ring = (ItemRing) new ItemRing().setUnlocalizedName("Jewelrycraft.ring").setTextureName("jewelrycraft:ring");
            crystal = new ItemCrystal().setUnlocalizedName("Jewelrycraft.crystal").setTextureName("jewelrycraft:crystal").setCreativeTab(JewelrycraftMod.jewelrycraft);
            necklace = (ItemNecklace) new ItemNecklace().setUnlocalizedName("Jewelrycraft.necklace").setTextureName("jewelrycraft:necklace");
            guide = new ItemGuide().setUnlocalizedName("Jewelrycraft.guide").setTextureName("jewelrycraft:guide").setCreativeTab(JewelrycraftMod.jewelrycraft);
            bucket = (ItemMoltenMetalBucket) new ItemMoltenMetalBucket().setUnlocalizedName("Jewelrycraft.bucket");
            metal = (ItemMoltenMetal) new ItemMoltenMetal().setUnlocalizedName("Jewelrycraft.bucket");
            
            // Test Ingots
            ingot2 = new Item().setUnlocalizedName("Jewelrycraft.ingot2").setTextureName("jewelrycraft:test/ingot2").setCreativeTab(JewelrycraftMod.jewelrycraft);
            ingot3 = new Item().setUnlocalizedName("Jewelrycraft.ingot3").setTextureName("jewelrycraft:ingot3").setCreativeTab(JewelrycraftMod.jewelrycraft);
            ingot4 = new Item().setUnlocalizedName("Jewelrycraft.ingot4").setTextureName("jewelrycraft:ingot4").setCreativeTab(JewelrycraftMod.jewelrycraft);
            ingot5 = new Item().setUnlocalizedName("Jewelrycraft.ingot5").setTextureName("jewelrycraft:test/ingot5").setCreativeTab(JewelrycraftMod.jewelrycraft);
            ingot6 = new Item().setUnlocalizedName("Jewelrycraft.ingot6").setTextureName("jewelrycraft:ingot6").setCreativeTab(JewelrycraftMod.jewelrycraft);
            ingot7 = new Item().setUnlocalizedName("Jewelrycraft.ingot7").setTextureName("jewelrycraft:ingot7").setCreativeTab(JewelrycraftMod.jewelrycraft);
            
            GameRegistry.registerItem(thiefGloves, "thiefGloves");
            GameRegistry.registerItem(shadowIngot, "shadowIngot");
            GameRegistry.registerItem(molds, "molds");
            GameRegistry.registerItem(clayMolds, "clayMolds");
            GameRegistry.registerItem(ring, "ring");
            GameRegistry.registerItem(necklace, "necklace");
            GameRegistry.registerItem(crystal, "crystal");
            GameRegistry.registerItem(guide, "guide");
            GameRegistry.registerItem(bucket, "moltenMetalBucket");
            GameRegistry.registerItem(metal, "moltenMetal");
            
            GameRegistry.registerItem(ingot2, "ingot2");
            GameRegistry.registerItem(ingot3, "ingot3");
            GameRegistry.registerItem(ingot4, "ingot4");
            GameRegistry.registerItem(ingot5, "ingot5");
            GameRegistry.registerItem(ingot6, "ingot6");
            GameRegistry.registerItem(ingot7, "ingot7");
            
            isInitialized = true;
        }
    }
}
