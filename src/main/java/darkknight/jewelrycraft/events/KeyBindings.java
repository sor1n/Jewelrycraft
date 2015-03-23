package darkknight.jewelrycraft.events;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.network.PacketKeyPressEvent;

public class KeyBindings
{
    public static KeyBinding render = new KeyBinding("Pretty Render", Keyboard.KEY_Z, "Jewelrycraft");
    public static KeyBinding inventory = new KeyBinding("Jewelry Inventory", Keyboard.KEY_J, "Jewelrycraft");
    
    /**
     * 
     */
    public KeyBindings()
    {
        ClientRegistry.registerKeyBinding(render);
        ClientRegistry.registerKeyBinding(inventory);
    }
    
    /**
     * @param event
     */
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (render.isPressed()) JewelrycraftMod.fancyRender = !JewelrycraftMod.fancyRender;
        if (inventory.isPressed()) JewelrycraftMod.netWrapper.sendToServer(new PacketKeyPressEvent(0));
    }
}