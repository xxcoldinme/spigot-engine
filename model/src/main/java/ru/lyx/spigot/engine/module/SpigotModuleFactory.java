package ru.lyx.spigot.engine.module;

import ru.lyx.spigot.engine.key.Keyable;

public interface SpigotModuleFactory<T extends SpigotModule<?>> extends Keyable<String> {

    T create();


}
