package me.masterejay.inferno.listeners;

import me.masterejay.inferno.Inferno;
import me.masterejay.inferno.match.MatchHandler;
import me.masterejay.inferno.match.MatchState;
import me.masterejay.inferno.shop.ShopHandler;
import me.masterejay.inferno.shop.ShopItem;
import me.masterejay.inferno.teams.TeamHandler;
import me.masterejay.inferno.utils.TeamPicker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author MasterEjay
 */
public class InventoryListener implements Listener{

	@EventHandler
	public void onRightClick(PlayerInteractEvent event){
		if (event.hasItem() && event.getAction() == Action.RIGHT_CLICK_AIR && event.getItem().getType() == Material.ENCHANTED_BOOK) {
			event.getPlayer().openInventory(TeamPicker.getPicker());
		}
		else if ((event.hasItem() && event.getAction() == Action.RIGHT_CLICK_AIR || event.hasItem() && event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem().getType() == ShopHandler.getItemOpen().getItem().getType()
				&& !TeamHandler.getObservers().isPlayerObserving(event.getPlayer()) && MatchHandler.getState() ==MatchState.PLAYING) {
			event.getPlayer().openInventory(ShopHandler.getShopInventory());
		}
	}


	@EventHandler
	public void onInventoryClick(InventoryClickEvent event){
		if (event.getInventory().getName().equalsIgnoreCase("Team Picker")){
			event.setCancelled(true);
			if (event.getClick() ==ClickType.LEFT){
				if (event.getCurrentItem().equals(TeamPicker.getAuto())){
					Inferno.getPlugin().getServer().dispatchCommand((CommandSender)event.getWhoClicked(), "join");
					event.getWhoClicked().closeInventory();
			}

			else if (event.getCurrentItem().equals(TeamPicker.getBlue())){
				Inferno.getPlugin().getServer().dispatchCommand((CommandSender)event.getWhoClicked(), "join b");
					event.getWhoClicked().closeInventory();
			}

		else if (event.getCurrentItem().equals(TeamPicker.getRed())){
			Inferno.getPlugin().getServer().dispatchCommand((CommandSender)event.getWhoClicked(), "join r");
			event.getWhoClicked().closeInventory();}
			}
		}
		else if (event.getInventory().getName().equalsIgnoreCase("shop")){
		   event.setCancelled(true);
			ShopItem item = ShopHandler.getItemFromItemStack(event.getCurrentItem());
			int price = item.getPrice();
			if (ShopHandler.getCoins((Player)event.getWhoClicked()) >= price){
				Inferno.getPlugin().getServer().dispatchCommand((CommandSender)event.getWhoClicked(), item.getCommand());
				ShopHandler.removeCoins((Player)event.getWhoClicked(), price);
				event.getWhoClicked().closeInventory();
			}
			else {
				((Player)event.getWhoClicked()).sendMessage(ChatColor.RED + "You can't afford that item!");
				event.getWhoClicked().closeInventory();
			}
		}
	}
}
