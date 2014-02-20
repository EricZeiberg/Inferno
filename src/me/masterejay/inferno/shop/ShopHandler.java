package me.masterejay.inferno.shop;

import me.masterejay.inferno.Inferno;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author MasterEjay
 */
public class ShopHandler{

	private static HashMap<Player, Integer> coins = new HashMap<Player, Integer>();

	private static ShopItem itemOpen = null;

	private static int size;
	private static int pointsPerKill = 0;

	private static List<ShopItem> items = new ArrayList<ShopItem>();

	private static Inventory shopInventory = null;

	public static int getCoins(Player p){
		if (coins.get(p) == null){
			return 0;
		}
		return coins.get(p);
	}

	public static void initShop(){
		shopInventory = Bukkit.createInventory(null, size, "Shop");
		ItemMeta meta = getItemOpen().getItem().getItemMeta();
		meta.setDisplayName(getItemOpen().getName());
		getItemOpen().getItem().setItemMeta(meta);
		for (ShopItem s: items){
			ItemMeta sMeta = s.getItem().getItemMeta();
			sMeta.setDisplayName(s.getName());
			s.getItem().setItemMeta(sMeta);
			Inferno.getPlugin().getLogger().info(String.valueOf(s.getSlot()));
			shopInventory.setItem(s.getSlot(), s.getItem());
		}
	}

	public static ShopItem getItemFromItemStack(ItemStack stack){
		for (ShopItem s: items){
			if (s.getItem().isSimilar(stack)){
				Inferno.getPlugin().getLogger().info("TEST");
				return s;
			}
		}
		return null;
	}

	public static void addCoins(Player p, int num){
		if (coins.get(p) == null){
			coins.put(p, num);
		}
		else {
			int i = coins.get(p);
			coins.remove(p);
			coins.put(p, num + i);
		}
	}

	public static void removeCoins(Player p, int num){
		if (coins.get(p)!= null){
			int i = coins.get(p);
			coins.remove(p);
			coins.put(p, i - num);
		}
	}

	public static ShopItem getItemOpen(){
		return itemOpen;
	}

	public static void setItemOpen(ShopItem itemOpen){
		ShopHandler.itemOpen=itemOpen;
	}

	public static List<ShopItem> getItems(){
		return items;
	}

	public static void setItems(List<ShopItem> items){
		ShopHandler.items=items;
	}

	public static void setSize(int size){
		ShopHandler.size=size;
	}

	public static Inventory getShopInventory(){
		return shopInventory;
	}

	public static int getPointsPerKill(){
		return pointsPerKill;
	}

	public static void setPointsPerKill(int pointsPerKill){
		ShopHandler.pointsPerKill=pointsPerKill;
	}
}
