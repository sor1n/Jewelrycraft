package darkknight.jewelrycraft.tileentity;

import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityJewelrsCraftingTable extends TileEntity
{
    public boolean hasJewelry, hasEndItem, isDirty, hasGem, crafting;
    public ItemStack jewelry, endItem, gem;
    public int carving, effect;
    public float angle;
    
    /**
     * 
     */
    public TileEntityJewelrsCraftingTable()
    {
        jewelry = new ItemStack(Item.getItemById(0), 0, 0);
        endItem = new ItemStack(Item.getItemById(0), 0, 0);
        gem = new ItemStack(Item.getItemById(0), 0, 0);
        hasJewelry = false;
        hasEndItem = false;
        hasGem = false;
        crafting = false;
        carving = 0;
        effect = 0;
        angle = 0;
        isDirty = false;
    }
    
    /**
     * @param nbt
     */
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("hasJewelry", hasJewelry);
        nbt.setBoolean("hasEndItem", hasEndItem);
        nbt.setBoolean("hasJewel", hasGem);
        nbt.setBoolean("crafting", crafting);
        nbt.setInteger("timer", carving);
        nbt.setInteger("effect", effect);
        nbt.setFloat("angle", angle);
        NBTTagCompound tag1 = new NBTTagCompound();
        NBTTagCompound tag2 = new NBTTagCompound();
        NBTTagCompound tag3 = new NBTTagCompound();
        jewelry.writeToNBT(tag1);
        nbt.setTag("jewelry", tag1);
        endItem.writeToNBT(tag2);
        nbt.setTag("endItem", tag2);
        gem.writeToNBT(tag3);
        nbt.setTag("jewel", tag3);
    }
    
    /**
     * @param nbt
     */
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        hasJewelry = nbt.getBoolean("hasJewelry");
        hasEndItem = nbt.getBoolean("hasEndItem");
        hasGem = nbt.getBoolean("hasJewel");
        crafting = nbt.getBoolean("crafting");
        carving = nbt.getInteger("timer");
        effect = nbt.getInteger("effect");
        angle = nbt.getFloat("angle");
        jewelry = new ItemStack(Item.getItemById(0), 0, 0);
        jewelry.readFromNBT(nbt.getCompoundTag("jewelry"));
        endItem = new ItemStack(Item.getItemById(0), 0, 0);
        endItem.readFromNBT(nbt.getCompoundTag("endItem"));
        gem = new ItemStack(Item.getItemById(0), 0, 0);
        gem.readFromNBT(nbt.getCompoundTag("jewel"));
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
        if (angle < 360F) angle += 3F;
        else angle = 0F;
        if (hasJewelry && hasGem && !hasEndItem && crafting){
            if (carving > 0) carving--;
            if (crafting) for(int l = 0; l < ConfigHandler.GEM_PLACEMENT_TIME / (carving + 2); ++l){
                if (worldObj.rand.nextInt(10) == 0) worldObj.playSoundEffect(xCoord, yCoord + 0.5F, zCoord, "random.orb", 0.05F, 1F);
                if (getBlockMetadata() == 0) worldObj.spawnParticle("instantSpell", xCoord + 0.5F, (double)yCoord + 0.8F, zCoord + 0.2F, 0.0D, 0.0D, 0.0D);
                if (getBlockMetadata() == 1) worldObj.spawnParticle("instantSpell", xCoord + 0.8F, (double)yCoord + 0.8F, zCoord + 0.5F, 0.0D, 0.0D, 0.0D);
                if (getBlockMetadata() == 2) worldObj.spawnParticle("instantSpell", xCoord + 0.5F, (double)yCoord + 0.8F, zCoord + 0.8F, 0.0D, 0.0D, 0.0D);
                if (getBlockMetadata() == 3) worldObj.spawnParticle("instantSpell", xCoord + 0.2F, (double)yCoord + 0.8F, zCoord + 0.5F, 0.0D, 0.0D, 0.0D);
            }
            if (carving == 0){
                hasEndItem = true;
                endItem = jewelry.copy();
                if (hasGem && gem != new ItemStack(Item.getItemById(0), 0, 0)) if (!JewelryNBT.hasTag(jewelry, "gem")){
                    JewelryNBT.addGem(endItem, gem);
                    hasGem = false;
                    gem = new ItemStack(Item.getItemById(0), 0, 0);
                }else{
                    ItemStack aux = JewelryNBT.gem(jewelry);
                    JewelryNBT.addGem(endItem, gem);
                    if (JewelrycraftUtil.rand.nextBoolean()) gem = aux.copy();
                    else{
                        hasGem = false;
                        gem = new ItemStack(Item.getItemById(0), 0, 0);
                    }
                }
                hasJewelry = false;
                jewelry = new ItemStack(Item.getItemById(0), 0, 0);
                carving = -1;
                crafting = false;
                isDirty = true;
            }
        }
    }
    
	public void setGemItemStack(ItemStack itemStack) 
	{
		if (itemStack != null && itemStack.getItem() != null) {
			this.gem = itemStack.copy();
			this.gem.stackSize = 1;
			this.hasGem = true;
			this.isDirty = true;
		}
	}
    
	public void setJewelryItemStack(ItemStack itemStack) 
	{
		if (itemStack != null && itemStack.getItem() != null) {
			this.jewelry = itemStack.copy();
			this.jewelry.stackSize = 1;
			this.hasJewelry = true;
			this.isDirty = true;
		}
	}
    
	public void setCrafting() 
	{
		carving = ConfigHandler.GEM_PLACEMENT_TIME;
		angle = 0;
		crafting = true;
		isDirty = true;
	}
    
	public void removeGem() 
	{
        dropItem(worldObj, xCoord, yCoord, zCoord, gem.copy());
        gem = new ItemStack(Item.getItemById(0), 0, 0);
        hasGem = false;
        carving = -1;
        crafting = false;
        angle = 0F;
        isDirty = true;
	}
    
	public void removeJewelry() 
	{
        dropItem(worldObj, xCoord, yCoord, zCoord, jewelry.copy());
        jewelry = new ItemStack(Item.getItemById(0), 0, 0);
        hasJewelry = false;
        carving = -1;
        crafting = false;
        angle = 0F;
        isDirty = true;
	}
    
	public void removeResult() 
	{
        dropItem(worldObj, xCoord, yCoord, zCoord, endItem.copy());
        endItem = new ItemStack(Item.getItemById(0), 0, 0);
        hasEndItem = false;
        isDirty = true;
	}
    
    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.21000000298023224D;
        world.spawnEntityInWorld(entityitem);
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
