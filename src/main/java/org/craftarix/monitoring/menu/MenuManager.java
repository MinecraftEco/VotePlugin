package org.craftarix.monitoring.menu;

import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class MenuManager {
    public static MenuManager INSTANCE = new MenuManager();
    private final ConcurrentHashMap<String, Menu> cache = new ConcurrentHashMap<>();

    public void add(Player player, Menu inventory) {
        cache.put(player.getName().toLowerCase(), inventory);
    }

    public Menu get(Player player) {
        return get(player.getName());
    }

    public Menu get(String playerName) {
        return cache.get(playerName.toLowerCase());
    }

    public void remove(Player player) {
        remove(player.getName());
    }

    public void remove(String playerName) {
        cache.remove(playerName.toLowerCase());
    }
}
