package darkknight.jewelrycraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import darkknight.jewelrycraft.worldGen.WorldGenStructure1;

public class ItemStructureGen extends Item
{
    int structure = 0;
    WorldGenerator[] structures = new WorldGenerator[]{new WorldGenStructure1(), new WorldGenStructure1(), new WorldGenStructure1()};
    
    public ItemStructureGen()
    {
        super();
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote){
            if (!player.isSneaking()){
                if (structure < structures.length - 1) structure++;
                else structure = 0;
            }else
            {
                if (structure > 0) structure--;
                else structure = structures.length - 1;                
            }
            player.addChatMessage(new ChatComponentText("Structure no. " + structure));
        }
        return stack;
    }
    
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par1, float par2, float par3, float par4)
    {
        structures[structure].generate(world, itemRand, x, y + 1, z);
        return true;
    }
}