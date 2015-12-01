package darkknight.jewelrycraft.block;

import net.minecraft.block.BlockOre;

public class BlockJCOre extends BlockOre
{
    protected BlockJCOre()
    {
        super();
        setHarvestLevel("pickaxe", 3);
    }
}
