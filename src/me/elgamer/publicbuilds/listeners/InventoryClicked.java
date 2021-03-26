package me.elgamer.publicbuilds.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import me.elgamer.publicbuilds.Main;
import me.elgamer.publicbuilds.gui.AcceptGui;
import me.elgamer.publicbuilds.gui.DenyGui;
import me.elgamer.publicbuilds.gui.MainGui;
import me.elgamer.publicbuilds.gui.PlotGui;
import me.elgamer.publicbuilds.gui.PlotInfo;
import me.elgamer.publicbuilds.gui.ReviewGui;
import me.elgamer.publicbuilds.utils.User;

public class InventoryClicked implements Listener {
	
	public InventoryClicked(Main plugin) {
	
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		String title = e.getView().getTitle();
		
		User u = Main.getInstance().getUser((Player) e.getWhoClicked());
		if (title.equals(MainGui.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null){
				return;
			}
			if (title.equals(MainGui.inventory_name)) {
				MainGui.clicked(u, e.getSlot(), e.getCurrentItem(), e.getInventory());
			}
		}
		else if (title.equals(ReviewGui.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null){
				return;
			}
			if (title.equals(ReviewGui.inventory_name)) {
				ReviewGui.clicked(u, e.getSlot(), e.getCurrentItem(), e.getInventory());
			}
		}
		else if (title.equals(PlotGui.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null){
				return;
			}
			if (title.equals(PlotGui.inventory_name)) {
				PlotGui.clicked(u, e.getSlot(), e.getCurrentItem(), e.getInventory());
			}
		}
		else if (title.equals(DenyGui.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null){
				return;
			}
			if (title.equals(DenyGui.inventory_name)) {
				DenyGui.clicked(u, e.getSlot(), e.getCurrentItem(), e.getInventory());
			}
		}
		else if (title.equals(AcceptGui.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null){
				return;
			}
			if (title.equals(AcceptGui.inventory_name)) {
				ReviewGui.clicked(u, e.getSlot(), e.getCurrentItem(), e.getInventory());
			}
		}
		else if (title.equals(PlotInfo.inventory_name)) {
			e.setCancelled(true);
			if (e.getCurrentItem() == null){
				return;
			}
			if (title.equals(PlotInfo.inventory_name)) {
				ReviewGui.clicked(u, e.getSlot(), e.getCurrentItem(), e.getInventory());
			}
		}
		else {
			
		}
	}

}
