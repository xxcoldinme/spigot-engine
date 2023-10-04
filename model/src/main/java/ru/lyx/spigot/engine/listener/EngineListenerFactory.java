package ru.lyx.spigot.engine.listener;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.SpigotEngine;

import java.util.function.Function;

@FunctionalInterface
public interface EngineListenerFactory {

    Listener createListener(@NotNull SpigotEngine engine);
}
