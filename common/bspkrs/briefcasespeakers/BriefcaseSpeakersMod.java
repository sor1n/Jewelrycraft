package bspkrs.briefcasespeakers;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import bspkrs.briefcasespeakers.client.BriefcaseSpeakersClient;
import bspkrs.briefcasespeakers.lib.Reference;
import bspkrs.briefcasespeakers.server.BriefcaseSpeakersServer;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
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
        clientPacketHandlerSpec = @SidedPacketHandler(channels = { Reference.MODID }, packetHandler = BriefcaseSpeakersClient.class),
        serverPacketHandlerSpec = @SidedPacketHandler(channels = { Reference.MODID }, packetHandler = BriefcaseSpeakersServer.class),
        connectionHandler = BriefcaseSpeakersMod.class)
public class BriefcaseSpeakersMod implements IConnectionHandler
{
    @Instance(Reference.MODID)
    public static BriefcaseSpeakersMod instance;
    
    @Metadata(Reference.MODID)
    public Metadata                    metadata;
    
    @SidedProxy(clientSide = "bspkrs.treecapitator.fml.ClientProxy", serverSide = "bspkrs.treecapitator.fml.CommonProxy")
    public CommonProxy                 proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {   
        
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
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
    {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void connectionClosed(INetworkManager manager)
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
    {
        // TODO Auto-generated method stub
        
    }
}
