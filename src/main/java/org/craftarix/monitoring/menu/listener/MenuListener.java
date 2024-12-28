package org.craftarix.monitoring.menu.listener;

import lombok.var;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.craftarix.monitoring.menu.MenuManager;

public class MenuListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        var human = event.getWhoClicked();
        if (!(human instanceof Player)) {
            return;
        }
        var player = (Player) human;
        var menuInventory = MenuManager.INSTANCE.get(player);
        if (menuInventory == null) {
            return;
        }
        event.setCancelled(true);
        var item = menuInventory.getItem(event.getSlot());
        if (item == null) {
            return;
        }
        item.onClick(event);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        var human = event.getPlayer();
        if (!(human instanceof Player)) {
            return;
        }
        var player = (Player) human;
        MenuManager.INSTANCE.remove(player);
    }

}
