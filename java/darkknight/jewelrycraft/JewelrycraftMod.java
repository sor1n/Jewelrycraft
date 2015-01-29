/*
 * Mod made by DarkKnight during the Modjam 3
 * It's an awesome mod
 * I love me! :D
 */

package darkknight.jewelrycraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.container.ContainerJewelryTab;
import darkknight.jewelrycraft.container.GuiHandler;
import darkknight.jewelrycraft.container.JewelryInventory;
import darkknight.jewelrycraft.events.BucketHandler;
import darkknight.jewelrycraft.events.EntityEventHandler;
import darkknight.jewelrycraft.events.KeyBindings;
import darkknight.jewelrycraft.events.ScreenHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.lib.Reference;
import darkknight.jewelrycraft.network.PacketClearColorCache;
import darkknight.jewelrycraft.network.PacketKeyPressEvent;
import darkknight.jewelrycraft.network.PacketRequestLiquidData;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.network.PacketSendLiquidData;
import darkknight.jewelrycraft.network.PacketSendPlayerInfo;
import darkknight.jewelrycraft.recipes.CraftingRecipes;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.worldGen.Generation;
import darkknight.jewelrycraft.worldGen.village.ComponentJewelry;
import darkknight.jewelrycraft.worldGen.village.JCTrades;
import darkknight.jewelrycraft.worldGen.village.VillageJewelryHandler;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class JewelrycraftMod
{
    @Instance(Reference.MODID)
    public static JewelrycraftMod instance;
    
    @Metadata(Reference.MODID)
    public static ModMetadata metadata;
    
    @SidedProxy(clientSide = "darkknight.jewelrycraft.client.ClientProxy", serverSide = "darkknight.jewelrycraft.CommonProxy")
    public static CommonProxy proxy;
    
    public static final Logger logger = Logger.getLogger("Jewelrycraft");
    public static File dir;
    public static CreativeTabs jewelrycraft = new CreativeTabs("JewelryCraft")
    {
        @Override
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(BlockList.jewelCraftingTable);
        }
    };
    public static CreativeTabs liquids = new CreativeTabLiquids("Liquids");
    public static NBTTagCompound saveData = new NBTTagCompound();
    public static NBTTagCompound clientData = new NBTTagCompound();
    public static File liquidsConf;
    
    public static SimpleNetworkWrapper netWrapper;
    public static boolean fancyRender;
    public static JewelryInventory jewelry = new JewelryInventory();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) throws IOException
    {
        ConfigHandler.preInit(e);
        BlockList.preInit(e);
        ItemList.preInit(e);
        CraftingRecipes.preInit(e);
        OreDictionary.registerOre("ingotShadow", new ItemStack(ItemList.shadowIngot));
        OreDictionary.registerOre("oreShadow", new ItemStack(BlockList.shadowOre));
        
        VillagerRegistry.instance().registerVillagerId(3000);
        VillagerRegistry.instance().registerVillageTradeHandler(3000, new JCTrades());
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageJewelryHandler());
        try
        {
            MapGenStructureIO.func_143031_a(ComponentJewelry.class, "Jewelrycraft:Jewelry");
        }
        catch (Throwable e2)
        {
            logger.severe("Error registering Jewelrycraft Structures with Vanilla Minecraft: this is expected in versions earlier than 1.7.10");
        }
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) MinecraftForge.EVENT_BUS.register(new ScreenHandler(Minecraft.getMinecraft()));
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
        BucketHandler.INSTANCE.buckets.put(BlockList.moltenMetal, ItemList.bucket);
        
        ModMetadata metadata = e.getModMetadata();
        
        List<String> authorList = new ArrayList<String>();
        authorList.add("DarkKnight (or sor1n)");
        authorList.add("bspkrs");
        authorList.add("domi1819");
        dir = e.getModConfigurationDirectory();
        proxy.registerRenderers();
        
        netWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
        netWrapper.registerMessage(PacketRequestLiquidData.class, PacketRequestLiquidData.class, 0, Side.SERVER);
        netWrapper.registerMessage(PacketSendLiquidData.class, PacketSendLiquidData.class, 1, Side.CLIENT);
        netWrapper.registerMessage(PacketClearColorCache.class, PacketClearColorCache.class, 2, Side.CLIENT);
        netWrapper.registerMessage(PacketKeyPressEvent.class, PacketKeyPressEvent.class, 3, Side.SERVER);
        netWrapper.registerMessage(PacketRequestPlayerInfo.class, PacketRequestPlayerInfo.class, 4, Side.SERVER);
        netWrapper.registerMessage(PacketSendPlayerInfo.class, PacketSendPlayerInfo.class, 5, Side.CLIENT);
        
        metadata.autogenerated = false;
        metadata.authorList = authorList;
        metadata.url = "https://github.com/sor1n/Jewelrycraft";
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        GameRegistry.registerWorldGenerator(new Generation(), 0);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) FMLCommonHandler.instance().bus().register(new KeyBindings());
        new GuiHandler();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        JewelrycraftUtil.addMetals();
        JewelrycraftUtil.addStuff();
        JewelrycraftUtil.jamcrafters();
    }
}
