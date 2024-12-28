package org.craftarix.monitoring.menu;

import org.bukkit.entity.Player;
import org.craftarix.monitoring.menu.item.Item;

public interface Menu {
    void openInventory(Player player);
    Item getItem(int slot);
}
