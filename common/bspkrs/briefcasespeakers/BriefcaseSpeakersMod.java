package bspkrs.briefcasespeakers;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import bspkrs.briefcasespeakers.client.BriefcaseSpeakersClient;
import bspkrs.briefcasespeakers.config.ConfigHandler;
import bspkrs.briefcasespeakers.item.ItemList;
import bspkrs.briefcasespeakers.lib.Reference;
import bspkrs.briefcasespeakers.server.BriefcaseSpeakersServer;
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

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
@NetworkMod(clientSideRequired = false, serverSideRequired = false,
        clientPacketHandlerSpec = @SidedPacketHandler(channels = { Reference.PACKET_CHANNEL }, packetHandler = BriefcaseSpeakersClient.class),
        serverPacketHandlerSpec = @SidedPacketHandler(channels = { Reference.PACKET_CHANNEL }, packetHandler = BriefcaseSpeakersServer.class),
        connectionHandler = BriefcaseSpeakersMod.class)
public class BriefcaseSpeakersMod implements IConnectionHandler
{
    @Instance(Reference.MODID)
    public static BriefcaseSpeakersMod instance;
    
    @Metadata(Reference.MODID)
    public static ModMetadata          metadata;
    
    @SidedProxy(clientSide = "bspkrs.briefcasespeakers.client.ClientProxy", serverSide = "bspkrs.briefcasespeakers.CommonProxy")
    public static CommonProxy          proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        ConfigHandler.preInit(e);
        ItemList.preInit(e);
        
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {   
        
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
