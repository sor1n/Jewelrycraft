package darkknight.jewelrycraft.curses;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import darkknight.jewelrycraft.achievements.AchievementsList;
import darkknight.jewelrycraft.api.Curse;
import darkknight.jewelrycraft.config.ConfigHandler;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import darkknight.jewelrycraft.util.PlayerUtils;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class CursePentagram extends Curse {
	float rot = 0F;

	public CursePentagram(String name, int txtID, String pack) {
		super(name, txtID, pack);
	}

	@Override
	public void action(World world, EntityPlayer player) {
		super.action(world, player);
		if (!world.isRemote) {
			NBTTagCompound playerInfo = PlayerUtils.getModPlayerPersistTag(player, Variables.MODID);
			for (Object entity : world.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox(player.boundingBox.minX - 0.5F, player.boundingBox.minY, player.boundingBox.minZ - 0.5F, player.boundingBox.maxX + 0.5F, player.boundingBox.maxY, player.boundingBox.maxZ + 0.5F))) {
				if (entity instanceof EntityLivingBase) {
					NBTTagCompound target = ((EntityLivingBase) entity).getEntityData();
					if (target.getInteger("stolenHealth") < (JewelrycraftUtil.AchievemtUnlocked(player, AchievementsList.pentagram) ? 3 : 2) && rand.nextInt(40) == 0) {
						((EntityLivingBase) entity).getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(((EntityLivingBase) entity).getMaxHealth() - 2f);
						target.setInteger("stolenHealth", target.hasKey("stolenHealth") ? target.getInteger("stolenHealth") + 1 : 1);
						playerInfo.setInteger("heartsStolen", playerInfo.hasKey("heartsStolen") ? playerInfo.getInteger("heartsStolen") + 1 : 1);
						if (player.shouldHeal()) player.heal(2F);
						else player.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(player.getMaxHealth() + 2f);
					}
				}
			}
			if (!playerInfo.getBoolean(AchievementsList.pentagram.statId)) {
				if (ticksActive > 24000 && playerInfo.getInteger("heartsStolen") <= 0) player.addStat(AchievementsList.pentagram, 1);
				else {
					player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + StatCollector.translateToLocal("challenge.failed") + " " + EnumChatFormatting.GOLD + StatCollector.translateToLocal(AchievementsList.pentagram.statId)));
					playerInfo.setBoolean(AchievementsList.pentagram.statId, true);
				}
			}
		}
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
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
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
