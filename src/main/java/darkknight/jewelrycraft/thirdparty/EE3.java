package darkknight.jewelrycraft.thirdparty;

import java.util.Arrays;
import java.util.List;
import net.minecraft.item.ItemStack;
import com.pahimar.ee3.api.EnergyValueRegistryProxy;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;

public class EE3 implements IThirdParty
{
    @Override
    public void preInit()
    {
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(BlockList.shadowOre), 4096);
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(ItemList.shadowIngot), 4096);
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(ItemList.clayMolds), 128);
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(ItemList.molds), 128);}
    
    @Override
    public void init()
    {}
    
    @Override
    public void postInit()
    {
    }
    
    @Override
    public void clientSide()
    {}
    
    @Override
    public void clientInit()
    {}
}