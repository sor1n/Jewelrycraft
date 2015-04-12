package darkknight.jewelrycraft.item;

import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.Variables;

public class ItemCrystal extends Item
{
    public IIcon overlay;
    public static final int[] dyeColors = new int[]{1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320};
    
    /**
     * 
     */
    public ItemCrystal()
    {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);
    }
    
    /**
     * @param iconRegister
     */
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(Variables.MODID + ":crystal");
        overlay = iconRegister.registerIcon(Variables.MODID + ":crystalOverlay");
    }
    
    /**
     * @return
     */
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
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
        if (pass == 1 && getDamage(stack) != 16) return dyeColors[getDamage(stack)];
        return 16777215;
    }
    
    /**
     * @param stack
     * @param pass
     * @return
     */
    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return pass == 0 ? itemIcon : overlay;
    }
    
    /**
     * @param stack
     * @param player
     * @param world
     * @param i
     * @param j
     * @param k
     * @param side
     * @param par8
     * @param par9
     * @param par10
     * @return
     */
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int side, float par8, float par9, float par10)
    {
        return true;
    }
    
    /**
     * @param stack
     * @return
     */
    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + stack.getItemDamage();
    }
    
    /**
     * @param par1
     * @param par2CreativeTabs
     * @param par3List
     */
    @Override
    @SuppressWarnings ({"unchecked", "rawtypes"})
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for(int j = 0; j < 16; ++j)
            par3List.add(new ItemStack(par1, 1, j));
    }
}
