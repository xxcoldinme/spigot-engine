package ru.lyx.spigot.engine.core.module;

@FunctionalInterface
public interface SpigotModuleFactory<T extends SpigotModule<?, ?>> {

    T create();
}
