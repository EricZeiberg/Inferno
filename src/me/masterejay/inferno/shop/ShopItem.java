package me.masterejay.inferno.shop;

import org.bukkit.inventory.ItemStack;

/**
 * @author MasterEjay
 */
public class ShopItem{

	private ItemStack item;
	private int slot;
	private String name;
	private String command;
	private int price;

	public ShopItem(ItemStack item,int slot,String name,String command, int price){
		this.item=item;
		this.slot=slot;
		this.name=name;
		this.command=command;
		this.price = price;
	}

	public String getCommand(){
		return command;
	}

	public String getName(){
		return name;
	}

	public int getSlot(){
		return slot;
	}

	public ItemStack getItem(){
		return item;
	}

	public int getPrice(){
		return price;
	}
}
