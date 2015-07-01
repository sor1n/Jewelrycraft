package darkknight.jewelrycraft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.client.JewelryInventory;
import darkknight.jewelrycraft.client.gui.container.ContainerCurseInfo;
import darkknight.jewelrycraft.client.gui.container.ContainerGuide;
import darkknight.jewelrycraft.client.gui.container.ContainerJewelryModifier;
import darkknight.jewelrycraft.client.gui.container.ContainerJewelryTab;
import darkknight.jewelrycraft.client.gui.container.ContainerRingChest;
import darkknight.jewelrycraft.util.Variables;

public class GuiHandler implements IGuiHandler
{
    ResourceLocation pageTexture = new ResourceLocation(Variables.MODID, "textures/gui/guidePage.png");
    ResourceLocation flippedPageTexture = new ResourceLocation(Variables.MODID, "textures/gui/guidePageFlip.png");
    ResourceLocation chestTexture = new ResourceLocation(Variables.MODID, "textures/gui/chest_ring.png");
    ResourceLocation jewelryInvTexture = new ResourceLocation(Variables.MODID, "textures/gui/jewelry_tab.png");
    ResourceLocation cursesInvTexture = new ResourceLocation(Variables.MODID, "textures/gui/curses_tab.png");
    ResourceLocation jewlryModTexture = new ResourceLocation(Variables.MODID, "textures/gui/jewelry_modifier.png");
    
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
            case 3:
                return new ContainerJewelryModifier(player.inventory, new InventoryBasic("ItemModifier", false, 37));
            case 4:
                return new ContainerCurseInfo();
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
            case 3:
                return new GuiJewelryModifier((ContainerJewelryModifier)getServerGuiElement(ID, player, world, x, y, z), jewlryModTexture);
            case 4:
                return new GuiCurseInfo((ContainerCurseInfo)getServerGuiElement(ID, player, world, x, y, z), world, player, cursesInvTexture);
            default:
                return null;
        }
    }
}
