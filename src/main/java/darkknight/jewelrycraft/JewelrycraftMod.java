/*
 * Mod made by DarkKnight during the Modjam 3
 * It's an awesome mod
 * I love me! :D
 */
package darkknight.jewelrycraft;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.achievements.AchievementsList;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.client.gui.GuiTab;
import darkknight.jewelrycraft.client.gui.GuiTabBlocks;
import darkknight.jewelrycraft.client.gui.GuiTabGemsAndIngots;
import darkknight.jewelrycraft.client.gui.GuiTabIntroduction;
import darkknight.jewelrycraft.client.gui.GuiTabItems;
import darkknight.jewelrycraft.client.gui.GuiTabModifiers;
import darkknight.jewelrycraft.client.gui.GuiTabOresToIngots;
import darkknight.jewelrycraft.client.gui.GuiTabRitual;
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
import darkknight.jewelrycraft.worldGen.ChestGeneration;
import darkknight.jewelrycraft.worldGen.village.VillageHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

@Mod (modid = Variables.MODID, name = Variables.MODNAME, version = Variables.VERSION, guiFactory = Variables.CONFIG_GUI, acceptedMinecraftVersions = "[1.7.10,1.8)")
public class JewelrycraftMod
{
    @Instance (Variables.MODID)
    public static JewelrycraftMod instance;
    @SidedProxy (clientSide = Variables.CLIENT_PROXY, serverSide = Variables.SERVER_PROXY)
    public static CommonProxy proxy;
    public static Logger logger;
    public static File dir;
    public static CreativeTabs jewelrycraft = new CreativeTabs(Variables.MODID){
        @Override
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(BlockList.jewelCraftingTable);
        }
    };
    public static CreativeTabs liquids = new CreativeTabLiquids("Liquids").setBackgroundImageName("item_search.png");
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
        logger = e.getModLog();
        ConfigHandler.INSTANCE.loadConfig(e);
        ThirdPartyManager.instance().index();
        logger.log(Level.INFO, "Registering Blocks");
        BlockList.preInit(e);
        logger.log(Level.INFO, "Registering Items");
        ItemList.preInit(e);
        logger.log(Level.INFO, "Registering Crafting Recipes");
        CraftingRecipes.preInit(e);
        logger.log(Level.INFO, "Registering Curses");
        CurseList.preInit(e);
        logger.log(Level.INFO, "Registering Packets");
        PacketHandler.preInit(e);
        logger.log(Level.INFO, "Registering Entities");
        EntityList.preInit(e);
        logger.log(Level.INFO, "Registering Village Stuff");
        VillageHandler.preInit(e);
        logger.log(Level.INFO, "Registering Events");
        EventList.preInit(e);
        logger.log(Level.INFO, "Registering Potions");
        PotionList.preInit(e);
        logger.log(Level.INFO, "Loading Third Party Mods");
        ThirdPartyManager.instance().preInit();
        logger.log(Level.INFO, "Adding Dungeons loot");        
        ChestGeneration.preInit(e);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        logger.log(Level.INFO, "Registering Events");
        EventList.init(e);
        logger.log(Level.INFO, "Registering Potions");
        PotionList.init(e);
        logger.log(Level.INFO, "Loading Third Party Mods");
        ThirdPartyManager.instance().init();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        logger.log(Level.INFO, "Loading Third Party Mods");
        ThirdPartyManager.instance().postInit();
        logger.log(Level.INFO, "Registering Events");
        EventList.postInit(e);
        logger.log(Level.INFO, "Registering Potions");
        PotionList.postInit(e);
        logger.log(Level.INFO, "Registering Achievements");
        AchievementsList.addAchievements();
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
