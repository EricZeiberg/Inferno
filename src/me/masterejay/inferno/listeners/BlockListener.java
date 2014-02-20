package me.masterejay.inferno.listeners;

import me.masterejay.inferno.Values;
import me.masterejay.inferno.match.MatchHandler;
import me.masterejay.inferno.match.MatchState;
import me.masterejay.inferno.teams.TeamHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * @author MasterEjay
 */
public class BlockListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		if (TeamHandler.getObservers().isPlayerObserving(event.getPlayer()) || !Values.isBreakBlocks() || !(MatchHandler.getState()== MatchState.PLAYING)){
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event){
		if (TeamHandler.getObservers().isPlayerObserving(event.getPlayer()) || !Values.isBreakBlocks()  || !(MatchHandler.getState()== MatchState.PLAYING)){
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event) {
		if (TeamHandler.getObservers().isPlayerObserving(event.getPlayer())  || !(MatchHandler.getState()== MatchState.PLAYING)){
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event){
		if (TeamHandler.getObservers().isPlayerObserving(event.getPlayer())  || !(MatchHandler.getState()== MatchState.PLAYING)){
			event.setCancelled(true);
		}
	}
}
