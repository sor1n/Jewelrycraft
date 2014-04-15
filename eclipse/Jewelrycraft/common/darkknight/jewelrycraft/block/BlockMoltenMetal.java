package darkknight.jewelrycraft.block;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class BlockMoltenMetal extends BlockFluidClassic {

	@SideOnly(Side.CLIENT)
	protected IIcon stillIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon flowingIcon;

	public BlockMoltenMetal(Fluid fluid, Material material) {
		super(fluid, material);
		setBlockName("Jewelrycraft.moltenMetal");
		this.setQuantaPerBlock(100);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1)? stillIcon : flowingIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon("jewelrycraft:moltenMetalStill");
		flowingIcon = register.registerIcon("jewelrycraft:moltenMetalFlow");
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
		return super.displaceIfPossible(world, x, y, z);
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int i, int j, int k)
	{
//		try {
//			return color(world, i, j, k);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return 0;
	}

    public static int color(IBlockAccess world, int i, int j, int k) throws IOException
    {
        String domain = "", texture;
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        BufferedImage icon;
        ItemStack item = new ItemStack(BlockList.moltenMetal);
        JewelryNBT.addMetal(item, new ItemStack(JewelrycraftUtil.liquids.get(String.valueOf(i) + " " + String.valueOf(j) + " " + String.valueOf(k))));
        int x=0, y=0, ok = 0, red, green, blue;
        if (JewelryNBT.ingot(item) != null && JewelryNBT.ingot(item).getIconIndex() != null && JewelryNBT.ingotColor(item) == 16777215)
        {
            String ingotIconName = JewelryNBT.ingot(item).getIconIndex().getIconName(); 

            if (ingotIconName.substring(0, ingotIconName.indexOf(":") + 1) != "") domain = ingotIconName.substring(0, ingotIconName.indexOf(":") + 1).replace(":", " ").trim();
            else domain = "minecraft";

            texture = ingotIconName.substring(ingotIconName.lastIndexOf(":") + 1) + ".png";
            ResourceLocation ingot = null;

            if (JewelryNBT.ingot(item).getUnlocalizedName().contains("item")) ingot = new ResourceLocation(domain, "textures/items/" + texture);
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
            JewelryNBT.addIngotColor(item, icon.getRGB(x, y));
        }
        if(JewelryNBT.ingot(item) != null) return JewelryNBT.ingotColor(item);
        return 16777215;
    }
}
