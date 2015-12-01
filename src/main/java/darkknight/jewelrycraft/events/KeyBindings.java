package darkknight.jewelrycraft.events;

import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.network.PacketKeyPressEvent;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings
{
    public static KeyBinding render = new KeyBinding("Pretty Render", Keyboard.KEY_Z, Variables.MODNAME);
    public static KeyBinding inventory = new KeyBinding("Jewelry Inventory", Keyboard.KEY_J, Variables.MODNAME);
    public static KeyBinding curses = new KeyBinding("Curses Tab", Keyboard.KEY_C, Variables.MODNAME);
    
    /**
     * 
     */
    public KeyBindings()
    {
        ClientRegistry.registerKeyBinding(render);
        ClientRegistry.registerKeyBinding(inventory);
        ClientRegistry.registerKeyBinding(curses);
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (render.isPressed()) JewelrycraftMod.fancyRender = !JewelrycraftMod.fancyRender;
        if (inventory.isPressed()) JewelrycraftMod.netWrapper.sendToServer(new PacketKeyPressEvent(0));
        if (curses.isPressed()) JewelrycraftMod.netWrapper.sendToServer(new PacketKeyPressEvent(1));
    }
}