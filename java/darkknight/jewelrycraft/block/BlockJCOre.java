package darkknight.jewelrycraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;

public class BlockJCOre extends BlockOre
{
    protected BlockJCOre()
    {
        super();
        setHarvestLevel("pickaxe", 3);
    }
}
