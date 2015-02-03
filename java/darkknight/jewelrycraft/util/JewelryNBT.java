package darkknight.jewelrycraft.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import darkknight.jewelrycraft.item.ItemRing;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class JewelryNBT
{
    // TODO NBT Tag Adding
    /**
     * @param item The item you want to add the NBT data on
     * @param metal The metal you want to add on the item
     */
    public static void addMetal(ItemStack item, ItemStack metal)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound ingotNBT = new NBTTagCompound();
        metal.writeToNBT(ingotNBT);
        itemStackData.setTag("ingot", ingotNBT);
    }
    
    /**
     * @param item The item you want to add the NBT data on
     * @param gem The gem you want to add on the item
     */
    public static void addGem(ItemStack item, ItemStack gem)
    {
        if (gem != null)
        {
            NBTTagCompound itemStackData;
            if (item.hasTagCompound()) itemStackData = item.getTagCompound();
            else
            {
                itemStackData = new NBTTagCompound();
                item.setTagCompound(itemStackData);
            }
            NBTTagCompound gemNBT = new NBTTagCompound();
            gem.writeToNBT(gemNBT);
            itemStackData.setTag("gem", gemNBT);
        }
    }
    
    /**
     * @param item The item you want to add the NBT data on
     * @param modifier The modifier you want to add on the item
     */
    public static void addModifiers(ItemStack item, ArrayList<ItemStack> modifier)
    {
        if (modifier != null)
        {
            NBTTagCompound itemStackData;
            if (item.hasTagCompound()) itemStackData = item.getTagCompound();
            else
            {
                itemStackData = new NBTTagCompound();
                item.setTagCompound(itemStackData);
            }
            for (int i = 0; i < modifier.size(); i++)
            {
                NBTTagCompound modifierNBT = new NBTTagCompound();
                modifier.get(i).writeToNBT(modifierNBT);
                itemStackData.setTag("modifier" + i, modifierNBT);
            }
            itemStackData.setInteger("modifierSize", modifier.size());
        }
    }
    
    /**
     * @param item The item you want to add the NBT data on
     * @param entity The entity to add on the item
     */
    public static void addEntity(ItemStack item, EntityLivingBase entity)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound entityNBT = new NBTTagCompound();
        entity.writeToNBT(entityNBT);
        itemStackData.setTag("entity", entityNBT);
    }
    
    public static void addEntityID(ItemStack item, EntityLivingBase entity)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound entityNBT = new NBTTagCompound();
        int id = EntityList.getEntityID(entity);
        entityNBT.setInteger("entityID", id);
        itemStackData.setTag("entityID", entityNBT);
    }
    
    public static void addCoordonates(ItemStack item, double x, double y, double z)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound coords = new NBTTagCompound();
        coords.setDouble("x", x);
        coords.setDouble("y", y);
        coords.setDouble("z", z);
        itemStackData.setTag("x", coords);
        itemStackData.setTag("y", coords);
        itemStackData.setTag("z", coords);
    }
    
    public static void addTileEntityBlock(ItemStack item, World world, int x, int y, int z)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound tileNBT = new NBTTagCompound();
        NBTTagCompound block = new NBTTagCompound();
        world.getTileEntity(x, y, z).writeToNBT(tileNBT);
        itemStackData.setTag("tile", tileNBT);
        block.setInteger("blockID", Block.getIdFromBlock(world.getBlock(x, y, z)));
        block.setInteger("metadata", world.getBlockMetadata(x, y, z));
        block.setInteger("blockX", x);
        block.setInteger("blockY", y);
        block.setInteger("blockZ", z);
        itemStackData.setTag("metadata", block);
        itemStackData.setTag("blockID", block);
        itemStackData.setTag("blockX", block);
        itemStackData.setTag("blockY", block);
        itemStackData.setTag("blockZ", block);
    }
    
    public static void addBlock(ItemStack item, int block, int metadata)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound blockNBT = new NBTTagCompound();
        blockNBT.setInteger("blockID", block);
        itemStackData.setTag("blockID", blockNBT);
        blockNBT.setInteger("metadata", metadata);
        itemStackData.setTag("metadata", blockNBT);
    }
    
    public static void addBlockCoordonates(ItemStack item, int x, int y, int z)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound coords = new NBTTagCompound();
        coords.setInteger("blockX", x);
        coords.setInteger("blockY", y);
        coords.setInteger("blockZ", z);
        itemStackData.setTag("blockX", coords);
        itemStackData.setTag("blockY", coords);
        itemStackData.setTag("blockZ", coords);
    }
    
    public static void addCoordonatesAndDimension(ItemStack item, double x, double y, double z, int dim, String name)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound coords = new NBTTagCompound();
        coords.setDouble("x", x);
        coords.setDouble("y", y);
        coords.setDouble("z", z);
        coords.setInteger("dimension", dim);
        coords.setString("dimName", name);
        itemStackData.setTag("x", coords);
        itemStackData.setTag("y", coords);
        itemStackData.setTag("z", coords);
        itemStackData.setTag("dimension", coords);
        itemStackData.setTag("dimName", coords);
    }
    
    public static void addFakeEnchantment(ItemStack item)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        itemStackData.setTag("ench", new NBTTagList());
    }
    
    public static void addIngotColor(ItemStack item, int color)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound colors = new NBTTagCompound();
        colors.setInteger("ingotColor", color);
        itemStackData.setTag("ingotColor", colors);
    }
    
    // TODO
    public static void addGemColor(ItemStack item, int color)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound colors = new NBTTagCompound();
        colors.setInteger("gemColor", color);
        itemStackData.setTag("gemColor", colors);
    }
    
    @SuppressWarnings("rawtypes")
    public static void addEntities(ItemStack item, List list)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        NBTTagCompound entityNBT = new NBTTagCompound();
        for (int i = 0; i < list.size(); i++)
            ((EntityLivingBase) list.get(i)).writeToNBT(entityNBT);
        itemStackData.setTag("entities", entityNBT);
    }
    
    // TODO NBT Tag Removing
    public static void removeNBT(ItemStack item, String tag)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        itemStackData.removeTag(tag);
    }
    
    public static void removeEntity(ItemStack item)
    {
        JewelryNBT.removeNBT(item, "entityID");
        JewelryNBT.removeNBT(item, "entity");
        JewelryNBT.removeNBT(item, "ench");
    }
    
    public static void removeBlock(ItemStack item)
    {
        JewelryNBT.removeNBT(item, "blockID");
        JewelryNBT.removeNBT(item, "metadata");
        JewelryNBT.removeNBT(item, "tile");
        JewelryNBT.removeNBT(item, "blockX");
        JewelryNBT.removeNBT(item, "blockY");
        JewelryNBT.removeNBT(item, "blockZ");
    }
    
    // TODO NTB Tag Checking
    public static boolean hasTag(ItemStack item, String tag)
    {
        NBTTagCompound itemStackData;
        if (item.hasTagCompound()) itemStackData = item.getTagCompound();
        else
        {
            itemStackData = new NBTTagCompound();
            item.setTagCompound(itemStackData);
        }
        if (itemStackData.hasKey(tag)) return true;
        return false;
    }
    
    public static boolean isGemX(ItemStack stack, ItemStack gem)
    {
        if (gem(stack) != null && gem(stack).getItem() == gem.getItem() && gem(stack).getItemDamage() == gem.getItemDamage()) return true;
        return false;
    }
    
    public static boolean isModifierX(ItemStack stack, ItemStack modifier)
    {
        if (modifier(stack) != null)
        {
            ArrayList<ItemStack> list = modifier(stack);
            for (int i = 0; i < list.size(); i++)
                if (list.get(i).getItem() == modifier.getItem() && list.get(i).getItemDamage() == modifier.getItemDamage()) return true;
        }
        return false;
    }
    
    public static boolean isIngotX(ItemStack stack, ItemStack ingot)
    {
        if (ingot(stack) != null && ingot(stack).getItem() == ingot.getItem() && ingot(stack).getItemDamage() == ingot.getItemDamage()) return true;
        return false;
    }
    
    public static boolean isEntityX(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (entity != null && entity instanceof EntityLivingBase && entity(stack, player) != null && entity(stack, player).equals(entity)) return true;
        return false;
    }
    
    public static boolean isDimNameX(ItemStack stack, String dimName)
    {
        if (ingot(stack) != null && dimName(stack).equals(dimName)) return true;
        return false;
    }
    
    public static boolean isDimensionX(ItemStack stack, int dimension)
    {
        if (dimension(stack) != -2 && dimension(stack) == dimension) return true;
        return false;
    }
    
    // TODO Return components based on NBT    
    public static ItemStack gem(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("gem"))
        {
            NBTTagCompound jewelNBT = (NBTTagCompound) stack.getTagCompound().getTag("gem");
            ItemStack gem = new ItemStack(Item.getItemById(0), 0, 0);
            gem.readFromNBT(jewelNBT);
            return gem;
        }
        return null;
    }
    
    public static ArrayList<ItemStack> modifier(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound())
        {
            int size = stack.getTagCompound().getInteger("modifierSize");
            ArrayList<ItemStack> list = new ArrayList<ItemStack>();
            for (int i = 0; i < size; i++)
            {
                ItemStack modifier = new ItemStack(Item.getItemById(0), 0, 0);
                NBTTagCompound modifierNBT = (NBTTagCompound) stack.getTagCompound().getTag("modifier" + i);
                modifier.readFromNBT(modifierNBT);
                list.add(modifier);
            }
            return list;
        }
        return null;
    }
    
    public static ItemStack ingot(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("ingot"))
        {
            NBTTagCompound ingotNBT = (NBTTagCompound) stack.getTagCompound().getTag("ingot");
            ItemStack ingot = new ItemStack(Item.getItemById(0), 0, 0);
            ingot.readFromNBT(ingotNBT);
            return ingot;
        }
        return null;
    }
    
    public static EntityLivingBase entity(ItemStack stack, EntityPlayer player)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("entityID") && stack.getTagCompound().hasKey("entity"))
        {
            NBTTagCompound enID = (NBTTagCompound) stack.getTagCompound().getTag("entityID");
            NBTTagCompound en = (NBTTagCompound) stack.getTagCompound().getTag("entity");
            int entityID = 0;
            entityID = enID.getInteger("entityID");
            EntityLivingBase entity = (EntityLivingBase) EntityList.createEntityByID(entityID, player.worldObj);
            if (entity != null && entity instanceof EntityLivingBase)
            {
                entity.readFromNBT(en);
                return entity;
            }
            else return null;
        }
        return null;
    }
    
    public static TileEntity tileEntity(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("tile"))
        {
            NBTTagCompound tileNBT = (NBTTagCompound) stack.getTagCompound().getTag("tile");
            TileEntity tile = (TileEntity) TileEntity.createAndLoadEntity(tileNBT);
            if (tile != null && tile instanceof TileEntity)
            {
                tile.readFromNBT(tileNBT);
                return tile;
            }
            else return null;
        }
        return null;
    }
    
    public static String dimName(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("dimName"))
        {
            NBTTagCompound dim = (NBTTagCompound) stack.getTagCompound().getTag("dimName");
            String name = dim.getString("dimName");
            return name;
        }
        return null;
    }
    
    public static String modeName(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("mode"))
        {
            NBTTagCompound dim = (NBTTagCompound) stack.getTagCompound().getTag("mode");
            String name = dim.getString("mode");
            return name;
        }
        return null;
    }
    
    public static int dimension(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("dimension"))
        {
            NBTTagCompound dim = (NBTTagCompound) stack.getTagCompound().getTag("dimension");
            int dimension = dim.getInteger("dimension");
            return dimension;
        }
        return -2;
    }
    
    public static int blockCoordX(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("blockX"))
        {
            NBTTagCompound x = (NBTTagCompound) stack.getTagCompound().getTag("blockX");
            int posX = x.getInteger("blockX");
            return posX;
        }
        return -1;
    }
    
    public static int blockCoordY(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("blockY"))
        {
            NBTTagCompound y = (NBTTagCompound) stack.getTagCompound().getTag("blockY");
            int posY = y.getInteger("blockY");
            return posY;
        }
        return -1;
    }
    
    public static int blockCoordZ(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("blockZ"))
        {
            NBTTagCompound z = (NBTTagCompound) stack.getTagCompound().getTag("blockZ");
            int posZ = z.getInteger("blockZ");
            return posZ;
        }
        return -1;
    }
    
    public static int blockID(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("blockID"))
        {
            NBTTagCompound blockID = (NBTTagCompound) stack.getTagCompound().getTag("blockID");
            int blockId = blockID.getInteger("blockID");
            return blockId;
        }
        return -1;
    }
    
    public static int blockMetadata(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("metadata"))
        {
            NBTTagCompound metadataNBT = (NBTTagCompound) stack.getTagCompound().getTag("metadata");
            int metadata = metadataNBT.getInteger("metadata");
            return metadata;
        }
        return -1;
    }
    
    public static double playerPosX(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("x"))
        {
            NBTTagCompound x = (NBTTagCompound) stack.getTagCompound().getTag("x");
            double posX = x.getDouble("x");
            return posX;
        }
        return -1;
    }
    
    public static double playerPosY(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("y"))
        {
            NBTTagCompound y = (NBTTagCompound) stack.getTagCompound().getTag("y");
            double posY = y.getDouble("y");
            return posY;
        }
        return -1;
    }
    
    public static double playerPosZ(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("z"))
        {
            NBTTagCompound z = (NBTTagCompound) stack.getTagCompound().getTag("z");
            double posZ = z.getDouble("z");
            return posZ;
        }
        return -1;
    }
    
    public static int ingotColor(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("ingotColor"))
        {
            NBTTagCompound colors = (NBTTagCompound) stack.getTagCompound().getTag("ingotColor");
            int color = colors.getInteger("ingotColor");
            return color;
        }
        return 16777215;
    }
    
    // TODO
    public static int gemColor(ItemStack stack)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.hasTagCompound() && stack.getTagCompound().hasKey("gemColor"))
        {
            NBTTagCompound colors = (NBTTagCompound) stack.getTagCompound().getTag("gemColor");
            int color = colors.getInteger("gemColor");
            return color;
        }
        return 16777215;
    }
    
    @SuppressWarnings(
    { "rawtypes", "unchecked", "null" })
    public static List entities(ItemStack stack, EntityPlayer player)
    {
        if (stack != null && stack != new ItemStack(Item.getItemById(0), 0, 0) && stack.getTagCompound().hasKey("entities"))
        {
            NBTTagCompound enID = (NBTTagCompound) stack.getTagCompound().getTag("entitiesID");
            List list = null;
            int[] entityID;
            EntityLivingBase entity;
            entityID = enID.getIntArray("entitiesID");
            for (int i = 0; i < entityID.length; i++)
            {
                entity = (EntityLivingBase) EntityList.createEntityByID(entityID[i], player.worldObj);
                list.add(entity);
            }
            return list;
        }
        return null;
    }
}
