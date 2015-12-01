package darkknight.jewelrycraft.util;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class JewelryNBT
{
    // TODO NBT Tag Adding
    public static void addItem(ItemStack item, ItemStack target)
    {
        if (target != null){
            NBTTagCompound itemStackData;
            if (item.hasTagCompound()) itemStackData = item.getTagCompound();
            else{
                itemStackData = new NBTTagCompound();
                item.setTagCompound(itemStackData);
            }
            NBTTagCompound targetNBT = new NBTTagCompound();
            target.writeToNBT(targetNBT);
            itemStackData.setTag("target", targetNBT);
        }
    }
    
    /**
     * @param item The item you want to add the NBT data on
     * @param metal The metal you want to add on the item
     */
    public static void addMetal(ItemStack item, ItemStack metal)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else{
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        if(metal != null){
            NBTTagCompound ingotNBT = new NBTTagCompound(); 
        	metal.writeToNBT(ingotNBT);
        	itemStackData.setTag("ingot", ingotNBT);
        }
    }
    
    /**
     * @param item The item you want to add the NBT data on
     * @param gem The gem you want to add on the item
     */
    public static void addGem(ItemStack item, ItemStack gem)
    {
        if (gem != null){
            NBTTagCompound itemStackData;
            if (item.hasTagCompound()) itemStackData = item.getTagCompound();
            else{
                itemStackData = new NBTTagCompound();
                item.setTagCompound(itemStackData);
            }
            NBTTagCompound gemNBT = new NBTTagCompound();
            gem.writeToNBT(gemNBT);
            itemStackData.setTag("gem", gemNBT);
        }
    }
    
    /**
     * @param item The item you want to add the NBT data on
     * @param modifier The modifier you want to add on the item
     */
    public static void addModifiers(ItemStack item, ArrayList<ItemStack> modifier)
    {
        if (modifier != null){
            NBTTagCompound itemStackData;
            if (item.hasTagCompound()) itemStackData = item.getTagCompound();
            else{
                itemStackData = new NBTTagCompound();
                item.setTagCompound(itemStackData);
            }
            for(int i = 0; i < modifier.size(); i++){
                NBTTagCompound modifierNBT = new NBTTagCompound();
                modifier.get(i).writeToNBT(modifierNBT);
                itemStackData.setTag("modifier" + i, modifierNBT);
            }
            itemStackData.setInteger("modifierSize", modifier.size());
        }
    }
        
    /**
     * @param item
     * @param color
     */
    public static void addIngotColor(ItemStack item, int color)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else{
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        itemStackData.setInteger("ingotColor", color);
    }
    
    // TODO
    /**
     * @param item
     * @param color
     */
    public static void addGemColor(ItemStack item, int color)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else{
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        itemStackData.setInteger("gemColor", color);
    }
    
    // TODO NTB Tag Checking
    /**
     * @param item
     * @param tag
     * @return
     */
    public static boolean hasTag(ItemStack item, String tag)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else{
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        if (itemStackData.hasKey(tag)) return true;
        return false;
    }
    
    /**
     * @param stack
     * @param gem
     * @return
     */
    public static boolean isGemX(ItemStack stack, ItemStack gem)
    {
        if (gem(stack) != null && gem(stack).getItem() == gem.getItem() && gem(stack).getItemDamage() == gem.getItemDamage()) return true;
        return false;
    }
    
    /**
     * @param stack
     * @param modifier
     * @return
     */
    public static boolean doesModifierExist(ItemStack stack, ItemStack modifier)
    {
        if (modifier(stack) != null){
            ArrayList<ItemStack> list = modifier(stack);
            for(int i = 0; i < list.size(); i++)
                if (list.get(i).getItem() == modifier.getItem() && list.get(i).getItemDamage() == modifier.getItemDamage()) return true;
        }
        return false;
    }
    
    public static int modifierSize(ItemStack stack, ItemStack modifier)
    {
        if (modifier(stack) != null){
            ArrayList<ItemStack> list = modifier(stack);
            for(int i = 0; i < list.size(); i++)
                if (list.get(i).getItem() == modifier.getItem() && list.get(i).getItemDamage() == modifier.getItemDamage()) return list.get(i).stackSize;
        }
        return -1;
    }
    
    public static int numberOfModifiers(ItemStack stack)
    {
        if (modifier(stack) != null) return modifier(stack).size();
        return -1;
    }    
    
    /**
     * @param stack
     * @param ingot
     * @return
     */
    public static boolean isIngotX(ItemStack stack, ItemStack ingot)
    {
        if (ingot(stack) != null && ingot(stack).getItem() == ingot.getItem() && ingot(stack).getItemDamage() == ingot.getItemDamage()) return true;
        return false;
    }
    
    // TODO Return components based on NBT
    public static ItemStack item(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("target")){
            NBTTagCompound itemNBT = (NBTTagCompound)stack.getTagCompound().getTag("target");
            ItemStack target = new ItemStack(Item.getItemById(0), 0, 0);
            target.readFromNBT(itemNBT);
            return target;
        }
        return null;
    }
    
    /**
     * @param stack
     * @return
     */
    public static ItemStack gem(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("gem")){
            NBTTagCompound jewelNBT = (NBTTagCompound)stack.getTagCompound().getTag("gem");
            ItemStack gem = new ItemStack(Item.getItemById(0), 0, 0);
            gem.readFromNBT(jewelNBT);
            return gem;
        }
        return null;
    }
    
    /**
     * @param stack
     * @return
     */
    public static ArrayList<ItemStack> modifier(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound()){
            int size = stack.getTagCompound().getInteger("modifierSize");
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            for(int i = 0; i < size; i++){
                ItemStack modifier = new ItemStack(Item.getItemById(0), 0, 0);
                NBTTagCompound modifierNBT = (NBTTagCompound)stack.getTagCompound().getTag("modifier" + i);
                modifier.readFromNBT(modifierNBT);
                list.add(modifier);
            }
            return list;
        }
        return null;
    }
    
    /**
     * @param stack
     * @return
     */
    public static ItemStack ingot(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("ingot")){
            NBTTagCompound ingotNBT = (NBTTagCompound)stack.getTagCompound().getTag("ingot");
            ItemStack ingot = new ItemStack(Item.getItemById(0), 0, 0);
            ingot.readFromNBT(ingotNBT);
            return ingot;
        }
        return null;
    }
    
    /**
     * @param stack
     * @return
     */
    public static int ingotColor(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("ingotColor"))
            return stack.getTagCompound().getInteger("ingotColor");
        return 16777215;
    }
    
    // TODO
    /**
     * @param stack
     * @return
     */
    public static int gemColor(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("gemColor"))
            return stack.getTagCompound().getInteger("gemColor");
        return 16777215;
    }
    
}
