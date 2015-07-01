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
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.ModifierEffects;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;

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
        if (pass == 0 && JewelryNBT.ingot(stack) != null) return JewelrycraftUtil.getColor(JewelryNBT.ingot(stack));
        if (pass == 1 && JewelryNBT.gem(stack) != null) return JewelrycraftUtil.getColor(JewelryNBT.gem(stack));
        else if (JewelryNBT.ingot(stack) != null) return JewelrycraftUtil.getColor(JewelryNBT.ingot(stack));
        return 16777215;
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
        if (stack.hasTagCompound() && ConfigHandler.JEWELRY_INFO){
            ItemStack ingot = JewelryNBT.ingot(stack);
            if (ingot != null && Item.getIdFromItem(JewelryNBT.ingot(stack).getItem()) > 0) list.add(StatCollector.translateToLocal("info." + Variables.MODID + ".metal") + ": " + EnumChatFormatting.YELLOW + ingot.getDisplayName().replace(StatCollector.translateToLocal("info." + Variables.MODID + ".ingot"), " "));
            ItemStack gem = JewelryNBT.gem(stack);
            if (gem != null) list.add(StatCollector.translateToLocal("info." + Variables.MODID + ".gem") + ": " + EnumChatFormatting.BLUE + gem.getDisplayName());
            ArrayList<ItemStack> modifier = JewelryNBT.modifier(stack);
            if (!modifier.isEmpty()) list.add(StatCollector.translateToLocal("info." + Variables.MODID + ".modifiers") + ": ");
            for(int i = 0; i < modifier.size(); i++)
                list.add(EnumChatFormatting.DARK_PURPLE + modifier.get(i).getDisplayName() + " x" + modifier.get(i).stackSize);
        }
    }
    
    /**
     * @param stack
     * @param player
     */
    public void action(ItemStack item, EntityPlayer player)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects())
            if (JewelryNBT.doesModifierExist(item, mod.getModifier())) mod.action(item, player, this);
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
            return JewelryNBT.doesModifierExist(item, mod.getModifier()) ? mod.onPlayerAttackedCacellable(item, player, source, this, amount) : false;
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
            return JewelryNBT.doesModifierExist(item, mod.getModifier()) ? mod.onEntityAttackedCacellable(item, player, target, this, amount) : false;
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
        for(ModifierEffects mod: ModifierEffects.getEffects())
            if (JewelryNBT.doesModifierExist(item, mod.getModifier())) mod.onPlayerAttacked(item, player, source, this, amount);
    }
    
    /**
     * @param item
     * @param player
     * @param target
     * @return
     */
    public void onEntityAttacked(ItemStack item, EntityPlayer player, Entity target, float amount)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects())
            if (JewelryNBT.doesModifierExist(item, mod.getModifier())) mod.onEntityAttacked(item, player, target, this, amount);
    }
    
    public void onPlayerDead(ItemStack item, EntityPlayer player, DamageSource source)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects())
            if (JewelryNBT.doesModifierExist(item, mod.getModifier())) mod.onPlayerDead(item, player, source, this);
    }
    
    public void onPlayerRespawn(ItemStack item, PlayerEvent.Clone event)
    {
        for(ModifierEffects mod: ModifierEffects.getEffects())
            if (JewelryNBT.doesModifierExist(item, mod.getModifier())) mod.onPlayerRespawn(item, event, this);
    }
}
