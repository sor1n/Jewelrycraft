package bspkrs.briefcasespeakers.block;

import bspkrs.briefcasespeakers.tileentity.TileEntityMolder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMolder extends BlockContainer
{
    protected BlockMolder(int par1, Material par2Material)
    {
        super(par1, par2Material);
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
}
