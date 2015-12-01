package darkknight.jewelrycraft.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.events.ScreenHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class PacketSendClientPlayerInfo implements IMessage, IMessageHandler<PacketSendClientPlayerInfo, IMessage>
{
    public NBTTagCompound tagCompound;
    
    /**
     * @param tagCompound
     */
    public PacketSendClientPlayerInfo(NBTTagCompound tagCompound)
    {
        this.tagCompound = tagCompound;
    }
    
    /**
     * 
     */
    public PacketSendClientPlayerInfo()
    {}
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketSendClientPlayerInfo message, MessageContext ctx)
    {
        ScreenHandler.tagCache = message.tagCompound;
        return null;
    }
    
    /**
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        tagCompound = ByteBufUtils.readTag(buf);
    }
    
    /**
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, tagCompound);
    }
}
