package me.masterejay.inferno.listeners;

import me.masterejay.inferno.Inferno;
import me.masterejay.inferno.Values;
import me.masterejay.inferno.kits.KitItem;
import me.masterejay.inferno.match.MatchHandler;
import me.masterejay.inferno.match.MatchState;
import me.masterejay.inferno.shop.ShopHandler;
import me.masterejay.inferno.teams.BlueTeam;
import me.masterejay.inferno.teams.RedTeam;
import me.masterejay.inferno.teams.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * @author MasterEjay
 */
public class DeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		if (TeamHandler.getObservers().isPlayerObserving(event.getEntity())){
			event.getEntity().teleport(Values.getObserverSpawn());
		}
		else if (TeamHandler.getBlueTeam().isPlayerOnBlue(event.getEntity()) && MatchHandler.getState() == MatchState.PLAYING){
			MatchHandler.addPoints(Values.getPointsPerKill(), "red");
			if (event.getEntity().getKiller() != null){
				ShopHandler.addCoins(event.getEntity().getKiller(), ShopHandler.getPointsPerKill());
			}
		}
		else if (TeamHandler.getRedTeam().isPlayerOnRed(event.getEntity()) && MatchHandler.getState() == MatchState.PLAYING) {
			MatchHandler.addPoints(Values.getPointsPerKill(),"blue");
			if (event.getEntity().getKiller() != null){
				ShopHandler.addCoins(event.getEntity().getKiller(), ShopHandler.getPointsPerKill());
			}

		}
	}



	@EventHandler
	public void onPlayerRespawn(final PlayerRespawnEvent event){
		event.getPlayer().getInventory().clear();
		if (TeamHandler.getObservers().isPlayerObserving(event.getPlayer())){
					event.setRespawnLocation(Values.getObserverSpawn());
					event.getPlayer().teleport(Values.getObserverSpawn());
		}
		else if (TeamHandler.getBlueTeam().isPlayerOnBlue(event.getPlayer())){
			for (KitItem i : BlueTeam.getKit()){
				if (event.getPlayer().hasPermission(i.getPermission())){
					if (i.getSlot() == 100){
						event.getPlayer().getInventory().setBoots(i.getItem());
						continue;
					}
					else if (i.getSlot() == 101){
						event.getPlayer().getInventory().setLeggings(i.getItem());
						continue;
					}
					else if (i.getSlot() == 102){
						event.getPlayer().getInventory().setChestplate(i.getItem());
						continue;
					}
					else if (i.getSlot() == 103){
						event.getPlayer().getInventory().setHelmet(i.getItem());
						continue;
					}
					event.getPlayer().getInventory().setItem(i.getSlot(), i.getItem());
				}
			}
			event.getPlayer().getInventory().setItem(8, ShopHandler.getItemOpen().getItem());
		}
		else if (TeamHandler.getRedTeam().isPlayerOnRed(event.getPlayer())) {
			for (KitItem i : RedTeam.getKit()){
				if (i.getSlot() == 100){
					event.getPlayer().getInventory().setBoots(i.getItem());
					continue;
				}
				else if (i.getSlot() == 101){
					event.getPlayer().getInventory().setLeggings(i.getItem());
					continue;
				}
				else if (i.getSlot() == 102){
					event.getPlayer().getInventory().setChestplate(i.getItem());
					continue;
				}
				else if (i.getSlot() == 103){
					event.getPlayer().getInventory().setHelmet(i.getItem());
					continue;
				}
				if (event.getPlayer().hasPermission(i.getPermission())){
					event.getPlayer().getInventory().setItem(i.getSlot(), i.getItem());
				}
			}
			event.getPlayer().getInventory().setItem(8, ShopHandler.getItemOpen().getItem());
		}
	}
}