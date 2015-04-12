package darkknight.jewelrycraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.util.Variables;

public class BlockDisplayer extends BlockContainer
{
    Random rand = new Random();
    
    /**
     * @param par2Material
     */
    protected BlockDisplayer(Material par2Material)
    {
        super(par2Material);
    }
    
    /**
     * @param world
     * @param var2
     * @return
     */
    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityDisplayer();
    }
    
    /**
     * @return
     */
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /**
     * @param iblockaccess
     * @param i
     * @param j
     * @param k
     * @param l
     * @return
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public int getRenderType()
    {
        return -1;
    }
    
    /**
     * @param world
     * @param i
     * @param j
     * @param k
     * @param entityPlayer
     * @param par6
     * @param par7
     * @param par8
     * @param par9
     * @return
     */
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntityDisplayer te = (TileEntityDisplayer)world.getTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if (te != null){
            if (item != null && item != new ItemStack(Item.getItemById(0), 0, 0) && !world.isRemote){
                if (!te.hasObject){
                    te.object = item.copy();
                    te.quantity += item.stackSize;
                    te.object.stackSize = 1;
                    te.hasObject = true;
                    if (!entityPlayer.capabilities.isCreativeMode) entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, item.stackSize);
                    te.isDirty = true;
                }else if (te.object.getItem() == item.getItem() && te.object != null && te.object != new ItemStack(Item.getItemById(0), 0, 0) && te.object.getItemDamage() == item.getItemDamage()) if (te.object.hasTagCompound() && item.hasTagCompound() && te.object.getTagCompound().equals(item.getTagCompound())){
                    te.quantity += item.stackSize;
                    if (!entityPlayer.capabilities.isCreativeMode) entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, item.stackSize);
                    te.isDirty = true;
                }else if (!te.object.hasTagCompound() && !item.hasTagCompound()){
                    te.quantity += item.stackSize;
                    if (!entityPlayer.capabilities.isCreativeMode) entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, item.stackSize);
                    te.isDirty = true;
                }
            }else if (item == null || item == new ItemStack(Item.getItemById(0), 0, 0)) if (!entityPlayer.capabilities.isCreativeMode) for(int inv = 0; inv < entityPlayer.inventory.getSizeInventory(); inv++){
                item = entityPlayer.inventory.getStackInSlot(inv);
                if (item != null && te.object.getItem() == item.getItem() && te.object != null && te.object != new ItemStack(Item.getItemById(0), 0, 0) && te.object.getItemDamage() == item.getItemDamage()) if (te.object.hasTagCompound() && item.hasTagCompound() && te.object.getTagCompound().equals(item.getTagCompound())){
                    te.quantity += item.stackSize;
                    if (!entityPlayer.capabilities.isCreativeMode) entityPlayer.inventory.decrStackSize(inv, item.stackSize);
                    te.isDirty = true;
                    te.markDirty();
                }else if (!te.object.hasTagCompound() && !item.hasTagCompound()){
                    te.quantity += item.stackSize;
                    if (!entityPlayer.capabilities.isCreativeMode) entityPlayer.inventory.decrStackSize(inv, item.stackSize);
                    te.isDirty = true;
                }
            }
            else if (entityPlayer.capabilities.isCreativeMode && te.hasObject && te.object.getItem() != null){
                te.quantity += 64;
                te.isDirty = true;
            }
            te.isDirty = true;
        }
        return true;
    }
    
    /**
     * @param world
     * @param i
     * @param j
     * @param k
     * @param player
     */
    @Override
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player)
    {
        TileEntityDisplayer te = (TileEntityDisplayer)world.getTileEntity(i, j, k);
        if (te != null && !world.isRemote) if (te.hasObject && te.object != null && te.object != new ItemStack(Item.getItemById(0), 0, 0) && player.inventory.addItemStackToInventory(te.object)) if (!player.isSneaking()){
            if (te.quantity > te.object.getMaxStackSize()){
                te.object.stackSize = te.object.getMaxStackSize() - 1;
                player.inventory.addItemStackToInventory(te.object);
                te.object.stackSize = 1;
                te.quantity -= te.object.getMaxStackSize();
                te.isDirty = true;
            }else{
                te.object.stackSize = te.quantity - 1;
                player.inventory.addItemStackToInventory(te.object);
                te.hasObject = false;
                te.object = new ItemStack(Item.getItemById(0), 0, 0);
                te.quantity = 0;
                te.isDirty = true;
            }
            te.isDirty = true;
        }else{
            if (te.quantity >= 2){
                player.inventory.addItemStackToInventory(te.object);
                te.object.stackSize = 1;
                te.quantity--;
                te.isDirty = true;
            }else{
                player.inventory.addItemStackToInventory(te.object);
                te.object.stackSize = 1;
                te.hasObject = false;
                te.object = new ItemStack(Item.getItemById(0), 0, 0);
                te.quantity = 0;
                te.isDirty = true;
            }
            te.isDirty = true;
        }
    }
    
    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @param stack
     */
    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1.5D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.11000000298023224D;
        world.spawnEntityInWorld(entityitem);
    }
    
    /**
     * @param world
     * @param i
     * @param j
     * @param k
     * @param block
     * @param par6
     */
    @Override
    public void breakBlock(World world, int i, int j, int k, Block block, int par6)
    {
        TileEntityDisplayer te = (TileEntityDisplayer)world.getTileEntity(i, j, k);
        if (te != null && te.hasObject){
            te.object.stackSize = te.quantity;
            dropItem(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, te.object);
            world.removeTileEntity(i, j, k);
        }
        super.breakBlock(world, i, j, k, block, par6);
    }
    
    /**
     * @param world
     * @param i
     * @param j
     * @param k
     * @param entityLiving
     * @param par6ItemStack
     */
    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack par6ItemStack)
    {
        int rotation = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
    }
    
    /**
     * @param icon
     */
    @Override
    public void registerBlockIcons(IIconRegister icon)
    {
        blockIcon = icon.registerIcon(Variables.MODID + ":displayer");
    }
}
