package darkknight.jewelrycraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.container.GuiHandler;

public class ItemGuide extends Item 
{

    public ItemGuide(int id) 
    {
        super(id);
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) 
    {
        if (world.isRemote) 
        {
            player.openGui(JewelrycraftMod.instance, GuiHandler.GuiId.guide.ordinal(), player.worldObj, 0, 0, 0);
        }

        return stack;
    }
}