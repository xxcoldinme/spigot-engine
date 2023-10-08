package ru.lyx.spigot.engine.core.attachment;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.plugin.SpigotContextPlugin;

/**
 * Functional interface denoting the attachment of some object or
 * process belonging to processing within the Spigot/CraftBukkit.
 */
@FunctionalInterface
public interface SpigotAttachment {

    /**
     * Attach of some object or process.
     *
     * @param plugin Parent of same object or process as bukkit-plugin/
     */
    void attach(@NotNull SpigotContextPlugin plugin);
}
