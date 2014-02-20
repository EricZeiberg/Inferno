package me.masterejay.inferno.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import me.masterejay.inferno.countdowns.CountdownMethods;
import me.masterejay.inferno.countdowns.MatchStartCountdown;
import me.masterejay.inferno.match.MatchHandler;
import me.masterejay.inferno.match.MatchState;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author MasterEjay
 */
public class AdminCommands{

	@Command(aliases = { "start", "s" }, desc = "Starts the game", usage = "", min = 0, max = 1)
	@CommandPermissions("inferno.game.start")
	public static void start(final CommandContext args, CommandSender sender) throws CommandException{
	  if (args.argsLength() == 0) {
		  MatchHandler.setState(MatchState.STARTING);
		  CountdownMethods.start(new MatchStartCountdown(), 15);
	  }
	  else if (args.argsLength() == 1){
		  CountdownMethods.start(new MatchStartCountdown(), args.getInteger(0));
	  }
	}

	@Command(aliases = { "cancel" }, desc = "Cancels any countdowns going on", usage = "", min = 0, max = 0)
	@CommandPermissions("inferno.admin.cancel")
	public static void cancel(final CommandContext args, CommandSender sender) throws CommandException{
		CountdownMethods.cancelAll();
		sender.sendMessage(ChatColor.GREEN + "All countdowns cancelled");
	}

	@Command(aliases = { "end" }, desc = "Ends the game", usage = "", min = 0, max = 0)
	@CommandPermissions("inferno.admin.end")
	public static void end(final CommandContext args, CommandSender sender) throws CommandException{
		if (!(MatchHandler.getState()== MatchState.PLAYING)) {
			throw new CommandException("No matches are playing");
		}
		MatchHandler.finish(null);
	}
}
