package darkknight.jewelrycraft.client.gui.container;

import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.IJewelryItem;
import darkknight.jewelrycraft.client.gui.container.slots.SlotBracelet;
import darkknight.jewelrycraft.client.gui.container.slots.SlotEarrings;
import darkknight.jewelrycraft.client.gui.container.slots.SlotNecklace;
import darkknight.jewelrycraft.client.gui.container.slots.SlotRing;
import darkknight.jewelrycraft.item.ItemBaseJewelry;
import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.item.ItemRing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerJewelryTab extends Container
{
    public ContainerJewelryTab(EntityPlayer player, IInventory inv, IInventory extra)
    {
        int x, y;
        // Rings
        for(x = 0; x <= 9; x++)
            addSlotToContainer(new SlotRing(extra, x, 8 + x * 18, 7));
        // Bracelets
        for(x = 10; x <= 13; x++)
            addSlotToContainer(new SlotBracelet(extra, x, 8 + (x - 10) * 18, 26));
        // Necklaces
        for(x = 14; x <= 16; x++)
            addSlotToContainer(new SlotNecklace(extra, x, 8 + (x - 14) * 18, 45));
        // Earrings
        addSlotToContainer(new SlotEarrings(extra, 17, 8, 64));
        // Hotbar
        for(x = 0; x < 9; ++x)
            addSlotToContainer(new Slot(inv, x, 17 + x * 18, 142));
        // Inventory
        for(x = 0; x < 3; ++x)
            for(y = 0; y < 9; ++y)
                addSlotToContainer(new Slot(inv, 9 + y + x * 9, 17 + y * 18, 84 + x * 18));
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }
    
    public ItemStack slotClick(int slotID, int j, int k, EntityPlayer player)
    {
        if (slotID >= 0 && slotID <= 17 && !player.worldObj.isRemote) {
            try{
                if (player.inventory.getItemStack() == null && inventoryItemStacks.get(slotID) != null && ((ItemStack)inventoryItemStacks.get(slotID)).getItem() instanceof ItemBaseJewelry)
                    ((ItemBaseJewelry)((ItemStack)inventoryItemStacks.get(slotID)).getItem()).onJewelryUnequipped((ItemStack)inventoryItemStacks.get(slotID));
                else if (player.inventory.getItemStack() != null && player.inventory.getItemStack().getItem() instanceof ItemBaseJewelry && inventoryItemStacks.get(slotID) == null)
                    ((ItemBaseJewelry)player.inventory.getItemStack().getItem()).onJewelryEquipped(player.inventory.getItemStack());
                if (player.inventory.getItemStack() == null && inventoryItemStacks.get(slotID) != null && ((ItemStack)inventoryItemStacks.get(slotID)).getItem() instanceof IJewelryItem)
                    ((IJewelryItem)((ItemStack)inventoryItemStacks.get(slotID)).getItem()).onJewelryUnequipped((ItemStack)inventoryItemStacks.get(slotID));
                else if (player.inventory.getItemStack() != null && player.inventory.getItemStack().getItem() instanceof IJewelryItem && inventoryItemStacks.get(slotID) == null)
                    ((IJewelryItem)player.inventory.getItemStack().getItem()).onJewelryEquipped(player.inventory.getItemStack());
            }
            catch(Exception e){
                JewelrycraftMod.logger.error("An error has occured while equipping an item.");
                e.printStackTrace();
            }
        }
        return super.slotClick(slotID, j, k, player);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(slotID);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (slotID >= 18) {
                if (itemstack.getItem() instanceof ItemRing || (itemstack.getItem() instanceof IJewelryItem && ((IJewelryItem)itemstack.getItem()).type() == 0)) {
                    if (!mergeItemStack(itemstack, 0, 10, false) && !slot.getHasStack())
                        return null;
                }else  if (itemstack.getItem() instanceof ItemBracelet || (itemstack.getItem() instanceof IJewelryItem && ((IJewelryItem)itemstack.getItem()).type() == 1)) {
                    if (!mergeItemStack(itemstack, 10, 14, false) && !slot.getHasStack())
                        return null;
                }else if (itemstack.getItem() instanceof ItemNecklace || (itemstack.getItem() instanceof IJewelryItem && ((IJewelryItem)itemstack.getItem()).type() == 2)) {
                    if (!mergeItemStack(itemstack, 14, 17, false) && !slot.getHasStack())
                        return null;
                }else if (itemstack.getItem() instanceof ItemEarrings || (itemstack.getItem() instanceof IJewelryItem && ((IJewelryItem)itemstack.getItem()).type() == 3)) {
                    if (!mergeItemStack(itemstack, 17, 18, false) && !slot.getHasStack())
                        return null;
                }else{
                    if (slotID < 27) {
                        if (!mergeItemStack(itemstack, 27, 36 + 18, false))
                            return null;
                    }else{
                        if (!mergeItemStack(itemstack, 18, 27, false))
                            return null;
                    }
                }
            }else
                if (!mergeItemStack(itemstack, 18, inventorySlots.size(), false))
                    return null;
            if (itemstack.stackSize == 0)
                slot.putStack(null);
            else slot.onSlotChanged();
            if (itemstack.stackSize != itemstack.stackSize)
                slot.onPickupFromSlot(player, itemstack);
            else return null;
        }
        return itemstack;
    }
}
