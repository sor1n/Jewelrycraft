package darkknight.jewelrycraft.thirdparty;

import darkknight.jewelrycraft.JewelrycraftMod;
import fiskfille.alpaca.common.potion.AlpacaPotions;

/**
 * @author Sorin
 */
public class Alpaca implements IThirdParty
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
    	JewelrycraftMod.alpacaPotion = AlpacaPotions.potionAlpaca;
    }
    
    @Override
    public void clientSide()
    {}
    
    @Override
    public void clientInit()
    {}
}
