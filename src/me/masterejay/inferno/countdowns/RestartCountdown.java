package me.masterejay.inferno.countdowns;

import me.masterejay.inferno.Inferno;
import me.masterejay.inferno.Values;
import me.masterejay.inferno.match.MatchHandler;
import me.masterejay.inferno.match.MatchState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author MasterEjay
 */
public class RestartCountdown extends Countdown{

    @Override
    public void onEnd() {
	    MatchHandler.setState(MatchState.WAITING);
	    for (Player p : Bukkit.getOnlinePlayers()){
		    p.kickPlayer(ChatColor.GREEN + "Restarting server! Rejoin!");
	    }
	    MatchHandler.rollback("Map");
		Inferno.getPlugin().getServer().shutdown();




    }

    @Override
    public void tick(int secs) {
        if (secs % 5 == 0 || secs < 5) {
            Bukkit.broadcastMessage(ChatColor.AQUA + "Server restarting in " + ChatColor.DARK_RED + "" + secs + ChatColor.AQUA + " second" + (secs == 1 ? "" : "s") + "!");
        }

    }
}
