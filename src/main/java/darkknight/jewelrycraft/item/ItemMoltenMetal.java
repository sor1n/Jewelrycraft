package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockSkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockList;
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
        int x=0, y=0, ok = 0, red, green, blue;
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && JewelryNBT.ingot(stack) != null && JewelryNBT.ingot(stack) != new ItemStack(Item.getItemById(0), 0, 0) && JewelryNBT.ingot(stack).getIconIndex() != null && JewelryNBT.ingotColor(stack) == 16777215)
        {
            String ingotIconName = JewelryNBT.ingot(stack).getIconIndex().getIconName(); 

            if (ingotIconName.substring(0, ingotIconName.indexOf(":") + 1) != "") domain = ingotIconName.substring(0, ingotIconName.indexOf(":") + 1).replace(":", " ").trim();
            else domain = "minecraft";

            texture = ingotIconName.substring(ingotIconName.lastIndexOf(":") + 1) + ".png";
            ResourceLocation ingot = null;

            if (JewelryNBT.ingot(stack).getUnlocalizedName().contains("item")) ingot = new ResourceLocation(domain, "textures/items/" + texture);
            else ingot = new ResourceLocation(domain, "textures/blocks/" + texture);

            icon = ImageIO.read(rm.getResource(ingot).getInputStream());
            while(ok == 0)
            {
                red = (icon.getRGB(x, y) >> 16) & 0xFF;
                green = (icon.getRGB(x, y) >> 8) & 0xFF;
                blue = icon.getRGB(x, y) & 0xFF;
                if(!isColorPretty(red, green, blue))
                {
                    if(x<icon.getTileWidth()-1) x++;
                    if(x>=icon.getTileWidth()-1 && y<icon.getTileWidth()-1)
                    { 
                        x=0; 
                        y++;
                    }
                    if(x == icon.getTileWidth()-1 && y==icon.getTileWidth()-1) ok=1;
                }
                else ok=1;
            }
            JewelryNBT.addIngotColor(stack, icon.getRGB(x, y));
        }
        if(JewelryNBT.ingot(stack) != null) return JewelryNBT.ingotColor(stack);
        return 16777215;
    }
    
    public static boolean isColorPretty(int r, int g, int b)
    {
        if((r > 80 || g > 80 || b > 80) || (r > 80 && g > 80 && b > 80 && r < 230 && b < 230 && g < 230)) return true;
        else return false;
    }
}
