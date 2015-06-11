package darkknight.jewelrycraft.network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.JewelrycraftMod;

public class PacketRequestLiquidData implements IMessage, IMessageHandler<PacketRequestLiquidData, IMessage>
{
    int dimID, x, y, z;
    
    /**
     * 
     */
    public PacketRequestLiquidData()
    {}
    
    /**
     * @param dimID
     * @param x
     * @param y
     * @param z
     */
    public PacketRequestLiquidData(int dimID, int x, int y, int z)
    {
        this.dimID = dimID;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketRequestLiquidData message, MessageContext ctx)
    {
        String data = JewelrycraftMod.saveData.getString(message.x + " " + message.y + " " + message.z + " " + message.dimID);
        String[] splitData = data.split(":");
        IMessage replyPacket = null;
        if (splitData.length == 2){
            int itemID, itemDamage;
            try{
                itemID = Integer.parseInt(splitData[0]);
                itemDamage = Integer.parseInt(splitData[1]);
                replyPacket = new PacketSendLiquidData(message, itemID, itemDamage);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        return replyPacket;
    }
    
    /**
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        dimID = buf.readInt();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }
    
    /**
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(dimID);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }
}
