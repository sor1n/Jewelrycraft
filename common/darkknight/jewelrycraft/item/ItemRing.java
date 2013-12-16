package darkknight.jewelrycraft.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemRing extends ItemBase
{
    public ItemRing(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
    }
    
    public static void addMetal(ItemStack item, ItemStack metal)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound ingotNBT = new NBTTagCompound();
        metal.writeToNBT(ingotNBT);
        itemStackData.setTag("ingot", ingotNBT);
    }
    
    public static void addEffect(ItemStack item, PotionEffect effect)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound effectNBT = new NBTTagCompound();
        effect.writeCustomPotionEffectToNBT(effectNBT);
        itemStackData.setTag("effect", effectNBT);
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().hasKey("ingot"))
            {
                NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("ingot");
                ItemStack ingotStack = new ItemStack(0, 0, 0);
                ingotStack.readFromNBT(ingotNBT);
                list.add(EnumChatFormatting.GRAY + ingotStack.getDisplayName());
            }
            
            if (stack.getTagCompound().hasKey("effect"))
            {
                NBTTagCompound effectNBT = (NBTTagCompound) stack.getTagCompound().getTag("effect");
                PotionEffect effect = new PotionEffect(0, 0);
                effect.readCustomPotionEffectFromNBT(effectNBT);
                list.add(EnumChatFormatting.GREEN + effect.getEffectName());
            }
        }
    }
    
    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5) 
    {
        if(stack.getTagCompound().hasKey("effect"))
        {
            NBTTagCompound effectNBT = (NBTTagCompound) stack.getTagCompound().getTag("effect");
            PotionEffect effect = new PotionEffect(0, 0);
            effect.readCustomPotionEffectFromNBT(effectNBT);
            ((EntityPlayer)par3Entity).addPotionEffect(effect);
            
        }
    }
}
