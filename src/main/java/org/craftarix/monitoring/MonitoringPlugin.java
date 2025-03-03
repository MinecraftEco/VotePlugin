package org.craftarix.monitoring;

import lombok.Getter;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.craftarix.monitoring.api.McEcoService;
import org.craftarix.monitoring.api.McEcoServiceAsync;
import org.craftarix.monitoring.api.VoteService;
import org.craftarix.monitoring.command.VoteCommand;
import org.craftarix.monitoring.config.Settings;
import org.craftarix.monitoring.menu.listener.MenuListener;
@Getter
public final class MonitoringPlugin extends JavaPlugin {
    public static MonitoringPlugin INSTANCE;{
        INSTANCE = this;
    }

    private VoteService voteService;
    private final Settings settings = new Settings();
    @Override
    public void onEnable() {
        saveDefaultConfig();

        var apiKey = getConfig().getString("apiKey");
        voteService = new McEcoServiceAsync(apiKey);
        settings.load(getConfig());


        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
        getCommand("vote").setExecutor(new VoteCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
