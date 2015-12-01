package darkknight.jewelrycraft.api;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ModifierEffects
{
    protected ItemStack modifier;
    protected Random rand = new Random();
    protected static ArrayList<ModifierEffects> effects = new ArrayList<ModifierEffects>();
    
    /**
     * @param modifier The item to use as modifier
     */
    public ModifierEffects(ItemStack modifier)
    {
        this.modifier = modifier;
        effects.add(this);
    }
    
    /**
     * @return The list of all effects registered
     */
    public static ArrayList<ModifierEffects> getEffects()
    {
        return effects;
    }
    
    /**
     * @return The ItemStack set as the modifier
     */
    public ItemStack getModifier()
    {
        return modifier;
    }
    
    /**
     * This runs every tick
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param player The player wearing the jewelry wearing a jewelry with this modifier on it
     * @param jewelry The actual jewelry item (used by me to determine the type of jewelry so I don't have to call item.getItem() blah blah blah)
     */
    public void action(ItemStack item, EntityPlayer player, Item jewelry)
    {};
    
    /**
     * This runs when an entity is attacked. This event can be canceled.
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param player The player wearing the jewelry wearing a jewelry with this modifier on it
     * @param target The attacked entity
     * @param jewelry The actual jewelry item (aka item.getItem(), almost)
     * @param amount The amount of damage the entity took
     * @return The state of the event (true to cancel it, false to not)
     */
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {
        return false;
    }
    
    /**
     * This runs when a player gets damaged. This event can be canceled.
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param player The attacked player wearing a jewelry with this modifier on it
     * @param source The source of the damage
     * @param jewelry The actual jewelry item (aka item.getItem(), almost)
     * @param amount The amount of damage the player took
     * @return The state of the event (true to cancel it, false to not)
     */
    public boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {
        return false;
    }
    
    /**
     * This is the same as onEntityAttackedCacellable, but this can not be canceled. I recommend using this over onEntityAttackedCacellable, as it is more reliable.
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param player The player wearing the jewelry wearing a jewelry with this modifier on it
     * @param target The attacked entity
     * @param jewelry The actual jewelry item (aka item.getItem(), almost)
     * @param amount The amount of damage the entity took
     */
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, Item jewelry, float amount)
    {}
    
    /**
     * This is just like onPlayerAttackedCacellable, only that this can not be canceled. I recommend using this over onPlayerAttackedCacellable, as it is more reliable.
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param player The attacked player wearing a jewelry with this modifier on it
     * @param source The source of the damage
     * @param jewelry The actual jewelry item (aka item.getItem(), almost)
     * @param amount The amount of damage the player took
     */
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry, float amount)
    {}
    
    /**
     * This runs when the player dies
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param player The player that died wearing a jewelry with this modifier on it
     * @param source The source of the killing blow
     * @param jewelry The actual jewelry item (aka item.getItem(), almost)
     */
    public void onPlayerDead(ItemStack item, EntityPlayer player, DamageSource source, Item jewelry)
    {}
    
    /**
     * This runs when the player respawns
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param event The PlayerEvent that runs when the player respawns (this is also called when a player moves between dimensions)
     * @param jewelry The actual jewelry item (aka item.getItem(), almost)
     */
    public void onPlayerRespawn(ItemStack item, PlayerEvent.Clone event, Item jewelry)
    {}
    
    /**
     * This runs when the item containing this modifier is equipped
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param jewelry The actual jewelry item (aka item.getItem(), almost)
     */
    public void onJewelryEquipped(ItemStack item, Item jewelry)
    {}
    
    /**
     * This runs when the item containing this modifier is unquipped
     * 
     * @param item The ItemStack representing the jewelry that runs the effect
     * @param jewelry The actual jewelry item (aka item.getItem(), almost)
     */
    public void onJewelryUnequipped(ItemStack item, Item jewelry)
    {}
}
