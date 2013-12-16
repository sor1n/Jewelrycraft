package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityJewelrsCraftingTable extends TileEntity
{
    public boolean hasJewel, hasModifier;
    public ItemStack jewel, modifier;
    
    public TileEntityJewelrsCraftingTable()
    {
        this.jewel = new ItemStack(0, 0, 0);
        this.modifier = new ItemStack(0, 0, 0);
        this.hasJewel = false;
        this.hasModifier = false;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("hasJewel", hasJewel);
        nbt.setBoolean("hasModifier", hasModifier);
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound tag1 = new NBTTagCompound();
        this.jewel.writeToNBT(tag);
        nbt.setCompoundTag("jewel", tag);
        this.modifier.writeToNBT(tag1);
        nbt.setCompoundTag("modifier", tag1);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.hasJewel = nbt.getBoolean("hasJewel");
        this.hasModifier = nbt.getBoolean("hasModifier");
        this.jewel = new ItemStack(0, 0, 0);
        this.jewel.readFromNBT(nbt.getCompoundTag("jewel"));
        this.modifier = new ItemStack(0, 0, 0);
        this.modifier.readFromNBT(nbt.getCompoundTag("modifier"));
    }
    
    public void updateEntity()
    {
        super.updateEntity();
    }
    
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt)
    {
        readFromNBT(pkt.data);
    }
    
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }
}
