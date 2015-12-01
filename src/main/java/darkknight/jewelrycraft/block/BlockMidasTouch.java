package darkknight.jewelrycraft.block;

import java.util.Random;
import darkknight.jewelrycraft.tileentity.TileEntityMidasTouch;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMidasTouch extends BlockContainer
{
    protected BlockMidasTouch(Material mat)
    {
        super(mat);
        setHarvestLevel("pickaxe", 2);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityMidasTouch();
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return -1;
    }
    
    public int quantityDropped(Random rand)
    {
        return 0;
    }
    
    public Item getItemDropped(int id, Random rand, int size)
    {
        return Items.gold_nugget;
    }
    
    public void onBlockHarvested(World world, int i, int j, int k, int meta, EntityPlayer player) 
    {
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (((TileEntityMidasTouch)tile).target != null) this.setBlockBounds(0.5f - ((TileEntityMidasTouch)tile).target.width / 2, 0F, 0.5f - ((TileEntityMidasTouch)tile).target.width / 2, 0.5f + ((TileEntityMidasTouch)tile).target.width / 2, ((TileEntityMidasTouch)tile).target.height, 0.5f + ((TileEntityMidasTouch)tile).target.width / 2);
    }
    
    @Override
    public void registerBlockIcons(IIconRegister icon)
    {
        blockIcon = icon.registerIcon("minecraft:gold_block");
    }
}
