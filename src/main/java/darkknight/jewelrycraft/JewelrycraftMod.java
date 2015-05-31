/*
 * Mod made by DarkKnight during the Modjam 3
 * It's an awesome mod
 * I love me! :D
 */
package darkknight.jewelrycraft;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.commands.JewelrycraftCommands;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.curses.CurseList;
import darkknight.jewelrycraft.entities.EntityList;
import darkknight.jewelrycraft.events.EventList;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.network.PacketHandler;
import darkknight.jewelrycraft.potions.PotionList;
import darkknight.jewelrycraft.proxy.CommonProxy;
import darkknight.jewelrycraft.recipes.CraftingRecipes;
import darkknight.jewelrycraft.thirdparty.ThirdPartyManager;
import darkknight.jewelrycraft.util.Variables;
import darkknight.jewelrycraft.worldGen.village.VillageHandler;

@Mod (modid = Variables.MODID, name = Variables.MODNAME, version = Variables.VERSION, guiFactory = Variables.CONFIG_GUI, acceptedMinecraftVersions = "[1.7.10,1.8)")
public class JewelrycraftMod
{
    @Instance (Variables.MODID)
    public static JewelrycraftMod instance;
    @SidedProxy (clientSide = Variables.CLIENT_PROXY, serverSide = Variables.SERVER_PROXY)
    public static CommonProxy proxy;
    public static final Logger logger = Logger.getLogger(Variables.MODNAME);
    public static File dir;
    public static CreativeTabs jewelrycraft = new CreativeTabs(Variables.MODID){
        @Override
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(BlockList.jewelCraftingTable);
        }
    };
    public static CreativeTabs liquids = new CreativeTabLiquids("Liquids").setBackgroundImageName("item_search.png");
    public static NBTTagCompound saveData = new NBTTagCompound();
    public static NBTTagCompound clientData = new NBTTagCompound();
    public static File liquidsConf;
    public static SimpleNetworkWrapper netWrapper;
    public static boolean fancyRender = false;
    
    /**
     * Pre initialization of mod stuff.
     *
     * @param e FMLPreInitializationEvent
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) throws IOException
    {
        dir = e.getModConfigurationDirectory();
        ConfigHandler.INSTANCE.loadConfig(e);
        ThirdPartyManager.instance().index();
        BlockList.preInit(e);
        ItemList.preInit(e);
        CraftingRecipes.preInit(e);
        CurseList.preInit(e);
        PacketHandler.preInit(e);
        EntityList.preInit(e);
        VillageHandler.preInit(e);
        EventList.preInit(e);
        PotionList.preInit(e);
        ThirdPartyManager.instance().preInit();
        
        ChestGenHooks.addItem("dungeonChest", new WeightedRandomChestContent(new ItemStack(ItemList.thiefGloves), 1, 1, 1));
        ChestGenHooks.addItem("villageBlacksmith", new WeightedRandomChestContent(new ItemStack(ItemList.thiefGloves), 1, 1, 1));
        ChestGenHooks.addItem("strongholdCorridor", new WeightedRandomChestContent(new ItemStack(ItemList.thiefGloves), 1, 1, 5));        
        for(int i = 0; i < 16; i++) ChestGenHooks.addItem("mineshaftCorridor", new WeightedRandomChestContent(new ItemStack(BlockList.crystal, 1, i), 1, 4, 15));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        EventList.init(e);
        PotionList.init(e);
        ThirdPartyManager.instance().init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        ThirdPartyManager.instance().postInit();
        EventList.postInit(e);
        PotionList.postInit(e);
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new JewelrycraftCommands());
    }
    
    @EventHandler
    public void imcCallback(FMLInterModComms.IMCEvent event)
    {
        for (final FMLInterModComms.IMCMessage imcMessage : event.getMessages())
        {
            logger.info("The mod " + imcMessage.getSender() + " has sent the following message: " + imcMessage.getStringValue());
        }
    }
}
