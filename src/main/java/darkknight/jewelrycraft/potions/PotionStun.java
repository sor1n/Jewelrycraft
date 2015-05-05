/**
 * 
 */
package darkknight.jewelrycraft.potions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

/**
 * @author Sorin
 */
public class PotionStun extends Potion
{
    protected PotionStun(int id, boolean isBad, int color)
    {
        super(id, isBad, color);
        this.setPotionName(Variables.MODID + ".potion.stun");
    }
    
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) { }
}
