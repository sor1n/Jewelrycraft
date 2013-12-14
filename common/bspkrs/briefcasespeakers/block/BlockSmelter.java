package bspkrs.briefcasespeakers.block;

import bspkrs.briefcasespeakers.tileentity.TileEntitySmelter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSmelter extends BlockContainer
{
    protected BlockSmelter(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntitySmelter();
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntitySmelter te = (TileEntitySmelter)world.getBlockTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if(te != null)
        {
            if(!te.hasMetal)
            {
                te.metalID = item.getItem().itemID;
                te.hasMetal = true;
            }
            if(te.hasMetal && entityPlayer.isSneaking()){ entityPlayer.dropItem(te.metalID, 1); te.hasMetal = false;}            
            world.setBlockTileEntity(i, j, k, te);
        }
        return true;
    }
}
