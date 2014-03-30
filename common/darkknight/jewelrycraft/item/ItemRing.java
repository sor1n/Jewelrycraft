package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.container.GuiHandler;
import darkknight.jewelrycraft.util.JewelryNBT;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.resources.ResourceManager;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class ItemRing extends Item
{
    public Icon jewel;
    private int amplifier, cooldown = 0;
    int index = 0;

    public ItemRing(int par1)
    {
        super(par1);
        this.setMaxStackSize(1);
    }

    public void registerIcons(IconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon("jewelrycraft:ring");
        jewel = iconRegister.registerIcon("jewelrycraft:jewelRing");
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

    public Icon getIcon(ItemStack stack, int pass)
    {
        if(pass == 0) return itemIcon;
        if(pass == 1 && JewelryNBT.jewel(stack) != null) return jewel;
        return itemIcon;
    }

    public static int color(ItemStack stack, int pass) throws IOException
    {
        String domain = "", texture;
        ResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        BufferedImage icon;
        int x=0, y=0, ok = 0, red, green, blue;
        if (pass == 0 && JewelryNBT.ingot(stack) != null && JewelryNBT.ingotColor(stack) == 16777215)
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
                if((red <= 80 && green <= 80 && blue <= 80) || (red >= 180 && green >= 180 && blue >= 180))
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
        else if (pass == 1 && JewelryNBT.jewel(stack) != null && JewelryNBT.jewelColor(stack) == 16777215)
        {
            x = 0; y = 0; ok=0;
            String jewelIconName = JewelryNBT.jewel(stack).getIconIndex().getIconName(); 

            if (jewelIconName.substring(0, jewelIconName.indexOf(":") + 1) != "") domain = jewelIconName.substring(0, jewelIconName.indexOf(":") + 1).replace(":", " ").trim();
            else domain = "minecraft";

            texture = jewelIconName.substring(jewelIconName.lastIndexOf(":") + 1) + ".png";
            ResourceLocation jewelLoc = null;

            if (JewelryNBT.jewel(stack).getUnlocalizedName().contains("item")) jewelLoc = new ResourceLocation(domain, "textures/items/" + texture);
            else jewelLoc = new ResourceLocation(domain, "textures/blocks/" + texture);

            icon = ImageIO.read(rm.getResource(jewelLoc).getInputStream());
            while(ok == 0)
            {
                red = (icon.getRGB(x, y) >> 16) & 0xFF;
                green = (icon.getRGB(x, y) >> 8) & 0xFF;
                blue = icon.getRGB(x, y) & 0xFF;
                if((red <= 95 && green <= 95 && blue <= 95) || (red >= 180 && green >= 180 && blue >= 180))
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
            if(JewelryNBT.jewel(stack).getItem().getColorFromItemStack(JewelryNBT.jewel(stack), 1) == 16777215) JewelryNBT.addJewelColor(stack, icon.getRGB(x, y));
            else JewelryNBT.addJewelColor(stack, JewelryNBT.jewel(stack).getItem().getColorFromItemStack(JewelryNBT.jewel(stack), 1));
        }
        if(pass == 0 && JewelryNBT.ingot(stack) != null) return JewelryNBT.ingotColor(stack);
        if(pass == 1 && JewelryNBT.jewel(stack) != null) return JewelryNBT.jewelColor(stack);
        else if(JewelryNBT.ingot(stack) != null) return JewelryNBT.ingotColor(stack);
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
            if (JewelryNBT.playerPosX(stack) != -1 && JewelryNBT.playerPosY(stack) != -1 && JewelryNBT.playerPosZ(stack) != -1)
            {
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
                JewelryNBT.addFakeEnchantment(stack);
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
                        if (tile != null && tile instanceof TileEntityChest) FMLNetworkHandler.openGui(player, JewelrycraftMod.instance, GuiHandler.GuiId.ringChest.ordinal(), world, i, j, k);
                    }
                }
                else if(i != -1 && j != -1 && k != -1) player.addChatMessage("Chest out of range! You need to be " + ((int)player.getDistance(i + 0.5F, j + 0.5F, k + 0.5F) - 127) + " blocks closer.");
                else player.addChatMessage("You need to link the ring with a chest first, before using it!");
            }
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)) && !JewelryNBT.hasTag(stack, "modifier") && JewelryNBT.playerPosX(stack) == -1 && JewelryNBT.playerPosY(stack) == -1 && JewelryNBT.playerPosZ(stack) == -1){
                JewelryNBT.addCoordonatesAndDimension(stack, player.posX, player.posY, player.posZ, world.provider.dimensionId, world.provider.getDimensionName());
                JewelryNBT.addFakeEnchantment(stack);
            }

            if(JewelryNBT.hasTag(stack, "mode"))
            {
                String mode = "";
                if(JewelryNBT.isModeX(stack, "Disenchant")) mode = "Transfer";
                else if(JewelryNBT.isModeX(stack, "Transfer")) mode = "Enchant";
                else if(JewelryNBT.isModeX(stack, "Enchant")) mode = "Disenchant";
                if(mode != "")
                {
                    player.addChatMessage("Switched to " + mode + " mode");
                    JewelryNBT.addMode(stack, mode);
                }
                if(JewelryNBT.isModeX(stack, "Activated")) mode = "Deactivated";
                else if(JewelryNBT.isModeX(stack, "Deactivated")) mode = "Activated";
                if(mode != "")
                {
                    player.addChatMessage("The Ring has been " + mode);
                    JewelryNBT.addMode(stack, mode);
                }
            }
        }
        return stack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (!player.worldObj.isRemote && entity instanceof EntityLivingBase && JewelryNBT.isJewelX(stack, new ItemStack(Item.netherStar)) && JewelryNBT.isModifierX(stack, new ItemStack(Block.chest)) && JewelryNBT.entity(stack, player) == null){
            JewelryNBT.addEntity(stack, entity);
            JewelryNBT.addEntityID(stack, entity);
            entity.setDead();
            JewelryNBT.addFakeEnchantment(stack);
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
        if (stack.hasTagCompound() && stack.getDisplayName() != "Wedding Ring" && Minecraft.getMinecraft().gameSettings.advancedItemTooltips)
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

            String modeN = JewelryNBT.modeName(stack);
            if(modeN != null) list.add("Mode: " + modeN);

            int block = JewelryNBT.blockID(stack);
            if(block != -1) list.add("Block: " + StatCollector.translateToLocalFormatted(Block.blocksList[block].getUnlocalizedName()));

            int blockmeta = JewelryNBT.blockMetadata(stack);
            if(blockmeta != -1) list.add("Block Metadata: " + blockmeta);

            TileEntity tile = JewelryNBT.tileEntity(stack);
            if(tile != null) list.add(EnumChatFormatting.RED + "Contains a tile entity");

            int blockX = JewelryNBT.blockCoordX(stack);
            if(blockX != -1) list.add("Block Coords X: " + blockX);

            int blockY = JewelryNBT.blockCoordY(stack);
            if(blockY != -1) list.add("Block Coords Y: " + blockY);

            int blockZ = JewelryNBT.blockCoordZ(stack);
            if(blockZ != -1) list.add("Block Coords Z: " + blockZ);

            //            int colorI = JewelryNBT.ingotColor(stack);
            //            if(colorI != -1) list.add("Ingot Color: " + colorI);
            //            
            //            int colorJ = JewelryNBT.jewelColor(stack);
            //            if(colorJ != -1) list.add("Jewel Color: " + colorJ);
        }
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int side, float par8, float par9, float par10)
    {
        amplifier = 0;
        if (JewelryNBT.isJewelX(stack, new ItemStack(Item.diamond))) amplifier = 1;
        else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.emerald))) amplifier = 2;
        else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.netherStar))) amplifier = 7;

        if (!world.isRemote)
        {
            EntityLivingBase entity = JewelryNBT.entity(stack, player);
            boolean used = false;
            if(entity != null && entity instanceof EntityLivingBase){
                entity.setLocationAndAngles(i + 0.5D, j + 1D, k + 0.5D, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
                world.spawnEntityInWorld(entity);
                JewelryNBT.removeEntity(stack);
            }
            if (JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)) && JewelryNBT.isModifierX(stack, new ItemStack(Block.chest)) && world.getBlockId(i, j, k) == Block.chest.blockID) JewelryNBT.addBlockCoordonates(stack, i, j, k);
            onItemRightClick(stack, world, player);
            if(JewelryNBT.isModifierX(stack, new ItemStack(Item.dyePowder, 1, 15))) world.scheduleBlockUpdate(i, j, k, world.getBlockId(i, j, k), 7 - amplifier); 
            if(JewelryNBT.isModifierX(stack, new ItemStack(Item.pickaxeDiamond)) && JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)))
            {
                if(JewelryNBT.hasTag(stack, "blockID") && !used)
                {
                    int shiftX = 0, shiftY = 0, shiftZ = 0;
                    if(side == 0) shiftY = -1;
                    else if(side == 1) shiftY = 1;
                    else if(side == 2) shiftZ = -1;
                    else if(side == 3) shiftZ = 1;
                    else if(side == 4) shiftX = -1;
                    else if(side == 5) shiftX = 1;
                    world.setBlock(i + shiftX, j + shiftY, k + shiftZ, JewelryNBT.blockID(stack));
                    world.setBlockMetadataWithNotify(i + shiftX, j + shiftY, k + shiftZ, JewelryNBT.blockMetadata(stack), 2);
                    if(JewelryNBT.hasTag(stack, "tile")) world.setBlockTileEntity(i + shiftX, j + shiftY, k + shiftZ, JewelryNBT.tileEntity(stack));
                    if(JewelryNBT.tileEntity(stack) instanceof TileEntitySkull) ((BlockSkull)Block.skull).makeWither(world, i + shiftX, j + shiftY, k + shiftZ, (TileEntitySkull)JewelryNBT.tileEntity(stack));
                    if(JewelryNBT.blockID(stack) == Block.pumpkin.blockID || JewelryNBT.blockID(stack) == Block.pumpkinLantern.blockID) createGolems(world, i + shiftX, j + shiftY, k + shiftZ);
                    JewelryNBT.removeBlock(stack);
                    used = true;
                }

                if(!JewelryNBT.hasTag(stack, "tile") && world.getBlockTileEntity(i, j, k) != null && !used){
                    JewelryNBT.addTileEntityBlock(stack, world, i, j, k);
                    world.removeBlockTileEntity(i, j, k);
                    world.setBlock(i, j, k, 0);
                }
                else if(!JewelryNBT.hasTag(stack, "blockID") && !used){
                    JewelryNBT.addBlock(stack, world.getBlockId(i, j, k), world.getBlockMetadata(i, j, k));
                    JewelryNBT.addBlockCoordonates(stack, i, j, k);
                    world.setBlock(i, j, k, 0);
                }
            }
            if(JewelryNBT.isModifierX(stack, new ItemStack(Item.pickaxeDiamond)) && JewelryNBT.isJewelX(stack, new ItemStack(Item.netherStar)) && JewelryNBT.isIngotX(stack, new ItemStack(ItemList.shadowIngot)) && j > 0 && world.getBlockId(i, j, k) != Block.bedrock.blockID)
                world.destroyBlock(i, j, k, true);
        }
        return true;
    }

    public boolean canDisenchant(EntityPlayer player)
    {
        if(player.capabilities.isCreativeMode) return true;
        else if(player.experienceLevel >= 2) return true;
        return false;
    }

    public void dynamicLight(World world, EntityPlayer player)
    {
        world.setBlock((int)player.prevPosX, (int)player.prevPosY, (int)player.prevPosZ, 0);
        world.setBlock((int)player.posX, (int)player.posY, (int)player.posZ, BlockList.glow.blockID);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        amplifier = 0;
        if(cooldown > 0) cooldown--;
        if (!world.isRemote){
            EntityPlayer entityplayer = (EntityPlayer) entity;
            int posX = (int)Math.floor(entityplayer.posX), posY = (int)Math.floor(entityplayer.posY), posZ = (int)Math.floor(entityplayer.posZ);

            if (JewelryNBT.isJewelX(stack, new ItemStack(Item.diamond))) amplifier = 1;
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.emerald))) amplifier = 2;
            else if (JewelryNBT.isJewelX(stack, new ItemStack(Item.netherStar))) amplifier = 7;

            if(JewelryNBT.isModifierX(stack, new ItemStack(Item.dyePowder, 1, 15)) && world.getBlockId(posX, posY - 1, posZ) == Block.tilledField.blockID) 
                world.setBlockMetadataWithNotify(posX, posY - 1, posZ, 1, 2);            

            if(JewelryNBT.isModeX(stack, "Activated"))
            {
                if (JewelryNBT.isModifierX(stack, new ItemStack(Item.blazePowder)) && entityplayer != null)
                    entityplayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 4, amplifier, true));
                else if (JewelryNBT.isModifierX(stack, new ItemStack(Item.sugar)) && entityplayer != null)
                {
                    entityplayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 4, amplifier, true));
                    entityplayer.addExhaustion(0.05f*amplifier);
                }
                else if (JewelryNBT.isModifierX(stack, new ItemStack(Item.pickaxeIron)) && entityplayer != null && !JewelryNBT.isJewelX(stack, new ItemStack(Item.enderPearl)))
                    entityplayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 4, amplifier, true));
                else if (JewelryNBT.isModifierX(stack, new ItemStack(Item.feather)) && entityplayer != null)
                {
                    entityplayer.addPotionEffect(new PotionEffect(Potion.jump.id, 4, amplifier, true));
                    if(entityplayer.inventory.armorInventory[0] != null)
                    {
                        int damage = entityplayer.inventory.armorInventory[0].getMaxDamage() - entityplayer.inventory.armorInventory[0].getItemDamage();
                        if(damage - entityplayer.fallDistance > 0){
                            entityplayer.inventory.armorInventory[0].damageItem((int)entityplayer.fallDistance, entityplayer);
                            entityplayer.fallDistance = 0;
                        }
                        else
                        {
                            --entityplayer.inventory.armorInventory[0].stackSize;
                            entityplayer.fallDistance -= damage;
                        }
                    }
                }
                else if (JewelryNBT.isModifierX(stack, new ItemStack(Item.potion, 1, 8270)) && entityplayer != null) entityplayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 4, amplifier, true));
            }
            if(entityplayer.inventory.getCurrentItem() != null && JewelryNBT.isJewelX(stack, new ItemStack(Item.netherStar)) && JewelryNBT.isModifierX(stack, new ItemStack(Item.book)) && entityplayer.inventory.getCurrentItem().equals(stack))
            {
                ItemStack item = null;
                if(entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem + 1) != null && entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem + 1).isItemEnchanted()) item = entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem + 1);
                if(entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem - 1) != null && entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem - 1).isItemEnchanted()) item = entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem - 1);
                if(item != null && JewelryNBT.isModeX(stack, "Disenchant"))
                {
                    ItemStack enchBook = new ItemStack(Item.enchantedBook);
                    Map enchItem = EnchantmentHelper.getEnchantments(item);
                    Map book = EnchantmentHelper.getEnchantments(enchBook);
                    Iterator iterator = enchItem.keySet().iterator();
                    int e;

                    if (iterator.hasNext() && canDisenchant(entityplayer))
                    {                
                        e = ((Integer)iterator.next()).intValue();
                        book.put(Integer.valueOf(e), Integer.valueOf(((Integer)enchItem.get(Integer.valueOf(e))).intValue()));
                        EnchantmentHelper.setEnchantments(book, enchBook);
                        if(entityplayer.inventory.addItemStackToInventory(enchBook))
                        {
                            if(!entityplayer.capabilities.isCreativeMode)
                            {
                                entityplayer.addExperienceLevel(-2);
                                entityplayer.heal(-1f);
                            }
                            enchItem.remove(Integer.valueOf(e));
                            if(item.isItemStackDamageable() && (item.getMaxDamage() - item.getItemDamage())/3 > 0) item.damageItem((item.getMaxDamage() - item.getItemDamage())/3, entityplayer);
                            EnchantmentHelper.setEnchantments(enchItem, item);
                        }
                    }
                }
                if(entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem + 1) != null && entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem - 1) != null && JewelryNBT.isModeX(stack, "Transfer"))
                {
                    if(cooldown > 0) entityplayer.addChatMessage("Ring is currently cooling down (" + cooldown + ")");
                    ItemStack enchantedItem = null, enchantableItem = null;
                    if(entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem - 1).isItemEnchanted() && entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem + 1) != null)
                    {
                        enchantedItem = entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem - 1);
                        enchantableItem = entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem + 1);

                    }
                    if(enchantedItem != null && enchantableItem != null)
                    {
                        Map enchItem = EnchantmentHelper.getEnchantments(enchantedItem);
                        Map resultItem = EnchantmentHelper.getEnchantments(enchantableItem);
                        Iterator iterator = enchItem.keySet().iterator();
                        int e;

                        if (iterator.hasNext() && cooldown == 0)
                        {                
                            e = ((Integer)iterator.next()).intValue();
                            if(!EnchantmentHelper.getEnchantments(enchantableItem).containsKey(Integer.valueOf(e)))
                            {
                                resultItem.put(Integer.valueOf(e), Integer.valueOf(((Integer)enchItem.get(Integer.valueOf(e))).intValue()));
                                EnchantmentHelper.setEnchantments(resultItem, enchantableItem);
                                enchItem.remove(Integer.valueOf(e));
                                EnchantmentHelper.setEnchantments(enchItem, enchantedItem);
                                cooldown = 1000;
                            }
                        }
                    }
                }
                if(entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem + 1) != null) item = entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem + 1);
                else if(entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem - 1) != null) item = entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem - 1);
                if(item != null && !item.isItemEnchanted() && item.isItemEnchantable() && entityplayer.experienceLevel > 0 && JewelryNBT.isModeX(stack, "Enchant"))
                {
                    Map enchItem = EnchantmentHelper.getEnchantments(item);
                    int level = entityplayer.experienceLevel;
                    if(entityplayer.experienceLevel > 6) level = 6;
                    if(!entityplayer.capabilities.isCreativeMode) entityplayer.addExperienceLevel(-level);
                    enchItem.put(Enchantment.enchantmentsBookList[new Random().nextInt(Enchantment.enchantmentsBookList.length)].effectId, level);
                    EnchantmentHelper.setEnchantments(enchItem, item);
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
        if(JewelryNBT.isModifierEffectType(itemstack) && !(JewelryNBT.isJewelX(itemstack, new ItemStack(Item.enderPearl)) && JewelryNBT.isModifierX(itemstack, new ItemStack(Item.pickaxeIron)))) JewelryNBT.addMode(itemstack, "Activated");
        if(JewelryNBT.isJewelX(itemstack, new ItemStack(Item.netherStar)) && JewelryNBT.isModifierX(itemstack, new ItemStack(Item.book))) 
            JewelryNBT.addMode(itemstack, "Disenchant");
        return itemstack;
    }

    public void createGolems(World world, int i, int j, int k)
    {
        if (world.getBlockId(i, j - 1, k) == Block.blockSnow.blockID && world.getBlockId(i, j - 2, k) == Block.blockSnow.blockID)
        {
            if (!world.isRemote)
            {
                world.setBlock(i, j, k, 0, 0, 2);
                world.setBlock(i, j - 1, k, 0, 0, 2);
                world.setBlock(i, j - 2, k, 0, 0, 2);
                EntitySnowman entitysnowman = new EntitySnowman(world);
                entitysnowman.setLocationAndAngles((double)i + 0.5D, (double)j - 1.95D, (double)k + 0.5D, 0.0F, 0.0F);
                world.spawnEntityInWorld(entitysnowman);
                world.notifyBlockChange(i, j, k, 0);
                world.notifyBlockChange(i, j - 1, k, 0);
                world.notifyBlockChange(i, j - 2, k, 0);
            }

            for (int l = 0; l < 120; ++l)
            {
                world.spawnParticle("snowshovel", (double)i + world.rand.nextDouble(), (double)(j - 2) + world.rand.nextDouble() * 2.5D, (double)k + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
        else if (world.getBlockId(i, j - 1, k) == Block.blockIron.blockID && world.getBlockId(i, j - 2, k) == Block.blockIron.blockID)
        {
            boolean flag = world.getBlockId(i - 1, j - 1, k) == Block.blockIron.blockID && world.getBlockId(i + 1, j - 1, k) == Block.blockIron.blockID;
            boolean flag1 = world.getBlockId(i, j - 1, k - 1) == Block.blockIron.blockID && world.getBlockId(i, j - 1, k + 1) == Block.blockIron.blockID;

            if (flag || flag1)
            {
                world.setBlock(i, j, k, 0, 0, 2);
                world.setBlock(i, j - 1, k, 0, 0, 2);
                world.setBlock(i, j - 2, k, 0, 0, 2);

                if (flag)
                {
                    world.setBlock(i - 1, j - 1, k, 0, 0, 2);
                    world.setBlock(i + 1, j - 1, k, 0, 0, 2);
                }
                else
                {
                    world.setBlock(i, j - 1, k - 1, 0, 0, 2);
                    world.setBlock(i, j - 1, k + 1, 0, 0, 2);
                }

                EntityIronGolem entityirongolem = new EntityIronGolem(world);
                entityirongolem.setPlayerCreated(true);
                entityirongolem.setLocationAndAngles((double)i + 0.5D, (double)j - 1.95D, (double)k + 0.5D, 0.0F, 0.0F);
                world.spawnEntityInWorld(entityirongolem);

                for (int i1 = 0; i1 < 120; ++i1)
                {
                    world.spawnParticle("snowballpoof", (double)i + world.rand.nextDouble(), (double)(j - 2) + world.rand.nextDouble() * 3.9D, (double)k + world.rand.nextDouble(), 0.0D, 0.0D, 0.0D);
                }

                world.notifyBlockChange(i, j, k, 0);
                world.notifyBlockChange(i, j - 1, k, 0);
                world.notifyBlockChange(i, j - 2, k, 0);

                if (flag)
                {
                    world.notifyBlockChange(i - 1, j - 1, k, 0);
                    world.notifyBlockChange(i + 1, j - 1, k, 0);
                }
                else
                {
                    world.notifyBlockChange(i, j - 1, k - 1, 0);
                    world.notifyBlockChange(i, j - 1, k + 1, 0);
                }
            }
        }
    }
}
