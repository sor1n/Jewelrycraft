package bspkrs.briefcasespeakers.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import bspkrs.briefcasespeakers.tileentity.TileEntityBriefcaseSpeakers;

public class BlockBriefcaseSpeakers extends BlockContainer
{
    protected BlockBriefcaseSpeakers(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityBriefcaseSpeakers();
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
