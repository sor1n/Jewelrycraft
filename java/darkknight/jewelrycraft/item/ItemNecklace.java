package darkknight.jewelrycraft.item;

import java.io.IOException;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;

public class ItemNecklace extends Item
{
    public IIcon jewel;
    private double amplifier;
    int index = 0;
    
    public ItemNecklace()
    {
        super();
        this.setMaxStackSize(1);
    }
    
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("jewelrycraft:necklace");
        jewel = iconRegister.registerIcon("jewelrycraft:jewelNecklace");
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
            return ItemRing.color(stack, pass);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return 16777215;
    }
    
    public IIcon getIcon(ItemStack stack, int pass)
    {
        if (pass == 0) return itemIcon;
        if (pass == 1 && JewelryNBT.jewel(stack) != null) return jewel;
        return itemIcon;
    }
    
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (JewelryNBT.ingot(stack) != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0) return JewelryNBT.ingot(stack).getDisplayName().replace("Ingot", " ").trim() + " " + ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }
    
    @SuppressWarnings(
    { "rawtypes" })
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(player.posX - 1.5D, player.posY, player.posZ - 1.5D, player.posX + 1.5D, player.posY + 2D, player.posZ + 1.5D));
            if (JewelryNBT.playerPosX(stack) != -1 && JewelryNBT.playerPosY(stack) != -1 && JewelryNBT.playerPosZ(stack) != -1)
            {
                double posX = JewelryNBT.playerPosX(stack), posY = JewelryNBT.playerPosY(stack), posZ = JewelryNBT.playerPosZ(stack);
                if (JewelryNBT.isJewelX(stack, new ItemStack(Items.ender_pearl)) && JewelryNBT.isModifierX(stack, new ItemStack(Items.bed)) && JewelryNBT.dimension(stack) != -2 && JewelryNBT.dimName(stack) != null)
                {
                    int dimension = JewelryNBT.dimension(stack);
                    for (int i = 1; i <= 20; i++)
                        world.spawnParticle("largesmoke", player.posX - 0.5D + Math.random(), player.posY - 1.5D + Math.random(), player.posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                    if (!JewelryNBT.isDimensionX(stack, player.dimension)) player.travelToDimension(dimension);
                    for (int i = 0; i < entities.size(); i++)
                        ((EntityLivingBase) entities.get(i)).setPositionAndUpdate(posX, posY, posZ);
                    for (int i = 1; i <= 300; i++)
                        world.spawnParticle("portal", posX - 0.5D + Math.random(), posY + Math.random(), posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                }
                else if (JewelryNBT.isDimensionX(stack, player.dimension))
                {
                    for (int i = 1; i <= 20; i++)
                        world.spawnParticle("largesmoke", player.posX - 0.5D + Math.random(), player.posY - 1.5D + Math.random(), player.posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                    for (int i = 0; i < entities.size(); i++)
                        ((EntityLivingBase) entities.get(i)).setPositionAndUpdate(posX, posY, posZ);
                    for (int i = 1; i <= 300; i++)
                        world.spawnParticle("portal", posX - 0.5D + Math.random(), posY + Math.random(), posZ - 0.5D + Math.random(), 0.0D, 0.0D, 0.0D);
                }
                else player.addChatMessage(new ChatComponentText("You can't teleport to these coordonates! You need to be in the same dimension they were set!"));
            }
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Items.ender_pearl)) && JewelryNBT.isModifierX(stack, new ItemStack(Items.bed)) && JewelryNBT.dimension(stack) == -2 && JewelryNBT.playerPosX(stack) == -1 && JewelryNBT.playerPosY(stack) == -1 && JewelryNBT.playerPosZ(stack) == -1)
            {
                JewelryNBT.addCoordonatesAndDimension(stack, player.posX, player.posY, player.posZ, world.provider.dimensionId, world.provider.getDimensionName());
                JewelryNBT.addFakeEnchantment(stack);
            }
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Items.ender_pearl)) && !JewelryNBT.hasTag(stack, "modifier") && JewelryNBT.playerPosX(stack) == -1 && JewelryNBT.playerPosY(stack) == -1 && JewelryNBT.playerPosZ(stack) == -1)
            {
                JewelryNBT.addCoordonatesAndDimension(stack, player.posX, player.posY, player.posZ, world.provider.dimensionId, world.provider.getDimensionName());
                JewelryNBT.addFakeEnchantment(stack);
            }
            
            if (JewelryNBT.hasTag(stack, "mode"))
            {
                String mode = "";
                if (JewelryNBT.isModeX(stack, "Activated")) mode = "Deactivated";
                else if (JewelryNBT.isModeX(stack, "Deactivated")) mode = "Activated";
                if (mode != "")
                {
                    player.addChatMessage(new ChatComponentText("The Necklace has been " + mode));
                    JewelryNBT.addMode(stack, mode);
                }
            }
        }
        return stack;
    }
    
    // @Override
    // public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer
    // player, EntityLivingBase entity)
    // {
    // if (!player.worldObj.isRemote && entity instanceof EntityLivingBase &&
    // JewelryNBT.isJewelX(stack, new ItemStack(Item.netherStar)) &&
    // JewelryNBT.isModifierX(stack, new ItemStack(Block.chest)) &&
    // JewelryNBT.entity(stack, player) == null){
    // JewelryNBT.addEntity(stack, entity);
    // JewelryNBT.addEntityID(stack, entity);
    // entity.setDead();
    // JewelryNBT.addFakeEnchantment(stack);
    // }
    // return true;
    // }
    
    /**
     * allows items to add custom lines of information to the mouseover
     * description
     */
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound())
        {
            ItemStack ingot = JewelryNBT.ingot(stack);
            if (ingot != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0) list.add("Ingot: " + EnumChatFormatting.YELLOW + ingot.getDisplayName());
            
            ItemStack jewel = JewelryNBT.jewel(stack);
            if (jewel != null) list.add("Jewel: " + EnumChatFormatting.BLUE + jewel.getDisplayName());
            
            ItemStack modifier = JewelryNBT.modifier(stack);
            if (modifier != null) list.add("Modifier: " + EnumChatFormatting.DARK_PURPLE + modifier.getDisplayName());
            
            double playerPosX = JewelryNBT.playerPosX(stack), playerPosY = JewelryNBT.playerPosY(stack), playerPosZ = JewelryNBT.playerPosZ(stack);
            if (playerPosX != -1 && playerPosY != -1 && playerPosZ != -1) list.add(EnumChatFormatting.YELLOW + "X: " + EnumChatFormatting.GRAY + (int) playerPosX + EnumChatFormatting.YELLOW + " Y: " + EnumChatFormatting.GRAY + (int) playerPosY + EnumChatFormatting.YELLOW + " Z: " + EnumChatFormatting.GRAY + (int) playerPosZ);
            
            int posX = JewelryNBT.blockCoordX(stack), posY = JewelryNBT.blockCoordY(stack), posZ = JewelryNBT.blockCoordZ(stack);
            if (posX != -1 && posY != -1 && posZ != -1) list.add(EnumChatFormatting.YELLOW + "X: " + EnumChatFormatting.GRAY + (int) posX + EnumChatFormatting.YELLOW + " Y: " + EnumChatFormatting.GRAY + (int) posY + EnumChatFormatting.YELLOW + " Z: " + EnumChatFormatting.GRAY + (int) posZ);
            
            String name = JewelryNBT.dimName(stack);
            if (name != null) list.add("Dimension: " + EnumChatFormatting.DARK_GREEN + name);
            
            EntityLivingBase entity = JewelryNBT.entity(stack, player);
            if (entity != null) list.add("Entity: " + EnumChatFormatting.GOLD + entity.getCommandSenderName());
            
            String modeN = JewelryNBT.modeName(stack);
            if (modeN != null) list.add("Mode: " + modeN);
            
            int block = JewelryNBT.blockID(stack);
            if (block != -1) list.add("Block: " + StatCollector.translateToLocalFormatted(Block.getBlockById(block).getUnlocalizedName()));
            
            int blockmeta = JewelryNBT.blockMetadata(stack);
            if (blockmeta != -1) list.add("Block Metadata: " + blockmeta);
            
            TileEntity tile = JewelryNBT.tileEntity(stack);
            if (tile != null) list.add(EnumChatFormatting.RED + "Contains a tile entity");
            
            int blockX = JewelryNBT.blockCoordX(stack);
            if (blockX != -1) list.add("Block Coords X: " + blockX);
            
            int blockY = JewelryNBT.blockCoordY(stack);
            if (blockY != -1) list.add("Block Coords Y: " + blockY);
            
            int blockZ = JewelryNBT.blockCoordZ(stack);
            if (blockZ != -1) list.add("Block Coords Z: " + blockZ);
            
            // int colorI = JewelryNBT.ingotColor(stack);
            // if(colorI != -1) list.add("Ingot Color: " + colorI);
            //
            // int colorJ = JewelryNBT.jewelColor(stack);
            // if(colorJ != -1) list.add("Jewel Color: " + colorJ);
        }
    }
    
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int side, float par8, float par9, float par10)
    {
        amplifier = 0;
        if (JewelryNBT.isJewelX(stack, new ItemStack(Items.diamond))) amplifier = 1D;
        else if (JewelryNBT.isJewelX(stack, new ItemStack(Items.emerald))) amplifier = 2D;
        else if (JewelryNBT.isJewelX(stack, new ItemStack(Items.nether_star))) amplifier = 5D;
        
        if (!world.isRemote)
        {
            onItemRightClick(stack, world, player);
            if (JewelryNBT.isModifierX(stack, new ItemStack(Items.dye, 1, 15))) for (int x = (int) -amplifier; x <= amplifier; x++)
                for (int z = (int) -amplifier; z <= amplifier; z++)
                    world.scheduleBlockUpdate(i + x, j, k + z, world.getBlock(i + x, j, k + z), 7 - (int) amplifier);
            
            for (int x = (int) -1; x <= 1; x++)
                for (int z = (int) -1; z <= 1; z++)
                    if (JewelryNBT.isModifierX(stack, new ItemStack(Items.diamond_pickaxe)) && JewelryNBT.isJewelX(stack, new ItemStack(Items.nether_star)) && JewelryNBT.isIngotX(stack, new ItemStack(ItemList.shadowIngot))) if ((side == 0 || side == 1) && j > 0 && world.getBlock(i, j + x, k + z) != Blocks.air && world.getBlock(i + x, j, k + z).getBlockHardness(world, i + x, j, k + z) > 0F) world.func_147480_a(i + x, j, k + z, true);
                    else if ((side == 2 || side == 3) && j + x > 0 && world.getBlock(i, j + x, k + z) != Blocks.air && world.getBlock(i + z, j + x, k).getBlockHardness(world, i + z, j + x, k) > 0F) world.func_147480_a(i + z, j + x, k, true);
                    else if ((side == 4 || side == 5) && j + x > 0 && world.getBlock(i, j + x, k + z) != Blocks.air && world.getBlock(i, j + x, k + z).getBlockHardness(world, i, j + x, k + z) > 0F) world.func_147480_a(i, j + x, k + z, true);
        }
        return true;
    }
    
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        amplifier = 0D;
        if (!world.isRemote)
        {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            int posX = (int) Math.floor(entityplayer.posX), posY = (int) Math.floor(entityplayer.posY), posZ = (int) Math.floor(entityplayer.posZ);
            
            if (JewelryNBT.isJewelX(stack, new ItemStack(Items.diamond))) amplifier = 1D;
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Items.emerald))) amplifier = 2D;
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Items.nether_star))) amplifier = 5D;
            
            if (JewelryNBT.isModifierX(stack, new ItemStack(Items.dye, 1, 15)) && world.getBlock(posX, posY - 1, posZ) == Blocks.farmland) for (int i = (int) -amplifier; i <= amplifier; i++)
                for (int j = (int) -amplifier; j <= amplifier; j++)
                    world.setBlockMetadataWithNotify(posX + i, posY - 1, posZ + j, 1, 7);
            
            List entities = world.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(entityplayer.posX - amplifier, entityplayer.posY - amplifier, entityplayer.posZ - amplifier, entityplayer.posX + amplifier, entityplayer.posY + 2 * amplifier, entityplayer.posZ + amplifier));
            if (JewelryNBT.isModeX(stack, "Activated"))
            {
                for (int i = 0; i < entities.size(); i++)
                {
                    if (JewelryNBT.isModifierX(stack, new ItemStack(Items.blaze_powder))) ((EntityLivingBase) entities.get(i)).addPotionEffect(new PotionEffect(Potion.fireResistance.id, 4, 0, true));
                    else if (JewelryNBT.isModifierX(stack, new ItemStack(Items.sugar))) ((EntityLivingBase) entities.get(i)).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 4, 0, true));
                    else if (JewelryNBT.isModifierX(stack, new ItemStack(Items.iron_pickaxe)) && !JewelryNBT.isJewelX(stack, new ItemStack(Items.ender_pearl))) ((EntityLivingBase) entities.get(i)).addPotionEffect(new PotionEffect(Potion.digSpeed.id, 4, 0, true));
                    else if (JewelryNBT.isModifierX(stack, new ItemStack(Items.feather)))
                    {
                        ((EntityLivingBase) entities.get(i)).addPotionEffect(new PotionEffect(Potion.jump.id, 4, 0, true));
                        entityplayer.fallDistance = 0;
                    }
                    else if (JewelryNBT.isModifierX(stack, new ItemStack(Items.potionitem, 1, 8270))) ((EntityLivingBase) entities.get(i)).addPotionEffect(new PotionEffect(Potion.invisibility.id, 4, 0, true));
                }
            }
        }
    }
    
    public ItemStack getModifiedItemStack(ItemStack ingot, ItemStack modifier, ItemStack jewel)
    {
        ItemStack itemstack = new ItemStack(this);
        JewelryNBT.addMetal(itemstack, ingot);
        JewelryNBT.addModifier(itemstack, modifier);
        JewelryNBT.addJewel(itemstack, jewel);
        if (JewelryNBT.isModifierEffectType(itemstack) && !(JewelryNBT.isJewelX(itemstack, new ItemStack(Items.ender_pearl)) && JewelryNBT.isModifierX(itemstack, new ItemStack(Items.iron_pickaxe)))) JewelryNBT.addMode(itemstack, "Activated");
        if (JewelryNBT.isJewelX(itemstack, new ItemStack(Items.nether_star)) && JewelryNBT.isModifierX(itemstack, new ItemStack(Items.book))) JewelryNBT.addMode(itemstack, "Disenchant");
        return itemstack;
    }
}
