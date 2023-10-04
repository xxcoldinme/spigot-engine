package ru.lyx.spigot.engine.core.reflection;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;

@FunctionalInterface
public interface ReflectionHandler<T> {

    T handle(@NotNull SpigotMetadata metadata);
}
