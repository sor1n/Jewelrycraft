package darkknight.jewelrycraft.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.particles.EntityShadowsFX;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class TileEntityJewelrsCraftingTable extends TileEntity
{
    public boolean hasJewelry, hasEndItem, isDirty, hasGem, crafting;
    public ItemStack jewelry, endItem, gem;
    public int carving, effect;
    public float angle;
    
    public TileEntityJewelrsCraftingTable()
    {
        this.jewelry = new ItemStack(Item.getItemById(0), 0, 0);
        this.endItem = new ItemStack(Item.getItemById(0), 0, 0);
        this.gem = new ItemStack(Item.getItemById(0), 0, 0);
        this.hasJewelry = false;
        this.hasEndItem = false;
        this.hasGem = false;
        this.crafting = false;
        this.carving = 0;
        this.effect = 0;
        this.angle = 0;
        this.isDirty = false;
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("hasJewelry", hasJewelry);
        nbt.setBoolean("hasEndItem", hasEndItem);
        nbt.setBoolean("hasJewel", hasGem);
        nbt.setBoolean("crafting", crafting);
        nbt.setInteger("timer", carving);
        nbt.setInteger("effect", effect);
        nbt.setFloat("angle", angle);
        
        NBTTagCompound tag1 = new NBTTagCompound();
        NBTTagCompound tag2 = new NBTTagCompound();
        NBTTagCompound tag3 = new NBTTagCompound();
        
        this.jewelry.writeToNBT(tag1);
        nbt.setTag("jewelry", tag1);
        this.endItem.writeToNBT(tag2);
        nbt.setTag("endItem", tag2);
        this.gem.writeToNBT(tag3);
        nbt.setTag("jewel", tag3);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.hasJewelry = nbt.getBoolean("hasJewelry");
        this.hasEndItem = nbt.getBoolean("hasEndItem");
        this.hasGem = nbt.getBoolean("hasJewel");
        this.crafting = nbt.getBoolean("crafting");
        
        this.carving = nbt.getInteger("timer");
        this.effect = nbt.getInteger("effect");
        this.angle = nbt.getFloat("angle");
        this.jewelry = new ItemStack(Item.getItemById(0), 0, 0);
        this.jewelry.readFromNBT(nbt.getCompoundTag("jewelry"));
        this.endItem = new ItemStack(Item.getItemById(0), 0, 0);
        this.endItem.readFromNBT(nbt.getCompoundTag("endItem"));
        this.gem = new ItemStack(Item.getItemById(0), 0, 0);
        this.gem.readFromNBT(nbt.getCompoundTag("jewel"));
    }
    
    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (isDirty)
        {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = false;
        }
        if (angle < 360F) angle += 3F;
        else angle = 0F;
        
        if (this.hasJewelry && this.hasGem && !this.hasEndItem && crafting)
        {
            if (carving > 0) carving--;
            if (crafting)
            {
                for (int l = 0; l < ConfigHandler.jewelryCraftingTime / (carving + 2); ++l)
                {
                    if (worldObj.rand.nextInt(10) == 0) this.worldObj.playSoundEffect(xCoord, yCoord + 0.5F, zCoord, "random.orb", 0.05F, 1F);
                    if (this.getBlockMetadata() == 0) this.worldObj.spawnParticle("instantSpell", xCoord + 0.5F, (double) yCoord + 0.8F, zCoord + 0.2F, 0.0D, 0.0D, 0.0D);
                    if (this.getBlockMetadata() == 1) this.worldObj.spawnParticle("instantSpell", xCoord + 0.8F, (double) yCoord + 0.8F, zCoord + 0.5F, 0.0D, 0.0D, 0.0D);
                    if (this.getBlockMetadata() == 2) this.worldObj.spawnParticle("instantSpell", xCoord + 0.5F, (double) yCoord + 0.8F, zCoord + 0.8F, 0.0D, 0.0D, 0.0D);
                    if (this.getBlockMetadata() == 3) this.worldObj.spawnParticle("instantSpell", xCoord + 0.2F, (double) yCoord + 0.8F, zCoord + 0.5F, 0.0D, 0.0D, 0.0D);
                    
                }
            }
            if (carving == 0)
            {
                this.hasEndItem = true;
                this.endItem = jewelry.copy();
                if (hasGem && gem != new ItemStack(Item.getItemById(0), 0, 0))
                {
                    if (!JewelryNBT.hasTag(jewelry, "gem"))
                    {
                        JewelryNBT.addGem(endItem, gem);
                        this.hasGem = false;
                        this.gem = new ItemStack(Item.getItemById(0), 0, 0);
                    }
                    else
                    {
                        ItemStack aux = JewelryNBT.gem(jewelry);
                        JewelryNBT.addGem(endItem, gem);
                        if(JewelrycraftUtil.rand.nextBoolean()) gem = aux.copy();
                        else
                        {
                            this.hasGem = false;
                            this.gem = new ItemStack(Item.getItemById(0), 0, 0);
                        }
                    }
                }
                this.hasJewelry = false;
                this.jewelry = new ItemStack(Item.getItemById(0), 0, 0);
                carving = -1;
                crafting = false;
                isDirty = true;
            }
        }
    }
    
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbttagcompound);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
