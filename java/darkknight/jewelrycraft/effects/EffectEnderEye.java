package darkknight.jewelrycraft.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.util.JewelryNBT;

public class EffectEnderEye extends ModifierEffects
{
    public EffectEnderEye()
    {
        super(new ItemStack(Items.ender_eye));
    }
    
    @Override
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        if (jewelry instanceof ItemNecklace && pos != -1) ;
    }
    
    @Override
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        return false;
    }
    
    @Override
    public boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        return false;
    }
  
    @Override
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
    }
    
    @Override
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
    }
    
    @Override
    public boolean onPlayerFall(ItemStack item, EntityPlayer player, Item jewelry)
    {
        return false;
    }
}
