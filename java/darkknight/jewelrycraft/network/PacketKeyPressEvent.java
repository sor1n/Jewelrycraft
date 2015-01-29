package darkknight.jewelrycraft.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.JewelrycraftMod;

public class PacketKeyPressEvent implements IMessage, IMessageHandler<PacketKeyPressEvent, IMessage>
{
    public int actionID;
    
    public PacketKeyPressEvent(int id)
    {
        actionID = id;
    }
    
    public PacketKeyPressEvent()
    {
    }

    @Override
    public IMessage onMessage(PacketKeyPressEvent message, MessageContext ctx)
    {
        EntityPlayer sender = ctx.getServerHandler().playerEntity;
        
        // Jewelry inventory
        if (message.actionID == 0) sender.openGui(JewelrycraftMod.instance, 2, sender.worldObj, (int)sender.posX, (int)sender.posY, (int)sender.posZ);
        
        return null;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        actionID = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(actionID);
    }
}
