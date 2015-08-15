/**
 * 
 */
package darkknight.jewelrycraft.curses;

import java.util.ArrayList;

import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.entities.EntityHalfHeart;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CurseRabbitsPaw extends Curse
{
    public CurseRabbitsPaw(String name, int txtID, String pack)
    {
        super(name, txtID, pack);
    }
    
    @Override
    public void entityDropItems(EntityPlayer player, Entity target, ArrayList<EntityItem> drops)
    {
        for(EntityItem item: drops){
            ItemStack drop = item.getEntityItem().copy();
            drop.stackSize = this.rand.nextInt(4);
            if (drop.stackSize > 0) target.entityDropItem(drop, 0.5F);
        }
    }
    
    public void entityDeathAction(World world, EntityLivingBase target, EntityPlayer player)
    {
        String[] types = {"Red", "Blue", "White", "Black"};
        String type = types[rand.nextInt(4)];
        if (rand.nextInt(3) == 0 && target.getCreatureAttribute() != JewelrycraftUtil.HEART){
            if (type == "White"){
                EntityHeart h = new EntityHalfHeart(world);
                h.setType(type);
                h.setLocationAndAngles(target.posX, target.posY, target.posZ, MathHelper.wrapAngleTo180_float(rand.nextFloat() * 360.0F), 0.0F);
                world.spawnEntityInWorld(h);
            }else{
                for(int i = 1; i <= 1 + rand.nextInt(1 + (int)(target.getMaxHealth() / 20)); i++){
                    EntityHeart[] hearts = {new EntityHeart(world), new EntityHalfHeart(world)};
                    EntityHeart h = hearts[rand.nextInt(2)];
                    h.setType(type);
                    h.setLocationAndAngles(target.posX, target.posY, target.posZ, MathHelper.wrapAngleTo180_float(rand.nextFloat() * 360.0F), 0.0F);
                    world.spawnEntityInWorld(h);
                }
            }
        }
        if (rand.nextInt(3) == 0) world.spawnEntityInWorld(new EntityXPOrb(world, target.posX, target.posY, target.posZ, 1 + rand.nextInt(40)));
    }
    
    public String getDescription()
    {
        return StatCollector.translateToLocal("curse." + Variables.MODID + ".rabbitspaw.description");
    }
    
	@Override
	public String getDisplayName() 
	{
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".rabbitspaw");
	}

    @Override
    public boolean canCurseBeActivated()
    {
        return ConfigHandler.CURSE_RABBIT_PAW;
    }
}
