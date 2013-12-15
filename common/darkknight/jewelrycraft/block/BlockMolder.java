package darkknight.jewelrycraft.block;

import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMolder extends BlockContainer
{
    protected BlockMolder(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.setBlockBounds(0.1F, 0F, 0.1F, 0.9F, 0.2F, 0.9F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityMolder();
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntityMolder te = (TileEntityMolder) world.getBlockTileEntity(i, j, k);
        return true;
    }
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }
    
    public void registerIcons(IconRegister icon) 
    {
        this.blockIcon = icon.registerIcon("jewelrycraft:molder");
    }
}
