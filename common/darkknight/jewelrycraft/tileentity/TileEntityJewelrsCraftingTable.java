package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityJewelrsCraftingTable extends TileEntity
{
    public boolean hasJewel, hasModifier, hasEndItem, isDirty;
    public ItemStack jewel, modifier, endItem;
    public int       timer;

    public TileEntityJewelrsCraftingTable()
    {
        this.jewel = new ItemStack(0, 0, 0);
        this.modifier = new ItemStack(0, 0, 0);
        this.endItem = new ItemStack(0, 0, 0);
        this.hasJewel = false;
        this.hasModifier = false;
        this.hasEndItem = false;
        this.timer = 0;
        this.isDirty = false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("hasJewel", hasJewel);
        nbt.setBoolean("hasModifier", hasModifier);
        nbt.setBoolean("hasEndItem", hasEndItem);
        nbt.setInteger("timer", timer);
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound tag1 = new NBTTagCompound();
        NBTTagCompound tag2 = new NBTTagCompound();
        this.jewel.writeToNBT(tag);
        nbt.setCompoundTag("jewel", tag);
        this.modifier.writeToNBT(tag1);
        nbt.setCompoundTag("modifier", tag1);
        this.endItem.writeToNBT(tag2);
        nbt.setCompoundTag("endItem", tag2);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.hasJewel = nbt.getBoolean("hasJewel");
        this.hasModifier = nbt.getBoolean("hasModifier");
        this.hasEndItem = nbt.getBoolean("hasEndItem");
        this.timer = nbt.getInteger("timer");
        this.jewel = new ItemStack(0, 0, 0);
        this.jewel.readFromNBT(nbt.getCompoundTag("jewel"));
        this.modifier = new ItemStack(0, 0, 0);
        this.modifier.readFromNBT(nbt.getCompoundTag("modifier"));
        this.endItem = new ItemStack(0, 0, 0);
        this.endItem.readFromNBT(nbt.getCompoundTag("endItem"));
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if(isDirty){
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = true;
        }
        if (this.hasJewel && this.hasModifier && !this.hasEndItem)
        {
            if (timer > 0)
            {
                timer--;
                for (int l = 0; l < 2000/(timer + 2); ++l)
                {
                    if(this.getBlockMetadata() == 0) this.worldObj.spawnParticle("witchMagic", xCoord + 0.5F, (double) yCoord + 0.8F, zCoord + 0.2F, 0.0D, 0.0D, 0.0D);
                    if(this.getBlockMetadata() == 1) this.worldObj.spawnParticle("witchMagic", xCoord + 0.8F, (double) yCoord + 0.8F, zCoord + 0.5F, 0.0D, 0.0D, 0.0D);
                    if(this.getBlockMetadata() == 2) this.worldObj.spawnParticle("witchMagic", xCoord + 0.5F, (double) yCoord + 0.8F, zCoord + 0.8F, 0.0D, 0.0D, 0.0D);
                    if(this.getBlockMetadata() == 3) this.worldObj.spawnParticle("witchMagic", xCoord + 0.2F, (double) yCoord + 0.8F, zCoord + 0.5F, 0.0D, 0.0D, 0.0D);
                }
            }
            System.out.println(timer);
            if (timer == 0)
            {
                this.hasEndItem = true;
                this.endItem = jewel.copy();
                this.hasJewel = false;
                this.jewel = new ItemStack(0, 0, 0);
                this.hasModifier = false;
                this.modifier = new ItemStack(0, 0, 0);
            }
        }
    }

    @Override
    public Packet getDescriptionPacket() 
    {
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound dataTag = packet != null ? packet.data : new NBTTagCompound();
        writeToNBT(dataTag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, dataTag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) 
    {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt != null ? pkt.data : new NBTTagCompound();
        readFromNBT(tag);
    }
}
