package darkknight.jewelrycraft.item;

import java.util.List;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import darkknight.jewelrycraft.JewelrycraftMod;

public class ItemJewelryModifier extends Item
{
    public ItemJewelryModifier()
    {
        super();
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote && player.capabilities.isCreativeMode) player.openGui(JewelrycraftMod.instance, 3, world, 0, 0, 0);
        return stack;
    }
    
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
       list.add(EnumChatFormatting.GRAY + "Creative Only");
    }
}