package darkknight.jewelrycraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.events.ScreenHandler;

public class PacketSendPlayerInfo implements IMessage, IMessageHandler<PacketSendPlayerInfo, IMessage>
{
    public NBTTagCompound tagCompound;
    
    /**
     * @param tagCompound
     */
    public PacketSendPlayerInfo(NBTTagCompound tagCompound)
    {
        this.tagCompound = tagCompound;
    }
    
    /**
     * 
     */
    public PacketSendPlayerInfo()
    {}
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketSendPlayerInfo message, MessageContext ctx)
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
