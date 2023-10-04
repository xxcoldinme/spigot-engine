package ru.lyx.spigot.engine.attachment;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.plugin.SpigotContextPlugin;

@FunctionalInterface
public interface SpigotEngineAttachment {

    void post(@NotNull SpigotContextPlugin plugin);
}
