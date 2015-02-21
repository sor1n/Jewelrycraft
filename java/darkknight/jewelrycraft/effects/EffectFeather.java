package darkknight.jewelrycraft.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.util.JewelryNBT;

public class EffectFeather extends ModifierEffects
{
    public EffectFeather()
    {
        super(new ItemStack(Items.feather));
    }
    
    @Override
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        // Positive necklace
        if (jewelry instanceof ItemNecklace && pos != -1 && !player.onGround){
            player.motionY = -10D;
            System.out.println(player.motionY);
        }
    }
    
    @Override
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        // if (jewelry instanceof ItemNecklace && pos != -1) player.fallDistance *= 0.5F;
        if (jewelry instanceof ItemBracelet && pos != -1) return true;
        return false;
    }
    
    @Override
    public boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        // Positive bracelet
        if (jewelry instanceof ItemBracelet && pos != -1) return true;
        return true;
    }
    
    @Override
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {}
    
    @Override
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        // Negative necklace
        if (jewelry instanceof ItemNecklace && pos != -1 && source != DamageSourceList.weak) player.attackEntityFrom(DamageSourceList.weak, amount * 2F);
    }
    
    @Override
    public boolean onPlayerFall(ItemStack item, EntityPlayer player, Item jewelry)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        return false;
    }
}
