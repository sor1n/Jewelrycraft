package darkknight.jewelrycraft.block;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.init.Blocks;
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
import darkknight.jewelrycraft.JewelrycraftMod;
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
		setLightLevel(15f);
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

	@Override
    protected boolean canFlowInto(IBlockAccess world, int x, int y, int z)
    {
        if (world.getBlock(x, y, z).isAir(world, x, y, z)) return true;

        Block block = world.getBlock(x, y, z);
        if (block == this)
        {
            return false;
        }

        if (displacements.containsKey(block))
        {
            return displacements.get(block);
        }

        Material material = block.getMaterial();
        if (material.blocksMovement()  ||
            material == Material.water ||
            material == Material.lava  ||
            material == Material.portal)
        {
            return false;
        }

        int density = getDensity(world, x, y, z);
        if (density == Integer.MAX_VALUE) 
        {
             return true;
        }
        
        if (this.density > density)
        {
            return true;
        }
        else
        {
        	return false;
        }
    }

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int i, int j, int k)
	{
		try {
			return color(world, i, j, k, false, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
    public void updateTick(World world, int x, int y, int z, Random rand)
    {
        int quantaRemaining = quantaPerBlock - world.getBlockMetadata(x, y, z);
        int expQuanta = -101;

        // check adjacent block levels if non-source
        if (quantaRemaining < quantaPerBlock)
        {
            int y2 = y - densityDir;

            if ((world.getBlock(x,     y2, z    ) == this && JewelrycraftMod.saveData.getInteger(coords(x, y2, z)) == JewelrycraftMod.saveData.getInteger(coords(x, y, z)))||
                (world.getBlock(x - 1, y2, z    ) == this && JewelrycraftMod.saveData.getInteger(coords(x - 1, y2, z)) == JewelrycraftMod.saveData.getInteger(coords(x, y, z)))||
                (world.getBlock(x + 1, y2, z    ) == this && JewelrycraftMod.saveData.getInteger(coords(x + 1, y2, z)) == JewelrycraftMod.saveData.getInteger(coords(x, y, z)))||
                (world.getBlock(x,     y2, z - 1) == this && JewelrycraftMod.saveData.getInteger(coords(x, y2, z - 1)) == JewelrycraftMod.saveData.getInteger(coords(x, y, z)))||
                (world.getBlock(x,     y2, z + 1) == this && JewelrycraftMod.saveData.getInteger(coords(x, y2, z + 1)) == JewelrycraftMod.saveData.getInteger(coords(x, y, z))))
            {
                expQuanta = quantaPerBlock - 1;
            }
            else
            {
                int maxQuanta = -100;
                maxQuanta = getLargerQuanta(world, x - 1, y, z,     maxQuanta);
                maxQuanta = getLargerQuanta(world, x + 1, y, z,     maxQuanta);
                maxQuanta = getLargerQuanta(world, x,     y, z - 1, maxQuanta);
                maxQuanta = getLargerQuanta(world, x,     y, z + 1, maxQuanta);

                expQuanta = maxQuanta - 1;
            }

            // decay calculation
            if (expQuanta != quantaRemaining)
            {
                quantaRemaining = expQuanta;

                if (expQuanta <= 0)
                {
                    world.setBlock(x, y, z, Blocks.air);
                }
                else
                {
                    world.setBlockMetadataWithNotify(x, y, z, quantaPerBlock - expQuanta, 3);
                    world.scheduleBlockUpdate(x, y, z, this, tickRate);
                    world.notifyBlocksOfNeighborChange(x, y, z, this);
                }
            }
        }
        // This is a "source" block, set meta to zero, and send a server only update
        else if (quantaRemaining >= quantaPerBlock)
        {
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }

        // Flow vertically if possible
        if (canDisplace(world, x, y + densityDir, z))
        {
        	JewelrycraftMod.saveData.setInteger(coords(x, y + densityDir, z), JewelrycraftMod.saveData.getInteger(coords(x, y, z)));
            flowIntoBlock(world, x, y + densityDir, z, 1);
            return;
        }

        // Flow outward if possible
        int flowMeta = quantaPerBlock - quantaRemaining + 1;
        if (flowMeta >= quantaPerBlock)
        {
            return;
        }

        if (isSourceBlock(world, x, y, z) || !isFlowingVertically(world, x, y, z))
        {
            if (world.getBlock(x, y - densityDir, z) == this)
            {
                flowMeta = 1;
            }
            boolean flowTo[] = getOptimalFlowDirections(world, x, y, z);

            if (flowTo[0]){
            	if(JewelrycraftMod.saveData.getTag(coords(x - 1, y, z)) == null || world.getBlock(x - 1, y, z).isAir(world, x - 1, y, z))
            		JewelrycraftMod.saveData.setInteger(coords(x - 1, y, z), JewelrycraftMod.saveData.getInteger(coords(x, y, z)));
            	flowIntoBlock(world, x - 1, y, z,     flowMeta);
            }
            if (flowTo[1]){
            	if(JewelrycraftMod.saveData.getTag(coords(x + 1, y, z)) == null || world.getBlock(x + 1, y, z).isAir(world, x + 1, y, z))
            	JewelrycraftMod.saveData.setInteger(coords(x + 1, y, z), JewelrycraftMod.saveData.getInteger(coords(x, y, z)));
            	flowIntoBlock(world, x + 1, y, z,     flowMeta);
            }
            if (flowTo[2]){
            	if(JewelrycraftMod.saveData.getTag(coords(x, y, z - 1)) == null || world.getBlock(x, y, z - 1).isAir(world, x, y, z - 1))
            	JewelrycraftMod.saveData.setInteger(coords(x, y, z - 1), JewelrycraftMod.saveData.getInteger(coords(x, y, z)));
            	flowIntoBlock(world, x,     y, z - 1, flowMeta);
            }
            if (flowTo[3]){
            	if(JewelrycraftMod.saveData.getTag(coords(x, y, z + 1)) == null || world.getBlock(x, y, z + 1).isAir(world, x, y, z + 1))
            	JewelrycraftMod.saveData.setInteger(coords(x, y, z + 1), JewelrycraftMod.saveData.getInteger(coords(x, y, z)));
            	flowIntoBlock(world, x,     y, z + 1, flowMeta);
            }
        }
    }

    public static int color(IBlockAccess world, int i, int j, int k, boolean forcecolor, Item itemC) throws IOException
    {
        String domain = "", texture;
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        BufferedImage icon;
        ItemStack item = new ItemStack(BlockList.moltenMetal);
        if(JewelrycraftMod.saveData.getInteger(String.valueOf(i) + " " + String.valueOf(j) + " " + String.valueOf(k)) > 0)
        JewelryNBT.addMetal(item, new ItemStack(Item.getItemById(JewelrycraftMod.saveData.getInteger(coords(i, j, k)))));
        if(forcecolor) JewelryNBT.addMetal(item, new ItemStack(itemC));
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
                if(!isColorPretty(red, green, blue))
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
    
    public static boolean isColorPretty(int r, int g, int b)
    {
        if((r > 80 || g > 80 || b > 80) || (r > 80 && g > 80 && b > 80 && r < 230 && b < 230 && g < 230)) return true;
        else return false;
    }
    
    public static String coords(int x, int y, int z)
    {
    	return String.valueOf(x) + " " + String.valueOf(y) + " " + String.valueOf(z);
    }
}
