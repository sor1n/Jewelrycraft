package darkknight.jewelrycraft.potions;

import java.util.ArrayList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class PotionBase extends Potion {
	private static ArrayList<PotionBase> potions = new ArrayList<PotionBase>();

	public PotionBase(int id, boolean isBad, int color) {
		super(id, isBad, color);
		potions.add(this);
	}

	public static ArrayList<PotionBase> getPotionList() {
		return potions;
	}

	public void action(EntityLivingBase entity) {}
}
