package ru.lyx.spigot.engine.main;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.lyx.spigot.engine.core.SpigotEngine;

public final class SpigotEngineMain extends JavaPlugin {

    @Getter
    private SpigotEngine engine = new SpigotEngine(Bukkit.getServer());

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new SpigotPluginLoadingObserver(engine), this);
    }

    @Override
    public void onDisable() {
        engine = null;
    }
}
