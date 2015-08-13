package darkknight.jewelrycraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.Variables;

public class ItemTest extends Item
{
    @SideOnly (Side.CLIENT)
    private IIcon[] icons;
    
    public ItemTest()
    {
        super();
        setHasSubtypes(true);
    }
    
    @Override
    @SideOnly (Side.CLIENT)
    public IIcon getIconFromDamage(int damage)
    {
        int j = MathHelper.clamp_int(damage, 0, Curse.getCurseList().size() - 1);
        return icons[j];
    }
    
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for(int j = 0; j < Curse.getCurseList().size(); ++j)
            par3List.add(new ItemStack(par1, 1, j));
    }
    
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	icons = new IIcon[Curse.getCurseList().size()];
        for(int i = 0; i < Curse.getCurseList().size(); ++i)
        	icons[i] = par1IconRegister.registerIcon(Variables.MODID + ":" + "testItem_" + i);
    }
}
