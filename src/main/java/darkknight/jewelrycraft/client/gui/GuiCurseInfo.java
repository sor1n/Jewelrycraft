package darkknight.jewelrycraft.client.gui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.events.PlayerRenderHandler;
import darkknight.jewelrycraft.events.ScreenHandler;
import darkknight.jewelrycraft.network.PacketRequestPlayerInfo;
import darkknight.jewelrycraft.network.PacketSendServerPlayersInfo;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;

public class GuiCurseInfo extends GuiScreen
{
    World world;
    EntityPlayer player;
    
    public GuiCurseInfo(Container container, World world, EntityPlayer player)
    {
        super();
        this.world = world;
        this.player = player;
    }
    
    public void drawScreen(int x, int y, float size)
    {
        super.drawScreen(x, y, size);
        this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
        int ind = 0;
        if (player != null){
            NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
            if (!playerInfo.hasNoTags()){
                for(Curse curse: Curse.getCurseList())
                    if (playerInfo.getInteger(curse.getName()) > 0){
                        int halfDescrSize = fontRendererObj.getStringWidth(curse.getDescription()) / 2;
                        mc.renderEngine.bindTexture(Variables.MISC_TEXTURE);
                        this.drawRect(0, 12 + ind * 34, this.width, 10 + (ind + 1) * 34, 0xff000000);
                        mc.renderEngine.bindTexture(new ResourceLocation(Variables.MODID, "textures/gui/" + curse.getTexturePack() + ".png"));
                        int tag = curse.getTextureID();
                        GL11.glColor3f(1F, 1F, 1F);
                        drawTexturedModalRect(this.width/2 - halfDescrSize - 35, 12 + ind * 34, tag % 8 * 32, tag / 8 * 32, 32, 32);
                        this.drawString(fontRendererObj, curse.getName().substring(curse.getName().indexOf(':') + 1), this.width/2 - halfDescrSize, 20 + ind * 34, 0xffff00);
                        this.drawCenteredString(fontRendererObj, curse.getDescription(), this.width/2, 30 + ind * 34, 0xffffff);
                        ind++;
                    }
            }
        }
    }
}