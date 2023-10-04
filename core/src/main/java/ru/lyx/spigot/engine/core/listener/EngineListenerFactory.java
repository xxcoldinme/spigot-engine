package ru.lyx.spigot.engine.core.listener;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;

@FunctionalInterface
public interface EngineListenerFactory {

    Listener createListener(@NotNull SpigotEngine engine);
}
