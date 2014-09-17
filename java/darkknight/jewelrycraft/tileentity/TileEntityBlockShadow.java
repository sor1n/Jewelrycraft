package darkknight.jewelrycraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;

public class TileEntityBlockShadow extends TileEntity
{
    public int metadata;
    
    public TileEntityBlockShadow()
    {
        this.metadata = -1;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("metadata", metadata);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.metadata = nbt.getInteger("metadata");
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        int blockLight, realLight;
        int lightValue = worldObj.getSavedLightValue(EnumSkyBlock.Sky, xCoord, yCoord, zCoord) - worldObj.skylightSubtracted;
        float sunPosAngle = worldObj.getCelestialAngleRadians(1.0F);
        
        if (sunPosAngle < (float) Math.PI) sunPosAngle += (0.0F - sunPosAngle) * 0.2F;
        else sunPosAngle += (((float) Math.PI * 2F) - sunPosAngle) * 0.2F;
        
        lightValue = Math.round((float) lightValue * MathHelper.cos(sunPosAngle));
        
        if (lightValue < 0) lightValue = 0;
        if (lightValue > 15) lightValue = 15;
        
        blockLight = worldObj.getChunkFromBlockCoords(xCoord, zCoord).getSavedLightValue(EnumSkyBlock.Block, xCoord & 15, yCoord, zCoord & 15);
        realLight = worldObj.getChunkFromBlockCoords(xCoord, zCoord).getBlockLightValue(xCoord & 15, yCoord, zCoord & 15, 0);
        
        if ((blockLight == 0 && worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)) || (lightValue >= blockLight)) metadata = 15 - lightValue;
        else if (!worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)) metadata = 15 - realLight;
        else if (lightValue < blockLight) metadata = 15 - blockLight;
        
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, metadata, 2);
        worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
    }
    
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
