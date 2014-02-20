package me.masterejay.inferno.listeners;

import me.masterejay.inferno.Values;
import me.masterejay.inferno.match.MatchHandler;
import me.masterejay.inferno.match.MatchState;
import me.masterejay.inferno.teams.TeamHandler;
import me.masterejay.inferno.utils.TeamPicker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author MasterEjay
 */
public class ConnectionListener implements Listener{




	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		TeamHandler.getObservers().addObserver(event.getPlayer());
		event.getPlayer().teleport(Values.getObserverSpawn());
		event.getPlayer().setScoreboard(MatchHandler.getBoard());
		event.getPlayer().getInventory().clear();
		event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
		event.getPlayer().getInventory().addItem(TeamPicker.getBook());
		if (MatchHandler.getState() == MatchState.PLAYING){
			for (Player p : Values.getParticipating()){
				p.hidePlayer(event.getPlayer());
			}
		}
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event){
		event.getPlayer().setScoreboard(MatchHandler.getManager().getNewScoreboard());
		Player p = event.getPlayer();
		if (TeamHandler.getBlueTeam().isPlayerOnBlue(p)){
			TeamHandler.getBlueTeam().removeBluePlayer(p);
		}
		else if (TeamHandler.getRedTeam().isPlayerOnRed(p)){
			TeamHandler.getRedTeam().removeRedPlayer(p);
		}
	}
}
