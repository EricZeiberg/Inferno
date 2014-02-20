package me.masterejay.inferno.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import me.masterejay.inferno.Inferno;
import me.masterejay.inferno.match.MatchHandler;
import me.masterejay.inferno.match.MatchState;
import me.masterejay.inferno.teams.TeamHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * @author MasterEjay
 */
public class JoinCommand {

    @Command(aliases = { "join", "j", "p" }, desc = "Joins the game", usage = "[team]", min = 0, max = 1)
    public static void join(final CommandContext args, CommandSender sender) throws CommandException {
	   if (TeamHandler.getRedTeam().getRedTeam().contains((Player) sender) || TeamHandler.getBlueTeam().isPlayerOnBlue((Player) sender)){
		   throw new CommandException("You are already on a team!");
	   }
      if (sender instanceof ConsoleCommandSender){
          throw new CommandException("Consoles can't use this command");
      }
	  else if (MatchHandler.getState() == MatchState.FINISHED){
	   throw new CommandException("The match is over! Please wait for a new one to start up!");
      }
      if (args.argsLength() == 1){
          if (sender.hasPermission("inferno.team.join")){
              if (args.getString(0).equalsIgnoreCase("b")){
                  TeamHandler.getObservers().removeObserver((Player) sender);
                  TeamHandler.getBlueTeam().addBluePlayer((Player) sender);
              }
              else if (args.getString(0).equalsIgnoreCase("r")){
                  TeamHandler.getObservers().removeObserver((Player) sender);
                  TeamHandler.getRedTeam().addRedPlayer((Player) sender);
              }
              else if (args.getString(0).equalsIgnoreCase("o")){
	              if (TeamHandler.getBlueTeam().isPlayerOnBlue((Player)sender)){
		              TeamHandler.getBlueTeam().removeBluePlayer((Player)sender);
		              TeamHandler.getObservers().addObserver((Player) sender);
	              }
	              else if (TeamHandler.getRedTeam().isPlayerOnRed((Player)sender)){
		              TeamHandler.getRedTeam().removeRedPlayer((Player)sender);
		              TeamHandler.getObservers().addObserver((Player) sender);
	              }
              }
              else  {
                  throw new CommandException("Thats not a team!");
              }
          }
      }
      else if (args.argsLength() == 0){
	    if (TeamHandler.getBlueTeam().getBlueTeam().size() == 0 || TeamHandler.getRedTeam().getRedTeam().size() == 0){
		    Random r = new Random();
		    Inferno.getPlugin().getLogger().info(String.valueOf(r.nextInt(1)));
		    if (r.nextInt(1) == 1){
			    TeamHandler.getObservers().removeObserver((Player) sender);
			    TeamHandler.getBlueTeam().addBluePlayer((Player) sender);
		    }
		    else if (r.nextInt(1) == 0){
			    TeamHandler.getObservers().removeObserver((Player) sender);
			    TeamHandler.getRedTeam().addRedPlayer((Player) sender);
		    }
		    return;
	    }
	   else if (TeamHandler.getRedTeam().getRedTeam().size() > TeamHandler.getBlueTeam().getBlueTeam().size()){
		   TeamHandler.getObservers().removeObserver((Player) sender);
		   TeamHandler.getBlueTeam().addBluePlayer((Player) sender);

	   }
	      else {
		   TeamHandler.getObservers().removeObserver((Player) sender);
		   TeamHandler.getRedTeam().addRedPlayer((Player) sender);
	   }
      }
    }
}
