package darkknight.jewelrycraft.network;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.util.PlayerUtils;

public class PacketRequestPlayerInfo implements IMessage, IMessageHandler<PacketRequestPlayerInfo, IMessage>
{
    public PacketRequestPlayerInfo()
    {
    }
    
    @Override
    public IMessage onMessage(PacketRequestPlayerInfo message, MessageContext ctx)
    {
        return new PacketSendPlayerInfo(PlayerUtils.getModPlayerPersistTag(ctx.getServerHandler().playerEntity, "Jewelrycraft"));
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
