package darkknight.jewelrycraft.tileentity;

import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.JewelryNBT;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMolder extends TileEntity
{
    public int cooling;
    public boolean hasMoltenMetal, hasJewelBase, hasMold, isDirty;
    public ItemStack mold, jewelBase, moltenMetal, ringMetal;
    public float quantity;
    
    /**
     * 
     */
    public TileEntityMolder()
    {
        moltenMetal = new ItemStack(Item.getItemById(0), 0, 0);
        jewelBase = new ItemStack(Item.getItemById(0), 0, 0);
        mold = new ItemStack(Item.getItemById(0), 0, 0);
        ringMetal = new ItemStack(Item.getItemById(0), 0, 0);
        cooling = -1;
        quantity = 0f;
        hasJewelBase = false;
        hasMoltenMetal = false;
        hasMold = false;
        isDirty = false;
    }
    
    /**
     * @param nbt
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("cooling", cooling);
        nbt.setFloat("quantity", quantity);
        nbt.setBoolean("hasJewelBase", hasJewelBase);
        nbt.setBoolean("hasMoltenMetal", hasMoltenMetal);
        nbt.setBoolean("hasMold", hasMold);
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound tag1 = new NBTTagCompound();
        NBTTagCompound tag2 = new NBTTagCompound();
        NBTTagCompound tag3 = new NBTTagCompound();
        mold.writeToNBT(tag);
        nbt.setTag("mold", tag);
        jewelBase.writeToNBT(tag1);
        nbt.setTag("jewelBase", tag1);
        moltenMetal.writeToNBT(tag2);
        nbt.setTag("moltenMetal", tag2);
        ringMetal.writeToNBT(tag3);
        nbt.setTag("ringMetal", tag3);
    }
    
    /**
     * @param nbt
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        cooling = nbt.getInteger("cooling");
        quantity = nbt.getFloat("quantity");
        hasJewelBase = nbt.getBoolean("hasJewelBase");
        hasMoltenMetal = nbt.getBoolean("hasMoltenMetal");
        hasMold = nbt.getBoolean("hasMold");
        mold = new ItemStack(Item.getItemById(0), 0, 0);
        mold.readFromNBT(nbt.getCompoundTag("mold"));
        jewelBase = new ItemStack(Item.getItemById(0), 0, 0);
        jewelBase.readFromNBT(nbt.getCompoundTag("jewelBase"));
        moltenMetal = new ItemStack(Item.getItemById(0), 0, 0);
        moltenMetal.readFromNBT(nbt.getCompoundTag("moltenMetal"));
        ringMetal = new ItemStack(Item.getItemById(0), 0, 0);
        ringMetal.readFromNBT(nbt.getCompoundTag("ringMetal"));
    }
    
    /**
     * 
     */
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (isDirty){
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = false;
        }
        if (hasMoltenMetal && moltenMetal.getItem() != Item.getItemById(0) && quantity > 0f){
            if (worldObj.rand.nextInt(20) == 0) worldObj.playSoundEffect(xCoord, yCoord + 0.5F, zCoord, "random.fizz", 0.5F, 1F);
            for(int l = 0; l < 2; ++l)
                worldObj.spawnParticle("reddust", xCoord + Math.random(), (double)yCoord + 0.2F, zCoord + Math.random(), 0.0D, 1.0D, 1.0D);
        }
        if (hasMoltenMetal && !hasJewelBase && quantity >= 0.1f){
            ringMetal = moltenMetal.copy();
            if (cooling > 0) cooling--;
            if (cooling <= 0f){
                if (mold.getItemDamage() == 0) jewelBase = moltenMetal;
                else if (mold.getItemDamage() == 1) jewelBase = new ItemStack(ItemList.ring);
                else if (mold.getItemDamage() == 2) jewelBase = new ItemStack(ItemList.necklace);
                else if (mold.getItemDamage() == 3) jewelBase = new ItemStack(ItemList.bracelet);
                else if (mold.getItemDamage() == 4) jewelBase = new ItemStack(ItemList.earrings);
                ringMetal.stackSize = 1;
                jewelBase.stackSize = 1;
                if (mold.getItemDamage() != 0 && jewelBase != new ItemStack(Item.getItemById(0), 0, 0)) JewelryNBT.addMetal(jewelBase, ringMetal);
                hasMoltenMetal = false;
                moltenMetal = new ItemStack(Item.getItemById(0), 0, 0);
                hasJewelBase = true;
                cooling = -1;
                quantity = 0f;
                isDirty = true;
            }
        }
    }
    
    /**
     * @return
     */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }
    
    /**
     * @param net
     * @param packet
     */
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
