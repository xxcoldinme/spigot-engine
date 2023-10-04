package ru.lyx.spigot.engine.attachment.type;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.attachment.SpigotEngineAttachment;
import ru.lyx.spigot.engine.plugin.SpigotContextPlugin;

@RequiredArgsConstructor
public class ListenerAttachment implements SpigotEngineAttachment {

    private final Listener listener;

    @Override
    public void post(@NotNull SpigotContextPlugin plugin) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(listener, plugin);
    }
}
