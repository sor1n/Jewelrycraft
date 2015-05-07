package darkknight.jewelrycraft.thirdparty;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
/** 
 * @author MineMarteen from Pneumaticraft
 */
public interface IRegistryListener{
    public void onItemRegistry(Item item);

    public void onBlockRegistry(Block block);
}