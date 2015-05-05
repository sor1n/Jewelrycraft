package darkknight.jewelrycraft.item;

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
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.ModifierEffects;
import darkknight.jewelrycraft.util.JewelryNBT;

public abstract class ItemBaseJewelry extends Item
{
    public ItemBaseJewelry()
    {
        super();
        setMaxStackSize(1);
        setCreativeTab(JewelrycraftMod.jewelrycraft);
    }
    
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @SideOnly (Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
    {
        try{
            return color(stack, pass);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return 16777215;
    }
    
    /**
     * @param stack
     * @param pass
     * @return
     * @throws IOException
     */
    public static int color(ItemStack stack, int pass) throws IOException
    {
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        BufferedImage icon;
        if (pass == 0 && stack != null && JewelryNBT.ingot(stack) != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0 && JewelryNBT.ingot(stack).getIconIndex() != null && JewelryNBT.ingotColor(stack) == 16777215){
            ItemStack ingot = JewelryNBT.ingot(stack);
            icon = ImageIO.read(rm.getResource(getLocation(ingot, stack, true)).getInputStream());
            int height = icon.getHeight();
            int width = icon.getWidth();
            Map m = new HashMap();
            for(int i = 0; i < width; i++)
                for(int j = 0; j < height; j++){
                    int rgb = icon.getRGB(i, j);
                    int red = rgb >> 16 & 0xff;
                    int green = rgb >> 8 & 0xff;
                    int blue = rgb & 0xff;
                    int[] rgbArr = {red, green, blue};
                    int Cmax = Math.max(red, Math.max(green, blue));
                    int Cmin = Math.min(red, Math.min(green, blue));
                    if (!isGray(rgbArr)) m.put(rgb, (Cmax + Cmin) / 2);
                }
            int color = getMostCommonColour(m);
            if (JewelryNBT.ingot(stack) != null && JewelryNBT.ingot(stack).getItem().getColorFromItemStack(JewelryNBT.ingot(stack), 1) != 16777215) JewelryNBT.addIngotColor(stack, JewelryNBT.ingot(stack).getItem().getColorFromItemStack(JewelryNBT.ingot(stack), 1));
            else JewelryNBT.addIngotColor(stack, color);
        }else if (pass == 1 && stack != null && JewelryNBT.gem(stack) != null && JewelryNBT.gem(stack).getIconIndex() != null && JewelryNBT.gem(stack) != null){
            ItemStack gem = JewelryNBT.gem(stack);
            icon = ImageIO.read(rm.getResource(getLocation(gem, stack, true)).getInputStream());
            int height = icon.getHeight();
            int width = icon.getWidth();
            Map m = new HashMap();
            for(int i = 0; i < width; i++)
                for(int j = 0; j < height; j++){
                    int rgb = icon.getRGB(i, j);
                    int red = rgb >> 16 & 0xff;
                    int green = rgb >> 8 & 0xff;
                    int blue = rgb & 0xff;
                    int[] rgbArr = {red, green, blue};
                    int Cmax = Math.max(red, Math.max(green, blue));
                    int Cmin = Math.min(red, Math.min(green, blue));
                    if (!isGray(rgbArr)) m.put(rgb, (Cmax + Cmin) / 2);
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
    
    /**
     * @param item
     * @param stack
     * @param changeMeta
     * @return
     */
    public static ResourceLocation getLocation(ItemStack item, ItemStack stack, boolean changeMeta)
    {
        String domain = "";
        String texture;
        if (changeMeta && (Item.getIdFromItem(item.getItem()) == Block.getIdFromBlock(Blocks.stained_glass) || Item.getIdFromItem(item.getItem()) == Block.getIdFromBlock(Blocks.stained_hardened_clay) || Item.getIdFromItem(item.getItem()) == Block.getIdFromBlock(Blocks.wool) || Item.getIdFromItem(item.getItem()) == Block.getIdFromBlock(Blocks.carpet))) item.setItemDamage(15 - item.getItemDamage());
        IIcon itemIcon = item.getItem().getIcon(item, 0);
        String iconName = itemIcon.getIconName();
        if (iconName.substring(0, iconName.indexOf(":") + 1) != "") domain = iconName.substring(0, iconName.indexOf(":") + 1).replace(":", " ").trim();
        else domain = "minecraft";
        texture = iconName.substring(iconName.lastIndexOf(":") + 1) + ".png";
        ResourceLocation textureLocation = null;
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        if (texturemanager.getResourceLocation(item.getItemSpriteNumber()).toString().contains("items")) textureLocation = new ResourceLocation(domain.toLowerCase(), "textures/items/" + texture);
        else textureLocation = new ResourceLocation(domain.toLowerCase(), "textures/blocks/" + texture);
        return textureLocation;
    }
    
    /**
     * @param map
     * @return
     */
    public static int getMostCommonColour(Map map)
    {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator(){
            public int compare(Object o1, Object o2)
            {
                return ((Comparable)((Map.Entry)o1).getValue()).compareTo(((Map.Entry)o2).getValue());
            }
        });
        Map.Entry me = (Map.Entry)list.get(list.size() - 1);
        for(int i = 0; i < list.size(); i++){
            float alpha = Float.valueOf(list.get(i).toString().split("=")[1]);
            if (alpha < 180) me = (Map.Entry)list.get(i);
        }
        int rgb = (Integer)me.getKey();
        return rgb;
    }
    
    /**
     * @param rgbArr
     * @return
     */
    public static boolean isGray(int[] rgbArr)
    {
        int rgbSum = rgbArr[0] + rgbArr[1] + rgbArr[2];
        if (rgbSum > 0 && rgbSum < 256 * 3) return false;
        return true;
    }
    
    /**
     * @param stack
     * @return
     */
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (JewelryNBT.ingot(stack) != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0) return JewelryNBT.ingot(stack).getDisplayName().replace("Ingot", " ").trim() + " " + ("" + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name")).trim();
        return ("" + StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description.
     *
     * @param stack
     * @param player
     * @param list
     * @param par4
     */
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        if (stack.hasTagCompound() && par4){
            ItemStack ingot = JewelryNBT.ingot(stack);
            if (ingot != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0) list.add("Metal: " + EnumChatFormatting.YELLOW + ingot.getDisplayName().replace("Ingot", " "));
            ItemStack gem = JewelryNBT.gem(stack);
            if (gem != null) list.add("Gem: " + EnumChatFormatting.BLUE + gem.getDisplayName());
            ArrayList<ItemStack> modifier = JewelryNBT.modifier(stack);
            if (!modifier.isEmpty()) list.add("Modifiers: ");
            for(int i = 0; i < modifier.size(); i++)
                list.add(EnumChatFormatting.DARK_PURPLE + modifier.get(i).getDisplayName() + " x" + modifier.get(i).stackSize);
        }
    }
    
    /**
     * @param stack
     * @param player
     */
    public void action(ItemStack stack, EntityPlayer player)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects())
            mod.action(stack, player, this);
    }
    
    /**
     * @param item
     * @param player
     * @param source
     * @return
     */
    public boolean onPlayerAttackedCacellable(ItemStack item, EntityPlayer player, DamageSource source, float amount)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects())
            return mod.onPlayerAttackedCacellable(item, player, source, this, amount);
        return false;
    }
    
    /**
     * @param item
     * @param player
     * @param target
     * @return
     */
    public boolean onEntityAttackedCacellable(ItemStack item, EntityPlayer player, Entity target, float amount)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects())
            return mod.onEntityAttackedCacellable(item, player, target, this, amount);
        return false;
    }
    
    /**
     * @param item
     * @param player
     * @param source
     * @return
     */
    public void onPlayerAttacked(ItemStack item, EntityPlayer player, DamageSource source, float amount)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects()) mod.onPlayerAttacked(item, player, source, this, amount);
    }
    
    /**
     * @param item
     * @param player
     * @param target
     * @return
     */
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, float amount)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects()) mod.onEntityAttacked(item, player, target, this, amount);
    }
    
    public void onPlayerDead(ItemStack stack, EntityPlayer player, DamageSource source)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects())
            mod.onPlayerDead(stack, player, source, this);
    }
}
