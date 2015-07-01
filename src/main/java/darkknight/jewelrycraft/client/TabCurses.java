package darkknight.jewelrycraft.client;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketKeyPressEvent;

public class TabCurses extends AbstractTab
{
    public TabCurses()
    {
        super(0, 0, 0, new ItemStack(Items.ender_eye));
    }

    @Override
    public void onTabClicked ()
    {
        JewelrycraftMod.netWrapper.sendToServer(new PacketKeyPressEvent(1));
    }

    @Override
    public boolean shouldAddToList ()
    {
        return true;
    }
}