package darkknight.jewelrycraft.block;

import java.util.Random;

import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSmelter extends BlockContainer
{
    Random rand = new Random();
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

    public void onBlockDestroyedByPlayer(World par1World, int i, int j, int k, int par5) 
    {
        TileEntitySmelter te = (TileEntitySmelter) par1World.getBlockTileEntity(i, j, k);
        if(te != null) 
        {            
            float f = this.rand.nextFloat() * 0.8F + 0.1F;
            float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
            float f2 = this.rand.nextFloat() * 0.8F + 0.1F;
            if(te.hasMetal)
            {
                EntityItem entityitem = new EntityItem(par1World, (double)((float)i + f), (double)((float)j + f1), (double)((float)k + f2), new ItemStack(te.metal.itemID, 1, te.metal.getItemDamage()));

                if (te.metal.hasTagCompound())
                {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound)te.metal.getTagCompound().copy());
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

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getBlockTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if (te != null && !world.isRemote)
        {
            if (!te.hasMetal && !te.hasMoltenMetal && item != null && item.getDisplayName().contains("Ingot") && !item.getDisplayName().contains("Mold"))
            {
                te.metal = new ItemStack(item.itemID, 1, item.getItemDamage());
                te.hasMetal = true;
                te.melting = 200000;
                --item.stackSize;
            }
            else if (te.hasMetal && !te.hasMoltenMetal && item != null && item.getDisplayName().contains("Ingot") && !item.getDisplayName().contains("Mold"))
                entityPlayer.addChatMessage("The Smelter already contains a " + te.metal.getDisplayName());
            else if (te.hasMoltenMetal)
                entityPlayer.addChatMessage("The Smelter contains molten " + te.moltenMetal.getDisplayName().toLowerCase().replace("ingot", ""));
            else if (item != null && !item.getDisplayName().contains("Ingot"))
                entityPlayer.addChatMessage("The item needs to be an ingot!");

            if (te.hasMetal && entityPlayer.isSneaking())
            {
                entityPlayer.dropPlayerItem(te.metal);
                te.hasMetal = false;
            }
            world.setBlockTileEntity(i, j, k, te);
        }
        return true;
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player) 
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getBlockTileEntity(i, j, k);
        TileEntityMolder me = null;
        if(world.getBlockMetadata(i, j, k) == 0) me = (TileEntityMolder) world.getBlockTileEntity(i, j, k - 1);
        else if(world.getBlockMetadata(i, j, k) == 1) me = (TileEntityMolder) world.getBlockTileEntity(i + 1, j, k);
        else if(world.getBlockMetadata(i, j, k) == 2) me = (TileEntityMolder) world.getBlockTileEntity(i, j, k + 1);
        else if(world.getBlockMetadata(i, j, k) == 3) me = (TileEntityMolder) world.getBlockTileEntity(i - 1, j, k);

        if(te.hasMoltenMetal && me != null)
        {
            if(isConnectedToMolder(world, i, j, k) && me.hasMold && !me.hasMoltenMetal && !me.hasJewelBase)
            {
                me.moltenMetal = te.moltenMetal;
                me.hasMoltenMetal = true;
                te.moltenMetal = new ItemStack(0, 0, 0);
                te.hasMoltenMetal = false;
            }
            else if(me.hasMoltenMetal) player.addChatMessage("The Molder already has molten metal in it!");
            else if(!me.hasMold) player.addChatMessage("The Molder doesn't have a mold in it! You might as well pour this stuff on the ground, won't you?");
            else if(me.hasJewelBase) player.addChatMessage("The Molder contains an item in it. Now you wouldn't want it to be destroyed, would you?");
            else player.addChatMessage("You need a Molder in front of this block in order to pour the molten metal!");
        }

    }

    public boolean isConnectedToMolder(World world, int i, int j, int k)
    {
        int blockMeta = world.getBlockMetadata(i, j, k);
        if(blockMeta == 0 && world.getBlockId(i, j, k - 1) == BlockList.molder.blockID) return true;
        else if(blockMeta == 1 && world.getBlockId(i + 1, j, k) == BlockList.molder.blockID) return true;
        else if(blockMeta == 2 && world.getBlockId(i, j, k + 1) == BlockList.molder.blockID) return true;
        else if(blockMeta == 3 && world.getBlockId(i - 1, j, k) == BlockList.molder.blockID) return true;
        return false;
    }

    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack par6ItemStack) 
    {
        int rotation = MathHelper.floor_double((double)(entityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
        System.out.println(world.getBlockMetadata(i, j, k));
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
        this.blockIcon = icon.registerIcon("jewelrycraft:smelter");
    }

}
