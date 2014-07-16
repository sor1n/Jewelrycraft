package darkknight.jewelrycraft.events;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
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

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) 
	{
		final Entity entity = event.entity;

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
		if(!event.world.isRemote)
		{
	        new File(JewelrycraftMod.dir + File.separator + "Jewelrycraft").mkdirs();
			JewelrycraftMod.liquidsConf = new File(JewelrycraftMod.dir + File.separator + "Jewelrycraft", "JLP" + event.world.getWorldInfo().getWorldName() + ".cfg");
			try {
				if(!JewelrycraftMod.liquidsConf.exists()) JewelrycraftMod.liquidsConf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			try {
				if(JewelrycraftMod.liquidsConf.exists()) 
					JewelrycraftMod.saveData = CompressedStreamTools.readCompressed(new FileInputStream(JewelrycraftMod.liquidsConf));
			} catch (EOFException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@SubscribeEvent
	public void onWorldSave(WorldEvent.Save event)
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isServer())
		{
			try {
				if(JewelrycraftMod.liquidsConf.exists())
					CompressedStreamTools.writeCompressed(JewelrycraftMod.saveData, new FileOutputStream(JewelrycraftMod.liquidsConf));
			} catch (EOFException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}