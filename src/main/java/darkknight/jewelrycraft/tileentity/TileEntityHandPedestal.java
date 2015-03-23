package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Paul Fulham (pau101)
 */
public class TileEntityHandPedestal extends TileEntity
{
    protected boolean isDirty;
    protected ItemStack heldItemStack;
    /**
     * When the hand is open the grip is 0 and is 20 when closed.
     */
    private float grip;
    private float prevGrip;
    private float gripMax;
    private float gripScale;
    private boolean isHandOpen;
    
    /**
     * 
     */
    public TileEntityHandPedestal()
    {
        isDirty = false;
        heldItemStack = null;
        grip = 0;
        gripMax = 20;
        gripScale = 1;
        isHandOpen = true;
    }
    
    /**
     * @param tagCompound
     */
    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        if (heldItemStack != null){
            NBTTagCompound objectCompound = new NBTTagCompound();
            heldItemStack.writeToNBT(objectCompound);
            tagCompound.setTag("object", objectCompound);
        }
        tagCompound.setBoolean("isHandOpen", isHandOpen);
    }
    
    /**
     * @param tagCompound
     */
    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        if (tagCompound.hasKey("object", 10)) setHeldItemStack(ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("object")));
        else removeHeldItemStack();
        isHandOpen = tagCompound.getBoolean("isHandOpen");
    }
    
    /**
     * 
     */
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        updateGrip();
        if (isDirty){
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = false;
        }
    }
    
    /**
     * 
     */
    private void updateGrip()
    {
        prevGrip = grip;
        if (grip > 0 && isHandOpen) grip -= 1 / gripScale;
        else if (grip < gripMax && !isHandOpen) grip += 1 / gripScale;
    }
    
    /**
     * @return
     */
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
    
    /**
     * @param networkManager
     * @param packet
     */
    @Override
    public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
    
    /**
     * 
     */
    @Override
    public void markDirty()
    {
        super.markDirty();
        isDirty = true;
    }
    
    /**
     * @return
     */
    public ItemStack getHeldItemStack()
    {
        return heldItemStack;
    }
    
    /**
     * @param heldItemStack
     */
    public void setHeldItemStack(ItemStack heldItemStack)
    {
        heldItemStack.stackSize = 1;
        this.heldItemStack = heldItemStack;
        if (heldItemStack.getItem() instanceof ItemBlock) gripScale = 0.5f;
        else gripScale = 1;
    }
    
    /**
     * 
     */
    public void removeHeldItemStack()
    {
        heldItemStack = null;
    }
    
    /**
     * 
     */
    public void openHand()
    {
        isHandOpen = true;
    }
    
    /**
     * 
     */
    public void closeHand()
    {
        isHandOpen = false;
    }
    
    /**
     * @param t
     * @return
     */
    public float getGrip(float t)
    {
        return (prevGrip * (1 - t) + grip * t) / gripMax;
    }
    
    /**
     * @return
     */
    public float getGripScale()
    {
        return gripScale;
    }
}