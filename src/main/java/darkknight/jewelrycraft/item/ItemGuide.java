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
import darkknight.jewelrycraft.api.IJewelryItem;

public class ItemGuide extends Item implements IJewelryItem
{
    public ItemGuide()
    {
        super();
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (world.isRemote) player.openGui(JewelrycraftMod.instance, 1, player.worldObj, 0, 0, 0);
        return stack;
    }

    /**
     * @return
     */
    @Override
    public int type()
    {
        return 0;
    }

    /**
     * @param player
     */
    @Override
    public void onWearAction(EntityPlayer player)
    {
        player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20, 0));
    }

    /**
     * @param player
     * @param source
     * @param amount
     */
    @Override
    public void onPlayerAttackedAction(EntityPlayer player, DamageSource source, float amount)
    {}

    /**
     * @param player
     * @param entity
     * @param amount
     */
    @Override
    public void onEntityAttackedByPlayer(EntityPlayer player, EntityLivingBase entity, float amount)
    {}

    /**
     * @param player
     * @param source
     */
    @Override
    public void onPlayerDeadAction(EntityPlayer player, DamageSource source)
    {}

    /**
     * @param event
     */
    @Override
    public void onPlayerRespawnAction(Clone event)
    {}
}