package darkknight.jewelrycraft.events;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.curses.CurseList;
import darkknight.jewelrycraft.item.render.BraceletRender;
import darkknight.jewelrycraft.item.render.EarringsRender;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.Variables;

public class PlayerRenderHandler
{
    EarringsRender earrings = new EarringsRender();
    BraceletRender bracelet = new BraceletRender();
    public static NBTTagCompound playersInfo = new NBTTagCompound();
    float size = 0.055F;
    
    @SubscribeEvent
    public void renderScreen(RenderPlayerEvent.Specials.Post event)
    {
        ModelBiped main = event.renderer.modelBipedMain;
        ModelRenderer rightArm = event.renderer.modelBipedMain.bipedRightArm;
        ModelRenderer leftArm = event.renderer.modelBipedMain.bipedLeftArm;
        ModelRenderer head = event.renderer.modelBipedMain.bipedHead;
        Iterator<EntityPlayer> players = event.entityPlayer.worldObj.playerEntities.iterator();
        if (playersInfo != null){
            while (players.hasNext()){
                int[] gemColor = {-1, -1, -1, -1};
                int[] ingotColor = {-1, -1, -1, -1};
                int earringGem = -1;
                int earringIngot = -1;
                EntityPlayer player = players.next();
                NBTTagCompound playerInfo = (NBTTagCompound)playersInfo.getTag(player.getDisplayName());
                for(Curse curse: Curse.getCurseList())
                    if (playerInfo.getInteger(curse.getName()) > 0 && event.entityPlayer.getDisplayName().equals(player.getDisplayName()) && playerInfo.getInteger("cursePoints") > 0) curse.playerRender(player, event);
                if (playerInfo.hasKey("ext17") && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                    NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext17");
                    ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                    GL11.glPushMatrix();
                    if (head.rotateAngleX == 0.0F && head.rotateAngleY == 0.0F && head.rotateAngleZ == 0.0F){
                        if (head.rotationPointX != 0.0F || head.rotationPointY != 0.0F || head.rotationPointZ != 0.0F) GL11.glTranslatef(head.rotationPointX * size, head.rotationPointY * size, head.rotationPointZ * size);
                    }else{
                        GL11.glTranslatef(head.rotationPointX * size, head.rotationPointY * size, head.rotationPointZ * size);
                        if (head.rotateAngleZ != 0.0F) GL11.glRotatef(head.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                        if (head.rotateAngleY != 0.0F) GL11.glRotatef(head.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                        if (head.rotateAngleX != 0.0F) GL11.glRotatef(head.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                    }
                    GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
                    GL11.glTranslatef(0.0F, 1.0F, -2.0F);
                    if (JewelryNBT.gem(item) != null) earringGem = JewelryNBT.gemColor(item);
                    if (JewelryNBT.ingot(item) != null) earringIngot = JewelryNBT.ingotColor(item);
                    earrings.doRender(event.entityPlayer, 0F, 0F, (float)earringIngot, (float)earringGem, 0F);
                    GL11.glPopMatrix();
                }
                for(int i = 10; i <= 13; i++)
                    if (playerInfo.hasKey("ext" + i) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                        NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                        ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                        if (JewelryNBT.gem(item) != null) gemColor[i - 10] = JewelryNBT.gemColor(item);
                        if (JewelryNBT.ingot(item) != null) ingotColor[i - 10] = JewelryNBT.ingotColor(item);
                    }
                if ((playerInfo.hasKey("ext10") || playerInfo.hasKey("ext11")) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                    GL11.glPushMatrix();
                    GL11.glColor4f(1, 1, 1, 1);
                    if (rightArm.rotateAngleX == 0.0F && rightArm.rotateAngleY == 0.0F && rightArm.rotateAngleZ == 0.0F){
                        if (rightArm.rotationPointX != 0.0F || rightArm.rotationPointY != 0.0F || rightArm.rotationPointZ != 0.0F) GL11.glTranslatef(rightArm.rotationPointX * size, rightArm.rotationPointY * size, rightArm.rotationPointZ * size);
                    }else{
                        GL11.glTranslatef(rightArm.rotationPointX * size, rightArm.rotationPointY * size, rightArm.rotationPointZ * size);
                        if (rightArm.rotateAngleZ != 0.0F) GL11.glRotatef(rightArm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                        if (rightArm.rotateAngleY != 0.0F) GL11.glRotatef(rightArm.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                        if (rightArm.rotateAngleX != 0.0F) GL11.glRotatef(rightArm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                    }
                    GL11.glScalef(0.05f, 0.03f, 0.05f);
                    bracelet.doRender(event.entityPlayer, (float)ingotColor[0], (float)gemColor[0], (float)ingotColor[1], (float)gemColor[1], 0.0F);
                    GL11.glPopMatrix();
                }
                if ((playerInfo.hasKey("ext12") || playerInfo.hasKey("ext13")) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                    GL11.glPushMatrix();
                    GL11.glColor4f(1, 1, 1, 1);
                    if (leftArm.rotateAngleX == 0.0F && leftArm.rotateAngleY == 0.0F && leftArm.rotateAngleZ == 0.0F){
                        if (leftArm.rotationPointX != 0.0F || leftArm.rotationPointY != 0.0F || leftArm.rotationPointZ != 0.0F) GL11.glTranslatef(leftArm.rotationPointX * size, leftArm.rotationPointY * size, leftArm.rotationPointZ * size);
                    }else{
                        GL11.glTranslatef(leftArm.rotationPointX * size + 0.2F, leftArm.rotationPointY * size, leftArm.rotationPointZ * size);
                        if (leftArm.rotateAngleZ != 0.0F) GL11.glRotatef(leftArm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                        if (leftArm.rotateAngleY != 0.0F) GL11.glRotatef(leftArm.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                        if (leftArm.rotateAngleX != 0.0F) GL11.glRotatef(leftArm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                    }
                    GL11.glScalef(0.05f, 0.03f, 0.05f);
                    bracelet.doRender(event.entityPlayer, (float)ingotColor[2], (float)gemColor[2], (float)ingotColor[3], (float)gemColor[3], 0F);
                    GL11.glPopMatrix();
                }
            }
        }
    }
    
    float rot = 0F;
    
    @SubscribeEvent
    public void renderHand(RenderHandEvent event)
    {
        if (playersInfo != null){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            NBTTagCompound playerInfo = (NBTTagCompound)playersInfo.getTag(player.getDisplayName());
            for(Curse curse: Curse.getCurseList())
                if (playerInfo.getInteger(curse.getName()) > 0 && playerInfo.getInteger("cursePoints") > 0) curse.playerHandRender(player, event);
        }
    }
}