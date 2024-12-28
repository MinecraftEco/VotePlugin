package org.craftarix.monitoring.menu.listener;

import lombok.NonNull;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.craftarix.monitoring.MonitoringPlugin;
import org.craftarix.monitoring.util.NbtUtil;

public class AntiDupeListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        var item = event.getCurrentItem();
        if(NbtUtil.hasTag(item, NbtUtil.AntiDupeTAG)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event){
        var item = event.getItem();
        if(NbtUtil.hasTag(item.getItemStack(), NbtUtil.AntiDupeTAG)){
            event.setCancelled(true);
            item.remove();
        }
    }
    @EventHandler
    public void onItemDropEvent(@NonNull PlayerDropItemEvent event) {
        Item item = event.getItemDrop();

        if (NbtUtil.hasTag(item.getItemStack(), NbtUtil.AntiDupeTAG)) {
            Bukkit.getScheduler().runTaskLater(MonitoringPlugin.INSTANCE, item::remove, 5);
        }

    }
}
