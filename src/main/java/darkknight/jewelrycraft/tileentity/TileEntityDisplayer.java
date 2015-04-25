package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDisplayer extends TileEntity
{
    public float ringTranslation1, ringTranslation2, ringTranslation3, rotAngle;
    public boolean isDescending1, isDescending2, isDescending3, isDirty, hasObject;
    public ItemStack object;
    public int quantity, infoIndex, timer = 0;
    
    /**
     * 
     */
    public TileEntityDisplayer()
    {
        ringTranslation1 = 0.6f;
        ringTranslation2 = 0.3f;
        ringTranslation3 = 0.0f;
        rotAngle = 0;
        quantity = 0;
        infoIndex = 1;
        isDescending1 = false;
        isDescending2 = false;
        isDescending3 = false;
        isDirty = false;
        hasObject = false;
        object = new ItemStack(Item.getItemById(0), 0, 0);
    }
    
    /**
     * @param nbt
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setFloat("translation1", ringTranslation1);
        nbt.setFloat("translation2", ringTranslation2);
        nbt.setFloat("translation3", ringTranslation3);
        nbt.setFloat("angle", rotAngle);
        nbt.setInteger("quantity", quantity);
        nbt.setInteger("infoIndex", infoIndex);
        nbt.setBoolean("descending1", isDescending1);
        nbt.setBoolean("descending2", isDescending2);
        nbt.setBoolean("descending3", isDescending3);
        nbt.setBoolean("hasObject", hasObject);
        NBTTagCompound tag = new NBTTagCompound();
        object.writeToNBT(tag);
        nbt.setTag("object", tag);
    }
    
    /**
     * @param nbt
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        ringTranslation1 = nbt.getFloat("translation1");
        ringTranslation2 = nbt.getFloat("translation2");
        ringTranslation3 = nbt.getFloat("translation3");
        rotAngle = nbt.getFloat("angle");
        quantity = nbt.getInteger("quantity");
        infoIndex = nbt.getInteger("infoIndex");
        isDescending1 = nbt.getBoolean("descending1");
        isDescending2 = nbt.getBoolean("descending2");
        isDescending3 = nbt.getBoolean("descending3");
        hasObject = nbt.getBoolean("hasObject");
        object = new ItemStack(Item.getItemById(0), 0, 0);
        object.readFromNBT(nbt.getCompoundTag("object"));
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
        if (ringTranslation1 >= 0.6) isDescending1 = true;
        if (ringTranslation1 <= 0) isDescending1 = false;
        if (!isDescending1) ringTranslation1 += 0.03;
        if (isDescending1) ringTranslation1 -= 0.03;
        if (ringTranslation2 >= 0.6) isDescending2 = true;
        if (ringTranslation2 <= 0) isDescending2 = false;
        if (!isDescending2) ringTranslation2 += 0.02;
        if (isDescending2) ringTranslation2 -= 0.02;
        if (ringTranslation3 >= 0.6) isDescending3 = true;
        if (ringTranslation3 <= 0) isDescending3 = false;
        if (!isDescending3) ringTranslation3 += 0.01;
        if (isDescending3) ringTranslation3 -= 0.01;
        if (rotAngle < 360F) rotAngle += 3F;
        if (rotAngle >= 360F) rotAngle = 0F;
        timer++;
        if (timer >= 20){
            infoIndex++;
            timer = 0;
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
