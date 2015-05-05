package darkknight.jewelrycraft.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerCurseInfo extends Container
{
    public ContainerCurseInfo()
    {}
    
    @Override
    public void addCraftingToCrafters(ICrafting c)
    {
        return;
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
}