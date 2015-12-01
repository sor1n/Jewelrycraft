/**
 * 
 */
package darkknight.jewelrycraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Sorin
 */
public class TileEntityMoltenMetal extends TileEntity {
	private ItemStack metal;

	public TileEntityMoltenMetal() {
		metal = null;
	}

	public boolean canUpdate() {
		return false;
	}

	public void setMetal(ItemStack metal) {
		if(metal != null) this.metal = metal.copy();
		else this.metal = null;
	}

	public ItemStack getMetal() {
		return metal;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (metal != null) {
			NBTTagCompound tag = new NBTTagCompound();
			metal.writeToNBT(tag);
			nbt.setTag("metal", tag);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("metal")) setMetal(ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("metal")));
		else metal = null;
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
		worldObj.func_147479_m(xCoord, yCoord, zCoord);
	}
}
