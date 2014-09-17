package darkknight.jewelrycraft.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketSendLiquidData;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class BlockSmelter extends BlockContainer
{
    Random rand = new Random();
    
    protected BlockSmelter(Material par2Material)
    {
        super(par2Material);
    }
    
    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntitySmelter();
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1.3D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.11000000298023224D;
        world.spawnEntityInWorld(entityitem);
    }
    
    public void breakBlock(World world, int i, int j, int k, Block par5, int par6)
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getTileEntity(i, j, k);
        if (te != null)
        {
            if (te.hasMetal) dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.metal.copy());
            if (te.hasMoltenMetal && te.moltenMetal != null && Item.getIdFromItem(te.moltenMetal.getItem()) > 0)
            {
                JewelrycraftMod.saveData.setString(i + " " + j + " " + k + " " + world.provider.dimensionId, Item.getIdFromItem(te.moltenMetal.getItem()) + ":" + te.moltenMetal.getItemDamage());
                JewelrycraftMod.netWrapper.sendToAll(new PacketSendLiquidData(world.provider.dimensionId, i, j, k, Item.getIdFromItem(te.moltenMetal.getItem()), te.moltenMetal.getItemDamage()));
                
                world.setBlock(i, j, k, BlockList.moltenMetal, 0, 3);
                int quant = (int) (te.quantity * 10);
                if (quant == 1) world.setBlockMetadataWithNotify(i, j, k, 4, 3);
                if (quant == 2) world.setBlockMetadataWithNotify(i, j, k, 4, 3);
                if (quant == 3) world.setBlockMetadataWithNotify(i, j, k, 3, 3);
                if (quant == 4) world.setBlockMetadataWithNotify(i, j, k, 3, 3);
                if (quant == 5) world.setBlockMetadataWithNotify(i, j, k, 2, 3);
                if (quant == 6) world.setBlockMetadataWithNotify(i, j, k, 2, 3);
                if (quant == 7) world.setBlockMetadataWithNotify(i, j, k, 1, 3);
                if (quant == 8) world.setBlockMetadataWithNotify(i, j, k, 1, 3);
                if (quant == 9) world.setBlockMetadataWithNotify(i, j, k, 0, 3);
            }
            world.removeTileEntity(i, j, k);
        }
        super.breakBlock(world, i, j, k, par5, par6);
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getTileEntity(i, j, k);
        ItemStack item = entityPlayer.inventory.getCurrentItem();
        if(te != null && te.hasMoltenMetal && te.quantity >= 0.9f && !te.pouring && item != null && item.getItem() == Items.bucket)
        {
            te.quantity = 0f;
            te.hasMoltenMetal = false;
            ItemStack metalBucket = new ItemStack(ItemList.bucket, 1);
            JewelryNBT.addMetal(metalBucket, te.moltenMetal);
            --item.stackSize;
            entityPlayer.inventory.addItemStackToInventory(metalBucket);
            te.isDirty = true;
            return true;
        }
        if (te != null && !world.isRemote)
        {
            int index = -1;
            for (int a = 0; a < JewelrycraftUtil.jamcraftPlayers.size(); a++)
                if (entityPlayer.getDisplayName().equals(JewelrycraftUtil.jamcraftPlayers.get(a))) index = a;
            boolean canPlace = item != null && (JewelrycraftUtil.isMetal(item) || JewelrycraftUtil.isOre(item) || index != -1);
            boolean isOre = false;
            boolean oreCoincidesWithMetal = false;
            if(item != null && Item.getIdFromItem(item.getItem()) != 0) isOre = JewelrycraftUtil.isOre(item);
            if(isOre && te.moltenMetal != null && item != null && Item.getIdFromItem(te.moltenMetal.getItem()) != 0)
                oreCoincidesWithMetal = te.moltenMetal.getItem().equals(JewelrycraftUtil.getIngotFromOre(item.getItem()).getItem()) && te.moltenMetal.getItemDamage() == JewelrycraftUtil.getIngotFromOre(item.getItem()).getItemDamage();
            if (!te.hasMetal && !te.hasMoltenMetal && !te.pouring && item != null && canPlace)
            {
                entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chatmessage.Jewelrycraft.smelter.nowsmeltingingot", item.getDisplayName())));
                te.metal = item.copy();
                te.metal.stackSize = 1;
                te.hasMetal = true;
                te.melting = ConfigHandler.ingotMeltingTime;
                if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                te.isDirty = true;
            }
            else if (te.hasMetal && !te.hasMoltenMetal && item != null && canPlace && item.getUnlocalizedName().equals(te.metal.getUnlocalizedName()) && (!isOre && te.metal.stackSize < 9 || isOre && te.metal.stackSize < 4))
            {
                entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("Smelting extra " + (isOre ? "ores" : "ingots") + " (" + (te.metal.stackSize + 1) + ")")));
                te.metal.stackSize++;
                if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                te.isDirty = true;
            }
            else if (!te.hasMetal && te.hasMoltenMetal && item != null && canPlace && ((!isOre && item.getUnlocalizedName().equals(te.moltenMetal.getUnlocalizedName()) && te.moltenMetal.stackSize < 9) || (isOre && oreCoincidesWithMetal && te.moltenMetal.stackSize < 8)))
            {
                entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("Smelting extra " + (isOre ? "ore" : "ingot") + ".")));
                te.metal = item.copy();
                te.metal.stackSize = 1;
                te.hasMetal = true;
                te.melting = ConfigHandler.ingotMeltingTime;
                if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                te.isDirty = true;
            }
            else if (te.hasMetal && te.hasMoltenMetal && item != null && canPlace && item.getUnlocalizedName().equals(te.moltenMetal.getUnlocalizedName()))
            {
                if ((!isOre && te.moltenMetal.stackSize < 9 && te.metal.stackSize < 9 && (te.metal.stackSize + te.moltenMetal.stackSize) < 9) || (isOre && oreCoincidesWithMetal && te.metal.stackSize*2 < 9 && (te.metal.stackSize*2 + te.moltenMetal.stackSize) < 9))
                {
                    entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("Smelting extra ingots (" + (te.metal.stackSize + 1) + ")")));
                    te.metal.stackSize++;
                    te.hasMetal = true;
                    te.melting = ConfigHandler.ingotMeltingTime;
                    if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                    te.isDirty = true;
                }
            }
            else if ((te.hasMetal || te.hasMoltenMetal) && item != null && canPlace && ((te.moltenMetal != null && te.moltenMetal.stackSize > 0 && item.getUnlocalizedName().equals(te.moltenMetal.getUnlocalizedName())) || (te.metal != null && te.metal.stackSize > 0 && item.getUnlocalizedName().equals(te.metal.getUnlocalizedName()))) && ((te.moltenMetal != null && te.moltenMetal.stackSize >= 9) || (te.metal != null && te.metal.stackSize >= 9))) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("Smelter is at full capacity.")));
            else if (te.hasMetal && !te.hasMoltenMetal && item != null && canPlace) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chatmessage.Jewelrycraft.smelter.alreadyhasingot", te.metal.getDisplayName())));
            else if (te.hasMoltenMetal && Item.getIdFromItem(te.moltenMetal.getItem()) > 0) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chatmessage.Jewelrycraft.smelter.hasmolteningot", te.moltenMetal.getDisplayName())));
            else if (item != null && !item.getUnlocalizedName().toLowerCase().contains("ingot") && canPlace) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.smelter.itemrenamedtoingot")));
            else if (item != null && (!item.getUnlocalizedName().toLowerCase().contains("ingot") || item.getUnlocalizedName().toLowerCase().contains("mold"))) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.smelter.itemneedstobeingot")));
            
            if (te.hasMetal && entityPlayer.isSneaking())
            {
                dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.metal.copy());
                te.hasMetal = false;
                te.melting = -1;
                te.isDirty = true;
            }
            if (te != null) world.setTileEntity(i, j, k, te);
        }
        return true;
    }
    
    @Override
    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player)
    {
        TileEntitySmelter te = (TileEntitySmelter) world.getTileEntity(i, j, k);
        TileEntityMolder me = null;
        if (world.getBlockMetadata(i, j, k) == 0 && world.getTileEntity(i, j, k - 1) != null && world.getTileEntity(i, j, k - 1) instanceof TileEntityMolder) me = (TileEntityMolder) world.getTileEntity(i, j, k - 1);
        else if (world.getBlockMetadata(i, j, k) == 1 && world.getTileEntity(i + 1, j, k) != null && world.getTileEntity(i + 1, j, k) instanceof TileEntityMolder) me = (TileEntityMolder) world.getTileEntity(i + 1, j, k);
        else if (world.getBlockMetadata(i, j, k) == 2 && world.getTileEntity(i, j, k + 1) != null && world.getTileEntity(i, j, k + 1) instanceof TileEntityMolder) me = (TileEntityMolder) world.getTileEntity(i, j, k + 1);
        else if (world.getBlockMetadata(i, j, k) == 3 && world.getTileEntity(i - 1, j, k) != null && world.getTileEntity(i - 1, j, k) instanceof TileEntityMolder) me = (TileEntityMolder) world.getTileEntity(i - 1, j, k);
        
        if (te != null && me != null && !world.isRemote)
        {
            if (te.hasMoltenMetal && isConnectedToMolder(world, i, j, k) && me != null && me.hasMold && !me.hasMoltenMetal && !me.hasJewelBase)
            {
                te.pouring = true;
                te.isDirty = true;
            }
            else if (te.hasMetal && te.melting > 0) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chatmessage.Jewelrycraft.smelter.metalismelting", te.metal.getDisplayName()) + " (" + ((ConfigHandler.ingotMeltingTime - te.melting) * 100 / ConfigHandler.ingotMeltingTime) + "%)"));
            else if (te.hasMoltenMetal && !isConnectedToMolder(world, i, j, k)) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.smelter.molderismissing")));
            else if (!me.hasMold && te.hasMoltenMetal) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.smelter.molderhasnomold")));
            else if (me.hasMoltenMetal && te.hasMoltenMetal) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.smelter.molderhasmoltenmetal")));
            else if (me.hasJewelBase && te.hasMoltenMetal) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.smelter.modlerhasitem")));
            else player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage.Jewelrycraft.smelter.empty")));
        }
        
    }
    
    public boolean isConnectedToMolder(World world, int i, int j, int k)
    {
        int blockMeta = world.getBlockMetadata(i, j, k);
        if (blockMeta == 0 && world.getBlock(i, j, k - 1) instanceof BlockMolder) return true;
        else if (blockMeta == 1 && world.getBlock(i + 1, j, k) instanceof BlockMolder) return true;
        else if (blockMeta == 2 && world.getBlock(i, j, k + 1) instanceof BlockMolder) return true;
        else if (blockMeta == 3 && world.getBlock(i - 1, j, k) instanceof BlockMolder) return true;
        return false;
    }
    
    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack par6ItemStack)
    {
        int rotation = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
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
        this.blockIcon = icon.registerIcon("jewelrycraft:smelter");
    }
    
}
