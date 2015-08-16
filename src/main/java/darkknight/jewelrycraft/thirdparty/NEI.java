package darkknight.jewelrycraft.thirdparty;

import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.block.BlockList;
import darkknight.jewelrycraft.item.ItemList;
import net.minecraft.item.ItemStack;

public class NEI implements IThirdParty {
	@Override
	public void preInit() {}

	@Override
	public void init() {}

	@Override
	public void postInit() {
		for (int i = 0; i < Curse.getCurseList().size(); i++)
			codechicken.nei.api.API.hideItem(new ItemStack(ItemList.testItem, 1, i));
		codechicken.nei.api.API.hideItem(new ItemStack(ItemList.goldObj));
		codechicken.nei.api.API.hideItem(new ItemStack(BlockList.midasTouchBlock));
		codechicken.nei.api.API.hideItem(new ItemStack(ItemList.metal));
	}

	@Override
	public void clientSide() {}

	@Override
	public void clientInit() {}
}
