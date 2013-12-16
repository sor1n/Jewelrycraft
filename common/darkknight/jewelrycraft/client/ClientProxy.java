package darkknight.jewelrycraft.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import darkknight.jewelrycraft.CommonProxy;
import darkknight.jewelrycraft.renders.TileEntityMolderRender;
import darkknight.jewelrycraft.renders.TileEntitySmelterRender;
import darkknight.jewelrycraft.tileentity.TileEntityMolder;
import darkknight.jewelrycraft.tileentity.TileEntitySmelter;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySmelter.class, new TileEntitySmelterRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMolder.class, new TileEntityMolderRender());
    }
}
