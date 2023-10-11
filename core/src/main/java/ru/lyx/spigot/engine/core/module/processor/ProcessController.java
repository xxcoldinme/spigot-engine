package ru.lyx.spigot.engine.core.module.processor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;

@Getter
@AllArgsConstructor
public class ProcessController<T extends SpigotModule<C, ?>, C extends SpigotContext> {

    private final SpigotEngine engine;

    private final T module;
    private final C context;

    @Setter
    private ProcessTransaction previousTransaction;
}
