package ru.lyx.spigot.engine.core.attachment;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.plugin.SpigotContextPlugin;

@FunctionalInterface
public interface SpigotEngineAttachment {

    void attach(@NotNull SpigotContextPlugin plugin);
}
