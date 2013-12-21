package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRing extends ItemBase
{
    public ItemRing(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        try
        {
            return color(par1ItemStack);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    public static int color(ItemStack stack) throws IOException
    {
        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().hasKey("ingot"))
            {
                NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("ingot");
                ItemStack ingotStack = new ItemStack(0, 0, 0);
                ingotStack.readFromNBT(ingotNBT);
                if(ingotStack.getIconIndex().getIconName() != "")
                {
                    String domain = "";
                    if(ingotStack.getIconIndex().getIconName().substring(0, ingotStack.getIconIndex().getIconName().indexOf(":") + 1) != "")
                        domain = ingotStack.getIconIndex().getIconName().substring(0, ingotStack.getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
                    else
                        domain = "minecraft";
                    String texture = ingotStack.getIconIndex().getIconName().substring(ingotStack.getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
                    ResourceLocation lava = new ResourceLocation(domain, "textures/items/" + texture);
                    ResourceManager rm = Minecraft.getMinecraft().getResourceManager();
                    BufferedImage bufferedimage = ImageIO.read(rm.getResource(lava).getInputStream());
                    return bufferedimage.getRGB(8, 8);   
                }
            }
        }
        return 0;
    }

    public String getItemDisplayName(ItemStack stack)
    {
        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().hasKey("ingot"))
            {
                NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("ingot");
                ItemStack ingotStack = new ItemStack(0, 0, 0);
                ingotStack.readFromNBT(ingotNBT);
                return ingotStack.getDisplayName().replace("Ingot", " ").trim() + " " + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
            }
        }
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }

    public static void addMetal(ItemStack item, ItemStack metal)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound ingotNBT = new NBTTagCompound();
        metal.writeToNBT(ingotNBT);
        itemStackData.setTag("ingot", ingotNBT);
    }

    public static void addEffect(ItemStack item, int potion)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound())
            itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound potionNBT = new NBTTagCompound();
        potionNBT.setInteger("potion", potion);
        itemStackData.setTag("potion", potionNBT);
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().hasKey("ingot"))
            {
                NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("ingot");
                ItemStack ingotStack = new ItemStack(0, 0, 0);
                ingotStack.readFromNBT(ingotNBT);
                list.add(EnumChatFormatting.GRAY + ingotStack.getDisplayName());
            }

            if (stack.getTagCompound().hasKey("potion"))
            {
                NBTTagCompound potionNBT = (NBTTagCompound) stack.getTagCompound().getTag("potion");
                int potion = 0;
                potion = potionNBT.getInteger("potion");
                list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal(new PotionEffect(potion, 4).getEffectName()));
            }
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        if (stack.hasTagCompound())
        {
            if(stack.getTagCompound().hasKey("potion"))
            {
                if (par3Entity instanceof EntityPlayer)
                {
                    EntityPlayer entityplayer = (EntityPlayer)par3Entity;
                    NBTTagCompound potionNBT = (NBTTagCompound) stack.getTagCompound().getTag("potion");
                    int potion = 0;
                    potion = potionNBT.getInteger("potion");
                    if(potion != 0 && entityplayer != null) entityplayer.addPotionEffect(new PotionEffect(potion, 4));
                }
            }
        }
    }
}
