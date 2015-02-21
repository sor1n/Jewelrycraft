package darkknight.jewelrycraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.client.GuiGuide;
import darkknight.jewelrycraft.client.GuiJewelry;
import darkknight.jewelrycraft.client.GuiRingChest;

public class GuiHandler implements IGuiHandler
{
    ResourceLocation pageTexture = new ResourceLocation("jewelrycraft", "textures/gui/guidePage.png");
    ResourceLocation flippedPageTexture = new ResourceLocation("jewelrycraft", "textures/gui/guidePageFlip.png");
    ResourceLocation chestTexture = new ResourceLocation("jewelrycraft", "textures/gui/chest_ring.png");
    ResourceLocation jewelryInvTexture = new ResourceLocation("jewelrycraft", "textures/gui/jewelry_tab.png");
    
    /**
     * 
     */
    public GuiHandler()
    {
        NetworkRegistry.INSTANCE.registerGuiHandler(JewelrycraftMod.instance, this);
    }
    
    /**
     * @param ID
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch(ID)
        {
            case 0:
                return new ContainerRingChest(player.inventory, (TileEntityChest)world.getTileEntity(x, y, z));
            case 1:
                return new ContainerGuide();
            case 2:
                return new ContainerJewelryTab(player, player.inventory, new JewelryInventory(player));
            default:
                return null;
        }
    }
    
    /**
     * @param ID
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch(ID)
        {
            case 0:
                return new GuiRingChest((ContainerRingChest)getServerGuiElement(ID, player, world, x, y, z), chestTexture);
            case 1:
                return new GuiGuide((ContainerGuide)getServerGuiElement(ID, player, world, x, y, z), world, pageTexture, flippedPageTexture);
            case 2:
                return new GuiJewelry(new ContainerJewelryTab(player, player.inventory, new JewelryInventory(player)), jewelryInvTexture);
            default:
                return null;
        }
    }
}
