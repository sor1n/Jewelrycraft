package darkknight.jewelrycraft.item;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemClayMolds extends Item
{
    /** List of molds color names */
    public static final String[] moldsItemNames = new String[]{"clayIngot", "clayRing", "clayNecklace", "clayBracelet", "clayEarrings"};
    @SideOnly (Side.CLIENT)
    private IIcon[] moldsIcons;
    
    /**
     * 
     */
    public ItemClayMolds()
    {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
        setMaxStackSize(1);
    }
    
    /**
     * @param par1
     * @return
     */
    @Override
    @SideOnly (Side.CLIENT)
    /**
     * Gets an icon index based on an item's damage value
     */
    public IIcon getIconFromDamage(int par1)
    {
        int j = MathHelper.clamp_int(par1, 0, moldsItemNames.length - 1);
        return moldsIcons[j];
    }
    
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have different names based on their damage or NBT.
     *
     * @param par1ItemStack
     * @return
     */
    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, moldsItemNames.length - 1);
        return super.getUnlocalizedName() + "." + moldsItemNames[i];
    }
    
    /**
     * @param par1
     * @param par2CreativeTabs
     * @param par3List
     */
    @Override
    @SuppressWarnings ({"unchecked", "rawtypes"})
    @SideOnly (Side.CLIENT)
    /**
     * returns a list of items with the same ID, but different meta (eg: molds returns 16 items)
     */
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for(int j = 0; j < moldsItemNames.length; ++j)
            par3List.add(new ItemStack(par1, 1, j));
    }
    
    /**
     * @param par1IconRegister
     */
    @Override
    @SideOnly (Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        moldsIcons = new IIcon[moldsItemNames.length];
        for(int i = 0; i < moldsItemNames.length; ++i)
            moldsIcons[i] = par1IconRegister.registerIcon(Variables.MODID + ":" + moldsItemNames[i] + getIconString());
    }
}
