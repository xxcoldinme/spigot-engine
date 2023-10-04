package ru.lyx.spigot.engine.main;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.plugin.SpigotContextPlugin;

@RequiredArgsConstructor
public final class SpigotPluginListener implements Listener {

    private final SpigotEngine engine;

    @EventHandler
    public void handle(PluginEnableEvent event) {
        final Plugin plugin = event.getPlugin();

        if (plugin instanceof SpigotContextPlugin) {

            SpigotContextPlugin spigotContextPlugin = (SpigotContextPlugin) plugin;
            engine.registerPlugin(spigotContextPlugin);

            spigotContextPlugin.setEngine(engine);
        }
    }

    @EventHandler
    public void handle(PluginDisableEvent event) {
        final Plugin plugin = event.getPlugin();

        if (plugin instanceof SpigotContextPlugin) {

            SpigotContextPlugin spigotContextPlugin = (SpigotContextPlugin) plugin;
            engine.unregisterPlugin(spigotContextPlugin);

            spigotContextPlugin.setEngine(null);
        }
    }
}
