/**
 * 
 */
package darkknight.jewelrycraft.network;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.events.PlayerRenderHandler;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

/**
 * @author Sorin
 *
 */
public class PacketSendCurseStats implements IMessage, IMessageHandler<PacketSendCurseStats, IMessage>
{
    public PacketSendCurseStats() 
    {
        
    }
    
    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketSendCurseStats message, MessageContext ctx)
    {
        return null;
    }

    /**
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        String temp = ByteBufUtils.readUTF8String(buf);
        if (temp != "")
            PlayerRenderHandler.infamyCache = temp.split(";");
        else
            PlayerRenderHandler.infamyCache = new String[] { };
    }

    /**
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {
        Iterator<EntityPlayer> players = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
        String infamyPlayers = "";
        while (players.hasNext())
        {
            EntityPlayer current = players.next();
            if(PlayerUtils.getModPlayerPersistTag(current, Variables.MODID).getInteger(Variables.MODNAME + ":" + "Infamy") > 0)
                infamyPlayers = infamyPlayers + (infamyPlayers == "" ? "" : ";") + current.getDisplayName();
        }
        ByteBufUtils.writeUTF8String(buf, infamyPlayers);
    }
}
