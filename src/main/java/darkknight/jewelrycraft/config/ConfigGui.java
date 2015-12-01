package darkknight.jewelrycraft.config;

import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class ConfigGui extends GuiConfig
{
    public ConfigGui(GuiScreen parent)
    {
        super(parent, getElements(), Variables.MODID, Variables.MODID, false, false, Variables.MODNAME + " Config");
    }
    
    @SuppressWarnings ({"rawtypes"})
    private static List<IConfigElement> getElements()
    {
        List<IConfigElement> list = new ArrayList<IConfigElement>(ConfigHandler.categories.length);
        for(String category: ConfigHandler.categories)
            list.add(new ConfigElement(ConfigHandler.INSTANCE.config.getCategory(category)));
        return list;
    }
}