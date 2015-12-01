package darkknight.jewelrycraft.client;

import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class JewelryInventory implements IInventory
{
    public EntityPlayer player;
    public ItemStack[] inventory = new ItemStack[18];
    
    /**
     * @param player
     */
    public JewelryInventory(EntityPlayer player)
    {
        this.player = player;
        NBTTagCompound nbt = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        for(int i = 0; i < 18; i++)
            inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("ext" + i));
    }
    
    /**
     * @return
     */
    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }
    
    /**
     * @param slot
     * @return
     */
    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory[slot];
    }
    
    /**
     * @param slot
     * @param amount
     * @return
     */
    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) if (stack.stackSize > amount){
            stack = stack.splitStack(amount);
            markDirty();
        }else setInventorySlotContents(slot, null);
        return stack;
    }
    
    /**
     * @param slot
     * @return
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        ItemStack stack = getStackInSlot(slot);
        setInventorySlotContents(slot, null);
        return stack;
    }
    
    /**
     * @param slot
     * @param stack
     */
    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) stack.stackSize = getInventoryStackLimit();
        markDirty();
    }
    
    /**
     * @return
     */
    @Override
    public String getInventoryName()
    {
        return "Jewelry";
    }
    
    /**
     * @return
     */
    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }
    
    /**
     * @return
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }
    
    /**
     * 
     */
    @Override
    public void markDirty()
    {
        NBTTagCompound nbt = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
        for(int i = 0; i < 18; i++)
            if (inventory[i] != null) nbt.setTag("ext" + i, inventory[i].writeToNBT(nbt.getCompoundTag("ext" + i)));
            else nbt.removeTag("ext" + i);
    }
    
    /**
     * @param player
     * @return
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return true;
    }
    
    /**
     * 
     */
    @Override
    public void openInventory()
    {}
    
    /**
     * 
     */
    @Override
    public void closeInventory()
    {}
    
    /**
     * @param slot
     * @param stack
     * @return
     */
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        if (slot >= 0 && slot <= 9 && stack.getItem() instanceof ItemRing) return true;
        else if (slot >= 10 && slot <= 13 && stack.getItem() instanceof ItemBracelet) return true;
        else if (slot >= 14 && slot <= 16 && stack.getItem() instanceof ItemNecklace) return true;
        else if (slot == 17 && stack.getItem() instanceof ItemEarrings) return true;
        return false;
    }
}