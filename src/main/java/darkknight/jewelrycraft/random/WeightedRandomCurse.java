package darkknight.jewelrycraft.random;

import java.util.Random;
import darkknight.jewelrycraft.api.Curse;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class WeightedRandomCurse extends WeightedRandom.Item
{
    private final Curse curse;

    public WeightedRandomCurse(Curse curse, int weight)
    {
        super(weight);
        this.curse = curse;
    }

    public Curse getCurse(Random random)
    {
        return curse;
    }
}