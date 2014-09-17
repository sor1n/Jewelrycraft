package darkknight.jewelrycraft.network;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.handshake.NetworkDispatcher;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.util.JewelryNBT;

public class PacketRequestLiquidData implements IMessage, IMessageHandler<PacketRequestLiquidData, IMessage>
{
    int dimID, x, y, z;
    
    public PacketRequestLiquidData()
    {
    }
    
    public PacketRequestLiquidData(int dimID, int x, int y, int z)
    {
        this.dimID = dimID;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public IMessage onMessage(PacketRequestLiquidData message, MessageContext ctx)
    {
        String data = JewelrycraftMod.saveData.getString(message.x + " " + message.y + " " + message.z + " " + message.dimID);
        String[] splitData = data.split(":");
        
        IMessage replyPacket = null;
        
        if (splitData.length == 2)
        {
            int itemID, itemDamage;
            try
            {
                itemID = Integer.parseInt(splitData[0]);
                itemDamage = Integer.parseInt(splitData[1]);
                
                replyPacket = (IMessage) new PacketSendLiquidData(message, itemID, itemDamage);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return replyPacket;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        dimID = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }
    
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(dimID);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
}
