package darkknight.jewelrycraft.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import darkknight.jewelrycraft.util.JewelryNBT;

public class ItemNecklace extends ItemBaseJewelry
{
    public IIcon gem;    
    public ItemNecklace()
    {
    }    
    
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("jewelrycraft:necklace");
        gem = iconRegister.registerIcon("jewelrycraft:jewelNecklace");
    }
    
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0) return itemIcon;
        if (pass == 1 && JewelryNBT.gem(stack) != null) return gem;
        return itemIcon;
    }
}
