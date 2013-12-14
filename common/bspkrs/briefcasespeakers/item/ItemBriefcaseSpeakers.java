package bspkrs.briefcasespeakers.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class ItemBriefcaseSpeakers extends ItemBase
{
    public ItemBriefcaseSpeakers(int par1)
    {
        super(par1);
        setCreativeTab(CreativeTabs.tabMaterials);
    }
    
    @Override
    public void registerIcons(IconRegister reg)
    {
        this.itemIcon = reg.registerIcon("amethyst");
    }
}
