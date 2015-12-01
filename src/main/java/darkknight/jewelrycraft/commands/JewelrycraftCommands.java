/**
 * 
 */
package darkknight.jewelrycraft.commands;

/**
 * @author Sorin
 *
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import darkknight.jewelrycraft.util.JewelrycraftUtil;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

public class JewelrycraftCommands extends CommandBase
{
    private List aliases;
    
    public JewelrycraftCommands()
    {
        this.aliases = new ArrayList();
        this.aliases.add("jw");
        this.aliases.add("jewelry");
    }
    
    @Override
    public String getCommandName()
    {
        return "jewelrycraft";
    }
    
    @Override
    public String getCommandUsage(ICommandSender var1)
    {
        return "/jewelrycraft <addCursePoints:getCursePoints:setCursePoints> <user> [points]";
    }
    
    @Override
    public List getCommandAliases()
    {
        return aliases;
    }
    
    @Override
    public void processCommand(ICommandSender commandSender, String[] astring)
    {
        if (astring.length == 0 || astring[0].equals("help")) throw new WrongUsageException(getCommandUsage(commandSender));
        if (astring[0].equals("getCursePoints")){
            EntityPlayerMP entityplayermp = getPlayer(commandSender, astring[1]);
            commandSender.addChatMessage(new ChatComponentTranslation(Integer.toString(JewelrycraftUtil.getCursePoints(entityplayermp))));
        }else if (astring[0].equals("addCursePoints")){
            int points = CommandBase.parseIntWithMin(commandSender, astring[2], 0);
            EntityPlayerMP entityplayermp = getPlayer(commandSender, astring[1]);
            JewelrycraftUtil.addCursePoints(entityplayermp, points);
        }else if (astring[0].equals("setCursePoints")){
            int points = CommandBase.parseIntWithMin(commandSender, astring[2], 0);
            EntityPlayerMP entityplayermp = getPlayer(commandSender, astring[1]);
            JewelrycraftUtil.addCursePoints(entityplayermp, points - JewelrycraftUtil.getCursePoints(entityplayermp));
        }
    }
    
    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring)
    {
        final List<String> MATCHES = new LinkedList<String>();
        final String ARG_LC = astring[astring.length - 1].toLowerCase();
        if (astring.length == 1){
            if ("addCursePoints".toLowerCase().startsWith(ARG_LC)) MATCHES.add("addCursePoints");
            if ("getCursePoints".toLowerCase().startsWith(ARG_LC)) MATCHES.add("getCursePoints");
            if ("setCursePoints".toLowerCase().startsWith(ARG_LC)) MATCHES.add("setCursePoints");
        }else if (astring.length == 2){
            for(String un: MinecraftServer.getServer().getAllUsernames())
                if (un.toLowerCase().startsWith(ARG_LC)) MATCHES.add(un);
        }
        return MATCHES.isEmpty() ? null : MATCHES;
    }
}
