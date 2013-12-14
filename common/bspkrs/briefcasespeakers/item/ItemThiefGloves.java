package bspkrs.briefcasespeakers.item;

import java.util.Iterator;
import java.util.Random;

import cpw.mods.fml.common.registry.VillagerRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

public class ItemThiefGloves extends ItemBase
{
    public Random rand;
    public ItemThiefGloves(int par1)
    {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean itemInteractionForEntity(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, EntityLivingBase par3EntityLivingBase)
    {
        if (par3EntityLivingBase instanceof EntityVillager)
        {
            EntityVillager villager = (EntityVillager) par3EntityLivingBase;
            int wealth = (Integer) ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "wealth", "field_70956_bz");
            MerchantRecipeList buyingList = (MerchantRecipeList) ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "buyingList", "field_70963_i");
            if(buyingList!=null)
            {
                Iterator iterator = buyingList.iterator();
                while(iterator.hasNext())
                {
                    MerchantRecipe recipe = (MerchantRecipe)iterator.next();
                    while(!recipe.func_82784_g())
                    {
                        villager.dropItem(recipe.getItemToSell().itemID, recipe.getItemToSell().stackSize);
                        villager.dropItem(Item.emerald.itemID, wealth);
                        ReflectionHelper.setPrivateValue(EntityVillager.class, villager, 0, wealth);
                        recipe.incrementToolUses();                        
                    }                    
                }    
            }
            return true;
        }
        else
        {
            return super.itemInteractionForEntity(par1ItemStack, par2EntityPlayer, par3EntityLivingBase);
        }
    }
    
}
