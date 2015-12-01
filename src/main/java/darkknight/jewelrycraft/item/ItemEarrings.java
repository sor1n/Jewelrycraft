package darkknight.jewelrycraft.item;

import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemEarrings extends ItemBaseJewelry
{
    public IIcon gem;
    
    /**
     * 
     */
    public ItemEarrings()
    {}
    
    /**
     * @param iconRegister
     */
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(Variables.MODID + ":earrings");
        gem = iconRegister.registerIcon(Variables.MODID + ":jewelEarrings");
    }
    
    /**
     * @param stack
     * @param pass
     * @return
     */
    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0) return itemIcon;
        if (pass == 1 && JewelryNBT.gem(stack) != null) return gem;
        return itemIcon;
    }
}
