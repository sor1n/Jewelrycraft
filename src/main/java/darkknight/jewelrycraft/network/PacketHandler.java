package darkknight.jewelrycraft.network;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.util.Variables;

public class PacketHandler
{

    public static void preInit(FMLPreInitializationEvent e)
    {
        JewelrycraftMod.netWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(Variables.MODID);
        JewelrycraftMod.netWrapper.registerMessage(PacketKeyPressEvent.class, PacketKeyPressEvent.class, 3, Side.SERVER);
        JewelrycraftMod.netWrapper.registerMessage(PacketRequestPlayerInfo.class, PacketRequestPlayerInfo.class, 4, Side.SERVER);
        JewelrycraftMod.netWrapper.registerMessage(PacketSendClientPlayerInfo.class, PacketSendClientPlayerInfo.class, 5, Side.CLIENT);
        JewelrycraftMod.netWrapper.registerMessage(PacketSendServerPlayersInfo.class, PacketSendServerPlayersInfo.class, 6, Side.CLIENT);
        JewelrycraftMod.netWrapper.registerMessage(PacketRequestSetSlot.class, PacketRequestSetSlot.class, 7, Side.SERVER);
        JewelrycraftMod.netWrapper.registerMessage(PacketSendServerPlayerInfo.class, PacketSendServerPlayerInfo.class, 8, Side.SERVER);
    }
}
