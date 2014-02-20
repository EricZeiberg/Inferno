package me.masterejay.inferno.kits;

import org.bukkit.inventory.ItemStack;

/**
 * @author MasterEjay
 */
public class KitItem {

	private ItemStack item;
	private int slot;
	private String permission;

	public KitItem(ItemStack item, int slot, String permission) {
		this.item = item;
		this.slot = slot;
		this.permission = permission;
	}

	public ItemStack getItem(){
		return item;
	}

	public void setItem(ItemStack item){
		this.item=item;
	}

	public int getSlot(){
		return slot;
	}

	public void setSlot(int slot){
		this.slot=slot;
	}

	public String getPermission(){
		return permission;
	}

	public void setPermission(String permission){
		this.permission=permission;
	}
}
