package darkknight.jewelrycraft.network;

import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.JewelrycraftMod;

public class PacketClearColorCache implements IMessage, IMessageHandler<PacketClearColorCache, IMessage>
{
    public PacketClearColorCache()
    {
    }
    
    @Override
    public IMessage onMessage(PacketClearColorCache message, MessageContext ctx)
    {
        JewelrycraftMod.clientData = new NBTTagCompound();
        return null;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
    }
}
