package darkknight.jewelrycraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlockItemCrystal extends ItemBlock 
{
	public BlockItemCrystal(Block block) {
		super(block);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata (int damageValue) 
	{
		return damageValue;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) 
	{
		return getUnlocalizedName() + "." + itemstack.getItemDamage();
	}
	
    @Override
    @SideOnly (Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        return stack.getItemDamage() < 16 ? BlockCrystal.colors[stack.getItemDamage()] : 0;
    }

}