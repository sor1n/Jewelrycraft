package darkknight.jewelrycraft.client;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import darkknight.jewelrycraft.CommonProxy;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.model.ModelDisplayer;
import darkknight.jewelrycraft.model.ModelHandPedestal;
import darkknight.jewelrycraft.model.ModelJewlersCraftingBench;
import darkknight.jewelrycraft.model.ModelMolder;
import darkknight.jewelrycraft.model.ModelShadowEye;
import darkknight.jewelrycraft.model.ModelShadowHand;
import darkknight.jewelrycraft.model.ModelSmelter;
import darkknight.jewelrycraft.renders.ItemRender;
import darkknight.jewelrycraft.renders.TileEntityDisplayerRender;
import darkknight.jewelrycraft.renders.TileEntityHandPedestalRender;
import darkknight.jewelrycraft.renders.TileEntityJewelrsCraftingTableRender;
import darkknight.jewelrycraft.renders.TileEntityMolderRender;
import darkknight.jewelrycraft.renders.TileEntityShadowEyeRender;
import darkknight.jewelrycraft.renders.TileEntityShadowHandRender;
import darkknight.jewelrycraft.renders.TileEntitySmelterRender;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityHandPedestal;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntityShadowEye;
import darkknight.jewelrycraft.tileentity.TileEntityShadowHand;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmelter.class, new TileEntitySmelterRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMolder.class, new TileEntityMolderRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityJewelrsCraftingTable.class, new TileEntityJewelrsCraftingTableRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDisplayer.class, new TileEntityDisplayerRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShadowEye.class, new TileEntityShadowEyeRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityHandPedestal.class, new TileEntityHandPedestalRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShadowHand.class, new TileEntityShadowHandRender());
        
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.displayer), new ItemRender(new TileEntityDisplayerRender(), new TileEntityDisplayer(), new ModelDisplayer()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.jewelCraftingTable), new ItemRender(new TileEntityJewelrsCraftingTableRender(), new TileEntityJewelrsCraftingTable(), new ModelJewlersCraftingBench()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.smelter), new ItemRender(new TileEntitySmelterRender(), new TileEntitySmelter(), new ModelSmelter()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.molder), new ItemRender(new TileEntityMolderRender(), new TileEntityMolder(), new ModelMolder()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.shadowEye), new ItemRender(new TileEntityShadowEyeRender(), new TileEntityShadowEye(), new ModelShadowEye()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.handPedestal), new ItemRender(new TileEntityHandPedestalRender(), new TileEntityHandPedestal(), new ModelHandPedestal()));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BlockList.shadowHand), new ItemRender(new TileEntityShadowHandRender(), new TileEntityShadowHand(), new ModelShadowHand()));
        
        VillagerRegistry.instance().registerVillagerSkin(3000, new ResourceLocation("jewelrycraft", "textures/entities/jeweler.png"));
    }
}
