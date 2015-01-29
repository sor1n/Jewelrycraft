package darkknight.jewelrycraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.item.ItemRing;

public class SlotRing extends Slot
{
    public SlotRing(IInventory tile, int slotID, int x, int y)
    {
        super(tile, slotID, x, y);
    }
    
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof ItemRing;
    }
    
    @Override
    public ItemStack decrStackSize(int amount)
    {
        return super.decrStackSize(amount);
    }
    
    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        return true;
    }
}
