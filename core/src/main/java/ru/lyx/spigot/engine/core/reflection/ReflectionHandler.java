package ru.lyx.spigot.engine.core.reflection;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;

/**
 * Functional interface for processing
 * low-level Java processes - {@code java.lang.reflect}.
 */
@FunctionalInterface
public interface ReflectionHandler<T> {

    T handle(@NotNull SpigotMetadata metadata);
}
