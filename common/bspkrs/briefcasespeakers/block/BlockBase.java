package bspkrs.briefcasespeakers.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block
{
    public BlockBase(int par1, Material mat)
    {
        super(par1, mat);
    }
    
    @Override
    public Block setUnlocalizedName(String name)
    {
        Block r = super.setUnlocalizedName(name);
        return r.setTextureName(name.replaceAll("\\.", ":"));
    }
}
