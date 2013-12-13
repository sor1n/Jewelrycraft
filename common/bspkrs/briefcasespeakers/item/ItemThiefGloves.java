package bspkrs.briefcasespeakers.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

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
            Tuple tuple = (Tuple)entityliving.villagerStockList.get(Integer.valueOf(1));
            entityliving.dropItem(((Integer)tuple.getFirst()).intValue(), 1);
            return true;
        }
        else
        {
            return super.itemInteractionForEntity(par1ItemStack, par2EntityPlayer, par3EntityLivingBase);
        }
    }
    
}
