package darkknight.jewelrycraft.thirdparty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Level;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.IGuiHandler;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

/** 
 * @author MineMarteen from Pneumaticraft
 */
public class ThirdPartyManager implements IGuiHandler{

    private static ThirdPartyManager INSTANCE = new ThirdPartyManager();
    private final List<IThirdParty> thirdPartyMods = new ArrayList<IThirdParty>();

    public static ThirdPartyManager instance(){
        return INSTANCE;
    }

    public void index(){
        Map<String, Class<? extends IThirdParty>> thirdPartyClasses = new HashMap<String, Class<? extends IThirdParty>>();
        thirdPartyClasses.put(ModIds.EE3, EE3.class);
        thirdPartyClasses.put(ModIds.NEI, NEI.class);
        thirdPartyClasses.put(ModIds.ALPACA, Alpaca.class);

        List<String> enabledThirdParty = new ArrayList<String>();
        ConfigHandler.config.addCustomCategoryComment("third_party_enabling", "With these options you can disable third party content by mod. Useful if something in the mod changes and causes crashes.");
        for(String modid : thirdPartyClasses.keySet()) {
            if(ConfigHandler.config.get("Third_Party_Enabling", modid, true).getBoolean()) {
                enabledThirdParty.add(modid);
            }
        }
        ConfigHandler.config.save();

        for(Map.Entry<String, Class<? extends IThirdParty>> entry : thirdPartyClasses.entrySet()) {
            if(enabledThirdParty.contains(entry.getKey()) && Loader.isModLoaded(entry.getKey())) {
                try {
                    thirdPartyMods.add(entry.getValue().newInstance());
                } catch(Exception e) {
                    JewelrycraftMod.logger.log(Level.ERROR, "Failed to instantiate third party handler!");
                    e.printStackTrace();
                }
            }
        }
    }

    public void onItemRegistry(Item item){
        for(IThirdParty thirdParty : thirdPartyMods) {
            if(thirdParty instanceof IRegistryListener) ((IRegistryListener)thirdParty).onItemRegistry(item);
        }
    }

    public void onBlockRegistry(Block block){
        for(IThirdParty thirdParty : thirdPartyMods) {
            if(thirdParty instanceof IRegistryListener) ((IRegistryListener)thirdParty).onBlockRegistry(block);
        }
    }

    public void preInit(){
        for(IThirdParty thirdParty : thirdPartyMods) {
            try {
                thirdParty.preInit();
            } catch(Throwable e) {
                JewelrycraftMod.logger.log(Level.ERROR, "Jewelrycraft wasn't able to load third party content from the third party class " + thirdParty.getClass() + " in the PreInit phase!");
                e.printStackTrace();
            }
        }
    }

    public void init(){
        for(IThirdParty thirdParty : thirdPartyMods) {
            try {
                thirdParty.init();
            } catch(Throwable e) {
                JewelrycraftMod.logger.log(Level.ERROR, "Jewelrycraft wasn't able to load third party content from the third party class " + thirdParty.getClass() + " in the Init phase!");
                e.printStackTrace();
            }
        }
    }

    public void postInit(){
        for(IThirdParty thirdParty : thirdPartyMods) {
            try {
                thirdParty.postInit();
            } catch(Throwable e) {
                JewelrycraftMod.logger.log(Level.ERROR, "Jewelrycraft wasn't able to load third party content from the third party class " + thirdParty.getClass() + " in the PostInit phase!");
                e.printStackTrace();
            }
        }
    }

    public void clientSide(){
        for(IThirdParty thirdParty : thirdPartyMods) {
            try {
                thirdParty.clientSide();
            } catch(Throwable e) {
                JewelrycraftMod.logger.log(Level.ERROR, "Jewelrycraft wasn't able to load third party content from the third party class " + thirdParty.getClass() + " client side!");
                e.printStackTrace();
            }
        }
    }

    public void clientInit(){
        for(IThirdParty thirdParty : thirdPartyMods) {
            try {
                thirdParty.clientInit();
            } catch(Throwable e) {
                JewelrycraftMod.logger.log(Level.ERROR, "Jewelrycraft wasn't able to load third party content from the third party class " + thirdParty.getClass() + " client side on the init!");
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        for(IThirdParty thirdParty : thirdPartyMods) {
            if(thirdParty instanceof IGuiHandler) {
                Object obj = ((IGuiHandler)thirdParty).getServerGuiElement(ID, player, world, x, y, z);
                if(obj != null) return obj;
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        for(IThirdParty thirdParty : thirdPartyMods) {
            if(thirdParty instanceof IGuiHandler) {
                Object obj = ((IGuiHandler)thirdParty).getClientGuiElement(ID, player, world, x, y, z);
                if(obj != null) return obj;
            }
        }
        return null;
    }

}