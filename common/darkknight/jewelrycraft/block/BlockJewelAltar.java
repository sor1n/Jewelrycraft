package darkknight.jewelrycraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.tileentity.*;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockJewelAltar extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private Icon altarSide;
    @SideOnly(Side.CLIENT)
    private Icon altarBottom;
    @SideOnly(Side.CLIENT)
    private Icon altarTop;
    
    public BlockJewelAltar(int par1)
    {
        super(par1, Material.iron);
    }
    
    public static boolean isNormalCube(int par0)
    {
        return true;
    }
    
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.altarSide = par1IconRegister.registerIcon(this.getTextureName() + "_" + "side");
        this.altarBottom = par1IconRegister.registerIcon(this.getTextureName() + "_" + "bottom");
        this.altarTop = par1IconRegister.registerIcon(this.getTextureName() + "_" + "top");
    }
    
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.altarTop : (par1 == 0 ? this.altarBottom : this.altarSide);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntityAltar te = (TileEntityAltar) world.getBlockTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if (te != null && !world.isRemote)
        {            
            if(item != null && item != new ItemStack(0, 0, 0) && (item.itemID == ItemList.ring.itemID || item.itemID == ItemList.necklace.itemID) && !te.hasObject)
            {   
                te.object = item.copy();
                item.stackSize = 0;
                te.playerName = entityPlayer.username;
                te.isDirty = true;
                te.hasObject = true;
            }
            
            if(te.object != null && te.object != new ItemStack(0, 0, 0) && te.hasObject && entityPlayer.isSneaking())
            {
                entityPlayer.inventory.addItemStackToInventory(te.object);
                te.object = new ItemStack(0, 0, 0);
                te.playerName = "";
                te.isDirty = true;
                te.hasObject = false;
            }
        }
        return true;
    }

    @Override
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player)
    {
        TileEntityAltar te = (TileEntityAltar) world.getBlockTileEntity(i, j, k);
        if (te != null && !world.isRemote)
        {
            if (te.object != null && te.object != new ItemStack(0, 0, 0))
            {
            }
        }
    }

    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1.5D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.11000000298023224D;
        world.spawnEntityInWorld(entityitem);
    }

    public void breakBlock(World world, int i, int j, int k, int par5, int par6)
    {
        TileEntityAltar te = (TileEntityAltar) world.getBlockTileEntity(i, j, k);

        if (te != null && te.object != null && te.object != new ItemStack(0, 0, 0))
        {
            dropItem(te.worldObj, (double)te.xCoord, (double)te.yCoord, (double)te.zCoord, te.object);
            world.markTileEntityForDespawn(te);
        }

        super.breakBlock(world, i, j, k, par5, par6);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityAltar();
    }
}
