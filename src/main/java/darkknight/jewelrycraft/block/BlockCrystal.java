/**
 * 
 */
package darkknight.jewelrycraft.block;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.proxy.ClientProxy.BlockRenderIDs;
import darkknight.jewelrycraft.tileentity.TileEntityCrystal;
import darkknight.jewelrycraft.tileentity.TileEntityShadowEye;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author Sorin
 *
 */
public class BlockCrystal extends Block implements ITileEntityProvider
{
    public static int[] colors = {1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};
    
    protected BlockCrystal()
    {
        super(Material.glass);
        setBlockBounds(0.2F, 0F, 0.2F, 0.8F, 1.0F, 0.8F);
        setHarvestLevel("pickaxe", 0);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityCrystal();
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }
    
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for(int i = 0; i < 16; ++i) list.add(new ItemStack(item, 1, i));
    }
    
    @Override
    public int getRenderType()
    {
        return BlockRenderIDs.CRYSTAL.id();
    }
    
    @Override
    @SideOnly (Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int i, int j, int k)
    {
        return colors[world.getBlockMetadata(i, j, k)];
    }
    
    public int damageDropped(int meta)
    {
        return meta;
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
        super.breakBlock(world, x, y, z, block, meta);
        world.removeTileEntity(x, y, z);
    }

    public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNo, int arg)
    {
        super.onBlockEventReceived(world, x, y, z, eventNo, arg);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null ? tileentity.receiveClientEvent(eventNo, arg) : false;
    }
}
