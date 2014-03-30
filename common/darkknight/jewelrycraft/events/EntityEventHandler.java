package darkknight.jewelrycraft.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.BlockUtils;
import darkknight.jewelrycraft.util.PlayerUtils;

/**
 * Code taken from OpenBlocks
 */
public class EntityEventHandler 
{

    public static final String OPENBLOCKS_PERSIST_TAG = "Jewelrycraft";
    public static final String GIVEN_GUIDE_TAG = "givenGuive";

    @ForgeSubscribe
    public void onEntityJoinWorld(EntityJoinWorldEvent event) 
    {

        final Entity entity = event.entity;
        /**
         * If the player hasn't been given a manual, we'll give him one! (or
         * throw it on the floor..)
         */
        if (!event.world.isRemote && entity instanceof EntityPlayer) 
        {
            EntityPlayer player = (EntityPlayer)entity;
            NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");

            boolean shouldGiveManual = ItemList.guide != null && !persistTag.getBoolean(GIVEN_GUIDE_TAG);
            if (shouldGiveManual) 
            {
                ItemStack manual = new ItemStack(ItemList.guide);
                if (!player.inventory.addItemStackToInventory(manual)) 
                {
                    BlockUtils.dropItemStackInWorld(player.worldObj, player.posX, player.posY, player.posZ, manual);
                }
                persistTag.setBoolean(GIVEN_GUIDE_TAG, true);
            }
        }
    }
}