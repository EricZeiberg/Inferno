package me.masterejay.inferno.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author MasterEjay
 */
public class TeamPicker {

	private static Inventory picker = Bukkit.createInventory(null, 9, "Team Picker");

	private static ItemStack auto = new ItemStack(Material.NETHER_STAR);
	private static ItemMeta autoMeta = auto.getItemMeta();
	private static ItemStack red = new ItemStack(Material.WOOL, 1,(short)14);
	private static ItemMeta redMeta = red.getItemMeta();
	private static ItemStack blue = new ItemStack(Material.WOOL, 1,(short)11);
	private static ItemMeta blueMeta = blue.getItemMeta();

	private static ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
	private static ItemMeta bookMeta = book.getItemMeta();


	public static void initInventory(){
		bookMeta.setDisplayName(ChatColor.GREEN + "Right click to pick your team!");
		book.setItemMeta(bookMeta);
		autoMeta.setDisplayName(ChatColor.GREEN + "Auto join!");
		auto.setItemMeta(autoMeta);
		redMeta.setDisplayName(ChatColor.RED+"Join Red! (Donors only)");
		red.setItemMeta(redMeta);
		blueMeta.setDisplayName(ChatColor.BLUE+"Join Blue! (Donors only)");
		blue.setItemMeta(blueMeta);
		picker.setItem(0, auto);
		picker.setItem(1, red);
		picker.setItem(2, blue);
	}

	public static Inventory getPicker(){
		return picker;
	}
	
	public static ItemStack getAuto(){
		return auto;
	}

	public static ItemStack getRed(){
		return red;
	}

	public static ItemStack getBlue(){
		return blue;
	}

	public static ItemStack getBook(){
		return book;
	}
}
