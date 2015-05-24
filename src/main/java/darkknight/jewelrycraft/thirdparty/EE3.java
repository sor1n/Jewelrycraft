package darkknight.jewelrycraft.thirdparty;

import net.minecraft.item.ItemStack;
import com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy;
import darkknight.jewelrycraft.block.BlockCrystal;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemClayMolds;
import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.item.ItemMolds;

public class EE3 implements IThirdParty
{
    @Override
    public void preInit()
    {}
    
    @Override
    public void init()
    {}
    
    @Override
    public void postInit()
    {
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(BlockList.shadowOre), 4096);
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(BlockList.shadowBlock), 36864);
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(ItemList.shadowIngot), 4096);
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(BlockList.jewelCraftingTable), 16640);
        EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(ItemList.guide), 288);
        for(int i = 0; i < ItemClayMolds.moldsItemNames.length; i++) EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(ItemList.clayMolds, 1, i), 128);
        for(int i = 0; i < ItemMolds.moldsItemNames.length; i++) EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(ItemList.molds, 1, i), 128);
        for(int i = 0; i < BlockCrystal.colors.length; i++) EnergyValueRegistryProxy.addPostAssignedEnergyValue(new ItemStack(BlockList.crystal, 1, i), 64);
    }
    
    @Override
    public void clientSide()
    {}
    
    @Override
    public void clientInit()
    {}
}