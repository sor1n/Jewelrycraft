package darkknight.jewelrycraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
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
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.JewelryCraftClient;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.lib.Reference;
import darkknight.jewelrycraft.recipes.CraftingRecipes;
import darkknight.jewelrycraft.server.JewelryCraftServer;
import darkknight.jewelrycraft.worldGen.Generation;

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
    public static ModMetadata     metadata;
    
    @SidedProxy(clientSide = "darkknight.jewelrycraft.client.ClientProxy", serverSide = "darkknight.jewelrycraft.CommonProxy")
    public static CommonProxy     proxy;
    
    public static CreativeTabs    jewelrycraft = new CreativeTabs("JewelryCraft")
                                               {
                                                   @Override
                                                   public ItemStack getIconItemStack()
                                                   {
                                                       return new ItemStack(ItemList.shadowIngot, 1, 0);
                                                   }
                                               };
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        ConfigHandler.preInit(e);
        ItemList.preInit(e);
        BlockList.preInit(e);
        CraftingRecipes.preInit(e);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        OreDictionary.registerOre("ingotShadow", new ItemStack(ItemList.shadowIngot));
        GameRegistry.registerWorldGenerator(new Generation());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {   
        
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
