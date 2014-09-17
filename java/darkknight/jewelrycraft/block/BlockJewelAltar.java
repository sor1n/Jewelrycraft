package darkknight.jewelrycraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.tileentity.TileEntityAltar;

public class BlockJewelAltar extends BlockContainer
{
    @SideOnly(Side.CLIENT)
    private IIcon altarSide;
    @SideOnly(Side.CLIENT)
    private IIcon altarBottom;
    @SideOnly(Side.CLIENT)
    private IIcon altarTop;
    
    public BlockJewelAltar()
    {
        super(Material.iron);
    }
    
    public static boolean isNormalCube(int par0)
    {
        return true;
    }
    
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.altarSide = par1IconRegister.registerIcon(this.getTextureName() + "_" + "side");
        this.altarBottom = par1IconRegister.registerIcon(this.getTextureName() + "_" + "bottom");
        this.altarTop = par1IconRegister.registerIcon(this.getTextureName() + "_" + "top");
    }
    
    public IIcon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.altarTop : (par1 == 0 ? this.altarBottom : this.altarSide);
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntityAltar te = (TileEntityAltar) world.getTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if (te != null && !world.isRemote)
        {
            if (item != null && item != new ItemStack(Item.getItemById(0), 0, 0) && (item.getItem() == ItemList.ring || item.getItem() == ItemList.necklace) && !te.hasObject)
            {
                te.object = item.copy();
                item.stackSize = 0;
                te.playerName = entityPlayer.getDisplayName();
                te.isDirty = true;
                te.markDirty();
                te.hasObject = true;
            }
            
            if (te.object != null && te.object != new ItemStack(Item.getItemById(0), 0, 0) && te.hasObject && entityPlayer.isSneaking())
            {
                entityPlayer.inventory.addItemStackToInventory(te.object);
                te.object = new ItemStack(Item.getItemById(0), 0, 0);
                te.playerName = "";
                te.isDirty = true;
                te.markDirty();
                te.hasObject = false;
            }
        }
        return true;
    }
    
    @Override
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player)
    {
        TileEntityAltar te = (TileEntityAltar) world.getTileEntity(i, j, k);
        if (te != null && !world.isRemote)
        {
            if (te.object != null && te.object != new ItemStack(Item.getItemById(0), 0, 0))
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
    
    public void breakBlock(World world, int i, int j, int k, Block block, int par6)
    {
        TileEntityAltar te = (TileEntityAltar) world.getTileEntity(i, j, k);
        
        if (te != null && te.object != null && te.object != new ItemStack(Item.getItemById(0), 0, 0))
        {
            dropItem(te.getWorldObj(), (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.object);
            world.removeTileEntity(i, j, k);
        }
        
        super.breakBlock(world, i, j, k, block, par6);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityAltar();
    }
}
