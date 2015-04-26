/**
 * 
 */
package darkknight.jewelrycraft.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.block.BlockMidasTouch;
import darkknight.jewelrycraft.tileentity.TileEntityMidasTouch;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

/**
 * @author Sorin
 */
public class BlockEventHandler
{
    @SubscribeEvent
    public void onBlockDestroyed(BlockEvent.BreakEvent event)
    {
        if (event.getPlayer() != null){
            ItemStack item = event.getPlayer().inventory.getCurrentItem();
            if (event.block instanceof BlockMidasTouch && item != null && item.getItem().getHarvestLevel(item, "pickaxe") >= event.block.getHarvestLevel(event.blockMetadata)){
                TileEntityMidasTouch te = (TileEntityMidasTouch)event.world.getTileEntity(event.x, event.y, event.z);
                if (te.target != null) dropItem(te.getWorldObj(), te.xCoord, te.yCoord, te.zCoord, new ItemStack(Items.gold_nugget, (int)(te.target.width * te.target.height * 100), 0));
            }
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(event.getPlayer(), Variables.MODID);
            for(Curse curse: Curse.getCurseList())
                if (playerInfo.getInteger(curse.getName()) > 0) curse.onBlockDestroyed(event.getPlayer(), event);
        }
    }
    
    @SubscribeEvent
    public void onBlockItemsDrop(BlockEvent.HarvestDropsEvent event)
    {
        EntityPlayer player = event.harvester;
        if (player != null){
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            for(Curse curse: Curse.getCurseList())
                if (playerInfo.getInteger(curse.getName()) > 0) curse.onBlockItemsDrop(player, event);
        }
    }
    
    public void dropItem(World world, double x, double y, double z, ItemStack stack)
    {
        EntityItem entityitem = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, stack);
        entityitem.motionX = 0;
        entityitem.motionZ = 0;
        entityitem.motionY = 0.11000000298023224D;
        world.spawnEntityInWorld(entityitem);
    }
}
