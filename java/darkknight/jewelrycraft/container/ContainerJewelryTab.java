package darkknight.jewelrycraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.JewelrycraftMod;

public class ContainerJewelryTab extends Container
{
    public ContainerJewelryTab(EntityPlayer player, InventoryPlayer inv)
    {
        int x, y;
        // Rings
        for (x = 0; x <= 9; x++)
            this.addSlotToContainer(new SlotRing(new JewelryInventory(), x, 8 + x * 18, 7));

        // Hotbar
        for (x = 0; x < 9; ++x)
            this.addSlotToContainer(new Slot(inv, x, 17 + x * 18, 142));
        
        // Inventory
        for (x = 0; x < 3; ++x)
            for (y = 0; y < 9; ++y)
                this.addSlotToContainer(new Slot(inv, 9 + y + x * 9, 17 + y * 18, 84 + x * 18));
        
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);
        
//        if (slot != null && slot.getHasStack())
//        {
//            ItemStack itemstack1 = slot.getStack();
//            itemstack = itemstack1.copy();
//            
//            if (par2 < 27)
//            {
//                if (!this.mergeItemStack(itemstack1, 27, this.inventorySlots.size(), true)) { return null; }
//            }
//            else if (!this.mergeItemStack(itemstack1, 0, 27, false)) { return null; }
//            
//            if (itemstack1.stackSize == 0)
//            {
//                slot.putStack((ItemStack) null);
//            }
//            else
//            {
//                slot.onSlotChanged();
//            }
//        }
//        
        return itemstack;
    }
}
