package darkknight.jewelrycraft.client;

import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketKeyPressEvent;
import net.minecraft.item.ItemStack;

public class TabCurses extends AbstractTab
{
    public TabCurses()
    {
        super(0, 0, 0, new ItemStack(ItemList.testItem, 1, 4));
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