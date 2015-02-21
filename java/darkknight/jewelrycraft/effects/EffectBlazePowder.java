package darkknight.jewelrycraft.effects;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import darkknight.jewelrycraft.item.ItemBracelet;
import darkknight.jewelrycraft.item.ItemEarrings;
import darkknight.jewelrycraft.item.ItemNecklace;
import darkknight.jewelrycraft.item.ItemRing;
import darkknight.jewelrycraft.util.JewelryNBT;

public class EffectBlazePowder extends ModifierEffects
{
    public EffectBlazePowder()
    {
        super(new ItemStack(Items.blaze_powder));
    }
    
    @Override
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        if (jewelry instanceof ItemNecklace && pos != -1){
            //Positive for necklace
            player.extinguish();
            
            //Negative for necklace
            if (player.isInWater()) player.attackEntityFrom(DamageSource.drown, 1f);
        }
        //Negative for bracelet
        if (jewelry instanceof ItemBracelet && pos != -1) if(player.isInWater()) player.fallDistance *= 0.8F;
            
        //Negative for earrings
        if (jewelry instanceof ItemEarrings && pos != -1){
            if (player.getAir() >= 300) player.setAir(player.getAir() / 2);
            else player.setAir(player.getAir() - 1);
            
        }
        
    }
    
    @Override
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        //Balanced for ring
        if (jewelry instanceof ItemRing && pos != -1 && !player.isInWater()) target.setFire(2);
        return false;
    }
    
    @Override
    public boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        int pos = JewelryNBT.doesModifierExist(item, modifier);
        if (jewelry instanceof ItemEarrings && pos != -1 && player.worldObj.rand.nextInt(4) == 0) if (source == DamageSource.lava || source == DamageSource.inFire || source == DamageSource.onFire){
            //Positive for earrings
            int stackSize = JewelryNBT.modifier(item).get(pos).stackSize;
            player.heal((float)(0.05 * stackSize));
            return true;
        }
        //Positive for bracelet
        if (jewelry instanceof ItemBracelet && pos != -1) if (source == DamageSource.inFire || source == DamageSource.onFire || source == DamageSource.lava && player.worldObj.isMaterialInBB(AxisAlignedBB.getBoundingBox(player.boundingBox.minX, player.boundingBox.minY, player.boundingBox.minZ, player.boundingBox.maxX, player.boundingBox.maxY - 0.7, player.boundingBox.maxZ), Material.lava) && !player.worldObj.isMaterialInBB(AxisAlignedBB.getBoundingBox(player.boundingBox.minX, player.boundingBox.minY + 0.9, player.boundingBox.minZ, player.boundingBox.maxX, player.boundingBox.maxY, player.boundingBox.maxZ), Material.lava)) return true;
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
