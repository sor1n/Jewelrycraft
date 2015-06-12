package darkknight.jewelrycraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.tileentity.TileEntityBlockShadow;

public class BlockShadow extends BlockContainer
{
    private IIcon[] iconArray;
    
    /**
     * 
     */
    public BlockShadow()
    {
        super(Material.iron);
        setHarvestLevel("pickaxe", 3);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    /**
     * @return
     */
    @Override
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    /**
     * @param worldObj
     * @param x
     * @param y
     * @param z
     * @param beaconX
     * @param beaconY
     * @param beaconZ
     * @return
     */
    public boolean isBeaconBase(World worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ)
    {
        return true;
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
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @param side
     * @return
     */
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
    {
        return false;
    }
    
    /**
     * @param par0
     * @return
     */
    public static boolean isNormalCube(int par0)
    {
        return true;
    }
    
    /**
     * @param world
     * @param var2
     * @return
     */
    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityBlockShadow();
    }
    
    /**
     * @param par1IconRegister
     */
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        iconArray = new IIcon[16];
        for(int i = 0; i < iconArray.length; ++i)
            iconArray[i] = par1IconRegister.registerIcon(getTextureName() + (15 - i));
    }
    
    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) == 15) return null;
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }
    
    /**
     * @param par1IBlockAccess
     * @param par2
     * @param par3
     * @param par4
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    /**
     * @param p_149646_1_
     * @param p_149646_2_
     * @param p_149646_3_
     * @param p_149646_4_
     * @param p_149646_5_
     * @return
     */
    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);
        if (this == BlockList.shadowBlock) if (block == this) return false;
        return block == this ? false : super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
    
    /**
     * @return
     */
    @Override
    public boolean hasComparatorInputOverride()
    {
        return true;
    }
    
    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @param meta
     * @return
     */
    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta)
    {
        return world.getBlockMetadata(x, y, z);
    }
    
    /**
     * @param side
     * @param meta
     * @return
     */
    @Override
    @SideOnly (Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return (meta < 16) ? iconArray[meta] : iconArray[0];
    }
}
