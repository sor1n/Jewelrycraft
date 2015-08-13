package darkknight.jewelrycraft.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class PacketSendServerPlayerInfo implements IMessage, IMessageHandler<PacketSendServerPlayerInfo, IMessage>
{
    public NBTTagCompound tagCompound;
    public String curse, action;
    
    /**
     * @param tagCompound
     */
    public PacketSendServerPlayerInfo(String action, String curse, NBTTagCompound tagCompound)
    {
        this.tagCompound = tagCompound;
        this.curse = curse;
        this.action = action;
    }
    
    /**
     * 
     */
    public PacketSendServerPlayerInfo()
    {}
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketSendServerPlayerInfo message, MessageContext ctx)
    {
    	EntityPlayerMP serverPlayer = ctx.getServerHandler().playerEntity;
    	NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(serverPlayer, Variables.MODID); 
    	playerInfo.setInteger(message.curse, message.action.equals("remove") ? 0 : 1);
    	playerInfo.setInteger("activeCurses", message.tagCompound.getInteger("activeCurses"));
		System.out.println(message.action + " " + message.curse + " " + message.tagCompound.getInteger(curse) + " " + message.tagCompound.getInteger("activeCurses"));
        return null;
    }
    
    /**
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        tagCompound = ByteBufUtils.readTag(buf);
        curse = ByteBufUtils.readUTF8String(buf);
        action = ByteBufUtils.readUTF8String(buf);
    }
    
    /**
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeTag(buf, tagCompound);
        ByteBufUtils.writeUTF8String(buf, curse);
        ByteBufUtils.writeUTF8String(buf, action);
    }
}
