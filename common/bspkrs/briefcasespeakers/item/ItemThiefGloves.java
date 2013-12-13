package bspkrs.briefcasespeakers.item;

import java.lang.reflect.Field;
import java.util.Iterator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;

public class ItemThiefGloves extends Item
{
    public ItemThiefGloves(int par1)
    {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
    {
        if (par3EntityLivingBase instanceof EntityVillager)
        {
            EntityVillager entityliving = (EntityVillager)par3EntityLivingBase;
            MerchantRecipe recipe = null;            
            entityliving.dropItem(recipe.getItemToSell().itemID, recipe.getItemToSell().stackSize);
            return true;
        }
        else
        {
            return super.itemInteractionForEntity(par1ItemStack, par2EntityPlayer, par3EntityLivingBase);
        }
    }
    
}
