package darkknight.jewelrycraft.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockUtils
{
    public static final ForgeDirection DEFAULT_BLOCK_DIRECTION = ForgeDirection.WEST;
    
    /**
     * This method is used to get the direction an entity is facing (NORTH, SOUTH, EAST or WEST) based on the entity's rotationYaw.
     * 
     * @param entity the living entity
     * @return a direction
     */
    public static ForgeDirection get2dOrientation(EntityLivingBase entity)
    {
        int l = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
        switch(l)
        {
            case 0:
                return ForgeDirection.SOUTH;
            case 1:
                return ForgeDirection.WEST;
            case 2:
                return ForgeDirection.NORTH;
            case 3:
                return ForgeDirection.EAST;
        }
        return ForgeDirection.SOUTH;
    }
    
    /**
     * This gets a float value depending on a direction
     * 
     * @param direction the forge direction
     * @return value depending on direction
     */
    public static float getRotationFromDirection(ForgeDirection direction)
    {
        switch(direction)
        {
            case NORTH:
                return 0F;
            case SOUTH:
                return 180F;
            case WEST:
                return 90F;
            case EAST:
                return -90F;
            case DOWN:
                return -90f;
            case UP:
                return 90f;
            default:
                return 0f;
        }
    }
    
    /**
     * This method is used to get the direction an entity is looking at (UP or DOWN) based on the entitiy's rotationPitch
     * 
     * @param entity the living entity
     * @return a forge direction 
     */
    public static ForgeDirection get3dOrientation(EntityLivingBase entity)
    {
        if (entity.rotationPitch > 45.5F) return ForgeDirection.DOWN;
        else if (entity.rotationPitch < -45.5F) return ForgeDirection.UP;
        return get2dOrientation(entity);
    }
    
    /**
     * This spawns the item specified and returns the EntityItem it created
     * 
     * @param worldObj the world
     * @param x position of the item to drop on the X axis
     * @param y position of the item to drop on the Y axis
     * @param z position of the item to drop on the Z axis
     * @param stack the item to spawn
     * @return the EntityItem of the stack
     */
    public static EntityItem dropItemStackInWorld(World worldObj, double x, double y, double z, ItemStack stack)
    {
        float f = 0.7F;
        float d0 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d1 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        float d2 = worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5F;
        EntityItem entityitem = new EntityItem(worldObj, x + d0, y + d1, z + d2, stack);
        entityitem.delayBeforeCanPickup = 10;
        if (stack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
        worldObj.spawnEntityInWorld(entityitem);
        return entityitem;
    }
    
    /**
     * It spawns the item with momentum in a certain direction
     * 
     * @param world the world to spawn the item
     * @param x the X coordinate to spawn it in
     * @param y the Y coordinate to spawn it in
     * @param z the Z coordinate to spawn it in
     * @param direction the direction towards which it should eject
     * @param stack the item to spawn
     * @return the spawned EntityItem
     */
    public static EntityItem ejectItemInDirection(World world, double x, double y, double z, ForgeDirection direction, ItemStack stack)
    {
        EntityItem item = BlockUtils.dropItemStackInWorld(world, x, y, z, stack);
        item.motionX = direction.offsetX / 5F;
        item.motionY = direction.offsetY / 5F;
        item.motionZ = direction.offsetZ / 5F;
        return item;
    }
    
    /**
     * Drops the content of an inventory with doubles as coordinates
     * 
     * @param inventory the inventory the items are contained in
     * @param world the world in which to spawn
     * @param x the X coordinate to spawn it in
     * @param y the Y coordinate to spawn it in
     * @param z the Z coordinate to spawn it in
     */
    public static void dropInventory(IInventory inventory, World world, double x, double y, double z)
    {
        if (inventory == null) return;
        for(int i = 0; i < inventory.getSizeInventory(); ++i){
            ItemStack itemStack = inventory.getStackInSlot(i);
            if (itemStack != null) dropItemStackInWorld(world, x, y, z, itemStack);
        }
    }
    
    /**
     * Drops the content of an inventory with integer as coordinates
     * 
     * @param inventory the inventory the items are contained in
     * @param world the world in which to spawn
     * @param x the X coordinate to spawn it in
     * @param y the Y coordinate to spawn it in
     * @param z the Z coordinate to spawn it in
     */
    public static void dropInventory(IInventory inventory, World world, int x, int y, int z)
    {
        dropInventory(inventory, world, x + 0.5, y + 0.5, z + 0.5);
    }
}