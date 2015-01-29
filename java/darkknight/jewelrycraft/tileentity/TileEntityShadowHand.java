package darkknight.jewelrycraft.tileentity;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityShadowHand extends TileEntity
{
    public boolean isDirty, hasObject;
    public ItemStack object = new ItemStack(Item.getItemById(0), 0, 0);
    public float grip;
    
    public TileEntityShadowHand()
    {
        this.isDirty = false;
        this.hasObject = false;
        this.grip = 0f;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("hasObject", hasObject);
        nbt.setFloat("Grip", grip);
        NBTTagCompound tag = new NBTTagCompound();
        this.object.writeToNBT(tag);
        nbt.setTag("object", tag);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.hasObject = nbt.getBoolean("hasObject");
        this.grip = nbt.getFloat("Grip");
        this.object = new ItemStack(Item.getItemById(0), 0, 0);
        this.object.readFromNBT(nbt.getCompoundTag("object"));
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (isDirty)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = false;
        }
        if(grip <= 10f) grip++;
        else grip = 0;
        //System.out.println(Item.getIdFromItem(object.getItem()));
    }
    
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
