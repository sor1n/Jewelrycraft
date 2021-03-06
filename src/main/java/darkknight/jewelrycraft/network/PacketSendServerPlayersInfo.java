/**
 * 
 */
package darkknight.jewelrycraft.network;

import java.util.Iterator;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.events.PlayerRenderHandler;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;

/**
 * @author Sorin
 *
 */
public class PacketSendServerPlayersInfo implements IMessage, IMessageHandler<PacketSendServerPlayersInfo, IMessage>
{
    public PacketSendServerPlayersInfo() 
    {
        
    }
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketSendServerPlayersInfo message, MessageContext ctx)
    {
        return null;
    }

    /**
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        NBTTagCompound temp = ByteBufUtils.readTag(buf);
        if (temp != null)
            PlayerRenderHandler.playersInfo = temp;
        else
            PlayerRenderHandler.playersInfo = new NBTTagCompound();
    }

    /**
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {
        Iterator<EntityPlayer> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
        NBTTagCompound nbt = new NBTTagCompound();
        while (players.hasNext())
        {
            EntityPlayer current = players.next();
            nbt.setTag(current.getDisplayName(), PlayerUtils.getModPlayerPersistTag(current, Variables.MODID));
        }
        ByteBufUtils.writeTag(buf, nbt);
    }
}
