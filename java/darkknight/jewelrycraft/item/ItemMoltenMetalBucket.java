package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.network.PacketSendLiquidData;
import darkknight.jewelrycraft.util.JewelryNBT;

public class ItemMoltenMetalBucket extends Item
{
    public IIcon liquid;
    
    public ItemMoltenMetalBucket()
    {
        this.maxStackSize = 1;
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack stack, World par2World, EntityPlayer par3EntityPlayer)
    {
        boolean flag = BlockList.moltenMetal == Blocks.air;
        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(par2World, par3EntityPlayer, flag);
        
        if (movingobjectposition == null)
        {
            return stack;
        }
        else
        {
            FillBucketEvent event = new FillBucketEvent(par3EntityPlayer, stack, par2World, movingobjectposition);
            if (MinecraftForge.EVENT_BUS.post(event)) { return stack; }
            
            if (event.getResult() == Event.Result.ALLOW)
            {
                if (par3EntityPlayer.capabilities.isCreativeMode) { return stack; }
                
                if (--stack.stackSize <= 0) { return event.result; }
                
                if (!par3EntityPlayer.inventory.addItemStackToInventory(event.result))
                {
                    par3EntityPlayer.dropPlayerItemWithRandomChoice(event.result, false);
                }
                
                return stack;
            }
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
            {
                int i = movingobjectposition.blockX;
                int j = movingobjectposition.blockY;
                int k = movingobjectposition.blockZ;
                
                if (!par2World.canMineBlock(par3EntityPlayer, i, j, k)) { return stack; }
                
                if (flag)
                {
                    if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) { return stack; }
                    
                    Material material = par2World.getBlock(i, j, k).getMaterial();
                    int l = par2World.getBlockMetadata(i, j, k);
                    par2World.setBlockToAir(i, j, k);
                    return this.func_150910_a(stack, par3EntityPlayer, ItemList.bucket);
                }
                else
                {
                    if (BlockList.moltenMetal == Blocks.air) { return new ItemStack(Items.bucket); }
                    
                    if (movingobjectposition.sideHit == 0)
                    {
                        --j;
                    }
                    
                    if (movingobjectposition.sideHit == 1)
                    {
                        ++j;
                    }
                    
                    if (movingobjectposition.sideHit == 2)
                    {
                        --k;
                    }
                    
                    if (movingobjectposition.sideHit == 3)
                    {
                        ++k;
                    }
                    
                    if (movingobjectposition.sideHit == 4)
                    {
                        --i;
                    }
                    
                    if (movingobjectposition.sideHit == 5)
                    {
                        ++i;
                    }
                    
                    if (!par3EntityPlayer.canPlayerEdit(i, j, k, movingobjectposition.sideHit, stack)) { return stack; }
                    
                    try
                    {
                        if (this.tryPlaceContainedLiquid(par2World, i, j, k, stack) && !par3EntityPlayer.capabilities.isCreativeMode) { return new ItemStack(Items.bucket); }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            
            return stack;
        }
    }
    
    private ItemStack func_150910_a(ItemStack p_150910_1_, EntityPlayer p_150910_2_, Item p_150910_3_)
    {
        if (p_150910_2_.capabilities.isCreativeMode)
        {
            return p_150910_1_;
        }
        else if (--p_150910_1_.stackSize <= 0)
        {
            return new ItemStack(p_150910_3_);
        }
        else
        {
            if (!p_150910_2_.inventory.addItemStackToInventory(new ItemStack(p_150910_3_)))
            {
                p_150910_2_.dropPlayerItemWithRandomChoice(new ItemStack(p_150910_3_, 1, 0), false);
            }
            
            return p_150910_1_;
        }
    }
    
    /**
     * Attempts to place the liquid contained inside the bucket.
     * 
     * @throws IOException
     */
    public boolean tryPlaceContainedLiquid(World world, int x, int y, int z, ItemStack stack) throws IOException
    {
        if (BlockList.moltenMetal == Blocks.air)
        {
            return false;
        }
        else
        {
            Material material = world.getBlock(x, y, z).getMaterial();
            boolean flag = !material.isSolid();
            
            if (!world.isAirBlock(x, y, z) && !flag) return false;
            else if (stack != null && JewelryNBT.ingot(stack) != null)
            {
                if (!world.isRemote && flag && !material.isLiquid()) world.func_147480_a(x, y, z, true);
                int color = color(stack, 1);
                JewelrycraftMod.saveData.setString(x + " " + y + " " + z + " " + world.provider.dimensionId, Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) + ":" + JewelryNBT.ingot(stack).getItemDamage() + ":" + color);
                JewelrycraftMod.netWrapper.sendToAll(new PacketSendLiquidData(world.provider.dimensionId, x, y, z, Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()), JewelryNBT.ingot(stack).getItemDamage(), color));
                
                world.setBlock(x, y, z, BlockList.moltenMetal, 0, 3);
                return true;
            }
            else return false;
        }
    }
    
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("bucket_empty");
        liquid = iconRegister.registerIcon("jewelrycraft:bucketOverlay");
    }
    
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        try
        {
            return color(stack, pass);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 16777215;
    }
    
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0) return itemIcon;
        if (pass == 1) return liquid;
        return itemIcon;
    }
    
    public static int color(ItemStack stack, int pass) throws IOException
    {
        String domain = "", texture;
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        BufferedImage icon;
        if (pass == 1 && stack != null && JewelryNBT.ingot(stack) != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0 && JewelryNBT.ingot(stack).getIconIndex() != null && JewelryNBT.ingotColor(stack) == 16777215)
        {
            IIcon itemIcon = JewelryNBT.ingot(stack).getItem().getIcon(JewelryNBT.ingot(stack), 0);
            String ingotIconName = itemIcon.getIconName();
            
            if (ingotIconName.substring(0, ingotIconName.indexOf(":") + 1) != "") domain = ingotIconName.substring(0, ingotIconName.indexOf(":") + 1).replace(":", " ").trim();
            else domain = "minecraft";
            
            texture = ingotIconName.substring(ingotIconName.lastIndexOf(":") + 1) + ".png";
            ResourceLocation ingot = null;
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            
            if (texturemanager.getResourceLocation(JewelryNBT.ingot(stack).getItemSpriteNumber()).toString().contains("items")) ingot = new ResourceLocation(domain.toLowerCase(), "textures/items/" + texture);
            else ingot = new ResourceLocation(domain.toLowerCase(), "textures/blocks/" + texture);
            
            icon = ImageIO.read(rm.getResource(ingot).getInputStream());
            int height = icon.getHeight();
            int width = icon.getWidth();
            Map m = new HashMap();
            for (int i = 0; i < width; i++)
                for (int j = 0; j < height; j++)
                {
                    int rgb = icon.getRGB(i, j);
                    int red = (rgb >> 16) & 0xff;
                    int green = (rgb >> 8) & 0xff;
                    int blue = (rgb) & 0xff;
                    int[] rgbArr = {red, green, blue};
                    int Cmax = Math.max(red, Math.max(green, blue));
                    int Cmin = Math.min(red, Math.min(green, blue));
                    if (!isGray(rgbArr)) m.put(rgb, (Cmax + Cmin)/2);
                }
            int color = getMostCommonColour(m);
            if (JewelryNBT.ingot(stack) != null && JewelryNBT.ingot(stack).getItem().getColorFromItemStack(JewelryNBT.ingot(stack), 1) != 16777215) JewelryNBT.addIngotColor(stack, JewelryNBT.ingot(stack).getItem().getColorFromItemStack(JewelryNBT.ingot(stack), 1));
            else JewelryNBT.addIngotColor(stack, color);
        }
        if (JewelryNBT.ingot(stack) != null && pass == 1) return JewelryNBT.ingotColor(stack);
        return 16777215;
    }
    
    public static int getMostCommonColour(Map map)
    {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map.Entry me = (Map.Entry) list.get(list.size() - 1);
        for (int i = 0; i < list.size(); i++)
        {
            float alpha = Float.valueOf(list.get(i).toString().split("=")[1]);
            if (alpha < 180) me = (Map.Entry) list.get(i);
        }
        int rgb = (Integer) me.getKey();
        return rgb;
    }
    
    public static boolean isGray(int[] rgbArr)
    {
        int rgbSum = rgbArr[0] + rgbArr[1] + rgbArr[2];
        if (rgbSum > 0 && rgbSum < 256 * 3) { return false; }
        return true;
    }
    
    public ItemStack getModifiedItemStack(ItemStack ingot)
    {
        ItemStack itemstack = new ItemStack(this);
        JewelryNBT.addMetal(itemstack, ingot);
        return itemstack;
    }
    
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (JewelryNBT.ingot(stack) != null)
        {
            ItemStack ingot = JewelryNBT.ingot(stack);
            if (Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.stained_glass) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.stained_hardened_clay) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.wool) || Item.getIdFromItem(ingot.getItem()) == Block.getIdFromBlock(Blocks.carpet)) ingot.setItemDamage(15 - ingot.getItemDamage());
            return (StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim() + " " + ingot.getDisplayName().replace("Ingot", " ").trim();
        }
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim() + " Metal";
    }
}
