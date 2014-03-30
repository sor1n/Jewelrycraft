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
    public static enum GuiId {
        ringChest,
        guide;

        public static final GuiId[] VALUES = GuiId.values();
    }

    public GuiHandler()
    {
        NetworkRegistry.instance().registerGuiHandler(JewelrycraftMod.instance, this);
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        final GuiId guiId = getGuiId(ID);
        if (guiId == null) return null;        
        switch(guiId)
        {
            case ringChest: return new ContainerRingChest(player.inventory, (TileEntityChest) world.getBlockTileEntity(x, y, z));
            case guide: return new ContainerGuide();
            default: return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        final GuiId guiId = getGuiId(ID);
        if (guiId == null) return null;        
        switch(guiId)
        {
            case ringChest: return new GuiRingChest((ContainerRingChest) getServerGuiElement(ID, player, world, x, y, z));
            case guide: return new GuiGuide((ContainerGuide) getServerGuiElement(ID, player, world, x, y, z), world);
            default: return null;
        }
    }

    private static GuiId getGuiId(int id) {
        try 
        {
            return GuiId.VALUES[id];
        } 
        catch (ArrayIndexOutOfBoundsException e) 
        {
            return null;
        }
    }
}
