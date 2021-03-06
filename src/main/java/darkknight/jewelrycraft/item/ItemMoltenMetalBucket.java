package darkknight.jewelrycraft.item;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.tileentity.TileEntityMoltenMetal;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class ItemMoltenMetalBucket extends Item {
	public IIcon liquid;

	public ItemMoltenMetalBucket() {
		maxStackSize = 1;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer par3EntityPlayer) {
		boolean flag = BlockList.moltenMetal == Blocks.air;
		MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, flag);
		if (movingobjectposition == null) return stack;
		else {
			FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, stack, par2World, movingobjectposition);
			if (MinecraftForge.EVENT_BUS.post(event)) return stack;
			if (event.getResult() == Event.Result.ALLOW) {
				if (par3EntityPlayer.capabilities.isCreativeMode) return stack;
				if (--stack.stackSize <= 0) return event.result;
				if (!par3EntityPlayer.inventory.addItemStackToInventory(event.result)) par3EntityPlayer.dropPlayerItemWithRandomChoice(event.result, false);
				return stack;
			}
			if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
				int i = movingobjectposition.blockX;
				int j = movingobjectposition.blockY;
				int k = movingobjectposition.blockZ;
				if (!par2World.canMineBlock(par3EntityPlayer, i, j, k)) return stack;
				if (flag) {
					if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) return stack;
					par2World.getBlock(i, j, k).getMaterial();
					par2World.getBlockMetadata(i, j, k);
					par2World.setBlockToAir(i, j, k);
					return func_150910_a(stack, par3EntityPlayer, ItemList.bucket);
				} else {
					if (BlockList.moltenMetal == Blocks.air) return new ItemStack(Items.bucket);
					if (movingobjectposition.sideHit == 0) --j;
					else if (movingobjectposition.sideHit == 1) ++j;
					else if (movingobjectposition.sideHit == 2) --k;
					else if (movingobjectposition.sideHit == 3) ++k;
					else if (movingobjectposition.sideHit == 4) --i;
					else if (movingobjectposition.sideHit == 5) ++i;
					if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) return stack;
					if (tryPlaceContainedLiquid(par2World, i, j, k, stack) && !par3EntityPlayer.capabilities.isCreativeMode) return new ItemStack(Items.bucket);
				}
			}
			return stack;
		}
	}

	private ItemStack func_150910_a(ItemStack p_150910_1_, EntityPlayer p_150910_2_, Item p_150910_3_) {
		if (p_150910_2_.capabilities.isCreativeMode) return p_150910_1_;
		else if (--p_150910_1_.stackSize <= 0) return new ItemStack(p_150910_3_);
		else {
			if (!p_150910_2_.inventory.addItemStackToInventory(new ItemStack(p_150910_3_))) p_150910_2_.dropPlayerItemWithRandomChoice(new ItemStack(p_150910_3_, 1, 0), false);
			return p_150910_1_;
		}
	}

	public static boolean tryPlaceContainedLiquid(World world, int x, int y, int z, ItemStack stack) {
		if (BlockList.moltenMetal == Blocks.air) return false;
		else {
			Material material = world.getBlock(x, y, z).getMaterial();
			boolean flag = !material.isSolid();
			if (!world.isAirBlock(x, y, z) && !flag) return false;
			else if (stack != null && JewelryNBT.ingot(stack) != null) {
				if (!world.isRemote && flag && !material.isLiquid()) world.func_147480_a(x, y, z, true);
				world.setBlock(x, y, z, BlockList.moltenMetal, 0, 3);
            	TileEntity moltenLiquid = world.getTileEntity(x, y, z);
            	if(moltenLiquid instanceof TileEntityMoltenMetal) ((TileEntityMoltenMetal) moltenLiquid).setMetal(JewelryNBT.ingot(stack));
				return true;
			} else return false;
		}
	}

	@Override
	public void registerIcons(IIconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon("bucket_empty");
		liquid = iconRegister.registerIcon(Variables.MODID + ":bucketOverlay");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if (pass == 1 && JewelryNBT.ingot(stack) != null) return JewelrycraftUtil.getColor(JewelryNBT.ingot(stack));
		return 16777215;
	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (pass == 0) return itemIcon;
		if (pass == 1) return liquid;
		return itemIcon;
	}

	public ItemStack getModifiedItemStack(ItemStack ingot) {
		ItemStack itemstack = new ItemStack(this);
		JewelryNBT.addMetal(itemstack, ingot);
		return itemstack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		try {
			if (JewelryNBT.ingot(stack) != null) {
				ItemStack ingot = JewelryNBT.ingot(stack);
				if (ingot != null) {
					return StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name").trim() + " " + ingot.getDisplayName().replace("Ingot", " ").trim();
				} else return StatCollector.translateToLocal("bucket.unknown");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		return ("" + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name")).trim() + " " + StatCollector.translateToLocal("info.jewelrycraft2.metal");
	}
}
