package darkknight.jewelrycraft.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.achievements.AchievementsList;
import darkknight.jewelrycraft.api.IJewelryItem;

public class ItemGuide extends Item
{
    public ItemGuide()
    {
        super();
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (world.isRemote) player.openGui(JewelrycraftMod.instance, 1, player.worldObj, 0, 0, 0);
        player.addStat(AchievementsList.openGuide, 1);
        return stack;
    }
}