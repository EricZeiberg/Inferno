package me.masterejay.inferno.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author MasterEjay
 */
public class MiscListener implements Listener{

	@EventHandler
	public void onChat(AsyncPlayerChatEvent event){
		event.setFormat(event.getPlayer().getDisplayName() + ": " + ChatColor.GRAY + event.getMessage());
	}

	@EventHandler
	public void onLogin(PlayerJoinEvent event){
		event.setJoinMessage(event.getPlayer().getDisplayName() + ChatColor.YELLOW + " > Join");
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		event.setQuitMessage(event.getPlayer().getDisplayName() + ChatColor.YELLOW + " > Quit");
	}
}
