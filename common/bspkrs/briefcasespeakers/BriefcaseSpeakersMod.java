package bspkrs.briefcasespeakers;

import bspkrs.briefcasespeakers.lib.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION)
public class BriefcaseSpeakersMod
{
    @Instance(Reference.MODID)
    public static BriefcaseSpeakersMod instance;
    
    @Metadata(Reference.MODID)
    public Metadata                    metadata;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {   
        
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e)
    {   
        
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {   
        
    }
}
