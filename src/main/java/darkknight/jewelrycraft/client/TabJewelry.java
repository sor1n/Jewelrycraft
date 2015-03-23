package darkknight.jewelrycraft.client;

import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketKeyPressEvent;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TabJewelry extends AbstractTab
{
    public TabJewelry()
    {
        super(0, 0, 0, new ItemStack(ItemList.necklace));
    }

    @Override
    public void onTabClicked ()
    {
        JewelrycraftMod.netWrapper.sendToServer(new PacketKeyPressEvent(0));
    }

    @Override
    public boolean shouldAddToList ()
    {
        return true;
    }
}