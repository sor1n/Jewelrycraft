package darkknight.jewelrycraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class BlockJewelrsCraftingTable extends BlockContainer
{
    Random rand = new Random();
    
    protected BlockJewelrsCraftingTable(Material par2Material)
    {
        super(par2Material);
        this.setBlockBounds(0.0F, 0F, 0.0F, 1.0F, 0.8F, 1.0F);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int var2)
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
        TileEntityJewelrsCraftingTable te = (TileEntityJewelrsCraftingTable) world.getTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if (te != null && !world.isRemote)
        {
            if (te.hasEndItem && item != null) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.table.hasenditem")));
            if (!te.hasEndItem && !te.hasJewelry && item != null && JewelrycraftUtil.isJewelry(item))
            {
                te.jewelry = item.copy();
                te.hasJewelry = true;
                if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                te.isDirty = true;
            }
            if (!te.hasEndItem && !te.hasGem && item != null && JewelrycraftUtil.isGem(item))
            {
                te.gem = item.copy();
                te.gem.stackSize = 1;
                te.hasGem = true;
                if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                te.isDirty = true;
            }
            if (!te.hasEndItem && te.hasJewelry && te.hasGem && !te.crafting)
            {
                te.carving = ConfigHandler.jewelryCraftingTime;
                te.angle = 0;
                te.crafting = true;
                te.isDirty = true;
            }
        }
        return true;
    }
    
    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.21000000298023224D;
        world.spawnEntityInWorld(entityitem);
    }
    
    public void breakBlock(World world, int i, int j, int k, Block par5, int par6)
    {
        TileEntityJewelrsCraftingTable te = (TileEntityJewelrsCraftingTable) world.getTileEntity(i, j, k);
        if (te != null)
        {
            if (te.hasJewelry) dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.jewelry.copy());
            if (te.hasGem) dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.gem.copy());
            if (te.hasEndItem) dropItem(te.getWorldObj(), (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.endItem.copy());
            world.removeTileEntity(i, j, k);
        }
        super.breakBlock(world, i, j, k, par5, par6);
    }
    
    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack par6ItemStack)
    {
        int rotation = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
    }
    
    @Override
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player)
    {
        TileEntityJewelrsCraftingTable te = (TileEntityJewelrsCraftingTable) world.getTileEntity(i, j, k);
        if (te != null && !world.isRemote)
        {
            if (player.isSneaking())
            {
                if (te.hasJewelry)
                {
                    dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.jewelry.copy());
                    te.jewelry = new ItemStack(Item.getItemById(0), 0, 0);
                    te.hasJewelry = false;
                    te.carving = -1;
                    te.crafting = false;
                    te.angle = 0F;
                    te.isDirty = true;
                }
                if (te.hasGem)
                {
                    dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.gem.copy());
                    te.gem = new ItemStack(Item.getItemById(0), 0, 0);
                    te.hasGem = false;
                    te.carving = -1;
                    te.crafting = false;
                    te.angle = 0F;
                    te.isDirty = true;
                }
            }
            else
            {
                if (te.hasEndItem)
                {
                    dropItem(te.getWorldObj(), (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.endItem.copy());
                    te.endItem = new ItemStack(Item.getItemById(0), 0, 0);
                    te.hasEndItem = false;
                    te.isDirty = true;
                }
                else if (te.hasJewelry && te.hasGem && te.carving > 0 && te.jewelry != null) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chatmessage.Jewelrycraft.table.iscrafting", te.jewelry.getDisplayName()) + " (" + ((ConfigHandler.jewelryCraftingTime - te.carving) * 100 / ConfigHandler.jewelryCraftingTime) + "%)"));
                else if (!te.hasGem) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.table.missinggem")));
                else if (!te.hasJewelry) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.table.missingjewelry")));
            }
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
    
    public void registerBlockIcons(IIconRegister icon)
    {
        this.blockIcon = icon.registerIcon("jewelrycraft:jewelrsCraftingTable");
    }
}
