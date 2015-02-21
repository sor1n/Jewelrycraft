package darkknight.jewelrycraft.effects;

import java.util.ArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public abstract class ModifierEffects
{
    protected ItemStack modifier;
    protected static ArrayList<ModifierEffects> effects = new ArrayList<ModifierEffects>();
    
    /**
     * @param modifier
     */
    public ModifierEffects(ItemStack modifier)
    {
        this.modifier = modifier;
        effects.add(this);
    }
    
    /**
     * @return
     */
    public static ArrayList<ModifierEffects> getEffects()
    {
        return effects;
    }
    
    /**
     * @param item
     * @param player
     * @param jewelry
     */
    public abstract void action(ItemStack item, EntityPlayer player, Item jewelry);
    
    /**
     * @param item
     * @param player
     * @param target
     * @param jewelry
     * @return
     */
    public abstract boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount);
    
    /**
     * @param item
     * @param player
     * @param source
     * @param jewelry
     * @return
     */
    public abstract boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount);
    /**
     * @param item
     * @param player
     * @param target
     * @param jewelry
     * @return
     */
    public abstract void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount);
    
    /**
     * @param item
     * @param player
     * @param source
     * @param jewelry
     * @return
     */
    public abstract void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount);
    
    /**
     * @param item
     * @param player
     * @param source
     * @param jewelry
     * @return
     */
    public abstract boolean onPlayerFall(ItemStack item, EntityPlayer player, Item jewelry);
}
