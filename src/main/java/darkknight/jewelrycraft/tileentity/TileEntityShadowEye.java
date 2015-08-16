package darkknight.jewelrycraft.tileentity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.block.BlockHandPedestal;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.model.ModelShadowEye;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

public class TileEntityShadowEye extends TileEntity
{
    public int opening, timer, t = 20, soundTimer;
    public boolean active, shouldAddData;
    public ArrayList<ItemStack> pedestalItems = new ArrayList<ItemStack>();
    ResourceLocation particleTexture = new ResourceLocation(Variables.MODID, "textures/particle/shadows.png");
    public EntityPlayer target;
    public ModelShadowEye model = new ModelShadowEye();
    
    public TileEntityShadowEye()
    {
        opening = 1;
        timer = 20;
        active = false;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("opening", opening);
        nbt.setInteger("timer", timer);
        nbt.setBoolean("active", active);
        nbt.setBoolean("shouldAddData", shouldAddData);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        opening = nbt.getInteger("opening");
        timer = nbt.getInteger("timer");
        active = nbt.getBoolean("active");
        shouldAddData = nbt.getBoolean("shouldAddData");
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        boolean valid = isValidStructure(worldObj, xCoord, yCoord, zCoord, blockMetadata);
        boolean canStartRitual = valid && ((TileEntityHandPedestal)worldObj.getTileEntity(xCoord, yCoord - 3, zCoord)).heldItemStack != null && getNumberOfItems(worldObj, xCoord, yCoord, zCoord) > 0;
        if (active){
            timer--;
            if (canStartRitual && canChangePedestals(worldObj, xCoord, yCoord, zCoord) && opening == 4) changePedestals(worldObj, xCoord, yCoord, zCoord);
        }
        if (active && target != null && this.getDistanceFrom(target.posX, target.posY, target.posZ) > 27D){
            active = false;
            timer = -1;
            shouldAddData = false;
            revertPedestals(worldObj, xCoord, yCoord, zCoord);
        }
        if (opening == 4 && timer <= 0) active = false;
        if (!active && timer <= 0 && opening != 1){
            if (t > 0) t--;
            if (t <= 0){
                opening--;
                t = 20;
            }
        }
        if (opening == 2 && timer <= 0 && t == 10 && shouldAddData){
            addData(worldObj, xCoord, yCoord, zCoord);
            TileEntityHandPedestal target = (TileEntityHandPedestal)worldObj.getTileEntity(xCoord, yCoord - 3, zCoord);
            if (target != null && target.getHeldItemStack() != null) JewelryNBT.addModifiers(target.getHeldItemStack(), pedestalItems);
            revertPedestals(worldObj, xCoord, yCoord, zCoord);
        }
        if (active && timer <= 0){
            if (opening < 4){
                opening++;
                timer = 20;
            	soundTimer = 0;
            }
            if (canStartRitual && opening == 4) timer = ConfigHandler.RITUAL_TIME;
            else if (!canStartRitual){
                shouldAddData = false;
                active = false;
                timer = -1;
                if (!worldObj.isRemote){
                    JewelrycraftUtil.addCursePoints(target, 50);
                    target.addChatMessage(new ChatComponentText(EnumChatFormatting.BLACK + "The Shadows don't like to be disturbed for no reason!"));
                    target.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "You feel a strange aura encumbering you."));
                }
            }
        }
        if (active && opening == 4){
            for(int l = 0; l <= 100; l++)
                worldObj.spawnParticle("depthsuspend", xCoord + 6.5F - worldObj.rand.nextInt(12) - worldObj.rand.nextFloat(), yCoord - 2F + worldObj.rand.nextInt(9) - worldObj.rand.nextFloat(), zCoord + 6.5F - worldObj.rand.nextInt(12) - worldObj.rand.nextFloat(), 0, 0, 0);
        	if(soundTimer == 0) worldObj.playSound(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "jewelrycraft2:Ritual", 1.0F, 1.0F, false);
        	if(soundTimer < 20*14) soundTimer++;
        	else soundTimer = 0;
        }
    }
    
    public boolean isValidStructure(World world, int x, int y, int z, int metadata)
    {
        if (world.getBlockMetadata(x, y, z) == 0 || world.getBlockMetadata(x, y, z) == 2){
            // Layers from top to bottom
            // 1st Layer
            if (world.getBlock(x, y + 1, z) != Blocks.stone_slab || world.getBlockMetadata(x, y + 1, z) != 5) return false;
            if (world.getBlock(x + 1, y + 1, z) != Blocks.stone_slab || world.getBlockMetadata(x + 1, y + 1, z) != 5) return false;
            if (world.getBlock(x - 1, y + 1, z) != Blocks.stone_slab || world.getBlockMetadata(x - 1, y + 1, z) != 5) return false;
            // 2nd Layer
            if (world.getBlock(x + 2, y, z) != Blocks.stone_brick_stairs || world.getBlockMetadata(x + 2, y, z) != 1) return false;
            if (world.getBlock(x + 1, y, z) != Blocks.stone_brick_stairs || world.getBlockMetadata(x + 1, y, z) != 4) return false;
            if (world.getBlock(x - 1, y, z) != Blocks.stone_brick_stairs || world.getBlockMetadata(x - 1, y, z) != 5) return false;
            if (world.getBlock(x - 2, y, z) != Blocks.stone_brick_stairs || world.getBlockMetadata(x - 2, y, z) != 0) return false;
            // 3rd Layer
            if (world.getBlock(x + 2, y - 1, z) != Blocks.stonebrick) return false;
            if (world.getBlock(x - 2, y - 1, z) != Blocks.stonebrick) return false;
            // 4th Layer
            if (world.getBlock(x + 2, y - 2, z) != Blocks.stonebrick) return false;
            if (world.getBlock(x - 2, y - 2, z) != Blocks.stonebrick) return false;
            // 5th Layer
            if (world.getBlock(x + 2, y - 3, z) != Blocks.stonebrick) return false;
            if (world.getBlock(x - 2, y - 3, z) != Blocks.stonebrick) return false;
        }else if (world.getBlockMetadata(x, y, z) == 1 || world.getBlockMetadata(x, y, z) == 3){
            // Layers from top to bottom
            // 1st Layer
            if (world.getBlock(x, y + 1, z) != Blocks.stone_slab || world.getBlockMetadata(x, y + 1, z) != 5) return false;
            if (world.getBlock(x, y + 1, z + 1) != Blocks.stone_slab || world.getBlockMetadata(x, y + 1, z + 1) != 5) return false;
            if (world.getBlock(x, y + 1, z - 1) != Blocks.stone_slab || world.getBlockMetadata(x, y + 1, z - 1) != 5) return false;
            // 2nd Layer
            if (world.getBlock(x, y, z + 2) != Blocks.stone_brick_stairs || world.getBlockMetadata(x, y, z + 2) != 3) return false;
            if (world.getBlock(x, y, z + 1) != Blocks.stone_brick_stairs || world.getBlockMetadata(x, y, z + 1) != 6) return false;
            if (world.getBlock(x, y, z - 1) != Blocks.stone_brick_stairs || world.getBlockMetadata(x, y, z - 1) != 7) return false;
            if (world.getBlock(x, y, z - 2) != Blocks.stone_brick_stairs || world.getBlockMetadata(x, y, z - 2) != 2) return false;
            // 3rd Layer
            if (world.getBlock(x, y - 1, z + 2) != Blocks.stonebrick) return false;
            if (world.getBlock(x, y - 1, z - 2) != Blocks.stonebrick) return false;
            // 4th Layer
            if (world.getBlock(x, y - 2, z + 2) != Blocks.stonebrick) return false;
            if (world.getBlock(x, y - 2, z - 2) != Blocks.stonebrick) return false;
            // 5th Layer
            if (world.getBlock(x, y - 3, z + 2) != Blocks.stonebrick) return false;
            if (world.getBlock(x, y - 3, z - 2) != Blocks.stonebrick) return false;
        }
        // 3rd Layer
        if (world.getBlock(x - 4, y - 1, z - 4) != BlockList.shadowBlock) return false;
        if (world.getBlock(x - 4, y - 1, z + 4) != BlockList.shadowBlock) return false;
        if (world.getBlock(x + 4, y - 1, z - 4) != BlockList.shadowBlock) return false;
        if (world.getBlock(x + 4, y - 1, z + 4) != BlockList.shadowBlock) return false;
        // 4th Layer
        if (world.getBlock(x - 4, y - 2, z - 4) != Blocks.stonebrick) return false;
        if (world.getBlock(x - 4, y - 2, z + 4) != Blocks.stonebrick) return false;
        if (world.getBlock(x + 4, y - 2, z - 4) != Blocks.stonebrick) return false;
        if (world.getBlock(x + 4, y - 2, z + 4) != Blocks.stonebrick) return false;
        // 5th Layer
        // Pillars
        if (world.getBlock(x - 4, y - 3, z - 4) != Blocks.stonebrick) return false;
        if (world.getBlock(x - 4, y - 3, z + 4) != Blocks.stonebrick) return false;
        if (world.getBlock(x + 4, y - 3, z - 4) != Blocks.stonebrick) return false;
        if (world.getBlock(x + 4, y - 3, z + 4) != Blocks.stonebrick) return false;
        // Pedestals
        if (!(world.getBlock(x, y - 3, z) instanceof BlockHandPedestal)) return false;
        if (world.getBlock(x - 4, y - 3, z + 2) != BlockList.handPedestal || world.getBlockMetadata(x - 4, y - 3, z + 2) != 1) return false;
        if (world.getBlock(x - 5, y - 3, z) != BlockList.handPedestal || world.getBlockMetadata(x - 5, y - 3, z) != 2) return false;
        if (world.getBlock(x - 4, y - 3, z - 2) != BlockList.handPedestal || world.getBlockMetadata(x - 4, y - 3, z - 2) != 3) return false;
        if (world.getBlock(x - 2, y - 3, z - 4) != BlockList.handPedestal || world.getBlockMetadata(x - 2, y - 3, z - 4) != 3) return false;
        if (world.getBlock(x, y - 3, z - 5) != BlockList.handPedestal || world.getBlockMetadata(x, y - 3, z - 5) != 4) return false;
        if (world.getBlock(x + 2, y - 3, z - 4) != BlockList.handPedestal || world.getBlockMetadata(x + 2, y - 3, z - 4) != 5) return false;
        if (world.getBlock(x + 4, y - 3, z - 2) != BlockList.handPedestal || world.getBlockMetadata(x + 4, y - 3, z - 2) != 5) return false;
        if (world.getBlock(x + 5, y - 3, z) != BlockList.handPedestal || world.getBlockMetadata(x + 5, y - 3, z) != 6) return false;
        if (world.getBlock(x + 4, y - 3, z + 2) != BlockList.handPedestal || world.getBlockMetadata(x + 4, y - 3, z + 2) != 7) return false;
        if (world.getBlock(x + 2, y - 3, z + 4) != BlockList.handPedestal || world.getBlockMetadata(x + 2, y - 3, z + 4) != 7) return false;
        if (world.getBlock(x, y - 3, z + 5) != BlockList.handPedestal || world.getBlockMetadata(x, y - 3, z + 5) != 0) return false;
        if (world.getBlock(x - 2, y - 3, z + 4) != BlockList.handPedestal || world.getBlockMetadata(x - 2, y - 3, z + 4) != 1) return false;
        return true;
    }
    
    public void addData(World world, int x, int y, int z)
    {
        pedestalItems.clear();
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x + 2, y - 3, z - 4));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x - 4, y - 3, z + 2));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x, y - 3, z - 5));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x - 2, y - 3, z - 4));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x - 4, y - 3, z - 2));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x - 5, y - 3, z));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x + 4, y - 3, z - 2));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x + 5, y - 3, z));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x + 4, y - 3, z + 2));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x + 2, y - 3, z + 4));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x, y - 3, z + 5));
        addPedestalInfo((TileEntityHandPedestal)world.getTileEntity(x - 2, y - 3, z + 4));
    }
    
    public int getNumberOfItems(World world, int x, int y, int z)
    {
        int items = 0;
        if (((TileEntityHandPedestal)world.getTileEntity(x + 2, y - 3, z - 4)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x - 4, y - 3, z + 2)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x, y - 3, z - 5)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x - 2, y - 3, z - 4)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x - 4, y - 3, z - 2)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x - 5, y - 3, z)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x + 4, y - 3, z - 2)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x + 5, y - 3, z)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x + 4, y - 3, z + 2)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x + 2, y - 3, z + 4)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x, y - 3, z + 5)).heldItemStack != null) items++;
        if (((TileEntityHandPedestal)world.getTileEntity(x - 2, y - 3, z + 4)).heldItemStack != null) items++;
        return items;
    }
    
    public boolean canChangePedestals(World world, int x, int y, int z)
    {
        if (world.getBlock(x, y - 3, z) != BlockList.handPedestal) return false;
        if (world.getBlock(x + 2, y - 3, z - 4) != BlockList.handPedestal) return false;
        if (world.getBlock(x - 4, y - 3, z + 2) != BlockList.handPedestal) return false;
        if (world.getBlock(x, y - 3, z - 5) != BlockList.handPedestal) return false;
        if (world.getBlock(x - 2, y - 3, z - 4) != BlockList.handPedestal) return false;
        if (world.getBlock(x - 4, y - 3, z - 2) != BlockList.handPedestal) return false;
        if (world.getBlock(x - 5, y - 3, z) != BlockList.handPedestal) return false;
        if (world.getBlock(x + 4, y - 3, z - 2) != BlockList.handPedestal) return false;
        if (world.getBlock(x + 5, y - 3, z) != BlockList.handPedestal) return false;
        if (world.getBlock(x + 4, y - 3, z + 2) != BlockList.handPedestal) return false;
        if (world.getBlock(x + 2, y - 3, z + 4) != BlockList.handPedestal) return false;
        if (world.getBlock(x, y - 3, z + 5) != BlockList.handPedestal) return false;
        if (world.getBlock(x - 2, y - 3, z + 4) != BlockList.handPedestal) return false;
        return true;
    }
    
    public void changePedestals(World world, int x, int y, int z)
    {
        changeHand(world, x, y - 3, z);
        changeHand(world, x + 2, y - 3, z - 4);
        changeHand(world, x - 4, y - 3, z + 2);
        changeHand(world, x, y - 3, z - 5);
        changeHand(world, x - 2, y - 3, z - 4);
        changeHand(world, x - 4, y - 3, z - 2);
        changeHand(world, x - 5, y - 3, z);
        changeHand(world, x + 4, y - 3, z - 2);
        changeHand(world, x + 5, y - 3, z);
        changeHand(world, x + 4, y - 3, z + 2);
        changeHand(world, x + 2, y - 3, z + 4);
        changeHand(world, x, y - 3, z + 5);
        changeHand(world, x - 2, y - 3, z + 4);
    }
    
    public void changeHand(World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(world.getBlock(x, y, z)));
        TileEntityShadowHand tile = new TileEntityShadowHand();
        if (world.getTileEntity(x, y, z) instanceof TileEntityHandPedestal){
            if (((TileEntityHandPedestal)world.getTileEntity(x, y, z)).heldItemStack != null) tile.setHeldItemStack(((TileEntityHandPedestal)world.getTileEntity(x, y, z)).heldItemStack.copy());
            if (tile.heldItemStack != null) tile.closeHand();
            ((TileEntityHandPedestal)world.getTileEntity(x, y, z)).removeHeldItemStack();
            world.setBlock(x, y, z, BlockList.shadowHand, l, 2);
            world.setTileEntity(x, y, z, tile);
        }
    }
    
    public void revertPedestals(World world, int x, int y, int z)
    {
        revertHand(world, x, y - 3, z);
        revertHand(world, x + 2, y - 3, z - 4);
        revertHand(world, x - 4, y - 3, z + 2);
        revertHand(world, x, y - 3, z - 5);
        revertHand(world, x - 2, y - 3, z - 4);
        revertHand(world, x - 4, y - 3, z - 2);
        revertHand(world, x - 5, y - 3, z);
        revertHand(world, x + 4, y - 3, z - 2);
        revertHand(world, x + 5, y - 3, z);
        revertHand(world, x + 4, y - 3, z + 2);
        revertHand(world, x + 2, y - 3, z + 4);
        revertHand(world, x, y - 3, z + 5);
        revertHand(world, x - 2, y - 3, z + 4);
    }
    
    public void revertHand(World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(BlockList.handPedestal));
        world.playSoundEffect(x, y + 0.5F, z, "step.wood", 1F, 1F);
        TileEntityHandPedestal tile = new TileEntityHandPedestal();
        if (world.getTileEntity(x, y, z) instanceof TileEntityShadowHand){
            if (((TileEntityShadowHand)world.getTileEntity(x, y, z)).heldItemStack != null) tile.setHeldItemStack(((TileEntityShadowHand)world.getTileEntity(x, y, z)).heldItemStack.copy());
            ((TileEntityShadowHand)world.getTileEntity(x, y, z)).removeHeldItemStack();
            world.setBlock(x, y, z, BlockList.handPedestal, l, 2);
            world.setTileEntity(x, y, z, tile);
        }
    }
    
    public void addPedestalInfo(TileEntityHandPedestal pedestal)
    {
        ItemStack heldItemStack;
        if (pedestal != null && (heldItemStack = pedestal.getHeldItemStack()) != null){
            if (pedestalItems.isEmpty()) pedestalItems.add(heldItemStack.copy());
            else{
                boolean hasItem = false;
                int index = 0;
                for(int ind = 0; ind < pedestalItems.size() && !hasItem; ind++)
                    if (heldItemStack.getItem().equals(pedestalItems.get(ind).getItem()) && heldItemStack.getItemDamage() == pedestalItems.get(ind).getItemDamage()){
                        index = ind;
                        hasItem = true;
                        if (heldItemStack.hasTagCompound() && pedestalItems.get(ind).hasTagCompound() && !heldItemStack.getTagCompound().equals(pedestalItems.get(ind).getTagCompound())) hasItem = false;
                    }
                if (!hasItem) pedestalItems.add(heldItemStack.copy());
                else{
                    ItemStack object = pedestalItems.get(index).copy();
                    object.stackSize++;
                    pedestalItems.set(index, object);
                }
            }
            pedestal.removeHeldItemStack();
            pedestal.openHand();
        }else if (pedestal != null && target != null) JewelrycraftUtil.addCursePoints(target, 20);
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        AxisAlignedBB bb = AxisAlignedBB.getBoundingBox(xCoord - 5.5D, yCoord - 5.5D, zCoord - 5.5D, xCoord + 5.5D, yCoord + 5.5D, zCoord + 5.5D);
        return bb;
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
