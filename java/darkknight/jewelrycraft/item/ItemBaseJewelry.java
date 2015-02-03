package darkknight.jewelrycraft.item;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;

public class ItemBaseJewelry extends Item
{
    public ItemBaseJewelry()
    {
        super();
        this.setMaxStackSize(1);
    }
    
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
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
    
    public static int color(ItemStack stack, int pass) throws IOException
    {
        String domain = "", texture;
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        BufferedImage icon;
        if (pass == 0 && stack != null && JewelryNBT.ingot(stack) != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0 && JewelryNBT.ingot(stack).getIconIndex() != null && JewelryNBT.ingotColor(stack) == 16777215)
        {
            ItemStack in = JewelryNBT.ingot(stack);
            if (Item.getIdFromItem(in.getItem()) == Block.getIdFromBlock(Blocks.stained_glass) || Item.getIdFromItem(in.getItem()) == Block.getIdFromBlock(Blocks.stained_hardened_clay) || Item.getIdFromItem(in.getItem()) == Block.getIdFromBlock(Blocks.wool) || Item.getIdFromItem(in.getItem()) == Block.getIdFromBlock(Blocks.carpet)) in.setItemDamage(15 - in.getItemDamage());
            IIcon itemIcon = in.getItem().getIcon(in, 0);
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
        else if (pass == 1 && stack != null && JewelryNBT.gem(stack) != null && JewelryNBT.gem(stack).getIconIndex() != null && JewelryNBT.gem(stack) != null)
        {
            IIcon itemIcon = JewelryNBT.gem(stack).getItem().getIconFromDamage(JewelryNBT.gem(stack).getItemDamage());
            String jewelIconName = itemIcon.getIconName();
            
            if (jewelIconName.substring(0, jewelIconName.indexOf(":") + 1) != "") domain = jewelIconName.substring(0, jewelIconName.indexOf(":") + 1).replace(":", " ").trim();
            else domain = "minecraft";
            
            texture = jewelIconName.substring(jewelIconName.lastIndexOf(":") + 1) + ".png";
            ResourceLocation jewelLoc = null;
            
            if (JewelryNBT.gem(stack).getUnlocalizedName().contains("item")) jewelLoc = new ResourceLocation(domain, "textures/items/" + texture);
            else jewelLoc = new ResourceLocation(domain, "textures/blocks/" + texture);
            
            icon = ImageIO.read(rm.getResource(jewelLoc).getInputStream());
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
            if (JewelryNBT.gem(stack).getItem().getColorFromItemStack(JewelryNBT.gem(stack), 1) == 16777215) JewelryNBT.addGemColor(stack, color);
            else JewelryNBT.addGemColor(stack, JewelryNBT.gem(stack).getItem().getColorFromItemStack(JewelryNBT.gem(stack), 1));
        }
        if (pass == 0 && JewelryNBT.ingot(stack) != null) return JewelryNBT.ingotColor(stack);
        if (pass == 1 && JewelryNBT.gem(stack) != null) return JewelryNBT.gemColor(stack);
        else if (JewelryNBT.ingot(stack) != null) return JewelryNBT.ingotColor(stack);
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
    
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (JewelryNBT.ingot(stack) != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0) return JewelryNBT.ingot(stack).getDisplayName().replace("Ingot", " ").trim() + " " + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound() && par4)
        {
            ItemStack ingot = JewelryNBT.ingot(stack);
            if (ingot != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0) list.add("Ingot: " + EnumChatFormatting.YELLOW + ingot.getDisplayName());
            
            ItemStack gem = JewelryNBT.gem(stack);
            if (gem != null) list.add("Gem: " + EnumChatFormatting.BLUE + gem.getDisplayName());
            
            ArrayList<ItemStack> modifier = JewelryNBT.modifier(stack);
            if (!modifier.isEmpty()) list.add("Modifiers: ");
            for (int i = 0; i < modifier.size(); i++)
                list.add(EnumChatFormatting.DARK_PURPLE + modifier.get(i).getDisplayName() + " x" + modifier.get(i).stackSize);
        }
    }
}
