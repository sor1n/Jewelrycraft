package darkknight.jewelrycraft.events;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.api.IJewelryItem;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.entities.EntityHalfHeart;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.item.ItemBaseJewelry;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.network.PacketSendClientPlayerInfo;
import darkknight.jewelrycraft.network.PacketSendServerPlayersInfo;
import darkknight.jewelrycraft.potions.PotionBase;
import darkknight.jewelrycraft.potions.PotionList;
import darkknight.jewelrycraft.random.WeightedRandomCurse;
import darkknight.jewelrycraft.util.BlockUtils;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class EntityEventHandler {
	int updateTime = 0, totalUnavailableCurses = 0;
	boolean addedCurses = false;
	Random rand = new Random();

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer && !(event.entity instanceof EntityPlayerMP)) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
		final Entity entity = event.entity;
		if (!event.world.isRemote && entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			boolean shouldGiveManual = ItemList.guide != null && !persistTag.getBoolean("givenGuide");
			if (shouldGiveManual) {
				ItemStack manual = new ItemStack(ItemList.guide);
				if (!player.inventory.addItemStackToInventory(manual)) BlockUtils.dropItemStackInWorld(player.worldObj, player.posX, player.posY, player.posZ, manual);
				persistTag.setBoolean("givenGuide", true);
			}
			boolean render = persistTag.getBoolean("fancyRender");
			JewelrycraftMod.fancyRender = render;
			if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
				if (curse.canCurseBeActivated(event.world) && !persistTag.hasKey(curse.getName())) persistTag.setInteger(curse.getName(), 0);
			for (Curse curse : Curse.getCurseList())
				if (!curse.canCurseBeActivated(event.world)) {
					Curse.availableCurses.remove(curse);
					persistTag.setInteger(curse.getName(), 0);
					totalUnavailableCurses++;
				}
				else if (!Curse.availableCurses.contains(curse)) Curse.availableCurses.add(curse);
			persistTag.setBoolean("sendInfo", true);
		}
	}

	/**
	 * @param event
	 */
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event) {
		Entity entity = event.entity;
		EntityLivingBase entityLiving = event.entityLiving;
		for (PotionBase potion : PotionBase.getPotionList())
			if (entityLiving.isPotionActive(potion)) potion.action(entityLiving);
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			playerInfo.setBoolean("fancyRender", JewelrycraftMod.fancyRender);
			if (updateTime > 0) updateTime--;
			for (int i = 0; i < 18; i++)
				if (getJewelryItems(playerInfo, i) != null) {
					if (getJewelryItems(playerInfo, i).getItem() instanceof ItemBaseJewelry) ((ItemBaseJewelry) getJewelryItems(playerInfo, i).getItem()).action(getJewelryItems(playerInfo, i), player);
					if (getJewelryItems(playerInfo, i).getItem() instanceof IJewelryItem) ((IJewelryItem) getJewelryItems(playerInfo, i).getItem()).onWearAction(getJewelryItems(playerInfo, i), player);
				}
			if (!player.worldObj.isRemote) {
				timeUntilYouCanResetCurses(playerInfo);
				updateCurses(playerInfo, player);
				if (updateTime == 0) {
					JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
					updateTime = 200;
				}
				if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
					if (curse.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(curse.getName()) > 0) curse.action(player.worldObj, player);
			}
		}
	}

	public void updateCurses(NBTTagCompound playerInfo, EntityPlayer player) {
		if (playerInfo.hasKey("playerCursePointsChanged") && playerInfo.getBoolean("playerCursePointsChanged")) {
			int points = playerInfo.getInteger("cursePoints");
			int maxCurses = playerInfo.getInteger("cursePoints") / 1750 + 1;
			if (points > 0 && playerInfo.getInteger("activeCurses") < maxCurses) while (playerInfo.getInteger("activeCurses") < maxCurses && Curse.availableCurses.size() > 0 && playerInfo.getInteger("activeCurses") < Curse.getCurseList().size())
				addCurse(player, playerInfo);
			if (!playerInfo.hasKey("curseTime") || !playerInfo.hasKey("reselectCurses") || playerInfo.getBoolean("reselectCurses")) {
				playerInfo.setInteger("curseTime", 23000);
				playerInfo.setBoolean("reselectCurses", false);
			}
			JewelrycraftMod.netWrapper.sendTo(new PacketSendClientPlayerInfo(playerInfo), (EntityPlayerMP) player);
			if (addedCurses) {
				JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("curse." + Variables.MODID + ".activated")));
				addedCurses = false;
			}
		}
		if (playerInfo.getBoolean("playerCursePointsChanged")) playerInfo.setBoolean("playerCursePointsChanged", false);
	}

	public void timeUntilYouCanResetCurses(NBTTagCompound playerInfo) {
		if (playerInfo.hasKey("reselectCurses") && !playerInfo.getBoolean("reselectCurses")) {
			playerInfo.setInteger("curseTime", playerInfo.getInteger("curseTime") - 10000);
			if (playerInfo.getInteger("curseTime") <= 0) playerInfo.setBoolean("reselectCurses", true);
		}
	}

	public ItemStack getJewelryItems(NBTTagCompound playerInfo, int i) {
		if (playerInfo.hasKey("ext" + i)) {
			NBTTagCompound nbt = (NBTTagCompound) playerInfo.getTag("ext" + i);
			ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
			if (item != null) return item;
		}
		return null;
	}

	@SubscribeEvent
	public void onEntityLivingDropItems(LivingDropsEvent event) {
		if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
				if (curse.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(curse.getName()) > 0) curse.entityDropItems(player, event.entityLiving, event.drops);
		}
	}

	@SubscribeEvent
	public void onEntityAttacked(LivingAttackEvent event) {
		EntityLivingBase entity = event.entityLiving;
		if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityLivingBase && ((EntityLivingBase) event.source.getEntity()).isPotionActive(PotionList.stun)) event.setCanceled(true);
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			if (!(event.source.getEntity() instanceof EntityPlayer) && !player.capabilities.isCreativeMode) {
				if (!player.worldObj.isRemote) for (int i = 0; i < 18; i++)
					if (getJewelryItems(playerInfo, i) != null) {
						if (getJewelryItems(playerInfo, i).getItem() instanceof ItemBaseJewelry) {
							((ItemBaseJewelry) getJewelryItems(playerInfo, i).getItem()).onPlayerAttacked(getJewelryItems(playerInfo, i), player, event.source, event.ammount);
							if (((ItemBaseJewelry) getJewelryItems(playerInfo, i).getItem()).onPlayerAttackedCacellable(getJewelryItems(playerInfo, i), player, event.source, event.ammount)) event.setCanceled(true);
						}
						if (getJewelryItems(playerInfo, i).getItem() instanceof IJewelryItem) ((IJewelryItem) getJewelryItems(playerInfo, i).getItem()).onPlayerAttackedAction(getJewelryItems(playerInfo, i), player, event.source, event.ammount);
					}
				if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
					if (curse.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(curse.getName()) > 0) curse.attackedAction(player.worldObj, player);
			}
			removeHearts(event, player, playerInfo);
			JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
			JewelrycraftMod.netWrapper.sendTo(new PacketSendClientPlayerInfo(playerInfo), (EntityPlayerMP) player);
		}
		if (event.source.getEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			for (int i = 0; i < 18; i++)
				if (getJewelryItems(playerInfo, i) != null) {
					if (getJewelryItems(playerInfo, i).getItem() instanceof ItemBaseJewelry) {
						((ItemBaseJewelry) getJewelryItems(playerInfo, i).getItem()).onEntityAttacked(getJewelryItems(playerInfo, i), player, entity, event.ammount);
						if (((ItemBaseJewelry) getJewelryItems(playerInfo, i).getItem()).onEntityAttackedCacellable(getJewelryItems(playerInfo, i), player, entity, event.ammount)) event.setCanceled(true);
					}
					if (getJewelryItems(playerInfo, i).getItem() instanceof IJewelryItem) ((IJewelryItem) getJewelryItems(playerInfo, i).getItem()).onEntityAttackedByPlayer(getJewelryItems(playerInfo, i), player, entity, event.ammount);
				}
			if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
				if (curse.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(curse.getName()) > 0) {
					curse.attackedByPlayerAction(event, entity.worldObj, player, entity);
					if (curse.attackedByPlayerActionCancelable(event, player.worldObj, player, entity)) event.setCanceled(true);
				}
			if (entity instanceof EntityHeart && entity.getAge() < 30) event.setCanceled(true);
			if (event.source.getEntity() instanceof EntityPlayerMP) {
				JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
				JewelrycraftMod.netWrapper.sendTo(new PacketSendClientPlayerInfo(playerInfo), (EntityPlayerMP) player);
			}
		}
	}

	public void removeHearts(LivingAttackEvent event, EntityPlayer player, NBTTagCompound playerInfo) {
		if (!player.worldObj.isRemote && !player.capabilities.isCreativeMode && (float) player.hurtResistantTime <= (float) player.maxHurtResistantTime / 2.0F) {
			if (playerInfo.getFloat("WhiteHeart") > 0) {
				playerInfo.setFloat("WhiteHeart", 0f);
			}
			if (playerInfo.getFloat("BlueHeart") > 0) {
				float damage = playerInfo.getFloat("BlueHeart") - event.ammount;
				if (damage >= 0) playerInfo.setFloat("BlueHeart", damage);
				else playerInfo.setFloat("BlueHeart", 0f);
				if (damage < 0) {
					System.out.println(damage);
					player.attackEntityFrom(event.source, Math.abs(damage));
				}
				player.hurtResistantTime = player.maxHurtResistantTime;
				player.hurtTime = player.maxHurtTime = 10;
				player.worldObj.playSoundAtEntity(player, "game.player.hurt", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				event.setCanceled(true);
			}
			else if (playerInfo.getFloat("BlackHeart") > 0) {
				AxisAlignedBB axisalignedbb = player.boundingBox.expand(3.0D, 0.0D, 3.0D);
				List enemies = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, axisalignedbb);
				if (enemies != null && !enemies.isEmpty()) {
					Iterator iterator = enemies.iterator();
					while (iterator.hasNext()) {
						Entity enemy = (Entity) iterator.next();
						enemy.attackEntityFrom(DamageSourceList.blackHeart, 0.5f * event.ammount);
					}
				}
				float damage = playerInfo.getFloat("BlackHeart") - event.ammount;
				if (damage >= 0) playerInfo.setFloat("BlackHeart", damage);
				else playerInfo.setFloat("BlackHeart", 0f);
				if (damage < 0) player.attackEntityFrom(event.source, Math.abs(damage));
				player.hurtResistantTime = player.maxHurtResistantTime;
				player.hurtTime = player.maxHurtTime = 10;
				player.worldObj.playSoundAtEntity(player, "game.player.hurt", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.Clone event) {
		EntityPlayer player = event.entityPlayer;
		if (!player.worldObj.isRemote) {
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			if (playerInfo.hasKey("cursePoints")) {
				int points = playerInfo.getInteger("cursePoints");
				int maxCurses = playerInfo.getInteger("cursePoints") / 1750 + 1;
				if (points > 0 && playerInfo.getInteger("activeCurses") < maxCurses) while (playerInfo.getInteger("activeCurses") < maxCurses && Curse.availableCurses.size() > 0 && playerInfo.getInteger("activeCurses") < Curse.getCurseList().size())
					addCurse(player, playerInfo);
				if (!playerInfo.hasKey("curseTime") || !playerInfo.hasKey("reselectCurses") || playerInfo.getBoolean("reselectCurses")) {
					playerInfo.setInteger("curseTime", 23000);
					playerInfo.setBoolean("reselectCurses", false);
				}
			}
			playerInfo.setFloat("BlueHeart", 0f);
			playerInfo.setFloat("BlackHeart", 0f);
			playerInfo.setFloat("WhiteHeart", 0f);
			if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
				if (curse.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(curse.getName()) > 0) curse.respawnAction(player.worldObj, player);
			for (int i = 0; i < 18; i++)
				if (getJewelryItems(playerInfo, i) != null) {
					if (getJewelryItems(playerInfo, i).getItem() instanceof ItemBaseJewelry) ((ItemBaseJewelry) getJewelryItems(playerInfo, i).getItem()).onPlayerRespawn(getJewelryItems(playerInfo, i), event);
					if (getJewelryItems(playerInfo, i).getItem() instanceof IJewelryItem) ((IJewelryItem) getJewelryItems(playerInfo, i).getItem()).onPlayerRespawnAction(getJewelryItems(playerInfo, i), event);
				}
			JewelrycraftMod.netWrapper.sendTo(new PacketSendClientPlayerInfo(playerInfo), (EntityPlayerMP) player);
			if (addedCurses) {
				JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
				addedCurses = false;
			}
		}
	}

	public void addCurse(EntityPlayer player, NBTTagCompound playerInfo) {
		if (ConfigHandler.CURSES_ENABLED && Curse.availableCurses.size() > 0) {
			Curse cur = ((WeightedRandomCurse) WeightedRandom.getRandomItem(rand, JewelrycraftUtil.getCurses(player.worldObj, player, rand))).getCurse(rand);
			playerInfo.setInteger(cur.getName(), 1);
			Curse.availableCurses.remove(cur);
			addedCurses = true;
			if (playerInfo.getInteger("activeCurses") == 0) playerInfo.setInteger("activeCurses", 1);
			else playerInfo.setInteger("activeCurses", playerInfo.getInteger("activeCurses") + 1);
			JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
			JewelrycraftMod.netWrapper.sendTo(new PacketSendClientPlayerInfo(playerInfo), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public void itemToss(ItemTossEvent event) {
		NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(event.player, Variables.MODID);
		if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
			if (event.player != null && curse.canCurseBeActivated(event.player.worldObj) && playerInfo.getInteger(curse.getName()) > 0 && curse.itemToss()) {
				EntityItem entityitem = new EntityItem(event.player.worldObj, event.player.posX + 0.5D, event.player.posY + 0.5D, event.player.posZ + 0.5D, event.entityItem.getEntityItem());
				if (entityitem != null) {
					entityitem.motionX = 0;
					entityitem.motionZ = 0;
					entityitem.motionY = 0.11000000298023224D;
					event.player.worldObj.spawnEntityInWorld(entityitem);
					MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("<" + event.player.getDisplayName() + "> I shouldn't drop this. I might need it later."));
					event.setCanceled(true);
				}
			}
	}

	@SubscribeEvent
	public void playerFileSave(PlayerEvent.SaveToFile event) {
		if (event.entity instanceof EntityPlayer && !(event.entity instanceof EntityPlayerMP)) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
	}

	@SubscribeEvent
	public void onEntityDead(LivingDeathEvent event) {
		final Entity entity = event.entity;
		Random rand = new Random();
		String[] types = { "Red", "Blue", "White", "Black" };
		if (!entity.worldObj.isRemote && !(entity instanceof EntityPlayer) && entity instanceof EntityLiving) {
			EntityLiving live = (EntityLiving) entity;
			String type = types[rand.nextInt(4)];
			if (live.getCreatureAttribute() != JewelrycraftUtil.HEART) {
				if (type == "White") {
					EntityHeart h = new EntityHalfHeart(live.worldObj);
					h.setType(type);
					h.setLocationAndAngles(live.posX, live.posY, live.posZ, MathHelper.wrapAngleTo180_float(rand.nextFloat() * 360.0F), 0.0F);
					live.worldObj.spawnEntityInWorld(h);
				}
				else {
					for (int i = 1; i <= 1 + rand.nextInt(1 + (int) (live.getMaxHealth() / 2)); i++) {
						EntityHeart[] hearts = { new EntityHeart(live.worldObj), new EntityHalfHeart(entity.worldObj) };
						EntityHeart h = hearts[rand.nextInt(2)];
						h.setType(type);
						h.setLocationAndAngles(live.posX, live.posY, live.posZ, MathHelper.wrapAngleTo180_float(rand.nextFloat() * 360.0F), 0.0F);
						live.worldObj.spawnEntityInWorld(h);
					}
				}
			}
			if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.source.getEntity();
				NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
				if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
					if (curse.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(curse.getName()) > 0) curse.entityDeathAction(player.worldObj, event.entityLiving, player);
			}
		}
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			playerInfo.setFloat("BlueHeart", 0f);
			playerInfo.setFloat("BlackHeart", 0f);
			playerInfo.setFloat("WhiteHeart", 0f);
			if (playerInfo.hasKey("reselectCurses") && playerInfo.getBoolean("reselectCurses")) {
				playerInfo.setInteger("activeCurses", 0);
				if (ConfigHandler.CURSES_ENABLED) for (Curse l : Curse.getCurseList()) {
					if (l.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(l.getName()) == 1) {
						playerInfo.setInteger(l.getName(), 0);
						l.setTicksActive(0);
						if (!Curse.availableCurses.contains(l)) Curse.availableCurses.add(l);
					}
					else if (l.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(l.getName()) >= 2) playerInfo.setInteger(l.getName(), 1);
				}
				if (entity.worldObj.isRemote) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
			}
			if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
				if (curse.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(curse.getName()) > 0) curse.playerDeathAction(player.worldObj, player);
			for (int i = 0; i < 18; i++)
				if (getJewelryItems(playerInfo, i) != null) {
					if (getJewelryItems(playerInfo, i).getItem() instanceof ItemBaseJewelry) ((ItemBaseJewelry) getJewelryItems(playerInfo, i).getItem()).onPlayerDead(getJewelryItems(playerInfo, i), player, event.source);
					if (getJewelryItems(playerInfo, i).getItem() instanceof IJewelryItem) ((IJewelryItem) getJewelryItems(playerInfo, i).getItem()).onPlayerDeadAction(getJewelryItems(playerInfo, i), player, event.source);
				}
		}
		if (event.entity instanceof EntityPlayer && !(event.entity instanceof EntityPlayerMP)) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
	}
}