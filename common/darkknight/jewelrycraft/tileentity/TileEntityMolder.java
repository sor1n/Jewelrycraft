package darkknight.jewelrycraft.tileentity;

import darkknight.jewelrycraft.item.ItemList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMolder extends TileEntity
{
    public int cooling;
    public boolean hasMoltenMetal, hasJewelBase, hasMold;
    public ItemStack mold, jewelBase, moltenMetal, ringMetal;

    public TileEntityMolder()
    {
        this.moltenMetal = new ItemStack(0, 0, 0);
        this.jewelBase = new ItemStack(0, 0, 0);
        this.mold = new ItemStack(0, 0, 0);
        this.ringMetal = new ItemStack(0, 0, 0);
        this.cooling = 0;
        this.hasJewelBase = false;
        this.hasMoltenMetal = false;
        this.hasMold = false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("cooling", cooling);
        nbt.setBoolean("hasJewelBase", hasJewelBase);
        nbt.setBoolean("hasMoltenMetal", hasMoltenMetal);
        nbt.setBoolean("hasMold", hasMold);
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound tag1 = new NBTTagCompound();
        NBTTagCompound tag2 = new NBTTagCompound();
        NBTTagCompound tag3 = new NBTTagCompound();
        this.mold.writeToNBT(tag);
        nbt.setCompoundTag("mold", tag);
        this.jewelBase.writeToNBT(tag1);
        nbt.setCompoundTag("jewelBase", tag1);
        this.moltenMetal.writeToNBT(tag2);
        nbt.setCompoundTag("moltenMetal", tag2);
        this.ringMetal.writeToNBT(tag2);
        nbt.setCompoundTag("ringMetal", tag3);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.cooling = nbt.getInteger("cooling");
        this.hasJewelBase = nbt.getBoolean("hasJewelBase");
        this.hasMoltenMetal = nbt.getBoolean("hasMoltenMetal");
        this.hasMold = nbt.getBoolean("hasMold");
        this.mold = new ItemStack(0, 0, 0);
        this.mold.readFromNBT(nbt.getCompoundTag("mold"));
        this.jewelBase = new ItemStack(0, 0, 0);
        this.jewelBase.readFromNBT(nbt.getCompoundTag("jewelBase"));
        this.moltenMetal = new ItemStack(0, 0, 0);
        this.moltenMetal.readFromNBT(nbt.getCompoundTag("moltenMetal"));
        this.ringMetal = new ItemStack(0, 0, 0);
        this.ringMetal.readFromNBT(nbt.getCompoundTag("ringMetal"));
    }

    public void updateEntity()
    {
        super.updateEntity();
        if(this.hasMoltenMetal && !this.hasJewelBase)
        {            
            ringMetal = moltenMetal;
            if(cooling > 0) this.cooling--;
            System.out.println(mold.getItemDamage());
            if(cooling == 0)
            {
                this.hasMoltenMetal = false;
                if(mold.getItemDamage() == 0) this.jewelBase = moltenMetal;
                else this.jewelBase = new ItemStack(ItemList.ring);
                this.moltenMetal = new ItemStack(0, 0, 0);
                this.hasJewelBase = true;
            }
            
            this.worldObj.playSoundEffect((double)((float)xCoord + 0.5F), (double)((float)yCoord + 0.5F), (double)((float)zCoord + 0.5F), "random.fizz", 0.5F, 2.6F + 0.2F * 0.8F);
            for (int l = 0; l < 4; ++l)
            {
                //EntityFX entityfx = new EntityReddustFX(this.worldObj, (double)xCoord + Math.random(), (double)yCoord + 0.2D, (double)zCoord + Math.random(), 0.0F, 0.0F, 0.0F);
                this.worldObj.spawnParticle("reddust", (double)xCoord + Math.random(), (double)yCoord + 0.2F, (double)zCoord + Math.random(), 0.0D, 1.0D, 0.0D);
            }
        }
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
