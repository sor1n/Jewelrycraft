/**
 * 
 */
package darkknight.jewelrycraft.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * @author Sorin
 *
 */
public interface IJewelryItem
{
    /** 
     * @return Returns the id of the type, 0 is for ring, 1 is for bracelet, 2 is for necklace and 3 is for earrings
     */
    public int type();
    
    /**
     * This is the action performed each player tick
     * @param item The item stack of the item (allows for fine item search, like metadata and nbt)
     * @param player The player wearing the jewelry
     */
    public void onWearAction(ItemStack item, EntityPlayer player);
    
    /**
     * This performs an action whenever a player gets attacked by an entity besides another Player
     * @param item The item stack of the item (allows for fine item search, like metadata and nbt)
     * @param player The player that was attacked
     * @param source Source of the damage
     * @param amount The amount of damage taken
     */
    public void onPlayerAttackedAction(ItemStack item, EntityPlayer player, DamageSource source, float amount);
    
    /**
     * This does an action whenever an Entity gets attacked by a player, this includes other Players
     * @param item The item stack of the item (allows for fine item search, like metadata and nbt)
     * @param player The attacking player
     * @param entity The target entity
     * @param amount The amount of damage dealt
     */
    public void onEntityAttackedByPlayer(ItemStack item, EntityPlayer player, EntityLivingBase entity, float amount);
    
    /**
     * This runs whenever a player dies
     * @param item The item stack of the item (allows for fine item search, like metadata and nbt)
     * @param player The player that died
     * @param source The damage source that caused the death 
     */
    public void onPlayerDeadAction(ItemStack item, EntityPlayer player, DamageSource source);
    
    /**
     * Runs whenever a player respawns (switches dimensions or actually respawns)
     * @param item The item stack of the item (allows for fine item search, like metadata and nbt)
     * @param player The player that respawns
     * @param event The clone event that runs whenever a player respawns, either because he died or switched dimensions
     */
    public void onPlayerRespawnAction(ItemStack item, PlayerEvent.Clone event);
}
