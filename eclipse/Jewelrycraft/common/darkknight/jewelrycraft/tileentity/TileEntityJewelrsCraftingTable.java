package darkknight.jewelrycraft.tileentity;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.JewelryNBT;

public class TileEntityJewelrsCraftingTable extends TileEntity
{
    public boolean hasJewelry, hasModifier, hasEndItem, isDirty, hasJewel;
    public ItemStack jewelry, modifier, endItem, jewel;
    public int       timer, effect;
    public float angle;

    public TileEntityJewelrsCraftingTable()
    {
        this.jewelry = new ItemStack(Item.getItemById(0), 0, 0);
        this.modifier = new ItemStack(Item.getItemById(0), 0, 0);
        this.endItem = new ItemStack(Item.getItemById(0), 0, 0);
        this.jewel = new ItemStack(Item.getItemById(0), 0, 0);
        this.hasJewelry = false;
        this.hasModifier = false;
        this.hasEndItem = false;
        this.hasJewel = false;
        this.timer = 0;
        this.effect = 0;
        this.angle = 0;
        this.isDirty = false;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("hasJewelry", hasJewelry);
        nbt.setBoolean("hasModifier", hasModifier);
        nbt.setBoolean("hasEndItem", hasEndItem);
        nbt.setBoolean("hasJewel", hasJewel);
        nbt.setInteger("timer", timer);
        nbt.setInteger("effect", effect);
        nbt.setFloat("angle", angle);

        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound tag1 = new NBTTagCompound();
        NBTTagCompound tag2 = new NBTTagCompound();
        NBTTagCompound tag3 = new NBTTagCompound();

        this.jewelry.writeToNBT(tag);
        nbt.setTag("jewelry", tag);
        this.modifier.writeToNBT(tag1);
        nbt.setTag("modifier", tag1);
        this.endItem.writeToNBT(tag2);
        nbt.setTag("endItem", tag2);
        this.jewel.writeToNBT(tag3);
        nbt.setTag("jewel", tag3);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.hasJewelry = nbt.getBoolean("hasJewelry");
        this.hasModifier = nbt.getBoolean("hasModifier");
        this.hasEndItem = nbt.getBoolean("hasEndItem");
        this.hasJewel = nbt.getBoolean("hasJewel");

        this.timer = nbt.getInteger("timer");
        this.effect = nbt.getInteger("effect");
        this.angle = nbt.getFloat("angle");
        this.jewelry = new ItemStack(Item.getItemById(0), 0, 0);
        this.jewelry.readFromNBT(nbt.getCompoundTag("jewelry"));
        this.modifier = new ItemStack(Item.getItemById(0), 0, 0);
        this.modifier.readFromNBT(nbt.getCompoundTag("modifier"));
        this.endItem = new ItemStack(Item.getItemById(0), 0, 0);
        this.endItem.readFromNBT(nbt.getCompoundTag("endItem"));
        this.jewel = new ItemStack(Item.getItemById(0), 0, 0);
        this.jewel.readFromNBT(nbt.getCompoundTag("jewel"));
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if(isDirty){
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            isDirty = false;
        }
        if(angle<360F)angle+=3F;
        else angle=0F;
        if (this.hasJewelry && (this.hasModifier || this.hasJewel) && !this.hasEndItem)
        {
            if (timer > 0)
            {
                timer--;
                for (int l = 0; l < ConfigHandler.jewelryCraftingTime/(timer + 2); ++l)
                {
                    if(this.getBlockMetadata() == 0) this.worldObj.spawnParticle("witchMagic", xCoord + 0.5F, (double) yCoord + 0.8F, zCoord + 0.2F, 0.0D, 0.0D, 0.0D);
                    if(this.getBlockMetadata() == 1) this.worldObj.spawnParticle("witchMagic", xCoord + 0.8F, (double) yCoord + 0.8F, zCoord + 0.5F, 0.0D, 0.0D, 0.0D);
                    if(this.getBlockMetadata() == 2) this.worldObj.spawnParticle("witchMagic", xCoord + 0.5F, (double) yCoord + 0.8F, zCoord + 0.8F, 0.0D, 0.0D, 0.0D);
                    if(this.getBlockMetadata() == 3) this.worldObj.spawnParticle("witchMagic", xCoord + 0.2F, (double) yCoord + 0.8F, zCoord + 0.5F, 0.0D, 0.0D, 0.0D);
                }
            }
            if (timer == 0)
            {
                this.hasEndItem = true;
                this.endItem = jewelry.copy();
                if (hasModifier && modifier != new ItemStack(Item.getItemById(0), 0, 0)) JewelryNBT.addModifier(endItem, modifier);
                if (hasJewel && jewel != new ItemStack(Item.getItemById(0), 0, 0)) JewelryNBT.addJewel(endItem, jewel);
                if (hasJewel && hasModifier && JewelryNBT.isJewelX(endItem, new ItemStack(Items.nether_star)) && JewelryNBT.isModifierX(endItem, new ItemStack(Items.book))) JewelryNBT.addMode(endItem, "Disenchant");
                if (hasModifier && JewelryNBT.isModifierEffectType(endItem)) JewelryNBT.addMode(endItem, "Activated");
                this.hasJewelry = false;
                this.jewelry = new ItemStack(Item.getItemById(0), 0, 0);
                this.hasModifier = false;
                this.modifier = new ItemStack(Item.getItemById(0), 0, 0);
                this.hasJewel = false;
                this.jewel = new ItemStack(Item.getItemById(0), 0, 0);
                timer = -1;
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
    public void onDataPacket (NetworkManager net, S35PacketUpdateTileEntity packet)
    {
        readFromNBT(packet.func_148857_g());
        worldObj.func_147479_m(xCoord, yCoord, zCoord);
    }
}
