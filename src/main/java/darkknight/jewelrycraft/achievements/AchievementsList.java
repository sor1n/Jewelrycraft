package darkknight.jewelrycraft.achievements;

import darkknight.jewelrycraft.item.ItemList;
import darkknight.jewelrycraft.util.Variables;
import net.minecraft.init.Items;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementsList 
{
	public static AchievementPage jewelrycraft;
	
	//Achievements
	public static final Achievement openGuide = new Achievement("achievement.jewelrycraft.openGuide", "jewelrycraft.openGuide", 0, 0, ItemList.guide, (Achievement)null).initIndependentStat();
	
	//Challenges
	public static final Achievement pentagram = new Achievement("achievement.jewelrycraft.pentagram", "jewelrycraft.pentagram", -3, -2, Items.ender_eye, (Achievement)null).initIndependentStat().setSpecial();
	
	public static void addAchievements()
	{
		openGuide.registerStat(); 
		pentagram.registerStat(); 
		jewelrycraft = new AchievementPage("Jewelrycraft 2", openGuide, pentagram);
		AchievementPage.registerAchievementPage(jewelrycraft);
	}
}
