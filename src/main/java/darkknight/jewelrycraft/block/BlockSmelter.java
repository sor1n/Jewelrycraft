package darkknight.jewelrycraft.block;

import java.util.Random;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.item.ItemMoltenMetalBucket;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSmelter extends BlockContainer {
	Random rand = new Random();

	public BlockSmelter() {
		super(Material.rock);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2) {
		return new TileEntitySmelter();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public void dropItem(World world, double x, double y, double z, ItemStack stack) {
		EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 1.3D, z + 0.5D, stack);
		entityitem.motionX = 0;
		entityitem.motionZ = 0;
		entityitem.motionY = 0.11000000298023224D;
		entityitem.delayBeforeCanPickup = 0;
		world.spawnEntityInWorld(entityitem);
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int meta) {
		TileEntitySmelter te = (TileEntitySmelter) world.getTileEntity(i, j, k);
		if (te != null) {
			if (te.hasMetal) dropItem(world, i, j, k, te.metal.copy());
			if (te.hasMoltenMetal && te.moltenMetal != null && Item.getIdFromItem(te.moltenMetal.getItem()) > 0) {
				int quant = (int) (te.quantity * 10);
				ItemStack metalBucket = new ItemStack(ItemList.bucket);
				JewelryNBT.addMetal(metalBucket, te.moltenMetal.copy());
				if (quant == 9) {
					dropItem(world, i, j, k, new ItemStack(Blocks.cobblestone, 6));
					dropItem(world, i, j, k, new ItemStack(Items.lava_bucket));
					dropItem(world, i, j, k, metalBucket);
				}
			}
			world.removeTileEntity(i, j, k);
		}
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9) {
		TileEntitySmelter te = (TileEntitySmelter) world.getTileEntity(i, j, k);
		ItemStack item = entityPlayer.inventory.getCurrentItem();
		if (te != null && te.hasMoltenMetal && te.quantity >= .9f && !te.pouring && item != null && item.getItem() == Items.bucket) {
			te.quantity = 0f;
			te.hasMoltenMetal = false;
			ItemStack metalBucket = new ItemStack(ItemList.bucket, 1);
			ItemStack ingot = te.moltenMetal.copy();
			JewelryNBT.addMetal(metalBucket, ingot);
			--item.stackSize;
			entityPlayer.inventory.addItemStackToInventory(metalBucket);
			te.isDirty = true;
			return true;
		}
		if (te != null && !world.isRemote) {
			if (te.hasMetal && entityPlayer.isSneaking()) {
				dropItem(world, te.xCoord, te.yCoord, te.zCoord, te.metal.copy());
				te.hasMetal = false;
				te.melting = -1;
				te.isDirty = true;
			}
			if (item != null && item.getItem() != null && !(item.getItem() instanceof ItemMoltenMetalBucket)) {
				int index = -1;
				for (int a = 0; a < JewelrycraftUtil.jamcraftPlayers.size(); a++)
					if (entityPlayer.getUniqueID().toString().equals(JewelrycraftUtil.jamcraftPlayers.get(a))) index = a;
				if (entityPlayer.capabilities.isCreativeMode) index = 1;
				boolean canPlace = item != null && (JewelrycraftUtil.isMetal(item) || JewelrycraftUtil.isOre(item) || index >= 0 || JewelryNBT.ingot(item) != null);
				boolean isOre = false, oreCoincidesWithMetal = false, itemCoincidesWithMetal = false, itemCoincidesWithMoltenMetal = false, overflow = false;
				isOre = JewelrycraftUtil.isOre(item);
				if (te.metal != null && te.metal.getItem() != null) {
					if (JewelryNBT.ingot(item) == null) itemCoincidesWithMetal = item.getItem().equals(te.metal.getItem()) && item.getItemDamage() == te.metal.getItemDamage();
					else itemCoincidesWithMetal = item.getItem().equals(te.metal.getItem()) && item.getItemDamage() == te.metal.getItemDamage() && JewelryNBT.ingot(item).getItem().equals(JewelryNBT.ingot(te.metal).getItem()) && JewelryNBT.ingot(item).getItemDamage() == JewelryNBT.ingot(te.metal).getItemDamage();
				}
				if (te.moltenMetal != null && te.moltenMetal.getItem() != null) {
					if (JewelryNBT.ingot(item) == null) itemCoincidesWithMoltenMetal = item.getItem().equals(te.moltenMetal.getItem()) && item.getItemDamage() == te.moltenMetal.getItemDamage();
					else itemCoincidesWithMoltenMetal = JewelryNBT.ingot(item).getItem().equals(te.moltenMetal.getItem()) && JewelryNBT.ingot(item).getItemDamage() == te.moltenMetal.getItemDamage();
					if (isOre) oreCoincidesWithMetal = te.moltenMetal.getItem().equals(JewelrycraftUtil.getIngotFromOre(item).getItem()) && te.moltenMetal.getItemDamage() == JewelrycraftUtil.getIngotFromOre(item).getItemDamage();
				}
				overflow = isOre ? te.metal.stackSize * 0.2f + te.quantity < 0.8f : te.metal.stackSize * 0.1f + te.quantity < 0.9f;
				boolean isValid = te.hasMoltenMetal ? isOre ? oreCoincidesWithMetal : itemCoincidesWithMoltenMetal : true;
				if (te.quantity < 0.9f && !te.pouring && canPlace && isValid) {
					boolean check = isOre ? oreCoincidesWithMetal && te.quantity < 0.8f : itemCoincidesWithMoltenMetal;
					boolean check2 = isOre ? oreCoincidesWithMetal : itemCoincidesWithMetal;
					if (!te.hasMetal && !te.hasMoltenMetal || !te.hasMetal && te.hasMoltenMetal && check) {
						entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chatmessage." + Variables.MODID + ".smelter.nowsmeltingingot", item.getDisplayName())));
						te.metal = item.copy();
						te.metal.stackSize = 1;
						te.hasMetal = true;
						te.melting = ConfigHandler.INGOT_MELTING_TIME;
						if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
						te.isDirty = true;
					}
					else if (te.hasMetal && te.hasMoltenMetal && check2 && overflow || te.hasMetal && !te.hasMoltenMetal && itemCoincidesWithMetal && overflow) {
						entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("Smelting extra " + (isOre ? "ores" : "ingots") + " (" + (te.metal.stackSize + 1) + ")")));
						te.metal.stackSize++;
						te.hasMetal = true;
						te.melting += ConfigHandler.INGOT_MELTING_TIME;
						if (!entityPlayer.capabilities.isCreativeMode) --item.stackSize;
						te.isDirty = true;
					}
					te.isDirty = true;
				}
				else if (item != null && (te.hasMetal || te.hasMoltenMetal) && !itemCoincidesWithMoltenMetal && te.quantity < .9f) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.contentdoesnotmatch")));
				else if (item != null && !item.getUnlocalizedName().toLowerCase().contains("ingot") && item.getDisplayName().toLowerCase().contains("ingot") && te.quantity < .9f) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.itemrenamedtoingot")));
				else if (item != null && te.quantity >= .9f) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.full")));
				else entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.cannotsmelt")));
			}
			else if (item != null && item.getItem() != null && item.getItem() instanceof ItemMoltenMetalBucket && !te.hasMoltenMetal && !te.hasMetal) {
				te.hasMoltenMetal = true;
				ItemStack ingot = JewelryNBT.ingot(item);
				te.moltenMetal = ingot;
				te.quantity = .9f;
				te.isDirty = true;
				if (!entityPlayer.capabilities.isCreativeMode) {
					--item.stackSize;
					dropItem(world, entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, new ItemStack(Items.bucket));
				}
			}
			else if (item == null && te.hasMoltenMetal && te.moltenMetal.getItem() != null) entityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chatmessage." + Variables.MODID + ".smelter.hasmolteningot", te.moltenMetal.getDisplayName().replace(" Ingot", ""))));
			world.setTileEntity(i, j, k, te);
		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, int i, int j, int k, EntityPlayer player) {
		TileEntitySmelter te = (TileEntitySmelter) world.getTileEntity(i, j, k);
		TileEntityMolder me = null;
		if (world.getBlockMetadata(i, j, k) == 0 && world.getTileEntity(i, j, k - 1) != null && world.getTileEntity(i, j, k - 1) instanceof TileEntityMolder) me = (TileEntityMolder) world.getTileEntity(i, j, k - 1);
		else if (world.getBlockMetadata(i, j, k) == 1 && world.getTileEntity(i + 1, j, k) != null && world.getTileEntity(i + 1, j, k) instanceof TileEntityMolder) me = (TileEntityMolder) world.getTileEntity(i + 1, j, k);
		else if (world.getBlockMetadata(i, j, k) == 2 && world.getTileEntity(i, j, k + 1) != null && world.getTileEntity(i, j, k + 1) instanceof TileEntityMolder) me = (TileEntityMolder) world.getTileEntity(i, j, k + 1);
		else if (world.getBlockMetadata(i, j, k) == 3 && world.getTileEntity(i - 1, j, k) != null && world.getTileEntity(i - 1, j, k) instanceof TileEntityMolder) me = (TileEntityMolder) world.getTileEntity(i - 1, j, k);
		if (te != null && me != null && !world.isRemote) if (te.hasMoltenMetal && isConnectedToMolder(world, i, j, k) && me != null && me.hasMold && !me.hasMoltenMetal && !me.hasJewelBase) {
			te.pouring = true;
			te.isDirty = true;
		}
		else if (te.hasMetal && te.melting > 0) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocalFormatted("chatmessage." + Variables.MODID + ".smelter.metalismelting", te.metal.getDisplayName()) + " (" + (ConfigHandler.INGOT_MELTING_TIME * te.metal.stackSize - te.melting) * 100 / (ConfigHandler.INGOT_MELTING_TIME * te.metal.stackSize) + "%)"));
		else if (te.hasMoltenMetal && !isConnectedToMolder(world, i, j, k)) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.molderismissing")));
		else if (!me.hasMold && te.hasMoltenMetal) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.molderhasnomold")));
		else if (me.hasMoltenMetal && te.hasMoltenMetal) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.molderhasmoltenmetal")));
		else if (me.hasJewelBase && te.hasMoltenMetal) player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.modlerhasitem")));
		else player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chatmessage." + Variables.MODID + ".smelter.empty")));
	}

	public boolean isConnectedToMolder(World world, int i, int j, int k) {
		int blockMeta = world.getBlockMetadata(i, j, k);
		if (blockMeta == 0 && world.getBlock(i, j, k - 1) instanceof BlockMolder) return true;
		else if (blockMeta == 1 && world.getBlock(i + 1, j, k) instanceof BlockMolder) return true;
		else if (blockMeta == 2 && world.getBlock(i, j, k + 1) instanceof BlockMolder) return true;
		else if (blockMeta == 3 && world.getBlock(i - 1, j, k) instanceof BlockMolder) return true;
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack par6ItemStack) {
		int rotation = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, rotation, 2);
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess iblockaccess, int i, int j, int k, int l) {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}
    
    @Override
    public void registerBlockIcons(IIconRegister icon)
    {
        blockIcon = icon.registerIcon("minecraft:iron_block");
    }
}
