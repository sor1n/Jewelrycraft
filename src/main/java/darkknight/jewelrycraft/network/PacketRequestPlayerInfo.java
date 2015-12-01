package darkknight.jewelrycraft.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import io.netty.buffer.ByteBuf;

public class PacketRequestPlayerInfo implements IMessage, IMessageHandler<PacketRequestPlayerInfo, IMessage>
{
    
    /**
     * 
     */
    public PacketRequestPlayerInfo()
    {}
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketRequestPlayerInfo message, MessageContext ctx)
    {
        return new PacketSendClientPlayerInfo(PlayerUtils.getModPlayerPersistTag(ctx.getServerHandler().playerEntity, Variables.MODID));
    }
    
    /**
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {}
    
    /**
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {}
}
