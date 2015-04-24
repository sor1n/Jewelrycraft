/*
 * Mod made by DarkKnight during the Modjam 3
 * It's an awesome mod
 * I love me! :D
 */
package darkknight.jewelrycraft;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
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
import darkknight.jewelrycraft.proxy.CommonProxy;
import darkknight.jewelrycraft.recipes.CraftingRecipes;
import darkknight.jewelrycraft.util.Variables;
import darkknight.jewelrycraft.worldGen.village.VillageHandler;

@Mod (modid = Variables.MODID, name = Variables.MODNAME, version = Variables.VERSION, guiFactory = Variables.CONFIG_GUI, acceptedMinecraftVersions = "[1.7.10,1.8)", canBeDeactivated = true)
public class JewelrycraftMod
{
    @Instance (Variables.MODID)
    public static JewelrycraftMod instance;
    @SidedProxy (clientSide = Variables.CLIENT_PROXY, serverSide = Variables.SERVER_PROXY)
    public static CommonProxy proxy;
    public static final Logger logger = Logger.getLogger("Jewelrycraft");
    public static File dir;
    public static CreativeTabs jewelrycraft = new CreativeTabs(Variables.MODID){
        @Override
        public Item getTabIconItem()
        {
            return Item.getItemFromBlock(BlockList.jewelCraftingTable);
        }
    };
    public static CreativeTabs liquids = new CreativeTabLiquids("Liquids");
    public static NBTTagCompound saveData = new NBTTagCompound();
    public static NBTTagCompound clientData = new NBTTagCompound();
    public static File liquidsConf;
    public static SimpleNetworkWrapper netWrapper;
    public static boolean fancyRender;
    List<String> authorList = new ArrayList<String>();
    
    /**
     * Pre initialization of mod stuff.
     *
     * @param e FMLPreInitializationEvent
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @EventHandler
    public void preInit(FMLPreInitializationEvent e) throws IOException
    {
        ModMetadata metadata = e.getModMetadata();
        dir = e.getModConfigurationDirectory();
        ConfigHandler.INSTANCE.loadConfig(e);
        BlockList.preInit(e);
        ItemList.preInit(e);
        CraftingRecipes.preInit(e);
        CurseList.preInit(e);
        PacketHandler.preInit(e);
        EntityList.preInit(e);
        VillageHandler.preInit(e);
        EventList.preInit(e);
        authorList.add("OnyxDarkKnight");
        authorList.add("bspkrs");
        authorList.add("domi1819");
        metadata.autogenerated = false;
        metadata.authorList = authorList;
        metadata.url = "https://github.com/sor1n/Jewelrycraft";
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        EventList.init(e);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        EventList.postInit(e);
    }
    
    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new JewelrycraftCommands());
    }
}
