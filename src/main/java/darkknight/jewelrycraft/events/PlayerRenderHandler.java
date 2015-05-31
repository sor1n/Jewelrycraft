package darkknight.jewelrycraft.events;

import java.nio.FloatBuffer;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.render.BraceletRender;
import darkknight.jewelrycraft.item.render.EarringsRender;
import darkknight.jewelrycraft.item.render.NecklaceRender;
import darkknight.jewelrycraft.item.render.RingRender;
import darkknight.jewelrycraft.util.JewelryNBT;

public class PlayerRenderHandler
{
    EarringsRender earrings = new EarringsRender();
    BraceletRender bracelet = new BraceletRender();
    NecklaceRender necklace = new NecklaceRender();
    RingRender ring = new RingRender();
    public static NBTTagCompound playersInfo = new NBTTagCompound();
    float size = 0.055F;
    
    @SubscribeEvent
    public void renderScreen(RenderPlayerEvent.Specials.Post event)
    {
        GL11.glPushMatrix();
        ModelBiped main = event.renderer.modelBipedMain;
        ModelRenderer rightArm = event.renderer.modelBipedMain.bipedRightArm;
        ModelRenderer leftArm = event.renderer.modelBipedMain.bipedLeftArm;
        ModelRenderer head = event.renderer.modelBipedMain.bipedHead;
        ModelRenderer body = event.renderer.modelBipedMain.bipedBody;
        Iterator<EntityPlayer> players = event.entityPlayer.worldObj.playerEntities.iterator();
        if (playersInfo != null){
            while (players.hasNext()){
                int[] gemColor = {-1, -1, -1, -1};
                int[] ingotColor = {-1, -1, -1, -1};
                int gem = -1;
                int ingot = -1;
                EntityPlayer player = players.next();
                NBTTagCompound playerInfo = (NBTTagCompound)playersInfo.getTag(player.getDisplayName());
                if (ConfigHandler.CURSES_ENABLED) for(Curse curse: Curse.getCurseList())
                    if (curse.canCurseBeActivated(player.worldObj) && playerInfo.getInteger(curse.getName()) > 0 && event.entityPlayer.getDisplayName().equals(player.getDisplayName()) && playerInfo.getInteger("cursePoints") > 0) curse.playerRender(player, event);
                int no = 0;
                ModelRenderer arm = rightArm;
                if (player.inventory.getCurrentItem() != null && Block.getBlockFromItem(player.inventory.getCurrentItem().getItem()) instanceof BlockAir){
                    GL11.glDisable(GL11.GL_LIGHT1);
                    FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(4);
                    Vec3 lightPos = Vec3.createVectorHelper(0.0D, 2.0D, 1.0D).normalize();
                    colorBuffer.clear();
                    colorBuffer.put((float)lightPos.xCoord).put((float)lightPos.yCoord).put((float)lightPos.zCoord).put(-1F);
                    colorBuffer.flip();
                    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, colorBuffer);
                    colorBuffer.clear();
                    colorBuffer.put(0.03f).put(0.03f).put(0.03f).put(1.0F);
                    colorBuffer.flip();
                    GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, colorBuffer);
                    // GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F)); GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, setColorBuffer(var2, var2, var2, 1.0F));
                }
                for(int i = 0; i <= 9; i++)
                    if (playerInfo.hasKey("ext" + i) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                        gem = -1;
                        ingot = -1;
                        if (no > 4) arm = leftArm;
                        NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                        ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                        GL11.glPushMatrix();
                        if (arm.rotateAngleX == 0.0F && arm.rotateAngleY == 0.0F && arm.rotateAngleZ == 0.0F){
                            if (arm.rotationPointX != 0.0F || arm.rotationPointY != 0.0F || arm.rotationPointZ != 0.0F) GL11.glTranslatef(arm.rotationPointX * size, arm.rotationPointY * size, arm.rotationPointZ * size);
                        }else{
                            GL11.glTranslatef(arm.rotationPointX * size, arm.rotationPointY * size, arm.rotationPointZ * size);
                            if (arm.rotateAngleZ != 0.0F) GL11.glRotatef(arm.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                            if (arm.rotateAngleY != 0.0F) GL11.glRotatef(arm.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                            if (arm.rotateAngleX != 0.0F) GL11.glRotatef(arm.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                        }
                        if (JewelryNBT.ingot(item) != null) ingot = JewelryNBT.ingotColor(item);
                        if (JewelryNBT.gem(item) != null) gem = JewelryNBT.gemColor(item);
                        if (no <= 4) GL11.glTranslatef(0.64F + 0.05F * no, -1.15F, 0.07F);
                        else GL11.glTranslatef(0.59F + 0.05F * no, -1.15F, 0.07F);
                        GL11.glScalef(0.1f, 0.15f, 0.1f);
                        ring.doRender(event.entityPlayer, 0F, 0F, (float)ingot, (float)gem, 0F);
                        GL11.glPopMatrix();
                        no++;
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
                no = 0;
                for(int i = 14; i <= 16; i++)
                    if (playerInfo.hasKey("ext" + i) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                        gem = -1;
                        ingot = -1;
                        NBTTagCompound nbt = (NBTTagCompound)playerInfo.getTag("ext" + i);
                        ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
                        GL11.glPushMatrix();
                        if (body.rotateAngleX == 0.0F && body.rotateAngleY == 0.0F && body.rotateAngleZ == 0.0F){
                            if (body.rotationPointX != 0.0F || body.rotationPointY != 0.0F || body.rotationPointZ != 0.0F) GL11.glTranslatef(body.rotationPointX * size, body.rotationPointY * size, body.rotationPointZ * size);
                        }else{
                            GL11.glTranslatef(body.rotationPointX * size, body.rotationPointY * size, body.rotationPointZ * size);
                            if (body.rotateAngleZ != 0.0F) GL11.glRotatef(body.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                            if (body.rotateAngleY != 0.0F) GL11.glRotatef(body.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                            if (body.rotateAngleX != 0.0F) GL11.glRotatef(body.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                        }
                        GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
                        if (JewelryNBT.gem(item) != null) gem = JewelryNBT.gemColor(item);
                        if (JewelryNBT.ingot(item) != null) ingot = JewelryNBT.ingotColor(item);
                        if (no > 0){
                            GL11.glRotatef(no == 1 ? 25f : -25f, 0F, 0f, 1f);
                            GL11.glRotatef(no == 1 ? -5f : -10f, 1F, 0f, 0f);
                        }
                        necklace.doRender(event.entityPlayer, 0F, 0F, (float)ingot, (float)gem, 0F);
                        GL11.glPopMatrix();
                        no++;
                    }
                if (playerInfo.hasKey("ext17") && event.entityPlayer.getDisplayName().equals(player.getDisplayName())){
                    gem = -1;
                    ingot = -1;
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
                    if (JewelryNBT.gem(item) != null) gem = JewelryNBT.gemColor(item);
                    if (JewelryNBT.ingot(item) != null) ingot = JewelryNBT.ingotColor(item);
                    earrings.doRender(event.entityPlayer, 0F, 0F, (float)ingot, (float)gem, 0F);
                    GL11.glPopMatrix();
                }
            }
        }
        GL11.glPopMatrix();
    }
    
    float rot = 0F;
    
    @SubscribeEvent
    public void renderHand(RenderHandEvent event)
    {
        if (playersInfo != null){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (player != null){
                NBTTagCompound playerInfo = (NBTTagCompound)playersInfo.getTag(player.getDisplayName());
                if (ConfigHandler.CURSES_ENABLED) for(Curse curse: Curse.getCurseList())
                    if (curse.canCurseBeActivated(player.worldObj) && curse != null && playerInfo != null && playerInfo.hasKey(curse.getName()) && playerInfo.getInteger(curse.getName()) > 0 && playerInfo.hasKey("cursePoints") && playerInfo.getInteger("cursePoints") > 0) curse.playerHandRender(player, event);
            }
        }
    }
}
