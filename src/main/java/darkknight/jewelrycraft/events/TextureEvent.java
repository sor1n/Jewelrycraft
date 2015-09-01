package darkknight.jewelrycraft.events;


import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.block.BlockList;
import net.minecraftforge.client.event.TextureStitchEvent;

public class TextureEvent
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void textureFix(TextureStitchEvent.Post e)
    {
        if (e.map.getTextureType() == 0)
            BlockList.moltenMetalFluid.setIcons(BlockList.moltenMetal.getBlockTextureFromSide(0));
    }
}