package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySmelter extends TileEntity
{
    public int melting;
    public boolean hasMetal, hasMoltenMetal;
    public ItemStack metal, moltenMetal;

    public TileEntitySmelter()
    {
        this.melting = 0;
        this.hasMetal = false;
        this.hasMoltenMetal= false;
        this.metal = new ItemStack(0, 0, 0);
        this.moltenMetal = new ItemStack(0, 0, 0);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("melting", melting);
        nbt.setBoolean("hasMetal", hasMetal);
        nbt.setBoolean("hasMoltenMetal", hasMoltenMetal);
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound tag1 = new NBTTagCompound();
        this.metal.writeToNBT(tag);
        nbt.setCompoundTag("metal", tag);
        this.moltenMetal.writeToNBT(tag1);
        nbt.setCompoundTag("moltenMetal", tag1);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.melting = nbt.getInteger("melting");
        this.hasMetal = nbt.getBoolean("hasMetal");
        this.hasMoltenMetal = nbt.getBoolean("hasMoltenMetal");
        this.metal = new ItemStack(0, 0, 0);
        this.metal.readFromNBT(nbt.getCompoundTag("metal"));
        this.moltenMetal = new ItemStack(0, 0, 0);
        this.moltenMetal.readFromNBT(nbt.getCompoundTag("moltenMetal"));
    }

    public void updateEntity()
    {
        super.updateEntity();
        if(this.hasMetal && !this.hasMoltenMetal)
        {            
            while(melting > 0)
            {
                this.melting--;
                System.out.println(melting);
            }
            if(melting == 0)
            {
                this.hasMetal = false;
                this.moltenMetal = metal;
                this.metal = new ItemStack(0, 0, 0);
                this.hasMoltenMetal = true;
            }
        }
    }

    public Packet getDescriptionPacket() 
    {
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }
}
