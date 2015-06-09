package darkknight.jewelrycraft.item;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.Variables;

public class ItemMoltenMetal extends Item
{
    /**
     * 
     */
    public ItemMoltenMetal()
    {
        super();
        setMaxStackSize(1);
    }
    
    /**
     * @param iconRegister
     */
    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(Variables.MODID + ":moltenMetalStill");
    }
    
    /**
     * @param stack
     * @param pass
     * @return
     */
    @Override
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
    @SideOnly(Side.CLIENT)
    public static int color(ItemStack stack, int pass) throws IOException
    {
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        ResourceLocation ingot;
        BufferedImage icon;
        if (stack != null && JewelryNBT.ingot(stack) != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0 && JewelryNBT.ingot(stack).getIconIndex() != null && JewelryNBT.ingotColor(stack) == 16777215){
            try{
                ingot = ItemBaseJewelry.getLocation(JewelryNBT.ingot(stack), stack, false);
            }
            catch(Exception e){
                ingot = new ResourceLocation("textures/items/apple.png");
            }
            icon = ImageIO.read(rm.getResource(ingot).getInputStream());
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
            try{
                int color = getMostCommonColour(m);
                if (JewelryNBT.ingot(stack) != null && JewelryNBT.ingot(stack).getItem().getColorFromItemStack(JewelryNBT.ingot(stack), 1) != 16777215) JewelryNBT.addIngotColor(stack, JewelryNBT.ingot(stack).getItem().getColorFromItemStack(JewelryNBT.ingot(stack), 1));
                else JewelryNBT.addIngotColor(stack, color);
            }
            catch(Exception e){
                JewelryNBT.addIngotColor(stack, 16777215);
            }
        }
        if (JewelryNBT.ingot(stack) != null) return JewelryNBT.ingotColor(stack);
        return 0;
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
}
