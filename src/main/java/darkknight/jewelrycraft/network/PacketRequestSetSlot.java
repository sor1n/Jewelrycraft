/**
 * 
 */
package darkknight.jewelrycraft.network;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import darkknight.jewelrycraft.client.gui.container.ContainerJewelryModifier;
import darkknight.jewelrycraft.client.gui.container.ContainerJewelryTab;

/**
 * @author Sorin
 *
 */
public class PacketRequestSetSlot implements IMessage, IMessageHandler<PacketRequestSetSlot, IMessage>
{
    ItemStack stack;
    
    public PacketRequestSetSlot()
    {}
    
    public PacketRequestSetSlot(ItemStack stack)
    {
        this.stack = stack;
    }

    /**
     * @param message
     * @param ctx
     * @return
     */
    @Override
    public IMessage onMessage(PacketRequestSetSlot message, MessageContext ctx)
    {
        if (ctx.getServerHandler().playerEntity.openContainer instanceof ContainerJewelryModifier)
        {
            ((ContainerJewelryModifier)ctx.getServerHandler().playerEntity.openContainer).modInv.setInventorySlotContents(36, message.stack);
        }
        return null;
    }

    /**
     * @param buf
     */
    @Override
    public void fromBytes(ByteBuf buf)
    {
        stack = ByteBufUtils.readItemStack(buf);
    }

    /**
     * @param buf
     */
    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeItemStack(buf, stack);
    }
}
