package darkknight.jewelrycraft.util;

import net.minecraft.util.ResourceLocation;

public class Variables
{
    public static final int CURSE_POINTS_PER_LEVEL = 7000;
    
    public static final String MODID = "jewelrycraft2";
    public static final String MODNAME = "Jewelrycraft 2";
    public static final String VERSION = "1.1.4";
    public static final String PACKET_CHANNEL = "jewelrycraft2";
    
    public static final String CONFIG_GUI = "darkknight.jewelrycraft.config.ConfigGuiFactory";
    public static final String CLIENT_PROXY = "darkknight.jewelrycraft.proxy.ClientProxy";
    public static final String SERVER_PROXY = "darkknight.jewelrycraft.proxy.CommonProxy";
    
    public static final ResourceLocation PEDESTAL_TEXTURE = new ResourceLocation(Variables.MODID, "textures/tileentities/BricksPedestal.png");
    public static final ResourceLocation SHADOW_HAND_TEXTURE = new ResourceLocation(Variables.MODID, "textures/tileentities/ShadowHand.png");
    public static final ResourceLocation VILLAGER_TEXTURE = new ResourceLocation(Variables.MODID, "textures/entities/jeweler.png");
    public static final ResourceLocation MISC_TEXTURE = new ResourceLocation(Variables.MODID, "textures/gui/hearts.png");
}
