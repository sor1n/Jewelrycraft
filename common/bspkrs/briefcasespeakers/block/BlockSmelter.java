package bspkrs.briefcasespeakers.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import bspkrs.briefcasespeakers.tileentity.TileEntitySmelter;

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
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getBlockTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if (te != null && !world.isRemote)
        {
            if (!te.hasMetal && !te.hasMoltenMetal && item != null && item.getDisplayName().contains("Ingot"))
            {
                te.metalID = item.getItem().itemID;
                te.hasMetal = true;
                te.melting = 200000;
                --item.stackSize;
            }
            else if (te.hasMetal && !te.hasMoltenMetal && item != null && item.getDisplayName().contains("Ingot"))
                entityPlayer.addChatMessage("The Smelter already contains a " + new ItemStack(te.metalID, 1, 0).getDisplayName());
            else if (te.hasMoltenMetal && item != null && item.getDisplayName().contains("Ingot"))
                entityPlayer.addChatMessage("The Smelter contains molten " + new ItemStack(te.moltenMetalID, 1, 0).getDisplayName().toLowerCase().replace("ingot", ""));
            else if (item != null && !item.getDisplayName().contains("Ingot"))
                entityPlayer.addChatMessage("The item needs to be an ingot!");
            
            if (te.hasMetal && entityPlayer.isSneaking())
            {
                entityPlayer.dropPlayerItem(new ItemStack(te.metalID, 1, 0));
                te.hasMetal = false;
            }
            world.setBlockTileEntity(i, j, k, te);
        }
        return true;
    }
}
