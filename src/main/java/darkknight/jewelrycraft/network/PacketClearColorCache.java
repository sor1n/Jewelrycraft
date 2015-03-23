package darkknight.jewelrycraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.JewelrycraftMod;

public class PacketClearColorCache implements IMessage, IMessageHandler<PacketClearColorCache, IMessage>
{
    
    /**
     * 
     */
    public PacketClearColorCache()
    {}
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketClearColorCache message, MessageContext ctx)
    {
        JewelrycraftMod.clientData = new NBTTagCompound();
        return null;
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
