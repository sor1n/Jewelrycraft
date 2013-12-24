package darkknight.jewelrycraft.block;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;

public class BlockJewelrsCraftingTable extends BlockContainer
{
    Random rand = new Random();

    public static ArrayList<ItemStack> modifiers = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> jewel = new ArrayList<ItemStack>();
    public static ArrayList<ItemStack> jewelry = new ArrayList<ItemStack>();

    protected BlockJewelrsCraftingTable(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.setBlockBounds(0.0F, 0F, 0.0F, 1.0F, 0.8F, 1.0F);
    }

    public static void addStuff()
    {
        //Modifiers
        modifiers.add(new ItemStack(Item.blazePowder));
        modifiers.add(new ItemStack(Item.sugar));
        modifiers.add(new ItemStack(Block.chest));
        modifiers.add(new ItemStack(Item.pickaxeIron));
        modifiers.add(new ItemStack(Item.bed));
        modifiers.add(new ItemStack(Item.eyeOfEnder));

        //Jewels
        jewel.add(new ItemStack(Item.enderPearl));
        jewel.add(new ItemStack(Item.diamond));
        jewel.add(new ItemStack(Item.emerald));
        jewel.add(new ItemStack(Block.obsidian));
        jewel.add(new ItemStack(Item.netherStar));

        //Jewelry
        jewelry.add(new ItemStack(ItemList.ring));
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
            if (!te.hasEndItem && !te.hasJewelry && item != null && isJewelry(item))
            {
                if(te.hasModifier && te.hasJewel && item.hasTagCompound() && item.getTagCompound().hasKey("modifier") && item.getTagCompound().hasKey("jewel")) 
                    entityPlayer.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.jewelrymodifiedfull"));
                else if(te.hasJewel && item.hasTagCompound() && item.getTagCompound().hasKey("jewel")) 
                    entityPlayer.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.jewelrycontainsjewel"));
                else if(te.hasModifier && item.hasTagCompound() && item.getTagCompound().hasKey("modifier")) 
                    entityPlayer.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.jewelrycontainsmodifier"));
                else
                {
                    te.jewelry = item.copy();
                    te.hasJewelry = true;
                    if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                    entityPlayer.inventory.onInventoryChanged();
                    world.setBlockTileEntity(i, j, k, te);
                    te.isDirty = true;
                }
            }
            if (!te.hasEndItem && !te.hasModifier && item != null && isModifier(item))
            {
                if(te.hasJewelry && te.jewelry.hasTagCompound() && te.jewelry.getTagCompound().hasKey("modifier"))
                    entityPlayer.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.jewelrycontainsmodifier"));
                else
                {
                    te.modifier = item.copy();
                    te.modifier.stackSize = 1;
                    te.hasModifier = true;
                    if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                    entityPlayer.inventory.onInventoryChanged();
                    world.setBlockTileEntity(i, j, k, te);
                    te.isDirty = true;
                }
            }
            if (!te.hasEndItem && !te.hasJewel && item != null && isJewel(item))
            {
                if(te.hasJewelry && te.jewelry.hasTagCompound() && te.jewelry.getTagCompound().hasKey("jewel"))
                    entityPlayer.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.jewelrycontainsjewel"));
                else
                {
                    te.jewel = item.copy();
                    te.jewel.stackSize = 1;
                    te.hasJewel = true;
                    if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
                    entityPlayer.inventory.onInventoryChanged();
                    world.setBlockTileEntity(i, j, k, te);
                    te.isDirty = true;
                }
            }
            if (te.timer <= 0 && !te.hasEndItem && te.hasJewelry && (te.hasModifier || te.hasJewel)){ te.timer = ConfigHandler.jewelryCraftingTime; te.angle = 0;}
            if (te.hasEndItem && item != null) entityPlayer.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.hasenditem"));

            if (te.hasModifier && entityPlayer.isSneaking())
            {
                dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.modifier.copy());
                te.modifier = new ItemStack(0, 0, 0);
                te.hasModifier = false;
                te.timer = 0;
                te.angle = 0F;
                world.markTileEntityForDespawn(te);
                world.setBlockTileEntity(i, j, k, te);
            }
            if (te.hasJewelry && entityPlayer.isSneaking())
            {
                dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.jewelry.copy());
                te.jewelry = new ItemStack(0, 0, 0);
                te.hasJewelry = false;
                te.timer = 0;
                te.angle = 0F;
                world.markTileEntityForDespawn(te);
                world.setBlockTileEntity(i, j, k, te);
            }
            if (te.hasJewel && entityPlayer.isSneaking())
            {
                dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.jewel.copy());
                te.jewel = new ItemStack(0, 0, 0);
                te.hasJewel = false;
                te.timer = 0;
                te.angle = 0F;
                world.markTileEntityForDespawn(te);
                world.setBlockTileEntity(i, j, k, te);
            }
        }
        return true;
    }

    public boolean isModifier(ItemStack item)
    {
        Iterator<ItemStack> i = modifiers.iterator();

        while (i.hasNext())
        {
            ItemStack temp = i.next();
            if (temp.itemID == item.itemID && temp.getItemDamage() == item.getItemDamage())
                return true;
        }
        return false;
    }

    public boolean isJewel(ItemStack item)
    {
        Iterator<ItemStack> i = jewel.iterator();

        while (i.hasNext())
        {
            ItemStack temp = i.next();
            if (temp.itemID == item.itemID && temp.getItemDamage() == item.getItemDamage())
                return true;
        }
        return false;
    }

    public boolean isJewelry(ItemStack item)
    {
        Iterator<ItemStack> i = jewelry.iterator();

        while (i.hasNext())
        {
            ItemStack temp = i.next();
            if (temp.itemID == item.itemID && temp.getItemDamage() == item.getItemDamage())
                return true;
        }
        return false;
    }

    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.21000000298023224D;
        world.spawnEntityInWorld(entityitem);
    }

    public void breakBlock(World world, int i, int j, int k, int par5, int par6)
    {
        TileEntityJewelrsCraftingTable te = (TileEntityJewelrsCraftingTable) world.getBlockTileEntity(i, j, k);
        if (te != null)
        {
            if (te.hasModifier) dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.modifier.copy());
            if (te.hasJewelry) dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.jewelry.copy());
            if (te.hasJewel) dropItem(world, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.jewel.copy());
            if (te.hasEndItem) dropItem(te.worldObj, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.endItem.copy());
            world.markTileEntityForDespawn(te);
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
        TileEntityJewelrsCraftingTable te = (TileEntityJewelrsCraftingTable) world.getBlockTileEntity(i, j, k);
        if (te != null && !world.isRemote)
        {
            if (te.hasEndItem)
            {
                dropItem(te.worldObj, (double) te.xCoord, (double) te.yCoord, (double) te.zCoord, te.endItem.copy());
                te.endItem = new ItemStack(0, 0, 0);
                te.hasEndItem = false;
                world.markTileEntityForDespawn(te);
                world.setBlockTileEntity(i, j, k, te);
            }
            else if (te.hasJewelry && (te.hasModifier || te.hasJewel) && te.timer > 0 && te.jewelry != null) player.addChatMessage(StatCollector.translateToLocalFormatted("chatmessage.jewelrycraft.table.iscrafting", te.jewelry.getDisplayName()) + " (" + ((ConfigHandler.jewelryCraftingTime - te.timer) * 100 / ConfigHandler.jewelryCraftingTime) + "%)");
            else if ((!te.hasModifier || !te.hasJewel) && !te.hasJewelry) player.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.missingjewelryandmodifierorjewel"));
            else if (!te.hasJewelry) player.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.missingjewelry"));
            else if (!te.hasModifier || !te.hasJewel) player.addChatMessage(StatCollector.translateToLocal("chatmessage.jewelrycraft.table.missingmodifierorjewel"));
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
