package darkknight.jewelrycraft.block;

import java.io.IOException;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.tileentity.TileEntityMoltenMetal;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockMoltenMetal extends BlockFluidClassic implements ITileEntityProvider {
	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon flowingIcon;

	public BlockMoltenMetal(Fluid fluid, Material material) {
		super(fluid, material);
		setBlockName(Variables.MODID + ".moltenMetal");
		setQuantaPerBlock(5);
		setRenderPass(1);
		setLightLevel(15f);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return side == 0 || side == 1 ? stillIcon : flowingIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(Variables.MODID + ":moltenMetalStill");
		flowingIcon = register.registerIcon(Variables.MODID + ":moltenMetalFlow");
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	protected boolean canFlowInto(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x, y, z).isAir(world, x, y, z)) return true;
		Block block = world.getBlock(x, y, z);
		if (block == this) return false;
		if (displacements.containsKey(block)) return displacements.get(block);
		Material material = block.getMaterial();
		if (material.blocksMovement() || material == Material.water || material == Material.lava || material == Material.portal) return false;
		int density = getDensity(world, x, y, z);
		if (density == Integer.MAX_VALUE) return true;
		if (this.density > density) return true;
		else return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int i, int j, int k) {
		try {
			return color(world, i, j, k, false, null);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (getTileEntity(world, x, y, z) == null || (getTileEntity(world, x, y, z).getMetal() == null)) world.setBlockToAir(x, y, z);
		else {
			int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
			int expQuanta = -101;
			// check adjacent block levels if non-source
			if (quantaRemaining < quantaPerBlock) {
				int y2 = y - densityDir;
				if ((world.getBlock(x, y2, z) == this && getTileEntity(world, x, y2, z) != null && getTileEntity(world, x, y, z) != null && areMetalsEqual(world, x, y2, z, x, y, z)) || (world.getBlock(x - 1, y2, z) == this && getTileEntity(world, x - 1, y2, z) != null && getTileEntity(world, x, y, z) != null && areMetalsEqual(world, x - 1, y2, z, x, y, z)) || (world.getBlock(x + 1, y2, z) == this && getTileEntity(world, x + 1, y2, z) != null && getTileEntity(world, x, y, z) != null && areMetalsEqual(world, x + 1, y2, z, x, y, z)) || (world.getBlock(x, y2, z - 1) == this && getTileEntity(world, x, y2, z - 1) != null && getTileEntity(world, x, y, z) != null && areMetalsEqual(world, x, y2, z - 1, x, y, z)) || (world.getBlock(x, y2, z + 1) == this && getTileEntity(world, x, y2, z + 1) != null && getTileEntity(world, x, y, z) != null && areMetalsEqual(world, x, y2, z + 1, x, y, z))) expQuanta = quantaPerBlock - 1;
				else {
					int maxQuanta = -100;
					if (getTileEntity(world, x, y, z) != null && getTileEntity(world, x - 1, y, z) != null && areMetalsEqual(world, x, y, z, x - 1, y, z)) maxQuanta = getLargerQuanta(world, x - 1, y, z, maxQuanta);
					if (getTileEntity(world, x, y, z) != null && getTileEntity(world, x + 1, y, z) != null && areMetalsEqual(world, x, y, z, x + 1, y, z)) maxQuanta = getLargerQuanta(world, x + 1, y, z, maxQuanta);
					if (getTileEntity(world, x, y, z) != null && getTileEntity(world, x, y, z - 1) != null && areMetalsEqual(world, x, y, z, x, y, z - 1)) maxQuanta = getLargerQuanta(world, x, y, z - 1, maxQuanta);
					if (getTileEntity(world, x, y, z) != null && getTileEntity(world, x, y, z + 1) != null && areMetalsEqual(world, x, y, z, x, y, z + 1)) maxQuanta = getLargerQuanta(world, x, y, z + 1, maxQuanta);
					expQuanta = maxQuanta - 1;
				}
				// decay calculation
				if (expQuanta != quantaRemaining) {
					quantaRemaining = expQuanta;
					if (expQuanta <= 0) world.setBlock(x, y, z, Blocks.air);
					else {
						world.setBlockMetadataWithNotify(x, y, z, quantaPerBlock - expQuanta, 3);
						world.scheduleBlockUpdate(x, y, z, this, tickRate);
						world.notifyBlocksOfNeighborChange(x, y, z, this);
					}
				}
			}
			// This is a "source" block, set meta to zero, and send a server only update
			else if (quantaRemaining >= quantaPerBlock) world.setBlockMetadataWithNotify(x, y, z, 0, 2);
			// Flow vertically if possible
			if (canDisplace(world, x, y + densityDir, z)) {
				if (getTileEntity(world, x, y + densityDir, z) != null && getTileEntity(world, x, y, z) != null) this.getTileEntity(world, x, y + densityDir, z).setMetal(getTileEntity(world, x, y, z).getMetal());
				flowIntoBlock(world, x, y + densityDir, z, 1, getTileEntity(world, x, y, z).getMetal());
				return;
			}
			// Flow outward if possible
			int flowMeta = quantaPerBlock - quantaRemaining + 1;
			if (flowMeta >= quantaPerBlock) return;
			if (isSourceBlock(world, x, y, z) || !isFlowingVertically(world, x, y, z)) {
				if (world.getBlock(x, y - densityDir, z) == this && getTileEntity(world, x, y, z) != null && getTileEntity(world, x, y - densityDir, z) != null && areMetalsEqual(world, x, y, z, x, y - densityDir, z)) flowMeta = 1;
				boolean flowTo[] = getOptimalFlowDirections(world, x, y, z);
				if (flowTo[0]) flowIntoBlock(world, x - 1, y, z, flowMeta, getTileEntity(world, x, y, z).getMetal());
				if (flowTo[1]) flowIntoBlock(world, x + 1, y, z, flowMeta, getTileEntity(world, x, y, z).getMetal());
				if (flowTo[2]) flowIntoBlock(world, x, y, z - 1, flowMeta, getTileEntity(world, x, y, z).getMetal());
				if (flowTo[3]) flowIntoBlock(world, x, y, z + 1, flowMeta, getTileEntity(world, x, y, z).getMetal());
			}
		}
	}

	public void flowIntoBlock(World world, int x, int y, int z, int meta, ItemStack metal) {
		if (meta < 0 || world.isRemote) return;
		if (displaceIfPossible(world, x, y, z)) {
			world.setBlock(x, y, z, this, meta, 3);
			if (getTileEntity(world, x, y, z) != null) getTileEntity(world, x, y, z).setMetal(metal);
		}
	}

	public static TileEntityMoltenMetal getTileEntity(World world, int x, int y, int z) {
		TileEntity moltenLiquid = world.getTileEntity(x, y, z);
		if (moltenLiquid != null && moltenLiquid instanceof TileEntityMoltenMetal) return (TileEntityMoltenMetal) moltenLiquid;
		return null;
	}

	public static boolean areMetalsEqual(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
		return ItemStack.areItemStacksEqual(getTileEntity(world, x1, y1, z1).getMetal(), getTileEntity(world, x2, y2, z2).getMetal());
	}

	@SideOnly(Side.CLIENT)
	public static int color(IBlockAccess world, int i, int j, int k, boolean forcecolor, Item itemC) throws IOException {
		TileEntity te = world.getTileEntity(i, j, k);
		if (te instanceof TileEntityMoltenMetal && ((TileEntityMoltenMetal) te).getMetal() != null) return JewelrycraftUtil.getColor(((TileEntityMoltenMetal) te).getMetal().copy());
		return 16777215;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int value) {
		return new TileEntityMoltenMetal();
	}
}
