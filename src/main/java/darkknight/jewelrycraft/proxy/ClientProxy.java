package darkknight.jewelrycraft.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.VillagerRegistry;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.block.render.BlockCrystalRenderer;
import darkknight.jewelrycraft.client.InventoryTabVanilla;
import darkknight.jewelrycraft.client.TabCurses;
import darkknight.jewelrycraft.client.TabJewelry;
import darkknight.jewelrycraft.client.TabRegistry;
import darkknight.jewelrycraft.entities.EntityHalfHeart;
import darkknight.jewelrycraft.entities.EntityHeart;
import darkknight.jewelrycraft.entities.renders.HeartRender;
import darkknight.jewelrycraft.events.KeyBindings;
import darkknight.jewelrycraft.events.PlayerRenderHandler;
import darkknight.jewelrycraft.events.ScreenHandler;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.item.render.ItemRender;
import darkknight.jewelrycraft.model.ModelDisplayer;
import darkknight.jewelrycraft.model.ModelHalfHeart;
import darkknight.jewelrycraft.model.ModelHandPedestal;
import darkknight.jewelrycraft.model.ModelHeart;
import darkknight.jewelrycraft.model.ModelJewlersCraftingBench;
import darkknight.jewelrycraft.model.ModelMolder;
import darkknight.jewelrycraft.model.ModelShadowEye;
import darkknight.jewelrycraft.model.ModelShadowHand;
import darkknight.jewelrycraft.model.ModelSmelter;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.tileentity.TileEntityMidasTouch;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntityShadowEye;
import darkknight.jewelrycraft.tileentity.TileEntityShadowHand;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;
import darkknight.jewelrycraft.tileentity.renders.TileEntityDisplayerRender;
import darkknight.jewelrycraft.tileentity.renders.TileEntityHandPedestalRender;
import darkknight.jewelrycraft.tileentity.renders.TileEntityJewelrsCraftingTableRender;
import darkknight.jewelrycraft.tileentity.renders.TileEntityMidasTouchRender;
import darkknight.jewelrycraft.tileentity.renders.TileEntityMolderRender;
import darkknight.jewelrycraft.tileentity.renders.TileEntityShadowEyeRender;
import darkknight.jewelrycraft.tileentity.renders.TileEntityShadowHandRender;
import darkknight.jewelrycraft.tileentity.renders.TileEntitySmelterRender;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.Variables;

public class ClientProxy extends CommonProxy
{
    public enum BlockRenderIDs {
        CRYSTAL;
        
        private final int ID;
        
        BlockRenderIDs() 
        {
            ID = RenderingRegistry.getNextAvailableRenderId();
        }

        public int id() 
        {
            return ID;
        }
    }
    
    @Override
    public void preInit()
    {
        TileEntityHandPedestalRender pedestalRender = new TileEntityHandPedestalRender(new ModelHandPedestal(Variables.PEDESTAL_TEXTURE), Variables.PEDESTAL_TEXTURE);
        TileEntityShadowHandRender shadowHandRender = new TileEntityShadowHandRender(new ModelShadowHand(Variables.SHADOW_HAND_TEXTURE), Variables.SHADOW_HAND_TEXTURE);

        JewelrycraftMod.logger.log(Level.INFO, "Binding Tileentities to their Special Rendered");
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmelter.class, new TileEntitySmelterRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMolder.class, new TileEntityMolderRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJewelrsCraftingTable.class, new TileEntityJewelrsCraftingTableRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayer.class, new TileEntityDisplayerRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShadowEye.class, new TileEntityShadowEyeRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMidasTouch.class, new TileEntityMidasTouchRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHandPedestal.class, pedestalRender);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShadowHand.class, shadowHandRender);
        
        JewelrycraftMod.logger.log(Level.INFO, "Registering Item Renderes");
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.displayer), new ItemRender(new TileEntityDisplayerRender(), new TileEntityDisplayer(), new ModelDisplayer()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.jewelCraftingTable), new ItemRender(new TileEntityJewelrsCraftingTableRender(), new TileEntityJewelrsCraftingTable(), new ModelJewlersCraftingBench()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.smelter), new ItemRender(new TileEntitySmelterRender(), new TileEntitySmelter(), new ModelSmelter()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.molder), new ItemRender(new TileEntityMolderRender(), new TileEntityMolder(), new ModelMolder()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.shadowEye), new ItemRender(new TileEntityShadowEyeRender(), new TileEntityShadowEye(), new ModelShadowEye()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.handPedestal), new ItemRender(pedestalRender, new TileEntityHandPedestal(), new ModelHandPedestal(Variables.PEDESTAL_TEXTURE)));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.shadowHand), new ItemRender(shadowHandRender, new TileEntityShadowHand(), new ModelShadowHand(Variables.SHADOW_HAND_TEXTURE)));
        MinecraftForgeClient.registerItemRenderer(ItemList.goldObj, new ItemRender());
        
        VillagerRegistry.instance().registerVillagerSkin(3000, Variables.VILLAGER_TEXTURE);

        JewelrycraftMod.logger.log(Level.INFO, "Registering Entity Renders");
        RenderingRegistry.registerEntityRenderingHandler(EntityHeart.class, new HeartRender(new ModelHeart(), 0.25F));
        RenderingRegistry.registerEntityRenderingHandler(EntityHalfHeart.class, new HeartRender(new ModelHalfHeart(), 0.25F));
        
        RenderingRegistry.registerBlockHandler(new BlockCrystalRenderer());

        TabRegistry.registerTab(new InventoryTabVanilla());
        TabRegistry.registerTab(new TabJewelry());
        TabRegistry.registerTab(new TabCurses());
        MinecraftForge.EVENT_BUS.register(new TabRegistry());
        MinecraftForge.EVENT_BUS.register(new PlayerRenderHandler());
        MinecraftForge.EVENT_BUS.register(new ScreenHandler(Minecraft.getMinecraft()));
    }
    
    @Override
    public void init()
    {
        FMLCommonHandler.instance().bus().register(new KeyBindings());
    }
    
    @Override
    public void postInit()
    {   
    	JewelrycraftMod.logger.log(Level.INFO, "Generating colors for each item");
    	JewelrycraftUtil.generateColors();
    }
}
