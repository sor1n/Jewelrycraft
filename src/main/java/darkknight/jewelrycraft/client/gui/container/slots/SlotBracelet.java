package darkknight.jewelrycraft.client.gui.container.slots;

import darkknight.jewelrycraft.api.IJewelryItem;
import darkknight.jewelrycraft.item.ItemBracelet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBracelet extends Slot
{
    
    /**
     * @param tile
     * @param slotID
     * @param x
     * @param y
     */
    public SlotBracelet(IInventory tile, int slotID, int x, int y)
    {
        super(tile, slotID, x, y);
    }
    
    /**
     * @param stack
     * @return
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof ItemBracelet || (stack.getItem() instanceof IJewelryItem && ((IJewelryItem)stack.getItem()).type() == 1);
    }
    
    /**
     * @param amount
     * @return
     */
    @Override
    public ItemStack decrStackSize(int amount)
    {
        return super.decrStackSize(amount);
    }
    
    /**
     * @param player
     * @return
     */
    @Override
    public boolean canTakeStack(EntityPlayer player)
    {
        return true;
    }
}
