package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.util.JewelryNBT;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRing extends Item
{
    public Icon jewel;
    private int amplifier;
    int index = 0;

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
        if (JewelryNBT.jewel(stack) != null) return pass == 0 ? itemIcon : jewel;
        return itemIcon;
    }

    public static int color(ItemStack stack, int pass) throws IOException
    {
        String domain = "", texture;
        ResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        if (pass == 1 && JewelryNBT.ingot(stack) != null && JewelryNBT.jewel(stack) == null)
        {
            if (JewelryNBT.ingot(stack).getIconIndex().getIconName().substring(0, JewelryNBT.ingot(stack).getIconIndex().getIconName().indexOf(":") + 1) != "") domain = JewelryNBT.ingot(stack).getIconIndex().getIconName().substring(0, JewelryNBT.ingot(stack).getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
            else domain = "minecraft";
            texture = JewelryNBT.ingot(stack).getIconIndex().getIconName().substring(JewelryNBT.ingot(stack).getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
            ResourceLocation ingot = null;
            if (JewelryNBT.ingot(stack).getUnlocalizedName().contains("item")) ingot = new ResourceLocation(domain, "textures/items/" + texture);
            else ingot = new ResourceLocation(domain, "textures/blocks/" + texture);
            BufferedImage bufferedimage = ImageIO.read(rm.getResource(ingot).getInputStream());
            return bufferedimage.getRGB(9, 9);
        }
        else if (JewelryNBT.ingot(stack) != null && JewelryNBT.jewel(stack) != null)
        {
            if (pass == 1)
            {
                if (JewelryNBT.jewel(stack).getIconIndex().getIconName().substring(0, JewelryNBT.jewel(stack).getIconIndex().getIconName().indexOf(":") + 1) != "") domain = JewelryNBT.jewel(stack).getIconIndex().getIconName().substring(0, JewelryNBT.jewel(stack).getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
                else domain = "minecraft";
                texture = JewelryNBT.jewel(stack).getIconIndex().getIconName().substring(JewelryNBT.jewel(stack).getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
                ResourceLocation jewelLoc = null;
                if (JewelryNBT.jewel(stack).getUnlocalizedName().contains("item")) jewelLoc = new ResourceLocation(domain, "textures/items/" + texture);
                else jewelLoc = new ResourceLocation(domain, "textures/blocks/" + texture);
                BufferedImage bufferedimage = ImageIO.read(rm.getResource(jewelLoc).getInputStream());
                return bufferedimage.getRGB(9, 4);
            }
            if (JewelryNBT.ingot(stack).getIconIndex().getIconName().substring(0, JewelryNBT.ingot(stack).getIconIndex().getIconName().indexOf(":") + 1) != "") domain = JewelryNBT.ingot(stack).getIconIndex().getIconName().substring(0, JewelryNBT.ingot(stack).getIconIndex().getIconName().indexOf(":") + 1).replace(":", " ").trim();
            else domain = "minecraft";
            texture = JewelryNBT.ingot(stack).getIconIndex().getIconName().substring(JewelryNBT.ingot(stack).getIconIndex().getIconName().lastIndexOf(":") + 1) + ".png";
            ResourceLocation ingot = null;
            if (JewelryNBT.ingot(stack).getUnlocalizedName().contains("item")) ingot = new ResourceLocation(domain, "textures/items/" + texture);
            else ingot = new ResourceLocation(domain, "textures/blocks/" + texture);
            BufferedImage bufferedimage = ImageIO.read(rm.getResource(ingot).getInputStream());
            return bufferedimage.getRGB(9, 9);
        }
        return 16777215;
    }

    public String getItemDisplayName(ItemStack stack)
    {
        if (JewelryNBT.ingot(stack) != null && JewelryNBT.jewel(stack) != null && JewelryNBT.isJewelX(stack, new ItemStack(Item.diamond)) && JewelryNBT.isIngotX(stack, new ItemStack(Item.ingotGold))) return "Wedding Ring";
        else if(JewelryNBT.ingot(stack) != null) return JewelryNBT.ingot(stack).getDisplayName().replace("Ingot", " ").trim() + " " + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote){
            if (JewelryNBT.playerPosX(stack) != -1 && JewelryNBT.playerPosY(stack) != -1 && JewelryNBT.playerPosZ(stack) != -1){
                double posX = JewelryNBT.playerPosX(stack), posY = JewelryNBT.playerPosY(stack), posZ = JewelryNBT.playerPosZ(stack);
                if (JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)) && JewelryNBT.isModifierX(stack, new ItemStack(Item.bed)) && JewelryNBT.dimension(stack) != -2 && JewelryNBT.dimName(stack) != null)
                {
                    int dimension = JewelryNBT.dimension(stack);
                    for (int i = 1; i <= 20; i++) world.spawnParticle("largesmoke", player.posX - 0.5D + Math.random(), player.posY - 1.5D + Math.random(), player.posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                    if (!JewelryNBT.isDimensionX(stack, player.dimension)) player.travelToDimension(dimension);
                    player.setPositionAndUpdate(posX, posY, posZ);
                    for (int i = 1; i <= 300; i++) world.spawnParticle("portal", posX - 0.5D + Math.random(), posY + Math.random(), posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                }
                else if(JewelryNBT.isDimensionX(stack, player.dimension))
                {
                    for (int i = 1; i <= 20; i++) world.spawnParticle("largesmoke", player.posX - 0.5D + Math.random(), player.posY - 1.5D + Math.random(), player.posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                    player.setPositionAndUpdate(posX, posY, posZ);
                    for (int i = 1; i <= 300; i++) world.spawnParticle("portal", posX - 0.5D + Math.random(), posY + Math.random(), posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                }
                else player.addChatMessage("You can't teleport to these coordonates! You need to be in the same dimension they were set!");
            }
            else if(JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)) && JewelryNBT.isModifierX(stack, new ItemStack(Item.bed)) && JewelryNBT.dimension(stack) == -2 && JewelryNBT.playerPosX(stack) == -1 && JewelryNBT.playerPosY(stack) == -1 && JewelryNBT.playerPosZ(stack) == -1){
                JewelryNBT.addCoordonatesAndDimension(stack, player.posX, player.posY, player.posZ, world.provider.dimensionId, world.provider.getDimensionName());
                JewelryNBT.addEnchantment(stack);
            }
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Block.obsidian)) && JewelryNBT.isModifierX(stack, new ItemStack(Item.eyeOfEnder)))
            {
                InventoryEnderChest inventoryenderchest = player.getInventoryEnderChest();
                player.displayGUIChest(inventoryenderchest);
            }
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)) && JewelryNBT.isModifierX(stack, new ItemStack(Block.chest))){
                int i = JewelryNBT.blockCoordX(stack), j = JewelryNBT.blockCoordY(stack), k = JewelryNBT.blockCoordZ(stack);
                if (player.getDistance(i + 0.5F, j + 0.5F, k + 0.5F) <= 128 && i != -1 && j != -1 && k != -1){
                    int id = world.getBlockId(i, j, k);
                    if (id != 0 && Block.blocksList[id] != null && Block.blocksList[id].blockID == Block.chest.blockID){
                        TileEntity tile = world.getBlockTileEntity(i, j, k);
                        if (tile != null && tile instanceof TileEntityChest) FMLNetworkHandler.openGui(player, JewelrycraftMod.instance, 0, world, i, j, k);
                    }
                }
                else if(i != -1 && j != -1 && k != -1) player.addChatMessage("Chest out of range! You need to be " + ((int)player.getDistance(i + 0.5F, j + 0.5F, k + 0.5F) - 127) + " blocks closer.");
                else player.addChatMessage("You need to link the ring with a chest first, before using it!");
            }
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)) && JewelryNBT.playerPosX(stack) == -1 && JewelryNBT.playerPosY(stack) == -1 && JewelryNBT.playerPosZ(stack) == -1){
                JewelryNBT.addCoordonatesAndDimension(stack, player.posX, player.posY, player.posZ, world.provider.dimensionId, world.provider.getDimensionName());
                JewelryNBT.addEnchantment(stack);
            }
        }
        return stack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (!player.worldObj.isRemote && JewelryNBT.isJewelX(stack, new ItemStack(Item.netherStar)) && JewelryNBT.isModifierX(stack, new ItemStack(Block.chest)) && JewelryNBT.entity(stack, player) == null){
            JewelryNBT.addEntity(stack, entity);
            JewelryNBT.addEntityID(stack, entity);
            entity.setDead();
            JewelryNBT.addEnchantment(stack);
        }
        return true;
    }

    /**
     * allows items to add custom lines of information to the mouseover
     * description
     */
    @Override
    @SuppressWarnings(
            { "rawtypes", "unchecked" })
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound() && stack.getDisplayName() != "Wedding Ring")
        {
            ItemStack ingot = JewelryNBT.ingot(stack);
            if (ingot != null) list.add("Ingot: " + EnumChatFormatting.YELLOW + ingot.getDisplayName());

            ItemStack jewel = JewelryNBT.jewel(stack);
            if (jewel != null) list.add("Jewel: " + EnumChatFormatting.BLUE + jewel.getDisplayName());

            ItemStack modifier = JewelryNBT.modifier(stack);
            if(modifier != null) list.add("Modifier: " + EnumChatFormatting.DARK_PURPLE + modifier.getDisplayName());

            double playerPosX = JewelryNBT.playerPosX(stack), playerPosY = JewelryNBT.playerPosY(stack), playerPosZ = JewelryNBT.playerPosZ(stack);
            if(playerPosX != -1 && playerPosY != -1 && playerPosZ != -1) list.add(EnumChatFormatting.YELLOW + "X: " + EnumChatFormatting.GRAY + (int) playerPosX + EnumChatFormatting.YELLOW + " Y: " + EnumChatFormatting.GRAY + (int) playerPosY + EnumChatFormatting.YELLOW + " Z: " + EnumChatFormatting.GRAY + (int) playerPosZ);

            int posX = JewelryNBT.blockCoordX(stack), posY = JewelryNBT.blockCoordY(stack), posZ = JewelryNBT.blockCoordZ(stack);            
            if(posX != -1 && posY != -1 && posZ != -1) list.add(EnumChatFormatting.YELLOW + "X: " + EnumChatFormatting.GRAY + (int) posX + EnumChatFormatting.YELLOW + " Y: " + EnumChatFormatting.GRAY + (int) posY + EnumChatFormatting.YELLOW + " Z: " + EnumChatFormatting.GRAY + (int) posZ);

            String name = JewelryNBT.dimName(stack);
            if(name != null) list.add("Dimension: " + EnumChatFormatting.DARK_GREEN + name);

            EntityLivingBase entity = JewelryNBT.entity(stack, player);
            if (entity != null) list.add("Entity: " + EnumChatFormatting.GOLD + entity.getEntityName());
        }
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int side, float par8, float par9, float par10)
    {
        if (!world.isRemote)
        {
            EntityLivingBase entity = JewelryNBT.entity(stack, player);
            if(entity != null){
                entity.setLocationAndAngles(i + 0.5D, j + 1D, k + 0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
                world.spawnEntityInWorld(entity);
                JewelryNBT.removeEntity(stack);
            }
            if (JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)) && JewelryNBT.isModifierX(stack, new ItemStack(Block.chest)) && world.getBlockId(i, j, k) == Block.chest.blockID) JewelryNBT.addBlockCoordonates(stack, i, j, k);
            onItemRightClick(stack, world, player);
        }
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        amplifier = 0;
        if (!world.isRemote){
            EntityPlayer entityplayer = (EntityPlayer) entity;
            if (JewelryNBT.isJewelX(stack, new ItemStack(Item.diamond))) amplifier = 1;
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.emerald))) amplifier = 2;
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.netherStar))) amplifier = 7;

            if (JewelryNBT.isModifierX(stack, new ItemStack(Item.blazePowder)) && entityplayer != null) entityplayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 4, amplifier, true));
            else if (JewelryNBT.isModifierX(stack, new ItemStack(Item.sugar)) && entityplayer != null) entityplayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 4, amplifier, true));
            else if (JewelryNBT.isModifierX(stack, new ItemStack(Item.pickaxeIron)) && entityplayer != null) entityplayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 4, amplifier, true));
            else if (JewelryNBT.isModifierX(stack, new ItemStack(Item.feather)) && entityplayer != null)
            {
                entityplayer.addPotionEffect(new PotionEffect(Potion.jump.id, 4, amplifier, true));
                entityplayer.fallDistance=0;
            }
            else if (JewelryNBT.isModifierX(stack, new ItemStack(Item.potion, 1, 8270)) && entityplayer != null) entityplayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 4, amplifier, true));

        }
    }
}
