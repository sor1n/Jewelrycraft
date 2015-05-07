package darkknight.jewelrycraft.events;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.api.IJewelryItem;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.entities.EntityHalfHeart;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.item.ItemBaseJewelry;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketClearColorCache;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.network.PacketSendServerPlayersInfo;
import darkknight.jewelrycraft.potions.PotionList;
import darkknight.jewelrycraft.util.BlockUtils;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

/**
 * Code taken from OpenBlocks
 */
public class EntityEventHandler
{
    int updateTime = 0;
    boolean addedCurses = false;
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayerMP) JewelrycraftMod.netWrapper.sendTo(new PacketClearColorCache(), (EntityPlayerMP)event.entity);
        if (event.entity instanceof EntityPlayer && !(event.entity instanceof EntityPlayerMP)) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
        final Entity entity = event.entity;
        if (!event.world.isRemote && entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)entity;
            NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            boolean shouldGiveManual = ItemList.guide != null && !persistTag.getBoolean("givenGuide");
            if (shouldGiveManual){
                ItemStack manual = new ItemStack(ItemList.guide);
                if (!player.inventory.addItemStackToInventory(manual)) BlockUtils.dropItemStackInWorld(player.worldObj, player.posX, player.posY, player.posZ, manual);
                persistTag.setBoolean("givenGuide", true);
            }
            boolean render = persistTag.getBoolean("fancyRender");
            JewelrycraftMod.fancyRender = render;
            for(Curse curse: Curse.getCurseList())
                if (!persistTag.hasKey(curse.getName())) persistTag.setInteger(curse.getName(), 0);
            persistTag.setBoolean("sendInfo", true);
        }
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
        Entity entity = event.entity;
        EntityLivingBase entityLiving = event.entityLiving;
        if (entityLiving.isPotionActive(PotionList.stun)){
            entityLiving.motionX *= 0D;
            entityLiving.motionZ *= 0D;
            entityLiving.motionY *= 0D;
            entityLiving.isSwingInProgress = false;
            entityLiving.moveForward = 0F;
            entityLiving.moveStrafing = 0F;
            entityLiving.setAIMoveSpeed(0F);
            entityLiving.limbSwing = 0F;
            entityLiving.limbSwingAmount = 0F;
            entityLiving.swingProgressInt = 0;
            entityLiving.rotationPitch = entity.prevRotationPitch;
            entityLiving.rotationYaw = entity.prevRotationYaw;
            entityLiving.worldObj.spawnParticle("spell", entityLiving.posX, entityLiving.posY + entityLiving.getEyeHeight(), entityLiving.posZ, 0.0D, 0.3D, 0.0D);
        }
        if (entityLiving.isPotionActive(PotionList.stun) && entityLiving.getActivePotionEffect(PotionList.stun).getDuration() == 0) entityLiving.removePotionEffect(PotionList.stun.id);
        if (entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)entity;
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            playerInfo.setBoolean("fancyRender", JewelrycraftMod.fancyRender);
            if (updateTime > 0) updateTime--;
            for(int i = 0; i < 18; i++)
                if (playerInfo.hasKey("ext" + i)){
                    NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                    ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                    if (item != null){
                        if(item.getItem() instanceof ItemBaseJewelry)((ItemBaseJewelry)item.getItem()).action(item, player);
                        if(item.getItem() instanceof IJewelryItem)((IJewelryItem)item.getItem()).onWearAction(item, player);
                    }
                }
            if (!player.worldObj.isRemote){
                if (playerInfo.hasKey("reselectCurses") && !playerInfo.getBoolean("reselectCurses")){
                    playerInfo.setInteger("curseTime", playerInfo.getInteger("curseTime") - 10000);
                    if (playerInfo.getInteger("curseTime") <= 0) playerInfo.setBoolean("reselectCurses", true);
                }
                if (playerInfo.hasKey("playerCursePointsChanged") && playerInfo.getBoolean("playerCursePointsChanged")){
                    int points = playerInfo.getInteger("cursePoints");
                    for(int i = 1; i <= Variables.MAX_CURSES; i++)
                        if (points > (i - 1) * 1750) addCurse(player, playerInfo, i);
                    if (!playerInfo.hasKey("curseTime") || !playerInfo.hasKey("reselectCurses") || playerInfo.getBoolean("reselectCurses")){
                        playerInfo.setInteger("curseTime", 23000);
                        playerInfo.setBoolean("reselectCurses", false);
                    }
                    JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
                    if (addedCurses){
                        JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
                        // player.openGui(JewelrycraftMod.instance, 4, player.worldObj, 0, 0, 0);
                        addedCurses = false;
                    }
                }
                if (playerInfo.getBoolean("playerCursePointsChanged")) playerInfo.setBoolean("playerCursePointsChanged", false);
                if (updateTime == 0){
                    JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
                    updateTime = 200;
                }
                for(Curse curse: Curse.getCurseList())
                    if (playerInfo.getInteger(curse.getName()) > 0) curse.action(player.worldObj, player);
            }
        }
    }
    
    @SubscribeEvent
    public void onEntityLivingDropItems(LivingDropsEvent event)
    {
        if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.source.getEntity();
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            for(Curse curse: Curse.getCurseList())
                if (playerInfo.getInteger(curse.getName()) > 0) curse.entityDropItems(player, event.entityLiving, event.drops);
        }
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onEntityAttacked(LivingAttackEvent event)
    {
        EntityLivingBase entity = event.entityLiving;
        if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityLivingBase && ((EntityLivingBase)event.source.getEntity()).isPotionActive(PotionList.stun)) event.setCanceled(true);
        if (entity instanceof EntityPlayer && !(event.source.getEntity() instanceof EntityPlayer)){
            EntityPlayer player = (EntityPlayer)entity;
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            if (!player.worldObj.isRemote) for(int i = 0; i < 18; i++)
                if (playerInfo.hasKey("ext" + i)){
                    NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                    ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                    if (item != null && item.getItem() instanceof ItemBaseJewelry && ((ItemBaseJewelry)item.getItem()).onPlayerAttackedCacellable(item, player, event.source, event.ammount)){
                        event.setCanceled(true);
                        break;
                    }
                    if (playerInfo.getBoolean("negateDamage")){
                        playerInfo.setBoolean("negateDamage", false);
                        event.setCanceled(true);
                        break;
                    }
                    if (item != null){
                        if(item.getItem() instanceof ItemBaseJewelry)((ItemBaseJewelry)item.getItem()).onPlayerAttacked(item, player, event.source, event.ammount);
                        if(item.getItem() instanceof IJewelryItem)((IJewelryItem)item.getItem()).onPlayerAttackedAction(item, player, event.source, event.ammount);
                    }
                }
            if (player.getHealth() != player.prevHealth){
                if (playerInfo.getFloat("WhiteHeart") > 0){
                    playerInfo.setFloat("WhiteHeart", 0f);
                    JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
                }
                if (playerInfo.getFloat("BlueHeart") > 0){
                    float damage = playerInfo.getFloat("BlueHeart") - event.ammount;
                    if (damage >= 0){
                        playerInfo.setFloat("BlueHeart", damage);
                        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
                        player.heal(event.ammount);
                    }else{
                        playerInfo.setFloat("BlueHeart", 0f);
                        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
                        player.heal(Math.abs(damage));
                    }
                }else if (playerInfo.getFloat("BlackHeart") > 0){
                    AxisAlignedBB axisalignedbb = player.boundingBox.expand(2.0D, 0.0D, 2.0D);
                    List enemies = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, axisalignedbb);
                    if (enemies != null && !enemies.isEmpty()){
                        Iterator iterator = enemies.iterator();
                        while (iterator.hasNext()){
                            Entity enemy = (Entity)iterator.next();
                            enemy.attackEntityFrom(DamageSourceList.blackHeart, 5f * event.ammount);
                        }
                    }
                    float damage = playerInfo.getFloat("BlackHeart") - event.ammount;
                    if (damage >= 0){
                        playerInfo.setFloat("BlackHeart", damage);
                        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
                        player.heal(event.ammount);
                    }else{
                        playerInfo.setFloat("BlackHeart", 0f);
                        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
                        player.heal(Math.abs(damage));
                    }
                }
            }
            for(Curse curse: Curse.getCurseList())
                if (playerInfo.getInteger(curse.getName()) > 0) curse.attackedAction(player.worldObj, player);
        }else if (event.source.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.source.getEntity();
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            for(int i = 0; i < 18; i++)
                if (playerInfo.hasKey("ext" + i)){
                    NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                    ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                    if (item != null && item.getItem() instanceof ItemBaseJewelry && ((ItemBaseJewelry)item.getItem()).onEntityAttackedCacellable(item, player, entity, event.ammount)){
                        event.setCanceled(true);
                        break;
                    }
                    if (playerInfo.getBoolean("weakDamage")){
                        playerInfo.setBoolean("weakDamage", false);
                        event.setCanceled(true);
                        break;
                    }
                    if (item != null){
                        if(item.getItem() instanceof ItemBaseJewelry)((ItemBaseJewelry)item.getItem()).onEntityAttacked(item, player, entity, event.ammount);
                        if(item.getItem() instanceof IJewelryItem)((IJewelryItem)item.getItem()).onEntityAttackedByPlayer(item, player, entity, event.ammount);
                    }
                }
            for(Curse curse: Curse.getCurseList())
                if (playerInfo.getInteger(curse.getName()) > 0) curse.attackedByPlayerAction(entity.worldObj, player, entity);
        }
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.Clone event)
    {
        EntityPlayer player = event.entityPlayer;
        if (!player.worldObj.isRemote){
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            if (playerInfo.hasKey("cursePoints")){
                int points = playerInfo.getInteger("cursePoints");
                for(int i = 1; i <= Variables.MAX_CURSES; i++)
                    if (points > (i - 1) * 1750) addCurse(player, playerInfo, i);
                if (!playerInfo.hasKey("curseTime") || !playerInfo.hasKey("reselectCurses") || playerInfo.getBoolean("reselectCurses")){
                    playerInfo.setInteger("curseTime", 23000);
                    playerInfo.setBoolean("reselectCurses", false);
                }
            }
            playerInfo.setFloat("BlueHeart", 0f);
            playerInfo.setFloat("BlackHeart", 0f);
            playerInfo.setFloat("WhiteHeart", 0f);
            for(Curse curse: Curse.getCurseList())
                if (playerInfo.getInteger(curse.getName()) > 0) curse.respawnAction(player.worldObj, player);
            for(int i = 0; i < 18; i++)
                if (playerInfo.hasKey("ext" + i)){
                    NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                    ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                    if (item != null){
                        if(item.getItem() instanceof ItemBaseJewelry)((ItemBaseJewelry)item.getItem()).onPlayerRespawn(item, event);
                        if(item.getItem() instanceof IJewelryItem)((IJewelryItem)item.getItem()).onPlayerRespawnAction(item, event);
                    }
                }
        }
        if (event.entity instanceof EntityPlayer && !(event.entity instanceof EntityPlayerMP)) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
        if (!player.worldObj.isRemote){
            JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
            if (addedCurses){
                JewelrycraftMod.netWrapper.sendToAll(new PacketSendServerPlayersInfo());
//                 player.openGui(JewelrycraftMod.instance, 4, player.worldObj, 0, 0, 0);
                addedCurses = false;
            }
        }
    }
    
    /**
     * @param player
     * @param playerInfo
     * @param curse
     */
    public void addCurse(EntityPlayer player, NBTTagCompound playerInfo, int curseNo)
    {
        if (Curse.availableCurses.size() > 0 && curseNo > Curse.getCurseList().size() - Curse.availableCurses.size()){
            int no = JewelrycraftUtil.rand.nextInt(Curse.availableCurses.size());
            Curse cur = Curse.availableCurses.get(no);
            playerInfo.setInteger(cur.getName(), 1);
            Curse.availableCurses.remove(cur);
            addedCurses = true;
        }
    }
    
    @SubscribeEvent
    public void itemToss(ItemTossEvent event)
    {
        NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(event.player, Variables.MODID);
        for(Curse curse: Curse.getCurseList())
            if (playerInfo.getInteger(curse.getName()) > 0 && curse.itemToss()){
                EntityItem entityitem = new EntityItem(event.player.worldObj, event.player.posX + 0.5D, event.player.posY + 0.5D, event.player.posZ + 0.5D, event.entityItem.getEntityItem());
                entityitem.motionX = 0;
                entityitem.motionZ = 0;
                entityitem.motionY = 0.11000000298023224D;
                event.player.worldObj.spawnEntityInWorld(entityitem);
                MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("<" + event.player.getDisplayName() + "> This is MY item! MINE! I will NEVER give it to you! Mine! Mine! MINE!"));
                event.setCanceled(true);
            }
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void playerFileSave(PlayerEvent.SaveToFile event)
    {
        if (event.entity instanceof EntityPlayer && !(event.entity instanceof EntityPlayerMP)) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onEntityDead(LivingDeathEvent event)
    {
        final Entity entity = event.entity;
        Random rand = new Random();
        String[] types = {"Red", "Blue", "White", "Black"};
        if (!entity.worldObj.isRemote && !(entity instanceof EntityPlayer) && entity instanceof EntityLiving){
            EntityLiving live = (EntityLiving)entity;
            String type = types[rand.nextInt(4)];
            if (rand.nextInt(6) == 0){
                if (type == "White"){
                    EntityHeart h = new EntityHalfHeart(live.worldObj);
                    h.setType(type);
                    h.setLocationAndAngles(live.posX, live.posY, live.posZ, MathHelper.wrapAngleTo180_float(rand.nextFloat() * 360.0F), 0.0F);
                    live.worldObj.spawnEntityInWorld(h);
                }else{
                    for(int i = 1; i <= 1 + rand.nextInt(1 + (int)(live.getMaxHealth() / 20)); i++){
                        EntityHeart[] hearts = {new EntityHeart(live.worldObj), new EntityHalfHeart(entity.worldObj)};
                        EntityHeart h = hearts[rand.nextInt(2)];
                        h.setType(type);
                        h.setLocationAndAngles(live.posX, live.posY, live.posZ, MathHelper.wrapAngleTo180_float(rand.nextFloat() * 360.0F), 0.0F);
                        live.worldObj.spawnEntityInWorld(h);
                    }
                }
            }
            if (event.source.getEntity() != null && event.source.getEntity() instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer)event.source.getEntity();
                NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
                for(Curse curse: Curse.getCurseList())
                    if (playerInfo.getInteger(curse.getName()) > 0) curse.entityDeathAction(player.worldObj, event.entityLiving, player);
            }
        }
        if (entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)entity;
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            playerInfo.setFloat("BlueHeart", 0f);
            playerInfo.setFloat("BlackHeart", 0f);
            playerInfo.setFloat("WhiteHeart", 0f);
            if (playerInfo.hasKey("reselectCurses") && playerInfo.getBoolean("reselectCurses")){
                for(Curse l: Curse.getCurseList()){
                    if (playerInfo.getInteger(l.getName()) == 1){
                        playerInfo.setInteger(l.getName(), 0);
                        if (!Curse.availableCurses.contains(l)) Curse.availableCurses.add(l);
                    }else if (playerInfo.getInteger(l.getName()) >= 2) playerInfo.setInteger(l.getName(), 1);
                }
                if (entity.worldObj.isRemote) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
            }
            for(Curse curse: Curse.getCurseList())
                if (playerInfo.getInteger(curse.getName()) > 0) curse.playerDeathAction(player.worldObj, player);
            for(int i = 0; i < 18; i++)
                if (playerInfo.hasKey("ext" + i)){
                    NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                    ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                    if (item != null){
                        if(item.getItem() instanceof ItemBaseJewelry)((ItemBaseJewelry)item.getItem()).onPlayerDead(item, player, event.source);
                        if(item.getItem() instanceof IJewelryItem)((IJewelryItem)item.getItem()).onPlayerDeadAction(item, player, event.source);
                    }
                }
        }
        if (event.entity instanceof EntityPlayer && !(event.entity instanceof EntityPlayerMP)) JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (!event.world.isRemote){
            new File(JewelrycraftMod.dir + File.separator + Variables.MODID).mkdirs();
            JewelrycraftMod.liquidsConf = new File(JewelrycraftMod.dir + File.separator + Variables.MODID, "JLP" + event.world.getWorldInfo().getWorldName() + ".cfg");
            try{
                if (!JewelrycraftMod.liquidsConf.exists()) JewelrycraftMod.liquidsConf.createNewFile();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) try{
            if (JewelrycraftMod.liquidsConf.exists()) JewelrycraftMod.saveData = CompressedStreamTools.readCompressed(new FileInputStream(JewelrycraftMod.liquidsConf));
        }
        catch(EOFException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event)
    {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()) try{
            if (JewelrycraftMod.liquidsConf.exists()) CompressedStreamTools.writeCompressed(JewelrycraftMod.saveData, new FileOutputStream(JewelrycraftMod.liquidsConf));
        }
        catch(EOFException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    @SideOnly (Side.CLIENT)
    public void fogColors(EntityViewRenderEvent.FogColors event)
    {}
    
    /**
     * @param event
     */
    @SubscribeEvent
    @SideOnly (Side.CLIENT)
    public void fogDensity(EntityViewRenderEvent.FogDensity event)
    {}
    
    /**
     * @param event
     */
    @SubscribeEvent
    @SideOnly (Side.CLIENT)
    public void renderFog(EntityViewRenderEvent.RenderFogEvent event)
    {}
}