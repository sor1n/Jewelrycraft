package darkknight.jewelrycraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.client.GuiGuide;
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
        switch(ID)
        {
            case 0: return new ContainerRingChest(player.inventory, (TileEntityChest) world.getBlockTileEntity(x, y, z));
            case 1: return new ContainerGuide();
            default: return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {       
        switch(ID)
        {
            case 0: return new GuiRingChest((ContainerRingChest) getServerGuiElement(ID, player, world, x, y, z));
            case 1: return new GuiGuide((ContainerGuide) getServerGuiElement(ID, player, world, x, y, z), world);
            default: return null;
        }
    }
}
