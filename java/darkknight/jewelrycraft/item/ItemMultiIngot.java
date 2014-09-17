package darkknight.jewelrycraft.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMultiIngot extends Item
{
    public IIcon[] icons = new IIcon[2];
    
    public ItemMultiIngot()
    {
        super();
        setHasSubtypes(true);
    }
    
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List items)
    {
        items.add(new ItemStack(item, 1, 0));
        items.add(new ItemStack(item, 1, 1));
    }
    
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }
    
    @Override
    public void registerIcons(IIconRegister register)
    {
        icons[0] = register.registerIcon("jewelrycraft:test/ingot2");
        icons[1] = register.registerIcon("jewelrycraft:ingot3");
    }
    
    @Override
    public IIcon getIconFromDamage(int dmg)
    {
        return dmg == 1 ? icons[1] : icons[0];
    }
}
