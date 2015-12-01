package darkknight.jewelrycraft.item;

import java.util.List;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemStructureGen extends Item
{
    int no = 0;
    
    public ItemStructureGen()
    {
        super();
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote && player.capabilities.isCreativeMode){
            if (!player.isSneaking()){
                if (no < JewelrycraftUtil.structures.size() - 1) no++;
                else no = 0;
            }else
            {
                if (no > 0) no--;
                else no = JewelrycraftUtil.structures.size() - 1;                
            }
            player.addChatMessage(new ChatComponentText("Structure no. " + (no + 1)));
        }
        return stack;
    }
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par1, float par2, float par3, float par4)
    {
        if(player.capabilities.isCreativeMode) JewelrycraftUtil.structures.get(no).generate(world, world.getBiomeGenForCoords(x, z), itemRand, x, y + 1, z);
        return true;
    }
    
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
       list.add(EnumChatFormatting.GRAY + "Creative Only");
    }
}