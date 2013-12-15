package darkknight.jewelrycraft.block;

import java.util.Random;

import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMolder extends BlockContainer
{
    Random rand = new Random();
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
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if(te != null && item != null && !te.hasMold && item.itemID == ItemList.molds.itemID)
        {
            te.mold = item;
            te.hasMold = true;
            --item.stackSize;
        }
        if(te != null && te.hasMold && entityPlayer.isSneaking())
        {
            entityPlayer.inventory.addItemStackToInventory(new ItemStack(te.mold.itemID, 1, te.mold.getItemDamage()));
            te.mold = new ItemStack(0, 0, 0);
            te.hasMold = false;
        }   
        return true;
    }

    public void onBlockDestroyedByPlayer(World par1World, int i, int j, int k, int par5) 
    {
        TileEntityMolder te = (TileEntityMolder) par1World.getBlockTileEntity(i, j, k);
        if(te != null) 
        {            
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
            if(te.hasMold)
            {
                EntityItem entityitem = new EntityItem(par1World, (double)((float)i + f), (double)((float)j + f1), (double)((float)k + f2), new ItemStack(te.mold.itemID, 1, te.mold.getItemDamage()));

                if (te.mold.hasTagCompound())
                {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound)te.mold.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
                par1World.spawnEntityInWorld(entityitem);
            }
            if(te.hasJewelBase)
            {
                EntityItem entityitem = new EntityItem(par1World, (double)((float)i + f), (double)((float)j + f1), (double)((float)k + f2), new ItemStack(te.jewelBase.itemID, 1, te.jewelBase.getItemDamage()));

                if (te.jewelBase.hasTagCompound())
                {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound)te.jewelBase.getTagCompound().copy());
                }

                float f3 = 0.05F;
                entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
                par1World.spawnEntityInWorld(entityitem);
            }
        }
    }
    
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion par5Explosion) 
    {
        onBlockDestroyedByPlayer(world, i, j, k, 0);
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player) 
    {
        TileEntityMolder me = (TileEntityMolder) world.getBlockTileEntity(i, j, k); 
        if(me != null && me.hasJewelBase)
        {
            player.inventory.addItemStackToInventory(new ItemStack(me.jewelBase.itemID, 1, me.jewelBase.getItemDamage()));
            me.jewelBase = new ItemStack(0, 0, 0);
            me.hasJewelBase = false;            
        }        
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
