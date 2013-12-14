package bspkrs.briefcasespeakers.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySmelter extends TileEntity
{
    public int moltenMetalQ, metalID;
    public boolean hasMetal, hasMoltenMetal;
    
    public TileEntitySmelter()
    {
        this.moltenMetalQ = 0;
        this.metalID = 0;
        this.hasMetal = false;
        this.hasMoltenMetal= false;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1)
    {
       super.writeToNBT(par1);
       par1.setInteger("moltenMetalQ", moltenMetalQ);
       par1.setInteger("metalID", metalID);
       par1.setBoolean("hasMetal", hasMetal);
       par1.setBoolean("hasMoltenMetal", hasMoltenMetal);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1)
    {
       super.readFromNBT(par1);
       this.moltenMetalQ = par1.getInteger("moltenMetalQ");
       this.metalID = par1.getInteger("metalID");
       this.hasMetal = par1.getBoolean("hasMetal");
       this.hasMoltenMetal = par1.getBoolean("hasMoltenMetal");
    }
    
    public void updateEntity()
    {
        super.updateEntity();
    }
}
