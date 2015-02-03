package darkknight.jewelrycraft.block;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.network.PacketRequestLiquidData;
import darkknight.jewelrycraft.network.PacketSendLiquidData;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class BlockMoltenMetal extends BlockFluidClassic
{
    
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
    
    public BlockMoltenMetal(Fluid fluid, Material material)
    {
        super(fluid, material);
        setBlockName("Jewelrycraft.moltenMetal");
        this.setQuantaPerBlock(5);
        this.setRenderPass(1);
        setLightLevel(15f);
    }
    
    @Override
    public IIcon getIcon(int side, int meta)
    {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        stillIcon = register.registerIcon("jewelrycraft:moltenMetalStill");
        flowingIcon = register.registerIcon("jewelrycraft:moltenMetalFlow");
    }
    
    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        return super.canDisplace(world, x, y, z);
    }
    
    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        return super.displaceIfPossible(world, x, y, z);
    }
    
    @Override
    protected boolean canFlowInto(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).isAir(world, x, y, z)) return true;
        
        Block block = world.getBlock(x, y, z);
        if (block == this) { return false; }
        
        if (displacements.containsKey(block)) { return displacements.get(block); }
        
        Material material = block.getMaterial();
        if (material.blocksMovement() || material == Material.water || material == Material.lava || material == Material.portal) { return false; }
        
        int density = getDensity(world, x, y, z);
        if (density == Integer.MAX_VALUE) { return true; }
        
        if (this.density > density)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, int i, int j, int k)
    {
        try
        {
            return color(world, i, j, k, false, null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }
    
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
        int expQuanta = -101;
        
        // check adjacent block levels if non-source
        if (quantaRemaining < quantaPerBlock)
        {
            int y2 = y - densityDir;
            
            if ((world.getBlock(x, y2, z) == this && JewelrycraftMod.saveData.getString(stringFromLocation(x, y2, z, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))) || 
                    (world.getBlock(x - 1, y2, z) == this && JewelrycraftMod.saveData.getString(stringFromLocation(x - 1, y2, z, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))) || 
                    (world.getBlock(x + 1, y2, z) == this && JewelrycraftMod.saveData.getString(stringFromLocation(x + 1, y2, z, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))) || 
                    (world.getBlock(x, y2, z - 1) == this && JewelrycraftMod.saveData.getString(stringFromLocation(x, y2, z - 1, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))) || 
                    (world.getBlock(x, y2, z + 1) == this && JewelrycraftMod.saveData.getString(stringFromLocation(x, y2, z + 1, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))))
            {
                expQuanta = quantaPerBlock - 1;
            }
            else
            {
                int maxQuanta = -100;
                if(JewelrycraftMod.saveData.getString(stringFromLocation(x - 1, y, z, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))) maxQuanta = getLargerQuanta(world, x - 1, y, z, maxQuanta);
                if(JewelrycraftMod.saveData.getString(stringFromLocation(x + 1, y, z, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))) maxQuanta = getLargerQuanta(world, x + 1, y, z, maxQuanta);
                if(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z - 1, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))) maxQuanta = getLargerQuanta(world, x, y, z - 1, maxQuanta);
                if(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z + 1, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)))) maxQuanta = getLargerQuanta(world, x, y, z + 1, maxQuanta);
                
                expQuanta = maxQuanta - 1;
            }
            
            // decay calculation
            if (expQuanta != quantaRemaining)
            {
                quantaRemaining = expQuanta;
                
                if (expQuanta <= 0)
                {
                    world.setBlock(x, y, z, Blocks.air);
                }
                else
                {
                    world.setBlockMetadataWithNotify(x, y, z, quantaPerBlock - expQuanta, 3);
                    world.scheduleBlockUpdate(x, y, z, this, tickRate);
                    world.notifyBlocksOfNeighborChange(x, y, z, this);
                }
            }
        }
        // This is a "source" block, set meta to zero, and send a server only
        // update
        else if (quantaRemaining >= quantaPerBlock)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
        
        String originData = JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId));
        
        // Flow vertically if possible
        if (canDisplace(world, x, y + densityDir, z))
        {
            JewelrycraftMod.saveData.setString(stringFromLocation(x, y + densityDir, z, world.provider.dimensionId), JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)));
            flowIntoBlock(world, x, y + densityDir, z, 1, originData);
            return;
        }
        
        // Flow outward if possible
        int flowMeta = quantaPerBlock - quantaRemaining + 1;
        if (flowMeta >= quantaPerBlock) { return; }
        
        if (isSourceBlock(world, x, y, z) || !isFlowingVertically(world, x, y, z))
        {
            if (world.getBlock(x, y - densityDir, z) == this && JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)).equals(JewelrycraftMod.saveData.getString(stringFromLocation(x, y - densityDir, z, world.provider.dimensionId))))
            {
                flowMeta = 1;
            }
            boolean flowTo[] = getOptimalFlowDirections(world, x, y, z);
            
            if (flowTo[0])
            {
                if (JewelrycraftMod.saveData.getTag(stringFromLocation(x - 1, y, z, world.provider.dimensionId)) == null || world.getBlock(x - 1, y, z).isAir(world, x - 1, y, z)) JewelrycraftMod.saveData.setString(stringFromLocation(x - 1, y, z, world.provider.dimensionId), JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)));
                flowIntoBlock(world, x - 1, y, z, flowMeta, originData);
            }
            if (flowTo[1])
            {
                if (JewelrycraftMod.saveData.getTag(stringFromLocation(x + 1, y, z, world.provider.dimensionId)) == null || world.getBlock(x + 1, y, z).isAir(world, x + 1, y, z)) JewelrycraftMod.saveData.setString(stringFromLocation(x + 1, y, z, world.provider.dimensionId), JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)));
                flowIntoBlock(world, x + 1, y, z, flowMeta, originData);
            }
            if (flowTo[2])
            {
                if (JewelrycraftMod.saveData.getTag(stringFromLocation(x, y, z - 1, world.provider.dimensionId)) == null || world.getBlock(x, y, z - 1).isAir(world, x, y, z - 1)) JewelrycraftMod.saveData.setString(stringFromLocation(x, y, z - 1, world.provider.dimensionId), JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)));
                flowIntoBlock(world, x, y, z - 1, flowMeta, originData);
            }
            if (flowTo[3])
            {
                if (JewelrycraftMod.saveData.getTag(stringFromLocation(x, y, z + 1, world.provider.dimensionId)) == null || world.getBlock(x, y, z + 1).isAir(world, x, y, z + 1)) JewelrycraftMod.saveData.setString(stringFromLocation(x, y, z + 1, world.provider.dimensionId), JewelrycraftMod.saveData.getString(stringFromLocation(x, y, z, world.provider.dimensionId)));
                flowIntoBlock(world, x, y, z + 1, flowMeta, originData);
            }
        }
    }
    
    public void flowIntoBlock(World world, int x, int y, int z, int meta, String originData)
    {
        if (meta < 0 || world.isRemote) return;
        if (displaceIfPossible(world, x, y, z))
        {
            world.setBlock(x, y, z, this, meta, 3);
            JewelrycraftMod.saveData.setString(stringFromLocation(x, y, z, world.provider.dimensionId), originData);
            String[] data = originData.split(":");
            JewelrycraftMod.netWrapper.sendToAll(new PacketSendLiquidData(world.provider.dimensionId, x, y, z, Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public static int color(IBlockAccess world, int i, int j, int k, boolean forcecolor, Item itemC) throws IOException
    {
        String ingotData = JewelrycraftMod.clientData.getString(String.valueOf(i) + " " + String.valueOf(j) + " " + String.valueOf(k) + " " + Minecraft.getMinecraft().theWorld.provider.dimensionId);
        if (ingotData == "")
        {
            JewelrycraftMod.netWrapper.sendToServer(new PacketRequestLiquidData(Minecraft.getMinecraft().theWorld.provider.dimensionId, i, j, k));
            return 0xFFFFFF;
        }
        else
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
                    return color;
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return 16777215;
    }
    
    public static String stringFromLocation(int x, int y, int z, int dimID)
    {
        return x + " " + y + " " + z + " " + dimID;
    }
}
