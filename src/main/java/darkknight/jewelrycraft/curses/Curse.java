package darkknight.jewelrycraft.curses;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class Curse
{
    protected int textureID;
    protected String name, description, texturePackName;
    protected Random rand = new Random();
    private static ArrayList<Curse> curses = new ArrayList<Curse>();
    public static ArrayList<Curse> availableCurses = new ArrayList<Curse>();
    
    /**
     * @param id the ID of the curse
     * @param name the name of the curse
     * @param texturepack the ID of the pack the texture is located in
     */
    protected Curse(String name, int txtID, String texturepack)
    {
        this.name = name;
        this.texturePackName = texturepack;
        this.textureID = txtID;
        curses.add(this);
        availableCurses.add(this);
    }
    
    /**
     * @return the name of the curse
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the description of the curse
     */
    public String getDescription()
    {
        return description;
    }
    
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
     * @return the texture ID
     */
    public int getTextureID()
    {
        return textureID;
    }
    
    /**
     * @param world
     * @param player
     */
    public void action(World world, EntityPlayer player)
    {}

    /**
     * @param world
     * @param player
     */
    public void playerDeathAction(World world, EntityPlayer player)
    {}
    
    public void entityDeathAction(World world, EntityLivingBase target, EntityPlayer player)
    {}

    /**
     * @param world
     * @param player
     */
    public void respawnAction(World world, EntityPlayer player)
    {}

    /**
     * @param world
     * @param player
     */
    public void attackedAction(World world, EntityPlayer player)
    {}

    /**
     * @param world
     * @param player
     */
    public void attackedByPlayerAction(World world, EntityPlayer player, Entity target)
    {}
    
    public void entityDropItems(EntityPlayer player, Entity target, ArrayList<EntityItem> drops)
    {}
    
    public void playerRender(EntityPlayer player, RenderPlayerEvent.Specials.Post event)
    {}
    
    public void playerHandRender(EntityPlayer player, RenderHandEvent event)
    {}
    
    public boolean itemToss()
    {
        return false;
    }
    
    /**
     * @return
     */
    public static ArrayList<Curse> getCurseList()
    {
        return curses;
    }
}
