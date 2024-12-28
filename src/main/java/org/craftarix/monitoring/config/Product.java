package org.craftarix.monitoring.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
@Getter
public class Product {

    private final ItemStack icon;
    private final Integer price;
    private final List<String> onBuyCommands;

    public void executeCommands(Player player) {
        onBuyCommands.forEach(command -> {
            if (command.contains("{player}")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("{player}", player.getName()));
            } else {
                player.chat(command);
            }
        });
    }
}
