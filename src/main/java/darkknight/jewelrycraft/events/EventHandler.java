package darkknight.jewelrycraft.events;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.BlockUtils;
import darkknight.jewelrycraft.util.PlayerUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Code taken from OpenBlocks
 */
public class EventHandler 
{
	public static final String OPENBLOCKS_PERSIST_TAG = "Jewelrycraft";
	public static final String GIVEN_GUIDE_TAG = "givenGuive";

	@SubscribeEvent
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

	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load event)
	{
	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event)
	{
	}
}