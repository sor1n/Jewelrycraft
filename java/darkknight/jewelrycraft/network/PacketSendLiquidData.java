package darkknight.jewelrycraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.JewelrycraftMod;

public class PacketSendLiquidData implements IMessage, IMessageHandler<PacketSendLiquidData, IMessage>
{
    int dimID, x, y, z, itemID, itemMeta, color;
    
    /**
     * 
     */
    public PacketSendLiquidData()
    {}
    
    /**
     * @param packet
     * @param itemID
     * @param itemMeta
     * @param color
     */
    public PacketSendLiquidData(PacketRequestLiquidData packet, int itemID, int itemMeta, int color)
    {
        dimID = packet.dimID;
        x = packet.x;
        y = packet.y;
        z = packet.z;
        this.itemID = itemID;
        this.itemMeta = itemMeta;
        this.color = color;
    }
    
    /**
     * @param dimID
     * @param x
     * @param y
     * @param z
     * @param itemID
     * @param itemMeta
     * @param color
     */
    public PacketSendLiquidData(int dimID, int x, int y, int z, int itemID, int itemMeta, int color)
    {
        this.dimID = dimID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.itemID = itemID;
        this.itemMeta = itemMeta;
        this.color = color;
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
        itemID = buf.readInt();
        itemMeta = buf.readInt();
        color = buf.readInt();
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
        buf.writeInt(itemID);
        buf.writeInt(itemMeta);
        buf.writeInt(color);
    }
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketSendLiquidData message, MessageContext ctx)
    {
        JewelrycraftMod.clientData.setString(message.x + " " + message.y + " " + message.z + " " + message.dimID, message.itemID + ":" + message.itemMeta + ":" + message.color);
        Minecraft.getMinecraft().theWorld.getBlock(message.x, message.y, message.z);
        Minecraft.getMinecraft().theWorld.markBlockForUpdate(message.x, message.y, message.z);
        return null;
    }
}
