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
import net.minecraftforge.event.world.WorldEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.container.JewelryInventory;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.lib.Reference;
import darkknight.jewelrycraft.network.PacketClearColorCache;
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
                if (!player.inventory.addItemStackToInventory(manual))
                {
                    BlockUtils.dropItemStackInWorld(player.worldObj, player.posX, player.posY, player.posZ, manual);
                }
                persistTag.setBoolean("givenGuide", true);
            }
            
            boolean render = persistTag.getBoolean("fancyRender");
            JewelrycraftMod.fancyRender = render;
            // System.out.println("FancyRender: " + JewelrycraftMod.fancyRender
            // + " Render:" + render);
        }
    }
    
    @SubscribeEvent
    public void onEntityUpdate(LivingUpdateEvent event)
    {
        final Entity entity = event.entity;
        if (!entity.worldObj.isRemote && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            for (String l : JewelrycraftUtil.curses.keySet())
                if (!persistTag.hasKey(l) || player.getHealth() >= 19F) persistTag.setInteger(l, -1);
            if (!player.isDead && player.getHealth() > 0F && player.getHealth() < 19F) persistTag.setInteger(Reference.MODNAME + ":" + "Blind", 0);
            // boolean render = persistTag.getBoolean("fancyRender");
            // System.out.println("FancyRender: " + JewelrycraftMod.fancyRender
            // + " Render:" + render);
            // circle(player.worldObj, player.posX, player.posY, player.posZ,
            // 4);
        }
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            persistTag.setBoolean("fancyRender", JewelrycraftMod.fancyRender);
        }
    }
    
    // public static void circle(World world, double x, double y, double z, int
    // radius)
    // {
    // for (int i = 0; i < radius; i++)
    // for (int j = 0; j < radius; j++)
    // for (int k = 0; k < radius; k++)
    // {
    // double distance = (radius - i) * (radius - i) + (radius - j) * (radius -
    // j) + (radius - k) * (radius - k);
    //
    // if (distance - 1 == (radius * radius) || distance - 2 == (radius *
    // radius))
    // {
    // Minecraft.getMinecraft().effectRenderer.addEffect(new
    // EntityShadowsFX(world, x + i - radius + 1F, y + j - radius / 2 - 1.5f, z
    // + k - radius + 1F, 2F, 0.05F));
    // Minecraft.getMinecraft().effectRenderer.addEffect(new
    // EntityShadowsFX(world, x - i + radius - 1F, y + j - radius / 2 - 1.5f, z
    // + k - radius + 1F, 2F, 0.05F));
    //
    // Minecraft.getMinecraft().effectRenderer.addEffect(new
    // EntityShadowsFX(world, x + i - radius + 1F, y + j - radius / 2 - 1.5f, z
    // - k + radius - 1F, 2F, 0.05F));
    // Minecraft.getMinecraft().effectRenderer.addEffect(new
    // EntityShadowsFX(world, x - i + radius - 1F, y + j - radius / 2 - 1.5f, z
    // - k + radius - 1F, 2F, 0.05F));
    // }
    // }
    //
    // for (int i = 0; i < radius; i++)
    // for (int j = radius - 1; j >= 0; j--)
    // for (int k = 0; k < radius; k++)
    // {
    // double distance = (radius - i) * (radius - i) + (radius - j) * (radius -
    // j) + (radius - k) * (radius - k);
    //
    // if (distance - 1 == (radius * radius) || distance - 2 == (radius *
    // radius))
    // {
    // Minecraft.getMinecraft().effectRenderer.addEffect(new
    // EntityShadowsFX(world, x + i - radius + 1F, y - j + radius / 2 + 0.5f, z
    // + k - radius + 1F, 2F, 0.05F));
    // Minecraft.getMinecraft().effectRenderer.addEffect(new
    // EntityShadowsFX(world, x - i + radius - 1F, y - j + radius / 2 + 0.5f, z
    // + k - radius + 1F, 2F, 0.05F));
    //
    // Minecraft.getMinecraft().effectRenderer.addEffect(new
    // EntityShadowsFX(world, x + i - radius + 1F, y - j + radius / 2 + 0.5f, z
    // - k + radius - 1F, 2F, 0.05F));
    // Minecraft.getMinecraft().effectRenderer.addEffect(new
    // EntityShadowsFX(world, x - i + radius - 1F, y - j + radius / 2 + 0.5f, z
    // - k + radius - 1F, 2F, 0.05F));
    // }
    // }
    // }
    
    @SubscribeEvent
    public void onEntityDead(LivingDeathEvent event)
    {
        final Entity entity = event.entity;
        if (!entity.worldObj.isRemote && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            NBTTagCompound persistTag = PlayerUtils.getModPlayerPersistTag(player, "Jewelrycraft");
            for (String l : JewelrycraftUtil.curses.keySet())
                if (persistTag.getInteger(l) == 0) persistTag.setInteger(l, -1);
        }
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