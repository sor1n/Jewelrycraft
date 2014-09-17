package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;

public class ItemMoltenMetal extends Item
{
    private int amplifier, cooldown = 0;
    int index = 0;
    
    public ItemMoltenMetal()
    {
        super();
        this.setMaxStackSize(1);
    }
    
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("jewelrycraft:moltenMetalStill");
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
        int x = 0, y = 0, ok = 0, red, green, blue;
        if (stack != null && JewelryNBT.ingot(stack) != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0 && JewelryNBT.ingot(stack).getIconIndex() != null && JewelryNBT.ingotColor(stack) == 16777215)
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
            while (ok == 0)
            {
                red = (icon.getRGB(x, y) >> 16) & 0xFF;
                green = (icon.getRGB(x, y) >> 8) & 0xFF;
                blue = icon.getRGB(x, y) & 0xFF;
                if (!isColorPretty(red, green, blue))
                {
                    if (x < icon.getTileWidth() - 1) x++;
                    if (x >= icon.getTileWidth() - 1 && y < icon.getTileWidth() - 1)
                    {
                        x = 0;
                        y++;
                    }
                    if (x == icon.getTileWidth() - 1 && y == icon.getTileWidth() - 1) ok = 1;
                }
                else ok = 1;
            }
            JewelryNBT.addIngotColor(stack, icon.getRGB(x, y));
        }
        if (JewelryNBT.ingot(stack) != null) return JewelryNBT.ingotColor(stack);
        return 16777215;
    }
    
    public static boolean isColorPretty(int r, int g, int b)
    {
        if ((r >= 100 && g >= 100 && b >= 100 && r < 230 && b < 230 && g < 230) || ((r >= 100 && (g < 100 || b < 100)) || (g >= 100 && (r < 100 || b < 100)) || (b >= 100 && (g < 100 || r < 100)))) return true;
        else return false;
    }
}
