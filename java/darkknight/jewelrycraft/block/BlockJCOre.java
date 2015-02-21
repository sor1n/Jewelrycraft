package darkknight.jewelrycraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockJCOre extends Block
{
    
    /**
     * 
     */
    protected BlockJCOre()
    {
        super(Material.rock);
        setHarvestLevel("pickaxe", 3);
    }
}
