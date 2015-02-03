package darkknight.jewelrycraft.network;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockMoltenMetal;
import io.netty.buffer.ByteBuf;

public class PacketSendLiquidData implements IMessage, IMessageHandler<PacketSendLiquidData, IMessage>
{
    int dimID, x, y, z, itemID, itemMeta, color;
    
    public PacketSendLiquidData()
    {
    }
    
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
    
    @Override
    public IMessage onMessage(PacketSendLiquidData message, MessageContext ctx)
    {
        JewelrycraftMod.clientData.setString(message.x + " " + message.y + " " + message.z + " " + message.dimID, message.itemID + ":" + message.itemMeta + ":" + message.color);
        
        Block block = Minecraft.getMinecraft().theWorld.getBlock(message.x, message.y, message.z);
        Minecraft.getMinecraft().theWorld.markBlockForUpdate(message.x, message.y, message.z);
        
        return null;
    }
}
