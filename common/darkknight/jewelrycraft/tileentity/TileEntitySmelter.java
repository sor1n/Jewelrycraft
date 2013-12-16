package darkknight.jewelrycraft.tileentity;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySmelter extends TileEntity
{
    public int melting, flow, n=0, p=0;
    public boolean hasMetal, hasMoltenMetal;
    public ItemStack metal, moltenMetal;

    public TileEntitySmelter()
    {
        this.melting = 0;
        this.flow = 0;
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
        Random rand = new Random();
        if(p>0)--p;
        else p=5;
        if(n == 0 && p == 0){
            flow+=16;
            if(flow >= 16*20) n=1;
        }
        if(n == 1 && p == 0){
            flow-=16;
            if(flow <= 0) n=0;
        }
        if(this.melting > 0)
        {           
            for (int l = 0; l < 5; ++l)
            {
                //EntityFX entityfx = new EntityReddustFX(this.worldObj, (double)xCoord + Math.random(), (double)yCoord + 0.2D, (double)zCoord + Math.random(), 0.0F, 0.0F, 0.0F);
                this.worldObj.spawnParticle("flame", (double)xCoord + Math.random(), (double)yCoord + 0.5F, (double)zCoord + Math.random(), 0.0D, 0.0D, 0.0D);
            }
        }
        if(rand.nextInt(15) == 0){
            double d5 = (double)((float)this.xCoord + rand.nextFloat());
            double d7 = (double)this.yCoord;
            double d6 = (double)((float)this.zCoord + rand.nextFloat());
            //this.worldObj.spawnParticle("lava", d5, d7, d6, 0.0D, 0.0D, 0.0D);
            this.worldObj.playSound(d5, d7, d6, "liquid.lavapop", 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
        }
        if(this.hasMetal)
        {            
            if(melting > 0) this.melting--;
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
