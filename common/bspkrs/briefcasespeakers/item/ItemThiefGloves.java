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
            MerchantRecipeList buyingList = (MerchantRecipeList) ReflectionHelper.getPrivateValue(EntityVillager.class, villager, "buyingList", "field_70963_i");
            if(buyingList!=null)
            {
                Iterator<?> iterator = buyingList.iterator();
                while(iterator.hasNext())
                {
                    MerchantRecipe recipe = (MerchantRecipe)iterator.next();
                    int quantity;
                    if(recipe.getItemToSell().isStackable()) quantity = recipe.getItemToSell().stackSize * 7;
                    else quantity = 1;
                    ItemStack s = new ItemStack(recipe.getItemToSell().itemID, quantity, recipe.getItemToSell().getItemDamage());
                    s.setTagCompound(recipe.getItemToSell().getTagCompound());
                    if(par2EntityPlayer.inventory.addItemStackToInventory(s));
                    else villager.entityDropItem(s, 0);
                    par2EntityPlayer.addChatMessage("Villager #" + villager.getProfession() + ": Hmmm... I seem to have lost my " + s.getDisplayName() + "!");
                }    
                buyingList.clear();
                ReflectionHelper.setPrivateValue(EntityVillager.class, villager, 300, "timeUntilReset", "field_70961_j");
                ReflectionHelper.setPrivateValue(EntityVillager.class, villager, true, "needsInitilization", "field_70959_by");
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
