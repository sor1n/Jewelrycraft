package darkknight.jewelrycraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * User: joel / Date: 16.12.13 / Time: 22:36
 */
public class ContainerGuide extends Container
{
    
    public ContainerGuide()
    {
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
}