package darkknight.jewelrycraft.block;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;

public class BlockJewelrsCraftingTable extends BlockContainer
{
    Random rand = new Random();
    int modifiers[] = new int[] {Item.blazePowder.itemID};
    int effects[] = new int[] {12};
    
    protected BlockJewelrsCraftingTable(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.setBlockBounds(0.0F, 0F, 0.0F, 1.0F, 0.8F, 1.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityJewelrsCraftingTable();
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntityJewelrsCraftingTable te = (TileEntityJewelrsCraftingTable) world.getBlockTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if (te != null && !world.isRemote)
        {
            if (!te.hasJewel && item != null && item.getItem().itemID == ItemList.ring.itemID)
            {
                te.jewel = item;
                te.hasJewel = true;
                --item.stackSize;
            }
            if (!te.hasModifier && item != null && item.getItem().itemID == modifiers[0])
            {
                te.modifier = item;
                te.hasModifier = true;
                --item.stackSize;
            }   
            
            if (te.hasModifier && entityPlayer.isSneaking())
            {
                entityPlayer.dropPlayerItem(te.modifier);
                te.hasModifier = false;
            }            
            if (te.hasJewel && entityPlayer.isSneaking())
            {
                entityPlayer.dropPlayerItem(te.jewel);
                te.hasJewel = false;
            }
            world.setBlockTileEntity(i, j, k, te);
        }
        return true;
    }
    
    @Override
    public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int par5)
    {
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion par5Explosion)
    {
        onBlockDestroyedByPlayer(world, i, j, k, 0);
    }
    
    public void giveJewelToPlayer(TileEntityJewelrsCraftingTable cf, EntityPlayer player, ItemStack item)
    {
        if (item != null)
        {
            if (cf.modifier.itemID == Item.blazePowder.itemID)
            {
                ItemRing.addEffect(item, new PotionEffect(12, 12));
            }
            player.inventory.addItemStackToInventory(item);
        }
    }
    
    @Override
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player)
    {
        TileEntityJewelrsCraftingTable te = (TileEntityJewelrsCraftingTable) world.getBlockTileEntity(i, j, k);
        if(te.hasJewel && te.hasModifier)
        {
            giveJewelToPlayer(te, player, te.jewel);
            te.jewel = new ItemStack(0, 0, 0);
            te.hasJewel = false;
            te.modifier = new ItemStack(0, 0, 0);
            te.hasModifier = false;
        }
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
    
    @Override
    public void registerIcons(IconRegister icon)
    {
        this.blockIcon = icon.registerIcon("jewelrycraft:jewelrsCraftingTable");
    }
}
