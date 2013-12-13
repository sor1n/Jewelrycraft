package bspkrs.briefcasespeakers.item;

import bspkrs.briefcasespeakers.BriefcaseSpeakersMod;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBriefcaseSpeakers extends Item
{
    public ItemBriefcaseSpeakers(int par1)
    {
        super(par1);
        setCreativeTab(CreativeTabs.tabMaterials); 
    }
    
    public void registerIcons(IconRegister reg) 
    { 
        if (itemID == BriefcaseSpeakersMod.bspkrs.itemID);
        this.itemIcon = reg.registerIcon("amethyst");
    }    
}
