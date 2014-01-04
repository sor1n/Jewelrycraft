package darkknight.jewelrycraft.client;

import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.VillagerRegistry;
import darkknight.jewelrycraft.CommonProxy;
import darkknight.jewelrycraft.renders.TileEntityDisplayerRender;
import darkknight.jewelrycraft.renders.TileEntityJewelrsCraftingTableRender;
import darkknight.jewelrycraft.renders.TileEntityMolderRender;
import darkknight.jewelrycraft.renders.TileEntitySmelterRender;
import darkknight.jewelrycraft.tileentity.TileEntityDisplayer;
import darkknight.jewelrycraft.tileentity.TileEntityJewelrsCraftingTable;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
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
        VillagerRegistry.instance().registerVillagerSkin(3000, new ResourceLocation("jewelrycraft", "textures/entities/jeweler.png"));
    }
}
