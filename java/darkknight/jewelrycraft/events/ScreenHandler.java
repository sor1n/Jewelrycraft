package darkknight.jewelrycraft.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;

public class ScreenHandler extends Gui
{
    private Minecraft mc;
    
    public static NBTTagCompound tagCache;
    public static int cooldown;
    
    public ScreenHandler(Minecraft mc)
    {
        super();
        this.mc = mc;
    }
    
    @SubscribeEvent
    public void onEntityJoinWorld(RenderGameOverlayEvent event)
    {
        if (cooldown == 0)
        {
            JewelrycraftMod.netWrapper.sendToServer(new PacketRequestPlayerInfo());
            cooldown = 500;
        }        
        else cooldown--;

        if (event.isCancelable() || event.type != ElementType.EXPERIENCE || tagCache == null) return;
        mc.renderEngine.bindTexture(new ResourceLocation("jewelrycraft", "textures/gui/curses1.png"));
        for (String l : JewelrycraftUtil.curses.keySet())
            if (tagCache.getInteger(l) > -1){
                int tag = JewelrycraftUtil.curses.get(l) + 1;
                int size = 32;
                this.drawTexturedModalRect(2 + size * tag, 2, tag % size * size, tag / size * size, size, size);
            }
    }
}