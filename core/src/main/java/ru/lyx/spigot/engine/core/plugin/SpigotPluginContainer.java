package ru.lyx.spigot.engine.core.plugin;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.lang.String.format;

@RequiredArgsConstructor
public class SpigotPluginContainer {

    private final Map<String, SpigotContextPlugin> pluginsMap = new ConcurrentHashMap<>();
    private final Logger logger;

    public void addPlugin(@NotNull SpigotContextPlugin plugin) {
        final String name = plugin.getName();

        logger.info(format("Plugin '%s' with SpigotEngine support has registered", name));
        pluginsMap.put(name.toLowerCase(), plugin);
    }

    public void removePlugin(@NotNull SpigotContextPlugin plugin) {
        final String name = plugin.getName();

        logger.warning(format("Plugin '%s' with SpigotEngine support has unregistered", name));
        pluginsMap.remove(name.toLowerCase());
    }

    public boolean hasPlugin(@NotNull SpigotContextPlugin plugin) {
        return pluginsMap.containsKey(plugin.getName().toLowerCase());
    }

    public Collection<SpigotContextPlugin> matchPlugin(@NotNull String name, boolean ignoreCase) {
        return pluginsMap.values()
                .stream()
                .filter(plugin -> ignoreCase ? plugin.getName().toLowerCase().contains(name.toLowerCase()) : plugin.getName().contains(name))
                .collect(Collectors.toSet());
    }

    public SpigotContextPlugin findPlugin(@NotNull String name) {
        return pluginsMap.values()
                .stream()
                .filter(plugin -> plugin.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}
