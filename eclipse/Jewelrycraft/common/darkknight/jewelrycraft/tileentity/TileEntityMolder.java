package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;

public class TileEntityMolder extends TileEntity
{
    public int cooling;
    public boolean hasMoltenMetal, hasJewelBase, hasMold, isDirty;
    public ItemStack mold, jewelBase, moltenMetal, ringMetal;
    
    public TileEntityMolder()
    {
        this.moltenMetal = new ItemStack(Item.getItemById(0), 0, 0);
        this.jewelBase = new ItemStack(Item.getItemById(0), 0, 0);
        this.mold = new ItemStack(Item.getItemById(0), 0, 0);
        this.ringMetal = new ItemStack(Item.getItemById(0), 0, 0);
        this.cooling = 0;
        this.hasJewelBase = false;
        this.hasMoltenMetal = false;
        this.hasMold = false;
        this.isDirty = false;
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
        nbt.setTag("mold", tag);
        this.jewelBase.writeToNBT(tag1);
        nbt.setTag("jewelBase", tag1);
        this.moltenMetal.writeToNBT(tag2);
        nbt.setTag("moltenMetal", tag2);
        this.ringMetal.writeToNBT(tag3);
        nbt.setTag("ringMetal", tag3);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.cooling = nbt.getInteger("cooling");
        this.hasJewelBase = nbt.getBoolean("hasJewelBase");
        this.hasMoltenMetal = nbt.getBoolean("hasMoltenMetal");
        this.hasMold = nbt.getBoolean("hasMold");
        this.mold = new ItemStack(Item.getItemById(0), 0, 0);
        this.mold.readFromNBT(nbt.getCompoundTag("mold"));
        this.jewelBase = new ItemStack(Item.getItemById(0), 0, 0);
        this.jewelBase.readFromNBT(nbt.getCompoundTag("jewelBase"));
        this.moltenMetal = new ItemStack(Item.getItemById(0), 0, 0);
        this.moltenMetal.readFromNBT(nbt.getCompoundTag("moltenMetal"));
        this.ringMetal = new ItemStack(Item.getItemById(0), 0, 0);
        this.ringMetal.readFromNBT(nbt.getCompoundTag("ringMetal"));
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if(isDirty){
        	this.markDirty();
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = false;
        }
        if (moltenMetal.getItem() != Item.getItemById(0))
        {
            if(worldObj.rand.nextInt(20) == 0) this.worldObj.playSoundEffect(xCoord, yCoord + 0.5F, zCoord, "random.fizz", 0.5F, 1F);
            for (int l = 0; l < 2; ++l)
                this.worldObj.spawnParticle("reddust", xCoord + Math.random(), (double) yCoord + 0.2F, zCoord + Math.random(), 0.0D, 1.0D, 1.0D);
        }
        if (this.hasMoltenMetal && !this.hasJewelBase)
        {
            ringMetal = moltenMetal;
            if (cooling > 0)
                this.cooling--;
            if (cooling == 0)
            {
                this.hasMoltenMetal = false;
                if (mold.getItemDamage() == 0)
                    this.jewelBase = moltenMetal;
                else if (mold.getItemDamage() == 1)
                    this.jewelBase = new ItemStack(ItemList.ring);
                else
                    this.jewelBase = new ItemStack(ItemList.necklace);
                if(mold.getItemDamage() != 0 && jewelBase != new ItemStack(Item.getItemById(0), 0, 0))
                    JewelryNBT.addMetal(jewelBase, ringMetal);
                this.moltenMetal = new ItemStack(Item.getItemById(0), 0, 0);
                this.hasJewelBase = true;
                cooling = -1;
                this.isDirty = true;
                this.markDirty();
            }
        }
    }
    
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
    }
    
    @Override
    public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
