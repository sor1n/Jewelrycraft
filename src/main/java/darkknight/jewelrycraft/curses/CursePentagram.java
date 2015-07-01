package darkknight.jewelrycraft.curses;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.damage.DamageSourceList;
import darkknight.jewelrycraft.util.Variables;

public class CursePentagram extends Curse {
	float	rot	= 0F;

	public CursePentagram(String name, int txtID, String pack) {
		super(name, txtID, pack);
	}

	@Override
	public void action(World world, EntityPlayer player) {
		if (!world.isRemote) {
			for (Object entity : world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.boundingBox.minX - 0.5F, player.boundingBox.minY, player.boundingBox.minZ - 0.5F, player.boundingBox.maxX + 0.5F, player.boundingBox.maxY, player.boundingBox.maxZ + 0.5F))) {
				if (entity instanceof EntityLivingBase && rand.nextInt(40) == 0) {
					((EntityLivingBase) entity).attackEntityFrom(DamageSourceList.shadows, 2f);
					if (player.shouldHeal()) player.heal(2F);
					else player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(player.getMaxHealth() + 2f);
				}
			}
		}
	}

	@Override
	public void attackedByPlayerAction(World world, EntityPlayer player, Entity target) {
	}

	@Override
	public void playerRender(EntityPlayer player, RenderPlayerEvent.Specials.Post event) {
		ResourceLocation PENTAGRAM_TEXTURE = new ResourceLocation(Variables.MODID, "textures/gui/" + getTexturePack() + ".png");
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_SRC_COLOR);
		Tessellator tessellator = Tessellator.instance;
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		texturemanager.bindTexture(PENTAGRAM_TEXTURE);
		GL11.glRotatef(rot, 0F, 1F, 0F);
		GL11.glTranslatef(-0.8F, (player.isSneaking() ? 0.1625F : 0F) + 1.5F, -0.8F);
		GL11.glRotatef(90F, 1F, 0F, 0F);
		GL11.glScalef(0.05F, 0.05F, 0.05F);
		rot += 3F;
		if (rot > 360F) rot = 0F;
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		int x = 0;
		int y = 0;
		int u = 32 * 7;
		int v = 0;
		int width = 32;
		int height = 32;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) 0, (double) ((float) (u + 0) * f), (double) ((float) (v + height) * f1));
		tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) 0, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
		tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) 0, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) 0, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
		tessellator.draw();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	@SideOnly(Side.CLIENT)
	public void playerHandRender(EntityPlayer player, RenderHandEvent event) {
		if (mc.gameSettings.thirdPersonView == 0) {
			ResourceLocation PENTAGRAM_TEXTURE = new ResourceLocation(Variables.MODID, "textures/gui/" + getTexturePack() + ".png");
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_SRC_COLOR);
			Tessellator tessellator = Tessellator.instance;
			TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
			texturemanager.bindTexture(PENTAGRAM_TEXTURE);
			GL11.glRotatef(rot, 0F, 1F, 0F);
			GL11.glTranslatef(-0.8F, (player.isSneaking() ? 0.1625F : 0F) - 1.6F, -0.8F);
			GL11.glRotatef(90F, 1F, 0F, 0F);
			GL11.glScalef(0.05F, 0.05F, 0.05F);
			rot += 3F;
			if (rot > 360F) rot = 0F;
			float f = 0.00390625F;
			float f1 = 0.00390625F;
			int x = 0;
			int y = 0;
			int u = 32 * 7;
			int v = 0;
			int width = 32;
			int height = 32;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV((double) (x + 0), (double) (y + height), (double) 0, (double) ((float) (u + 0) * f), (double) ((float) (v + height) * f1));
			tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) 0, (double) ((float) (u + width) * f), (double) ((float) (v + height) * f1));
			tessellator.addVertexWithUV((double) (x + width), (double) (y + 0), (double) 0, (double) ((float) (u + width) * f), (double) ((float) (v + 0) * f1));
			tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), (double) 0, (double) ((float) (u + 0) * f), (double) ((float) (v + 0) * f1));
			tessellator.draw();
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
	}

	public String getDescription() {
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".pentagram.description");
	}

	@Override
	public String getDisplayName() {
		return StatCollector.translateToLocal("curse." + Variables.MODID + ".pentagram");
	}

	@Override
	public boolean canCurseBeActivated(World world) {
		return ConfigHandler.CURSE_PENTAGRAM;
	}
}
