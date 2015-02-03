/**
 * Copyright (c) SpaceToad, 2011 http://www.mod-buildcraft.com
 *
 * BuildCraft is distributed under the terms of the Minecraft Mod Public License
 * 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */
package darkknight.jewelrycraft.events;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockMoltenMetal;
import darkknight.jewelrycraft.util.JewelryNBT;

public class BucketHandler
{
    
    public static BucketHandler INSTANCE = new BucketHandler();
    public Map<Block, Item> buckets = new HashMap<Block, Item>();
    
    private BucketHandler()
    {
    }
    
    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {        
        ItemStack result = fillCustomBucket(event.world, event.target);        
        if (result == null) return;        
        event.result = result;
        event.setResult(Result.ALLOW);
    }
    
    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos)
    {        
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        Item bucket = buckets.get(block);
        
        if (bucket != null && world.getBlock(pos.blockX, pos.blockY, pos.blockZ) != Blocks.air && world.getBlock(pos.blockX, pos.blockY, pos.blockZ) instanceof BlockMoltenMetal)
        {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            ItemStack item = new ItemStack(bucket);
            String ingotData = JewelrycraftMod.saveData.getString(pos.blockX + " " + pos.blockY + " " + pos.blockZ + " " + world.provider.dimensionId);
            if (ingotData != null && ingotData != "")
            {
                String[] splitData = ingotData.split(":");
                if (splitData.length == 3)
                {
                    int itemID, itemDamage, color;
                    try
                    {
                        itemID = Integer.parseInt(splitData[0]);
                        itemDamage = Integer.parseInt(splitData[1]);
                        color = Integer.parseInt(splitData[2]);
                        JewelryNBT.addMetal(item, new ItemStack(Item.getItemById(itemID), 1, itemDamage));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return item;
        }
        else return null;
    }
}