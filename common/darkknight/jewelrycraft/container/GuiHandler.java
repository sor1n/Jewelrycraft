package darkknight.jewelrycraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.client.GuiRingChest;

public class GuiHandler implements IGuiHandler
{
    public GuiHandler()
    {
        NetworkRegistry.instance().registerGuiHandler(JewelrycraftMod.instance, this);
    }
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return new ContainerRingChest(player.inventory, (TileEntityChest) world.getBlockTileEntity(x, y, z));
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        return new GuiRingChest((ContainerRingChest) getServerGuiElement(ID, player, world, x, y, z));
    }

}
