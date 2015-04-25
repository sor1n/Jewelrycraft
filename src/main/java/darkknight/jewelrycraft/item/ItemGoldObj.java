/**
 * 
 */
package darkknight.jewelrycraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.Variables;

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
        return new ItemStack(Blocks.end_portal).getIconIndex();
    }
    
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (stack != null && stack.hasTagCompound() && stack.getTagCompound().hasKey("target") && Item.getItemById(Integer.valueOf(stack.getTagCompound().getTag("target").toString().split(",")[0].substring(4).replace("s", ""))) != null && JewelryNBT.item(stack) != null) return "Golden " + JewelryNBT.item(stack).getDisplayName();
        return "Golden Object";
    }
    
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean displayInfo)
    {
        if (displayInfo) if (stack != null && JewelryNBT.item(stack) != null && JewelryNBT.item(stack).getItem() instanceof ItemFood) list.add(EnumChatFormatting.DARK_PURPLE + "It's made of solid gold! How are you suppose to eat this?");
        else list.add(EnumChatFormatting.DARK_PURPLE + "Shiny, but useless :(");
    }
}
