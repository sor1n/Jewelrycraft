package darkknight.jewelrycraft.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMoltenMetal extends Item
{
    /**
     * 
     */
    public ItemMoltenMetal()
    {
        super();
        setMaxStackSize(1);
    }
    
    /**
     * @param iconRegister
     */
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(Variables.MODID + ":moltenMetalStill");
    }
    
    /**
     * @param stack
     * @param pass
     * @return
     */
    @Override
    @SideOnly (Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        if(JewelryNBT.ingot(stack) != null) return JewelrycraftUtil.getColor(JewelryNBT.ingot(stack));
        else return 0xFFFFFF;
    }
}
