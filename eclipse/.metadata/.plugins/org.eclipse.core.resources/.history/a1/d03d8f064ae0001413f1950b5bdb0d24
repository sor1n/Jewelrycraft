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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.Side;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.gui.GuiHandler;
import darkknight.jewelrycraft.commands.JewelrycraftCommands;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.curses.CurseList;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.effects.EffectsList;
import darkknight.jewelrycraft.entities.EntityHalfHeart;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.events.BucketHandler;
import darkknight.jewelrycraft.events.EntityEventHandler;
import darkknight.jewelrycraft.events.KeyBindings;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketClearColorCache;
import darkknight.jewelrycraft.network.PacketKeyPressEvent;
import darkknight.jewelrycraft.network.PacketRequestLiquidData;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.network.PacketRequestSetSlot;
import darkknight.jewelrycraft.network.PacketSendCurseStats;
import darkknight.jewelrycraft.network.PacketSendLiquidData;
import darkknight.jewelrycraft.network.PacketSendPlayerInfo;
import darkknight.jewelrycraft.proxy.CommonProxy;
import darkknight.jewelrycraft.recipes.CraftingRecipes;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import darkknight.jewelrycraft.worldGen.Generation;
import darkknight.jewelrycraft.worldGen.village.ComponentJewelry;
import darkknight.jewelrycraft.worldGen.village.JCTrades;
import darkknight.jewelrycraft.worldGen.village.VillageJewelryHandler;

@Mod (modid = Variables.MODID, name = Variables.MODNAME, version = Variables.VERSION)
public class JewelrycraftMod
{
    @Instance (Variables.MODID)
    public static JewelrycraftMod instance;
    @SidedProxy (clientSide = "darkknight.jewelrycraft.proxy.ClientProxy", serverSide = "darkknight.jewelrycraft.proxy.CommonProxy")
    public static CommonProxy proxy;
    public static final Logger logger = Logger.getLogger("Jewelrycraft");
    public static File dir;
    public static CreativeTabs jewelrycraft = new CreativeTabs(Variables.MODID){
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
    
    /**
     * Pre initialization of mod stuff.
     *
     * @param e FMLPreInitializationEvent
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) throws IOException
    {
        ConfigHandler.preInit(e);
        BlockList.preInit(e);
        ItemList.preInit(e);
        CraftingRecipes.preInit(e);
        CurseList.preInit(e);
        OreDictionary.registerOre("ingotShadow", new ItemStack(ItemList.shadowIngot));
        OreDictionary.registerOre("oreShadow", new ItemStack(BlockList.shadowOre));
        VillagerRegistry.instance().registerVillagerId(3000);
        VillagerRegistry.instance().registerVillageTradeHandler(3000, new JCTrades());
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageJewelryHandler());
        try{
            MapGenStructureIO.func_143031_a(ComponentJewelry.class, Variables.MODID + ":Jewelry");
        }
        catch(Throwable e2){
            logger.severe("Error registering Jewelrycraft Structures with Vanilla Minecraft: this is expected in versions earlier than 1.7.10");
        }
        MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
        BucketHandler.INSTANCE.buckets.put(BlockList.moltenMetal, ItemList.bucket);
        
        ModMetadata metadata = e.getModMetadata();
        List<String> authorList = new ArrayList<String>();
        
        authorList.add("OnyxDarkKnight");
        authorList.add("bspkrs");
        authorList.add("domi1819");
        
        dir = e.getModConfigurationDirectory();
        proxy.preInit();
        netWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Variables.MODID);
        netWrapper.registerMessage(PacketRequestLiquidData.class, PacketRequestLiquidData.class, 0, Side.SERVER);
        netWrapper.registerMessage(PacketSendLiquidData.class, PacketSendLiquidData.class, 1, Side.CLIENT);
        netWrapper.registerMessage(PacketClearColorCache.class, PacketClearColorCache.class, 2, Side.CLIENT);
        netWrapper.registerMessage(PacketKeyPressEvent.class, PacketKeyPressEvent.class, 3, Side.SERVER);
        netWrapper.registerMessage(PacketRequestPlayerInfo.class, PacketRequestPlayerInfo.class, 4, Side.SERVER);
        netWrapper.registerMessage(PacketSendPlayerInfo.class, PacketSendPlayerInfo.class, 5, Side.CLIENT);
        netWrapper.registerMessage(PacketSendCurseStats.class, PacketSendCurseStats.class, 6, Side.CLIENT);
        netWrapper.registerMessage(PacketRequestSetSlot.class, PacketRequestSetSlot.class, 7, Side.SERVER);
        
        metadata.autogenerated = false;
        metadata.authorList = authorList;
        metadata.url = "https://github.com/sor1n/Jewelrycraft";
        
        createEntity(EntityHeart.class, Variables.MODID + ".Heart", 0xFF0000, 0xFF0000, false);
        createEntity(EntityHalfHeart.class, Variables.MODID + ".Half-Heart", 0x000000, 0xFF0000, false);

//        EntityRegistry.addSpawn(EntityMob.class, 5, 2, 3, EnumCreatureType.creature, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.birchForest, BiomeGenBase.birchForestHills, BiomeGenBase.plains, BiomeGenBase.beach, BiomeGenBase.coldBeach, BiomeGenBase.frozenRiver);
    }

    public void createEntity(Class<? extends Entity> entity, String entityName, int solidColor, int spotColor, boolean hasSpawnEgg)
    {
        int randomID = EntityRegistry.findGlobalUniqueEntityId();
        if(hasSpawnEgg) EntityRegistry.registerGlobalEntityID(entity, entityName, randomID, solidColor, spotColor);
        else EntityRegistry.registerGlobalEntityID(entity, entityName, randomID);
        EntityRegistry.registerModEntity(entity, entityName, randomID, this, 40, 3, true);
    }
    
    /**
     * Initializes the world generation and key bindings.
     *
     * @param e FMLInitializationEvent
     */
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        GameRegistry.registerWorldGenerator(new Generation(), 0);
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) FMLCommonHandler.instance().bus().register(new KeyBindings());
        new GuiHandler();
    }
    
    /**
     * Post initialization of metals, modifiers and others.
     *
     * @param e FMLPostInitializationEvent
     */
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        JewelrycraftUtil.addMetals();
        JewelrycraftUtil.jamcrafters();
        EffectsList.postInit(e);
        DamageSourceList.postInit(e);
        proxy.postInit();
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new JewelrycraftCommands());
    }

}
