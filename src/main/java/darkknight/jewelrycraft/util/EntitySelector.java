package darkknight.jewelrycraft.util;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;

/**
 * @author Onyx
 *
 */
public class EntitySelector implements IEntitySelector
{
    public static IEntitySelector selectMonsters = new IEntitySelector(){

        @Override
        public boolean isEntityApplicable(Entity entity)
        {
            return entity instanceof EntityMob || entity instanceof IMob;
        }
    };

    @Override
    public boolean isEntityApplicable(Entity p_82704_1_)
    {
        return false;
    }
}
