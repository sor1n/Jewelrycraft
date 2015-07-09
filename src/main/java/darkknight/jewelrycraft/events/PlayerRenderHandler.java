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
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import darkknight.jewelrycraft.JewelrycraftMod;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.item.render.BraceletRender;
import darkknight.jewelrycraft.item.render.EarringsRender;
import darkknight.jewelrycraft.item.render.NecklaceRender;
import darkknight.jewelrycraft.item.render.RingRender;
import darkknight.jewelrycraft.util.JewelryNBT;
import darkknight.jewelrycraft.util.JewelrycraftUtil;

public class PlayerRenderHandler {
	EarringsRender					earrings	= new EarringsRender();
	BraceletRender					bracelet	= new BraceletRender();
	NecklaceRender					necklace	= new NecklaceRender();
	RingRender						ring		= new RingRender();
	public static NBTTagCompound	playersInfo	= new NBTTagCompound();
	float							size		= 0.055F;

	@SubscribeEvent
	public void renderScreen(RenderPlayerEvent.Specials.Post event) {
		GL11.glPushMatrix();
		ModelBiped main = event.renderer.modelBipedMain;
		ModelRenderer rightArm = event.renderer.modelBipedMain.bipedRightArm;
		ModelRenderer leftArm = event.renderer.modelBipedMain.bipedLeftArm;
		ModelRenderer head = event.renderer.modelBipedMain.bipedHead;
		ModelRenderer body = event.renderer.modelBipedMain.bipedBody;
		Iterator<EntityPlayer> players = event.entityPlayer.worldObj.playerEntities.iterator();
		float rotAngleX = 0F, rotAngleY = 0F, rotAngleZ = 0F;
		if (playersInfo != null) {
			while (players.hasNext()) {
				int[] gemColor = { -1, -1, -1, -1 };
				int[] ingotColor = { -1, -1, -1, -1 };
				int gem = -1;
				int ingot = -1;
				EntityPlayer player = players.next();
				NBTTagCompound playerInfo = (NBTTagCompound) playersInfo.getTag(player.getDisplayName());
				if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
					if (curse != null && curse.canCurseBeActivated(player.worldObj) && playerInfo != null && curse.getName() != null && playerInfo.getInteger(curse.getName()) > 0 && event.entityPlayer != null && player != null && event.entityPlayer.getDisplayName().equals(player.getDisplayName()) && playerInfo.getInteger("cursePoints") > 0) curse.playerRender(player, event);	
				int no = 0;
				ModelRenderer arm = rightArm;
				for (int i = 0; i <= 9; i++)
					if (playerInfo != null && event.entityPlayer != null && player != null && playerInfo.hasKey("ext" + i) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())) {
						gem = -1;
						ingot = -1;
						if (no > 4) arm = leftArm;
						float s = 0.055F;
						NBTTagCompound nbt = (NBTTagCompound) playerInfo.getTag("ext" + i);
						ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
						if (Loader.isModLoaded("alpaca") /*&& fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer)*/) arm = body;
						GL11.glPushMatrix();
						if (arm.rotateAngleX == 0.0F && arm.rotateAngleY == 0.0F && arm.rotateAngleZ == 0.0F) {
							if ((arm.rotationPointX + rotAngleX) != 0.0F || (arm.rotationPointY + rotAngleY) != 0.0F || (arm.rotationPointZ + rotAngleZ) != 0.0F) GL11.glTranslatef((arm.rotationPointX + rotAngleX) * s, (arm.rotationPointY + rotAngleY) * s, (arm.rotationPointZ + rotAngleZ) * s);
						} else if (!Loader.isModLoaded("alpaca") /*|| (Loader.isModLoaded("alpaca") && !fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer))*/) {
							GL11.glTranslatef((arm.rotationPointX + rotAngleX) * s, (arm.rotationPointY + rotAngleY) * s, (arm.rotationPointZ + rotAngleZ) * s);
							if (arm.rotateAngleZ != 0.0F) GL11.glRotatef(arm.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
							if (arm.rotateAngleY != 0.0F) GL11.glRotatef(arm.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
							if (arm.rotateAngleX != 0.0F) GL11.glRotatef(arm.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
						}
						if (JewelryNBT.ingot(item) != null) ingot = JewelrycraftUtil.getColor(JewelryNBT.ingot(item));
						if (JewelryNBT.gem(item) != null) gem = JewelrycraftUtil.getColor(JewelryNBT.gem(item));
						float scale = 0.1f;
						if (Loader.isModLoaded("alpaca") /*&& fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer)*/) {
							if (no <= 4) {
								GL11.glTranslatef(0.35F, -4.35F, -2.8F + 0.15F * no);
								GL11.glRotatef(90f, 0F, 1F, 0F);
							} else {
								GL11.glTranslatef(-0.35F, -4.35F, 1.48F + 0.15F * no);
								GL11.glRotatef(-90f, 0F, 1F, 0F);
							}
							scale = 0.3F;
						} else {
							if (no <= 4) GL11.glTranslatef(0.64F + 0.05F * no, -1.15F, 0.07F);
							else GL11.glTranslatef(0.59F + 0.05F * no, -1.15F, 0.07F);
						}
						GL11.glScalef(scale, scale + scale / 2, scale);
						ring.doRender(event.entityPlayer, 0F, 0F, (float) ingot, (float) gem, 0F);
						GL11.glPopMatrix();
						no++;
					}
				for (int i = 10; i <= 13; i++)
					if (playerInfo != null && event.entityPlayer != null && player != null && playerInfo.hasKey("ext" + i) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())) {
						NBTTagCompound nbt = (NBTTagCompound) playerInfo.getTag("ext" + i);
						ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
						if (JewelryNBT.ingot(item) != null) ingotColor[i - 10] = JewelrycraftUtil.getColor(JewelryNBT.ingot(item));
						if (JewelryNBT.gem(item) != null) gemColor[i - 10] = JewelrycraftUtil.getColor(JewelryNBT.gem(item));
					}
				if (playerInfo != null && event.entityPlayer != null && player != null && (playerInfo.hasKey("ext10") || playerInfo.hasKey("ext11")) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())) {
					GL11.glPushMatrix();
					GL11.glColor4f(1, 1, 1, 1);
					if (Loader.isModLoaded("alpaca") /*&& fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer)*/) {
						rightArm = body;
						GL11.glTranslatef(0.0F, 0.05F, 0.0F);
						GL11.glRotatef(-30F, 0.0F, 1.0F, 0.0F);
					}
					if (rightArm.rotateAngleX == 0.0F && rightArm.rotateAngleY == 0.0F && rightArm.rotateAngleZ == 0.0F) {
						if ((rightArm.rotationPointX + rotAngleX) != 0.0F || (rightArm.rotationPointY + rotAngleY) != 0.0F || (rightArm.rotationPointZ + rotAngleZ) != 0.0F) GL11.glTranslatef((rightArm.rotationPointX + rotAngleX) * size, (rightArm.rotationPointY + rotAngleY) * size, (rightArm.rotationPointZ + rotAngleZ) * size);
					} else if (!Loader.isModLoaded("alpaca") /*|| (Loader.isModLoaded("alpaca") && fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer))*/) {
						GL11.glTranslatef((rightArm.rotationPointX + rotAngleX) * size, (rightArm.rotationPointY + rotAngleY) * size, (rightArm.rotationPointZ + rotAngleZ) * size);
						if (rightArm.rotateAngleZ != 0.0F) GL11.glRotatef(rightArm.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
						if (rightArm.rotateAngleY != 0.0F) GL11.glRotatef(rightArm.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
						if (rightArm.rotateAngleX != 0.0F) GL11.glRotatef(rightArm.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
					}
					GL11.glScalef(0.05f, 0.03f, 0.05f);
					bracelet.doRender(event.entityPlayer, (float) ingotColor[0], (float) gemColor[0], (float) ingotColor[1], (float) gemColor[1], 0.0F);
					GL11.glPopMatrix();
				}
				if (playerInfo != null && event.entityPlayer != null && player != null && (playerInfo.hasKey("ext12") || playerInfo.hasKey("ext13")) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())) {
					GL11.glPushMatrix();
					GL11.glColor4f(1, 1, 1, 1);
					if (Loader.isModLoaded("alpaca") /*&& fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer)*/) {
						leftArm = body;
						GL11.glTranslatef(-0.1F, 0.0F, 0.1F);
						GL11.glRotatef(-140F, 0.0F, 1.0F, 0.0F);
					}
					if (leftArm.rotateAngleX == 0.0F && leftArm.rotateAngleY == 0.0F && leftArm.rotateAngleZ == 0.0F) {
						if ((leftArm.rotationPointX + rotAngleX) != 0.0F || (leftArm.rotationPointY + rotAngleY) != 0.0F || (leftArm.rotationPointZ + rotAngleZ) != 0.0F) GL11.glTranslatef((leftArm.rotationPointX + rotAngleX) * size, (leftArm.rotationPointY + rotAngleY) * size, (leftArm.rotationPointZ + rotAngleZ) * size);
					} else if (!Loader.isModLoaded("alpaca") /*|| (Loader.isModLoaded("alpaca") && !fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer))*/) {
						GL11.glTranslatef((leftArm.rotationPointX + rotAngleX) * size + 0.2F, (leftArm.rotationPointY + rotAngleY) * size, (leftArm.rotationPointZ + rotAngleZ) * size);
						if (leftArm.rotateAngleZ != 0.0F) GL11.glRotatef(leftArm.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
						if (leftArm.rotateAngleY != 0.0F) GL11.glRotatef(leftArm.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
						if (leftArm.rotateAngleX != 0.0F) GL11.glRotatef(leftArm.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
					}
					GL11.glScalef(0.05f, 0.03f, 0.05f);
					bracelet.doRender(event.entityPlayer, (float) ingotColor[2], (float) gemColor[2], (float) ingotColor[3], (float) gemColor[3], 0F);
					GL11.glPopMatrix();
				}
				no = 0;
				for (int i = 14; i <= 16; i++)
					if (playerInfo != null && event.entityPlayer != null && player != null && playerInfo.hasKey("ext" + i) && event.entityPlayer.getDisplayName().equals(player.getDisplayName())) {
						gem = -1;
						ingot = -1;
						NBTTagCompound nbt = (NBTTagCompound) playerInfo.getTag("ext" + i);
						ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
						GL11.glPushMatrix();
						if (body.rotateAngleX == 0.0F && body.rotateAngleY == 0.0F && body.rotateAngleZ == 0.0F) {
							if (body.rotationPointX != 0.0F || body.rotationPointY != 0.0F || body.rotationPointZ != 0.0F) GL11.glTranslatef(body.rotationPointX * size, body.rotationPointY * size, body.rotationPointZ * size);
						} else if (!Loader.isModLoaded("alpaca") /*|| (Loader.isModLoaded("alpaca") && !fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer))*/) {
							GL11.glTranslatef(body.rotationPointX * size, body.rotationPointY * size, body.rotationPointZ * size);
							if (body.rotateAngleZ != 0.0F) GL11.glRotatef(body.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
							if (body.rotateAngleY != 0.0F) GL11.glRotatef(body.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
							if (body.rotateAngleX != 0.0F) GL11.glRotatef(body.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
						}
						if (Loader.isModLoaded("alpaca") /*&& fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer)*/) GL11.glTranslatef(0.0F, 0.55F, -0.28F);
						GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
						if (JewelryNBT.ingot(item) != null) ingot = JewelrycraftUtil.getColor(JewelryNBT.ingot(item));
						if (JewelryNBT.gem(item) != null) gem = JewelrycraftUtil.getColor(JewelryNBT.gem(item));
						if (no > 0) {
							GL11.glRotatef(no == 1 ? 25f : -25f, 0F, 0f, 1f);
							GL11.glRotatef(no == 1 ? -5f : -10f, 1F, 0f, 0f);
						}
						necklace.doRender(event.entityPlayer, 0F, 0F, (float) ingot, (float) gem, 0F);
						GL11.glPopMatrix();
						no++;
					}
				if (playerInfo != null && event.entityPlayer != null && player != null && playerInfo.hasKey("ext17") && event.entityPlayer.getDisplayName().equals(player.getDisplayName())) {
					gem = -1;
					ingot = -1;
					NBTTagCompound nbt = (NBTTagCompound) playerInfo.getTag("ext17");
					ItemStack item = ItemStack.loadItemStackFromNBT(nbt);
					GL11.glPushMatrix();
					float s = 0.0625F;
					if (Loader.isModLoaded("alpaca") /*&& fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer)*/) head = body;
					if (head.rotateAngleX == 0.0F && head.rotateAngleY == 0.0F && head.rotateAngleZ == 0.0F) {
						if (head.rotationPointX != 0.0F || head.rotationPointY != 0.0F || head.rotationPointZ != 0.0F) GL11.glTranslatef(head.rotationPointX * size, head.rotationPointY * size, head.rotationPointZ * size);
					} else if (!Loader.isModLoaded("alpaca") /*|| (Loader.isModLoaded("alpaca") && !fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer))*/) {
						GL11.glTranslatef(head.rotationPointX * size, head.rotationPointY * size, head.rotationPointZ * size);
						if (head.rotateAngleZ != 0.0F) GL11.glRotatef(head.rotateAngleZ * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
						if (head.rotateAngleY != 0.0F) GL11.glRotatef(head.rotateAngleY * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
						if (head.rotateAngleX != 0.0F) GL11.glRotatef(head.rotateAngleX * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
					}
					if (Loader.isModLoaded("alpaca") /*&& fiskfille.alpaca.AlpacaAPI.isAlpacaClient(event.entityPlayer)*/) GL11.glTranslatef(0.0F, 0.7F, -0.2F);
					GL11.glScalef(s, s, s);
					GL11.glTranslatef(0.0F, 1.0F, -2.0F);
					if (JewelryNBT.ingot(item) != null) ingot = JewelrycraftUtil.getColor(JewelryNBT.ingot(item));
					if (JewelryNBT.gem(item) != null) gem = JewelrycraftUtil.getColor(JewelryNBT.gem(item));
					earrings.doRender(event.entityPlayer, 0F, 0F, (float) ingot, (float) gem, 0F);
					GL11.glPopMatrix();
				}
			}
		}
		GL11.glPopMatrix();
	}

	float	rot	= 0F;

	@SubscribeEvent
	public void renderHand(RenderHandEvent event) {
		if (playersInfo != null) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			if (player != null) {
				NBTTagCompound playerInfo = (NBTTagCompound) playersInfo.getTag(player.getDisplayName());
				if (ConfigHandler.CURSES_ENABLED) for (Curse curse : Curse.getCurseList())
					if (curse.canCurseBeActivated(player.worldObj) && curse != null && playerInfo != null && playerInfo.hasKey(curse.getName()) && playerInfo.getInteger(curse.getName()) > 0 && playerInfo.hasKey("cursePoints") && playerInfo.getInteger("cursePoints") > 0) curse.playerHandRender(player, event);
			}
		}
	}
}
