package ru.lyx.spigot.engine.core.module;

/**
 * A functional interface that stores an instance
 * of a fresh and not yet initialized module
 * of the SpigotEngine framework.
 */
@FunctionalInterface
public interface SpigotModuleFactory<T extends SpigotModule<?, ?>> {

    T create();
}
