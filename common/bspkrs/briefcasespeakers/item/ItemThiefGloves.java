package bspkrs.briefcasespeakers.item;

import java.util.Iterator;
import java.util.Random;

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
            boolean needsInitilization = (Boolean) ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "needsInitilization", "field_70959_by");
            int timeUntilReset = (Integer) ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "timeUntilReset", "field_70961_j");
            MerchantRecipeList buyingList = (MerchantRecipeList) ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "buyingList", "field_70963_i");
            if(buyingList!=null)
            {
                Iterator iterator = buyingList.iterator();
                while(iterator.hasNext())
                {
                    MerchantRecipe recipe = (MerchantRecipe)iterator.next();
                    while(!recipe.func_82784_g())
                    {
                        //par2EntityPlayer.inventory.addItemStackToInventory(recipe.getItemToSell());
                        //villager.entityDropItem(recipe.getItemToSell(), 0);
                        villager.dropItem(recipe.getItemToSell().itemID, recipe.getItemToSell().stackSize);
                        recipe.incrementToolUses();                         
                    }
                    //villager.useRecipe(recipe);
                    ReflectionHelper.setPrivateValue(EntityVillager.class, villager, 300, "timeUntilReset", "field_70961_j");
                    ReflectionHelper.setPrivateValue(EntityVillager.class, villager, true, "needsInitilization", "field_70959_by");
                }    
            }         
            villager.dropItem(Item.emerald.itemID, wealth);
            ReflectionHelper.setPrivateValue(EntityVillager.class, villager, 0, "wealth", "field_70956_bz");
            return true;
        }
        else
        {
            return super.itemInteractionForEntity(par1ItemStack, par2EntityPlayer, par3EntityLivingBase);
        }
    }
    
}
