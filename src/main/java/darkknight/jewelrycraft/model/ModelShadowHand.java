package darkknight.jewelrycraft.model;

import net.minecraft.util.ResourceLocation;

/**
 * @author Paul Fulham (pau101)
 */
public class ModelShadowHand extends ModelHandPedestal
{
    
    /**
     * @param resetResourceLocation
     */
    public ModelShadowHand(ResourceLocation resetResourceLocation)
    {
        super(resetResourceLocation);
    }
    
    /**
     * 
     */
    @Override
    protected void initPedestalRenderers()
    {
        plinth = createModelRenderer(0, 0).addBox(-3, 6, -3, 6, 2, 6);
        base = createModelRenderer(0, 15).addBox(-2, 0, -2, 4, 6, 4);
        shaft = createModelRenderer(28, 0).addBox(-3, -4, -3, 6, 4, 6);
    }
}