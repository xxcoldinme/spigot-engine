package ru.lyx.spigot.engine.core.module;

import ru.lyx.spigot.engine.core.key.Keyable;

public interface SpigotModuleFactory<T extends SpigotModule<?>> extends Keyable<String> {

    T create();


}
