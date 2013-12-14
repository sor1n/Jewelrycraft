package bspkrs.briefcasespeakers.item;

import net.minecraft.item.Item;

public class ItemBase extends Item
{
    public ItemBase(int par1)
    {
        super(par1);
    }
    
    @Override
    public Item setUnlocalizedName(String name)
    {
        Item r = super.setUnlocalizedName(name);
        return r.setTextureName(name.replaceAll("\\.", ":"));
    }
}
