package bspkrs.briefcasespeakers.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySmelter extends TileEntity
{
    public int moltenMetalID, metalID, melting;
    public boolean hasMetal, hasMoltenMetal;
    
    public TileEntitySmelter()
    {
        this.moltenMetalID = 0;
        this.metalID = 0;
        this.melting = 0;
        this.hasMetal = false;
        this.hasMoltenMetal= false;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound par1)
    {
       super.writeToNBT(par1);
       par1.setInteger("moltenMetalID", moltenMetalID);
       par1.setInteger("metalID", metalID);
       par1.setInteger("melting", melting);
       par1.setBoolean("hasMetal", hasMetal);
       par1.setBoolean("hasMoltenMetal", hasMoltenMetal);
    }

    @Override
    public void readFromNBT(NBTTagCompound par1)
    {
       super.readFromNBT(par1);
       this.moltenMetalID = par1.getInteger("moltenMetalID");
       this.metalID = par1.getInteger("metalID");
       this.melting = par1.getInteger("melting");
       this.hasMetal = par1.getBoolean("hasMetal");
       this.hasMoltenMetal = par1.getBoolean("hasMoltenMetal");
    }
    
    public void updateEntity()
    {
        super.updateEntity();
        if(this.hasMetal)
        {            
            while(melting > 0){
                this.melting--;
                System.out.println(melting);
            }
            if(melting == 0)
            {
                this.hasMetal = false;
                this.moltenMetalID = metalID;
                this.metalID = 0;
                this.hasMoltenMetal = true;
            }
        }
    }
}
