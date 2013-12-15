package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMolder extends TileEntity
{
    public int jewelBaseID, moltenMetalID, cooling;
    public boolean hasMoltenMetal, hasJewelBase;
    public ItemStack metal;

    public TileEntityMolder()
    {
        this.moltenMetalID = 0;
        this.jewelBaseID = 0;
        this.cooling = 0;
        this.hasJewelBase = false;
        this.hasMoltenMetal= false;
    }

    @Override
    public void writeToNBT(NBTTagCompound par1)
    {
        super.writeToNBT(par1);
        par1.setInteger("moltenMetalID", moltenMetalID);
        par1.setInteger("jewelBaseID", jewelBaseID);
        par1.setInteger("cooling", cooling);
        par1.setBoolean("hasJewelBase", hasJewelBase);
        par1.setBoolean("hasMoltenMetal", hasMoltenMetal);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1)
    {
        super.readFromNBT(par1);
        this.moltenMetalID = par1.getInteger("moltenMetalID");
        this.jewelBaseID = par1.getInteger("jewelBaseID");
        this.cooling = par1.getInteger("cooling");
        this.hasJewelBase = par1.getBoolean("hasJewelBase");
        this.hasMoltenMetal = par1.getBoolean("hasMoltenMetal");
    }

    public void updateEntity()
    {
        super.updateEntity();
        if(this.hasMoltenMetal && !this.hasJewelBase)
        {            
            while(cooling > 0)
            {
                this.cooling--;
                System.out.println(cooling);
            }
            if(cooling == 0)
            {
                this.hasMoltenMetal = false;
                this.jewelBaseID = moltenMetalID;
                this.moltenMetalID = 0;
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
