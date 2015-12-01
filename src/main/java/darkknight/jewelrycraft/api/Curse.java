package darkknight.jewelrycraft.api;

import java.util.ArrayList;
import java.util.Random;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.world.BlockEvent;

public abstract class Curse
{
    protected int textureID, ticksActive;
	protected String name, description, texturePackName;
    protected Random rand = new Random();
    private static ArrayList<Curse> curses = new ArrayList<Curse>();
    public static ArrayList<Curse> availableCurses = new ArrayList<Curse>();
    
    /**
     * @param name The name of the curse
     * @param txtID The texture ID of it
     * @param texturepack The texturepack location
     */
    protected Curse(String name, int txtID, String texturepack)
    {
        this.name = name;
        this.texturePackName = texturepack;
        this.textureID = txtID;
        this.ticksActive = 0;
        curses.add(this);
        availableCurses.add(this);
    }
    
    /**
     * @return The name of the curse
     */
    public String getName()
    {
        return name;
    }
    
    public abstract String getDisplayName();

    /**
     * @return The description of the curse
     */
    public String getDescription()
    {
        return description;
    }
    
    /**     
     * @param The description of the curse
     * @return The class
     */
    public Curse setDescription(String desc)
    {
        description = desc;
        return this;
    }
    
    /**
     * @return the texture pack ID
     */
    public String getTexturePack()
    {
        return texturePackName;
    }
    
    /**
     * @return The texture ID
     */
    public int getTextureID()
    {
        return textureID;
    }
    
    /**
     * This runs every tick
     * @param world The worldthe player is in
     * @param player The cursed player
     */
    public void action(World world, EntityPlayer player)
    {
    	ticksActive++;
    }
    
    public int getTicksActive() 
    {
		return ticksActive;
	}

	public void setTicksActive(int ticksActive) 
	{
		this.ticksActive = ticksActive;
	}

    /**
     * This runs when the player dies
     * @param world The world the player is in
     * @param player The cursed player
     */
    public void playerDeathAction(World world, EntityPlayer player)
    {}
    
    /**
     * This runs when an entity is killed (whether by a player or other causes)
     * @param world The world the player is in
     * @param target The entity that died
     * @param player The cursed player
     */
    public void entityDeathAction(World world, EntityLivingBase target, EntityPlayer player)
    {}

    /**
     * This runs when the player respawns
     * @param world The world the player is in
     * @param player The cursed player
     */
    public void respawnAction(World world, EntityPlayer player)
    {}

    /**
     * This runs when a player gets attacked by anything but another player
     * @param world The world the player is in
     * @param player The cursed player
     */
    public void attackedAction(World world, EntityPlayer player)
    {}

    /**
     * This runs when an entity is attacked by a player (this includes other players)
     * @param world The world the player is in
     * @param player The player that caused the damage
     * @param target The entity damaged
     */
    public void attackedByPlayerAction(LivingAttackEvent event, World world, EntityPlayer player, Entity target)
    {}
    
    /**
     * This runs when an entity is attacked by a player (this includes other players). This can cancel the damaging event.
     * @param world The world the player is in
     * @param player The player that caused the damage
     * @param target The entity damaged
     */
    public boolean attackedByPlayerActionCancelable(LivingAttackEvent event, World world, EntityPlayer player, Entity target)
    {
    	return false;
    }
    
    /**
     * This runs when an item is dropped by an entity
     * @param player The cursed player
     * @param target The entity that drops the item
     * @param drops An array list containing the dropped items
     */
    public void entityDropItems(EntityPlayer player, Entity target, ArrayList<EntityItem> drops)
    {}

    /**
     * This is for rendering special things on the player in 3rd person (meaning you won't see this render); 
     * This will also render in the inventory.
     * @param player The cursed player
     * @param event The event used for this
     */
    @SideOnly(Side.CLIENT)
    public void playerRender(EntityPlayer player, RenderPlayerEvent.Specials.Post event)
    {}

    /**
     * This is for rendering special things on the player in 1st and 3rd person, but not in the player inventory.
     * @param player The cursed player
     * @param event The event used for this
     */
    @SideOnly(Side.CLIENT)
    public void playerHandRender(EntityPlayer player, RenderHandEvent event)
    {}
    
    /**
     * This runs when a block drops an item (such as breaking a block and dropping itself, or breaking like a Diamond ore and dropping diamonds)
     * @param player The cursed player
     * @param event The BlockEvent that triggers this
     */
    public void onBlockItemsDrop(EntityPlayer player, BlockEvent.HarvestDropsEvent event)
    {}
    
    /**
     * This runs when a block is destroyed
     * @param player The cursed player
     * @param event The BlockEvent that runs this
     */
    public void onBlockDestroyed(EntityPlayer player, BlockEvent.BreakEvent event)
    {}
    
    /**
     * This determines if the player can toss an item or not (with Q)
     * @return True if the player can no longer toss items; False if he can. (by default false)
     */
    public boolean itemToss()
    {
        return false;
    }
    
    /**
     * This controls whether a curse can be activated or not (makes it easy to do things like disable certain curses if in hardcore)
     * @param world The world the player is in
     * @return True for curses to be used; False otherwise (by default true)
     */
    public boolean canCurseBeActivated(World world)
    {
        return canCurseBeActivated();
    }
    /**
     * This controls whether a curse can be activated or not (makes it easy to do things like disable certain curses if in hardcore)
     * @return True for curses to be used; False otherwise (by default true)
     */
    public boolean canCurseBeActivated()
    {
        return true;
    }

    /**
     * This is the list of all curses
     * @return The list of registered curses
     */
    public static ArrayList<Curse> getCurseList()
    {
        return curses;
    }
    
    /**
     * This is the weight of the curse when it chooses it. The lower the value, the less it gets chosen; the higher the value, the higher the chance of it getting picked.
     * @param world The world the player is currently in
     * @param player The cursed player
     * @param random A random class
     * @return The weight of the curse (by default 10)
     */
    public int weight(World world, EntityPlayer player, Random random)
    {
        return 10;
    }
    
    /*
     * This stat is used to determine the chance of certain actions happening
     */
    public int luck()
    {
        return 0;
    }
}
