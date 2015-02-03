package darkknight.jewelrycraft.events;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketClearColorCache;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.util.BlockUtils;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;

/**
 * Code taken from OpenBlocks
 */

public class EntityEventHandler
{
    
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayerMP) JewelrycraftMod.netWrapper.sendTo(new PacketClearColorCache(), (EntityPlayerMP) event.entity);
        
        final Entity entity = event.entity;
        
        if (!event.world.isRemote && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            
            boolean shouldGiveManual = ItemList.guide != null && !persistTag.getBoolean("givenGuide");
            if (shouldGiveManual)
            {
                ItemStack manual = new ItemStack(ItemList.guide);
                if (!player.inventory.addItemStackToInventory(manual)) BlockUtils.dropItemStackInWorld(player.worldObj, player.posX, player.posY, player.posZ, manual);
                persistTag.setBoolean("givenGuide", true);
            }
            
            boolean render = persistTag.getBoolean("fancyRender");
            JewelrycraftMod.fancyRender = render;
        }
        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
    }
    
    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
        final Entity entity = event.entity;
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            playerInfo.setBoolean("fancyRender", JewelrycraftMod.fancyRender);
            if (!player.worldObj.isRemote)
            {
//                System.out.println(playerInfo.getInteger("curseTime") + " " + playerInfo.getBoolean("reselectCurses"));
                if (playerInfo.hasKey("reselectCurses") && !playerInfo.getBoolean("reselectCurses"))
                {
                    playerInfo.setInteger("curseTime", playerInfo.getInteger("curseTime") - 100);
                    if (playerInfo.getInteger("curseTime") <= 0) playerInfo.setBoolean("reselectCurses", true);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.Clone event)
    {
        EntityPlayer player = event.entityPlayer;
        if (!player.worldObj.isRemote)
        {
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            if (playerInfo.hasKey("cursePoints") && playerInfo.getInteger("cursePoints") > 0)
            {
                int points = playerInfo.getInteger("cursePoints");
                addCurse(player, playerInfo, "curse1");
                if (points > 50) addCurse(player, playerInfo, "curse2");
                if(!playerInfo.hasKey("curseTime") || !playerInfo.hasKey("reselectCurses") || playerInfo.getBoolean("reselectCurses"))
                {
                    playerInfo.setInteger("curseTime", 23000);
                    playerInfo.setBoolean("reselectCurses", false);
                }
            }
        }
        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
    }
    
    public void addCurse(EntityPlayer player, NBTTagCompound playerInfo, String curse)
    {
        if ((!playerInfo.hasKey(curse) || playerInfo.getInteger(curse) == -1) && JewelrycraftUtil.availableCurseNames.size() > 0)
        {
            String name = JewelrycraftUtil.availableCurseNames.get(JewelrycraftUtil.rand.nextInt(JewelrycraftUtil.availableCurseNames.size()));
            int grade = player.worldObj.rand.nextInt(2);
            playerInfo.setByte(name, (byte) grade);
            JewelrycraftUtil.availableCurseNames.remove(JewelrycraftUtil.curseValues.get(name));
            playerInfo.setInteger(curse, grade);
        }
    }
    
    @SubscribeEvent
    public void playerFileSave(PlayerEvent.SaveToFile event)
    {
        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
    }
    
    @SubscribeEvent
    public void onEntityDead(LivingDeathEvent event)
    {
        final Entity entity = event.entity;
        if (!entity.worldObj.isRemote && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            if (playerInfo.hasKey("reselectCurses") && playerInfo.getBoolean("reselectCurses"))
            {
                for (String l : JewelrycraftUtil.curseValues.keySet())
                    if (playerInfo.getInteger(l) == 0 || playerInfo.getInteger("Deaths") == 2) playerInfo.setByte(l, (byte) -1);
                for (int i = 1; i <= 2; i++)
                    if ((playerInfo.hasKey(("curse" + i).toString()) && playerInfo.getInteger(("curse" + i).toString()) == 0) || playerInfo.getInteger("Deaths") == 2) playerInfo.setInteger(("curse" + i).toString(), -1);
                JewelrycraftUtil.availableCurseNames.putAll(JewelrycraftUtil.curseNames);
            }
            if (!playerInfo.hasKey("Deaths") || playerInfo.getInteger("Deaths") == 2) playerInfo.setInteger("Deaths", 0);
            playerInfo.setInteger("Deaths", playerInfo.getInteger("Deaths") + 1);
        }
        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
    }
    
    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (!event.world.isRemote)
        {
            new File(JewelrycraftMod.dir + File.separator + "Jewelrycraft").mkdirs();
            JewelrycraftMod.liquidsConf = new File(JewelrycraftMod.dir + File.separator + "Jewelrycraft", "JLP" + event.world.getWorldInfo().getWorldName() + ".cfg");
            try
            {
                if (!JewelrycraftMod.liquidsConf.exists()) JewelrycraftMod.liquidsConf.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        if (FMLCommonHandler.instance().getEffectiveSide().isServer())
        {
            try
            {
                if (JewelrycraftMod.liquidsConf.exists()) JewelrycraftMod.saveData = CompressedStreamTools.readCompressed(new FileInputStream(JewelrycraftMod.liquidsConf));
            }
            catch (EOFException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
        
    }
    
    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save event)
    {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer())
        {
            try
            {
                if (JewelrycraftMod.liquidsConf.exists()) CompressedStreamTools.writeCompressed(JewelrycraftMod.saveData, new FileOutputStream(JewelrycraftMod.liquidsConf));
            }
            catch (EOFException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void fogColors(EntityViewRenderEvent.FogColors event)
    {
        if (event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            if (persistTag.getBoolean("nearStartedRitual"))
            {
                event.red = 0f;
                event.green = 0f;
                event.blue = 0f;
            }
        }
        if (event.isCancelable()) event.setCanceled(true);
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void fogDensity(EntityViewRenderEvent.FogDensity event)
    {
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void renderFog(EntityViewRenderEvent.RenderFogEvent event)
    {
        if (event.entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.entity;
            NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            if (persistTag.getBoolean("nearStartedRitual"))
            {
                GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_EXP);
                GL11.glFogf(GL11.GL_FOG_DENSITY, 0.6F);
            }
        }
        if (event.isCancelable()) event.setCanceled(true);
    }
}