package ru.lyx.spigot.engine.core.module.handler;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.module.SpigotModule;

public interface SpigotHandler<T extends SpigotModule<?>> {

// -------------------------- // VALIDATION // ----------------------------------------- //

    Boolean ALWAYS = Boolean.TRUE;
    Boolean NEVER = Boolean.FALSE;

// ------------------------------------------------------------------------------------- //

    boolean validate(@NotNull SpigotHandlerContext<T> context);

    void handle(@NotNull SpigotHandlerContext<T> context);
}
