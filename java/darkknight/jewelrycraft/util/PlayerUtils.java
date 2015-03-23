package darkknight.jewelrycraft.util;

import darkknight.jewelrycraft.events.ScreenHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Code taken from OpenBlocks
 */
public class PlayerUtils
{
    /**
     * Returns the NBTTag of the player
     * 
     * @param player the player
     * @param modName the mod name
     * @return appropriate NBTTag
     */
    public static NBTTagCompound getModPlayerPersistTag(EntityPlayer player, String modName)
    {
        if (player.worldObj.isRemote && ScreenHandler.tagCache != null) return ScreenHandler.tagCache;
        
        NBTTagCompound tag = player.getEntityData();
        NBTTagCompound persistTag = null;
        if (tag.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) persistTag = tag.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
        else{
            persistTag = new NBTTagCompound();
            tag.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistTag);
        }
        NBTTagCompound modTag = null;
        if (persistTag.hasKey(modName)) modTag = persistTag.getCompoundTag(modName);
        else{
            modTag = new NBTTagCompound();
            persistTag.setTag(modName, modTag);
        }
        return modTag;
    }
}