/**
 * 
 */
package darkknight.jewelrycraft.tileentity;

import org.lwjgl.opengl.GL11;
import darkknight.jewelrycraft.config.ConfigHandler;
import net.minecraft.tileentity.TileEntity;

/**
 * @author Sorin
 */
public class TileEntityCrystal extends TileEntity
{
    public int shine = 120;
    boolean descent = false;
    int timer = 0;
    
    @Override
    public void updateEntity()
    {
        if (ConfigHandler.CRYSTAL_GLOW){
            timer++;
            if (timer > 20){
                if (shine < 230 && !descent){
                    shine += 2;
                    if (shine >= 230) descent = true;
                }else if (shine > 100 && descent){
                    shine -= 2;
                    if (shine <= 100) descent = false;
                }
                this.worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
                timer = 0;
            }
        }
        if(ConfigHandler.CRYSTAL_PARTICLES) 
        	worldObj.spawnParticle("instantSpell", xCoord + worldObj.rand.nextFloat(), yCoord + worldObj.rand.nextFloat(), zCoord + worldObj.rand.nextFloat(), 0.0D, -1.0D, 0.0D);
    }
    
    public boolean canUpdate()
    {
        return ConfigHandler.CRYSTAL_GLOW || ConfigHandler.CRYSTAL_PARTICLES;
    }

}
