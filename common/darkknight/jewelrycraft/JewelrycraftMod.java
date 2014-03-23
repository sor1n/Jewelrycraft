/*
 * Mod made by DarkKnight during the Modjam 3
 * It's an awesome mod
 * I love me! :D
 */

package darkknight.jewelrycraft;

import java.util.logging.Logger;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.JewelryCraftClient;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.container.GuiHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.lib.Reference;
import darkknight.jewelrycraft.recipes.CraftingRecipes;
import darkknight.jewelrycraft.server.JewelryCraftServer;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.worldGen.Generation;
import darkknight.jewelrycraft.worldGen.village.ComponentJewelry;
import darkknight.jewelrycraft.worldGen.village.JCTrades;
import darkknight.jewelrycraft.worldGen.village.VillageJewelryHandler;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = false, serverSideRequired = false,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { Reference.PACKET_CHANNEL }, packetHandler = JewelryCraftClient.class),
serverPacketHandlerSpec = @SidedPacketHandler(channels = { Reference.PACKET_CHANNEL }, packetHandler = JewelryCraftServer.class),
connectionHandler = JewelrycraftMod.class)
public class JewelrycraftMod implements IConnectionHandler
{
    @Instance(Reference.MODID)
    public static JewelrycraftMod instance;

    @Metadata(Reference.MODID)
    public static ModMetadata metadata;

    @SidedProxy(clientSide = "darkknight.jewelrycraft.client.ClientProxy", serverSide = "darkknight.jewelrycraft.CommonProxy")
    public static CommonProxy proxy;
    
    public static final Logger logger = Logger.getLogger("Jewelrycraft");

    public static CreativeTabs jewelrycraft = new CreativeTabs("JewelryCraft")
    {
        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(BlockList.jewelCraftingTable);
        }
    };
    public static CreativeTabs rings = new CreativeTabRings("Rings");
    public static CreativeTabs necklaces = new CreativeTabNecklaces("Necklaces");

    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        ConfigHandler.preInit(e);
        ItemList.preInit(e);
        BlockList.preInit(e);
        CraftingRecipes.preInit(e);
        OreDictionary.registerOre("ingotShadow", new ItemStack(ItemList.shadowIngot));
        OreDictionary.registerOre("oreShadow", new ItemStack(BlockList.shadowOre));
        MinecraftForge.setBlockHarvestLevel(BlockList.shadowOre, 0, "pickaxe", 3);
        
        VillagerRegistry.instance().registerVillagerId(3000);
        VillagerRegistry.instance().registerVillageTradeHandler(3000, new JCTrades());
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageJewelryHandler());
        try
        {
            MapGenStructureIO.func_143031_a(ComponentJewelry.class, "Jewelrycraft:Jewelry");
        }
        catch (Throwable e2)
        {
            logger.severe("Error registering Jewelrycraft Structures with Vanilla Minecraft: this is expected in versions earlier than 1.6.4");
        }
        
        proxy.registerRenderers();
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        GameRegistry.registerWorldGenerator(new Generation());
        new GuiHandler();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {   
        JewelrycraftUtil.addMetals();
        JewelrycraftUtil.addStuff();
        JewelrycraftUtil.jamcrafters();
    }

    @Override
    // 2) Called when a player logs into the server SERVER SIDE
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager)
    {   

    }

    @Override
    // If you don't want the connection to continue, return a non-empty string here SERVER SIDE
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
    {
        return null;
    }

    @Override
    // 1) Fired when a remote connection is opened CLIENT SIDE
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager)
    {   

    }

    @Override
    // 1) Fired when a local connection is opened CLIENT SIDE
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
    {   

    }

    @Override
    // Fired when a connection closes ALL SIDES
    public void connectionClosed(INetworkManager manager)
    {   

    }

    @Override
    // 3) Fired when the client established the connection to the server CLIENT SIDE
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
    {   

    }
}
