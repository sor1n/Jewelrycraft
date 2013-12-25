package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
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
            return color(par1ItemStack, pass);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 16777215;
    }

    public Icon getIcon(ItemStack stack, int pass)
    {
        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().hasKey("jewel"))
            {
                NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
                ItemStack ingotStack = new ItemStack(0, 0, 0);
                ingotStack.readFromNBT(ingotNBT);
                if(pass == 0) return itemIcon;
                if(pass == 1) return jewel;
            }
        }
        return itemIcon;
    }

    public static int color(ItemStack stack, int pass) throws IOException
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("ingot") && !stack.getTagCompound().hasKey("jewel") && pass == 1)
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
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("jewel"))
        {
            if(pass == 1)
            {
                NBTTagCompound jewelNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
                ItemStack jewel = new ItemStack(0, 0, 0);
                jewel.readFromNBT(jewelNBT);
                if(jewel != null && jewel != new ItemStack(0, 0, 0) && jewel.getIconIndex().getIconName() != "")
                {
                    String domain = "";
                    if(jewel.getIconIndex().getIconName().substring(0, jewel.getIconIndex().getIconName().indexOf(":") + 1) != "")
                        domain = jewel.getIconIndex().getIconName().substring(0, jewel.getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
                    else
                        domain = "minecraft";
                    String texture = jewel.getIconIndex().getIconName().substring(jewel.getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
                    ResourceLocation jewelLoc = null;
                    if(jewel.getUnlocalizedName().contains("item")) jewelLoc = new ResourceLocation(domain, "textures/items/" + texture);
                    else jewelLoc = new ResourceLocation(domain, "textures/blocks/" + texture);
                    ResourceManager rm = Minecraft.getMinecraft().getResourceManager();
                    BufferedImage bufferedimage = ImageIO.read(rm.getResource(jewelLoc).getInputStream());
                    return bufferedimage.getRGB(9, 4);   
                }
            }
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
                if (stack.getTagCompound().hasKey("jewel"))
                {
                    NBTTagCompound jewelNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
                    ItemStack jewel = new ItemStack(0, 0, 0);
                    jewel.readFromNBT(jewelNBT);
                    if(jewel.itemID == Item.diamond.itemID && ingotStack.itemID == Item.ingotGold.itemID) return "Wedding Ring";
                }
                return ingotStack.getDisplayName().replace("Ingot", " ").trim() + " " + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
            }
        }
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote && stack.hasTagCompound())
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
                if(stack.getTagCompound().hasKey("dimension") && stack.getTagCompound().hasKey("dimName"))
                {
                    NBTTagCompound dim = (NBTTagCompound) stack.getTagCompound().getTag("dimension");
                    int dimension = 0;
                    dimension = dim.getInteger("dimension");
                    for(int i = 1; i <= 20; i++)
                        world.spawnParticle("largesmoke", player.posX - 0.5D + Math.random(), player.posY - 1.5D + Math.random(), player.posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                    if(player.dimension != dimension) player.travelToDimension(dimension);
                    player.setPositionAndUpdate(posX, posY, posZ);
                    player.fallDistance = 7;
                    for(int i = 1; i <= 300; i++)
                        world.spawnParticle("portal", posX - 0.5D + Math.random(), posY + Math.random(), posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                }
                else
                {
                    for(int i = 1; i <= 20; i++)
                        world.spawnParticle("largesmoke", player.posX - 0.5D + Math.random(), player.posY - 1.5D + Math.random(), player.posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                    player.setPositionAndUpdate(posX, posY, posZ);
                    for(int i = 1; i <= 300; i++)
                        world.spawnParticle("portal", posX - 0.5D + Math.random(), posY + Math.random(), posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
            if(stack.getTagCompound().hasKey("jewel"))
            {
                NBTTagCompound jewelNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
                ItemStack jewel = new ItemStack(0, 0, 0);
                jewel.readFromNBT(jewelNBT);
                if(stack.getTagCompound().hasKey("modifier"))
                {
                    NBTTagCompound modifierNBT = (NBTTagCompound) stack.getTagCompound().getTag("modifier");
                    ItemStack modifier = new ItemStack(0, 0, 0);
                    modifier.readFromNBT(modifierNBT);
                    if(jewel.itemID == Item.enderPearl.itemID && modifier.itemID == Item.bed.itemID && !stack.getTagCompound().hasKey("x") && !stack.getTagCompound().hasKey("y") && !stack.getTagCompound().hasKey("z") && !stack.getTagCompound().hasKey("dimension")) 
                    {
                        JewelryNBT.addCoordonatesAndDimension(stack, player.posX, player.posY, player.posZ, world.provider.dimensionId, world.provider.getDimensionName());
                        JewelryNBT.addEnchantment(stack);
                    }
                    if(jewel.itemID == Block.obsidian.blockID && modifier.itemID == Item.eyeOfEnder.itemID)
                    {
                        InventoryEnderChest inventoryenderchest = player.getInventoryEnderChest();
                        player.displayGUIChest(inventoryenderchest);
                    }
                }
                else if(jewel.itemID == Item.enderPearl.itemID && !stack.getTagCompound().hasKey("x") && !stack.getTagCompound().hasKey("y") && !stack.getTagCompound().hasKey("z")) 
                {
                    JewelryNBT.addCoordonates(stack, player.posX, player.posY, player.posZ);
                    JewelryNBT.addEnchantment(stack);
                }
            }
        }
        return stack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (!player.worldObj.isRemote && stack.hasTagCompound())
        {
            if(stack.getTagCompound().hasKey("jewel") && stack.getTagCompound().hasKey("modifier") && !stack.getTagCompound().hasKey("entityID") && !stack.getTagCompound().hasKey("entity"))
            {
                NBTTagCompound jewelNBT = (NBTTagCompound) stack.getTagCompound().getTag("jewel");
                NBTTagCompound modifierNBT = (NBTTagCompound) stack.getTagCompound().getTag("modifier");
                ItemStack jewel = new ItemStack(0, 0, 0);
                ItemStack modifier = new ItemStack(0, 0, 0);
                jewel.readFromNBT(jewelNBT);
                modifier.readFromNBT(modifierNBT);
                if(jewel.itemID == Item.netherStar.itemID && modifier.itemID == Block.chest.blockID)
                {
                    JewelryNBT.addEntity(stack, entity);
                    JewelryNBT.addEntityID(stack, entity);
                    entity.setDead();
                    JewelryNBT.addEnchantment(stack);
                }
            }
        }
        return true;
    }

    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SuppressWarnings({ "rawtypes", "unchecked"})
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound() && stack.getDisplayName() != "Wedding Ring")
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

            if (stack != null && stack != new ItemStack(0, 0, 0) && stack.getTagCompound().hasKey("dimName"))
            {
                NBTTagCompound dim = (NBTTagCompound) stack.getTagCompound().getTag("dimName");
                String name = "";
                name = dim.getString("dimName");
                list.add("Dimension: " + EnumChatFormatting.DARK_GREEN + name);
            }

            if (stack != null && stack != new ItemStack(0, 0, 0) && stack.getTagCompound().hasKey("entityID") && stack.getTagCompound().hasKey("entity"))
            {
                NBTTagCompound enID = (NBTTagCompound) stack.getTagCompound().getTag("entityID");
                NBTTagCompound en = (NBTTagCompound) stack.getTagCompound().getTag("entity");
                int entityID = 0;
                entityID = enID.getInteger("entityID");
                EntityLivingBase entity = (EntityLivingBase) EntityList.createEntityByID(entityID, player.worldObj);
                entity.readFromNBT(en);
                list.add("Entity: " + EnumChatFormatting.GOLD + entity.getEntityName());
            }
        }
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int side, float par8, float par9, float par10)
    {
        if (!world.isRemote && stack.hasTagCompound())
        {
            if (stack != null && stack != new ItemStack(0, 0, 0) && stack.getTagCompound().hasKey("entityID") && stack.getTagCompound().hasKey("entity"))
            {
                NBTTagCompound enID = (NBTTagCompound) stack.getTagCompound().getTag("entityID");
                NBTTagCompound en = (NBTTagCompound) stack.getTagCompound().getTag("entity");
                int entityID = 0;
                entityID = enID.getInteger("entityID");
                EntityLivingBase entity = (EntityLivingBase) EntityList.createEntityByID(entityID, player.worldObj);
                entity.readFromNBT(en);
                entity.setLocationAndAngles(i + 0.5D, j + 1D, k + 0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);                
                world.spawnEntityInWorld(entity);
                JewelryNBT.removeNBT(stack, "entityID");
                JewelryNBT.removeNBT(stack, "entity");
                JewelryNBT.removeNBT(stack, "ench");
            }            
        }
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        amplifier = 0;
        if (!world.isRemote && stack.hasTagCompound())
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
                if (entity instanceof EntityPlayer)
                {
                    EntityPlayer entityplayer = (EntityPlayer)entity;
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
