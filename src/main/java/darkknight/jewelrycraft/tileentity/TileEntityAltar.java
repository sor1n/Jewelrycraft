package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityAltar extends TileEntity
{
    public ItemStack object;
    public boolean isDirty, hasObject;
    public String playerName;
    
    /**
     * 
     */
    public TileEntityAltar()
    {
        hasObject = false;
        object = new ItemStack(Item.getItemById(0), 0, 0);
        isDirty = false;
        playerName = "";
    }
    
    /**
     * @param nbt
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagCompound tag = new NBTTagCompound();
        object.writeToNBT(tag);
        nbt.setTag("object", tag);
        nbt.setBoolean("hasObject", hasObject);
        nbt.setString("playerName", playerName);
    }
    
    /**
     * @param nbt
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        object = new ItemStack(Item.getItemById(0), 0, 0);
        object.readFromNBT(nbt.getCompoundTag("object"));
        hasObject = nbt.getBoolean("hasObject");
        playerName = nbt.getString("playerName");
    }
    
    /**
     * 
     */
    @SuppressWarnings ("rawtypes")
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (isDirty){
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = false;
        }
    }
    
    /**
     * @return
     */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }
    
    /**
     * @param net
     * @param packet
     */
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
