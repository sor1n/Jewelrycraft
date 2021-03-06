/**
 * 
 */
package darkknight.jewelrycraft.item;

import java.util.List;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ItemGoldObj extends Item
{
    public ItemGoldObj()
    {}
    
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @SideOnly (Side.CLIENT)
    public boolean hasEffect(ItemStack item)
    {
        return true;
    }
    
    @SideOnly (Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        return 0xffff00;
    }
    
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("apple_golden");
    }
    
    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        ItemStack item = JewelryNBT.item(stack);
        if (item != null) return item.getIconIndex();
        return new ItemStack(Items.golden_apple).getIconIndex();
    }
    
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("target") && Item.getItemById(Integer.valueOf(stack.getTagCompound().getTag("target").toString().split(",")[0].substring(4).replace("s", ""))) != null && JewelryNBT.item(stack) != null) return StatCollector.translateToLocal("info." + Variables.MODID + ".golden") + " " + JewelryNBT.item(stack).getDisplayName();
        return StatCollector.translateToLocal("item." + Variables.MODID + ".goldObject.name");
    }
    
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean displayInfo)
    {
        if (ConfigHandler.JEWELRY_INFO) if (stack != null && JewelryNBT.item(stack) != null && JewelryNBT.item(stack).getItem() instanceof ItemFood) list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("item." + Variables.MODID + ".goldObject.info.food"));
        else list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("item." + Variables.MODID + ".goldObject.info.standard"));
    }
}
