package darkknight.jewelrycraft.thirdparty;

import java.util.List;

import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.tileentity.TileEntityMoltenMetal;
import darkknight.jewelrycraft.util.JewelryNBT;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import mcp.mobius.waila.api.SpecialChars;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class WailaHandler implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileEntityMoltenMetal te = (TileEntityMoltenMetal)accessor.getTileEntity();
		ItemStack is = new ItemStack(ItemList.metal);
		JewelryNBT.addMetal(is, te.getMetal());
		return is;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		TileEntityMoltenMetal te = (TileEntityMoltenMetal)accessor.getTileEntity();
		String metalName =  SpecialChars.WHITE + "Molten " + te.getMetal().getDisplayName();
		currenttip.remove(0);
		currenttip.add(0, metalName);
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,	IWailaConfigHandler config) {
		return currenttip;
	}

	public static void registration(IWailaRegistrar registrar){
		registrar.registerStackProvider(new WailaHandler(), TileEntityMoltenMetal.class);
		registrar.registerHeadProvider(new WailaHandler(), TileEntityMoltenMetal.class);
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound nbt, World world, int x, int y, int z) 
	{
		return null;
	}
	
}
