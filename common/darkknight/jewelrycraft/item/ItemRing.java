package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRing extends ItemBase
{
    public Icon jewel;
    private int amplifier;

    public ItemRing(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
    }

    public void registerIcons(IconRegister iconRegister) 
    {
        itemIcon = iconRegister.registerIcon("jewelrycraft:ring");
        jewel = iconRegister.registerIcon("jewelrycraft:jewel");
    }

    public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("jewel")) return getIcon(stack, 1);
        return getIcon(stack, 0);
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int pass) 
    {
        return pass == 0 ? super.getIconFromDamageForRenderPass(damage, pass) : jewel;
    }

    @Override
    public boolean requiresMultipleRenderPasses() 
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
    {
        try
        {
            if(par1ItemStack != null) return color(par1ItemStack, pass);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 16777215;
    }

    public static int color(ItemStack stack, int pass) throws IOException
    {
        if (pass == 0 && stack.hasTagCompound() && stack.getTagCompound().hasKey("ingot"))
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
                ResourceLocation ingot = null;
                if(ingotStack.getUnlocalizedName().contains("item")) ingot = new ResourceLocation(domain, "textures/items/" + texture);
                else ingot = new ResourceLocation(domain, "textures/blocks/" + texture);
                ResourceManager rm = Minecraft.getMinecraft().getResourceManager();
                BufferedImage bufferedimage = ImageIO.read(rm.getResource(ingot).getInputStream());
                return bufferedimage.getRGB(9, 9);   
            }
        }
        if (pass == 1 && stack.hasTagCompound() && stack.getTagCompound().hasKey("jewel"))
        {
            NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
            ItemStack ingotStack = new ItemStack(0, 0, 0);
            ingotStack.readFromNBT(ingotNBT);
            if(ingotStack != null && ingotStack != new ItemStack(0, 0, 0) && ingotStack.getIconIndex().getIconName() != "")
            {
                String domain = "";
                if(ingotStack.getIconIndex().getIconName().substring(0, ingotStack.getIconIndex().getIconName().indexOf(":") + 1) != "")
                    domain = ingotStack.getIconIndex().getIconName().substring(0, ingotStack.getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
                else
                    domain = "minecraft";
                String texture = ingotStack.getIconIndex().getIconName().substring(ingotStack.getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
                ResourceLocation jewel = null;
                if(ingotStack.getUnlocalizedName().contains("item")) jewel = new ResourceLocation(domain, "textures/items/" + texture);
                else jewel = new ResourceLocation(domain, "textures/blocks/" + texture);
                ResourceManager rm = Minecraft.getMinecraft().getResourceManager();
                BufferedImage bufferedimage = ImageIO.read(rm.getResource(jewel).getInputStream());
                return bufferedimage.getRGB(9, 4);   
            }
        }
        return 16777215;
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

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (stack.hasTagCompound())
        {
            if(stack.getTagCompound().hasKey("x") && stack.getTagCompound().hasKey("y") && stack.getTagCompound().hasKey("z"))
            {
                NBTTagCompound x = (NBTTagCompound) stack.getTagCompound().getTag("x");
                NBTTagCompound y = (NBTTagCompound) stack.getTagCompound().getTag("y");
                NBTTagCompound z = (NBTTagCompound) stack.getTagCompound().getTag("z");
                double posX = 0, posY = 0, posZ = 0;
                posX = x.getDouble("x");
                posY = y.getDouble("y");
                posZ = z.getDouble("z");
                for(int i = 1; i <= 20; i++)
                world.spawnParticle("largesmoke", player.posX - 0.5D + Math.random(), player.posY - 1.5D + Math.random(), player.posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                player.setPositionAndUpdate(posX, posY, posZ);
                for(int i = 1; i <= 300; i++)
                world.spawnParticle("portal", posX - 0.5D + Math.random(), posY + Math.random(), posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
            }
            if(stack.getTagCompound().hasKey("jewel"))
            {
                NBTTagCompound jewelNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
                ItemStack jewel = new ItemStack(0, 0, 0);
                jewel.readFromNBT(jewelNBT);
                if(jewel.itemID == Item.enderPearl.itemID && !stack.getTagCompound().hasKey("x") && !stack.getTagCompound().hasKey("y") && !stack.getTagCompound().hasKey("z")) 
                    JewelryNBT.addCoordonates(stack, player.posX, player.posY, player.posZ);
            }
        }
        return stack;
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
            if (stack != null && stack != new ItemStack(0, 0, 0) && stack.getTagCompound().hasKey("ingot"))
            {
                NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("ingot");
                ItemStack ingotStack = new ItemStack(0, 0, 0);
                ingotStack.readFromNBT(ingotNBT);
                if(ingotStack != null && ingotStack != new ItemStack(0, 0, 0) && ingotStack.getDisplayName() != null) 
                    list.add("Ingot: " + EnumChatFormatting.YELLOW + ingotStack.getDisplayName());
            }

            if (stack != null && stack != new ItemStack(0, 0, 0) && stack.getTagCompound().hasKey("jewel"))
            {
                NBTTagCompound jewelNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
                ItemStack jewel = new ItemStack(0, 0, 0);
                jewel.readFromNBT(jewelNBT);
                if(jewel != null && jewel != new ItemStack(0, 0, 0) && jewel.getDisplayName() != null) 
                    list.add("Jewel: " + EnumChatFormatting.BLUE + jewel.getDisplayName());
            }

            if (stack != null && stack != new ItemStack(0, 0, 0) && stack.getTagCompound().hasKey("modifier"))
            {
                NBTTagCompound modifierNBT = (NBTTagCompound) stack.getTagCompound().getTag("modifier");
                ItemStack modifier = new ItemStack(0, 0, 0);
                modifier.readFromNBT(modifierNBT);
                if(modifier != null && modifier != new ItemStack(0, 0, 0) && modifier.getDisplayName() != null) 
                    list.add("Modifier: " + EnumChatFormatting.DARK_PURPLE + modifier.getDisplayName());
            }

            if (stack != null && stack != new ItemStack(0, 0, 0) && stack.getTagCompound().hasKey("x") && stack.getTagCompound().hasKey("y") && stack.getTagCompound().hasKey("z"))
            {
                NBTTagCompound x = (NBTTagCompound) stack.getTagCompound().getTag("x");
                NBTTagCompound y = (NBTTagCompound) stack.getTagCompound().getTag("y");
                NBTTagCompound z = (NBTTagCompound) stack.getTagCompound().getTag("z");
                double posX = 0, posY = 0, posZ = 0;
                posX = x.getDouble("x");
                posY = y.getDouble("y");
                posZ = z.getDouble("z");
                list.add(EnumChatFormatting.YELLOW + "X: " + EnumChatFormatting.GRAY + (int)posX + EnumChatFormatting.YELLOW + " Y: " + EnumChatFormatting.GRAY + (int)posY + EnumChatFormatting.YELLOW + " Z: " + EnumChatFormatting.GRAY + (int)posZ);
            }
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World par2World, Entity par3Entity, int par4, boolean par5)
    {
        amplifier = 0;
        if (stack.hasTagCompound())
        {
            if(stack.getTagCompound().hasKey("jewel"))
            {
                NBTTagCompound jewelNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
                ItemStack jewel = new ItemStack(0, 0, 0);
                jewel.readFromNBT(jewelNBT);
                if(jewel.itemID == Item.diamond.itemID) amplifier = 1;
                if(jewel.itemID == Item.emerald.itemID) amplifier = 2;
            }
            if(stack.getTagCompound().hasKey("modifier"))
            {
                if (par3Entity instanceof EntityPlayer)
                {
                    EntityPlayer entityplayer = (EntityPlayer)par3Entity;
                    NBTTagCompound modifierNBT = (NBTTagCompound) stack.getTagCompound().getTag("modifier");
                    ItemStack modifier = new ItemStack(0, 0, 0);
                    modifier.readFromNBT(modifierNBT);
                    if(modifier.itemID == Item.blazePowder.itemID && entityplayer != null) 
                        entityplayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 4, amplifier));
                    if(modifier.itemID == Item.sugar.itemID && entityplayer != null) 
                        entityplayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 4, amplifier));
                    if(modifier.itemID == Item.pickaxeIron.itemID && entityplayer != null) 
                        entityplayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 4, amplifier));
                }
            }
        }
    }
}
