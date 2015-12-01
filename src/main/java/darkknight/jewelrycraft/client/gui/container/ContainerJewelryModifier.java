package darkknight.jewelrycraft.client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerJewelryModifier extends Container
{    
    public IInventory modInv;
    public ContainerJewelryModifier(InventoryPlayer inv, IInventory mod)
    {        
        int x, y;
        modInv = mod;
        for(x = 0; x < 9; x++)
            addSlotToContainer(new Slot(inv, x, 26 + 18 * x, 225));
        for(y = 0; y < 3; y++)
            for(x = 0; x < 9; x++)
                addSlotToContainer(new Slot(inv, x + 9 + y * 9, 26 + 18 * x, 167 + y * 18));
        addSlotToContainer(new Slot(mod, 36, 37, 9));
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
        Slot slot = (Slot)inventorySlots.get(par2);
        if (slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (par2 < 27){
                if (!mergeItemStack(itemstack1, 27, inventorySlots.size(), true)) return null;
            }else if (!mergeItemStack(itemstack1, 0, 27, false)) return null;
            if (itemstack1.stackSize == 0) slot.putStack((ItemStack)null);
            else slot.onSlotChanged();
        }
        return itemstack;
    }
}
