package ru.lyx.spigot.engine.module.processor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.lyx.spigot.engine.SpigotEngine;
import ru.lyx.spigot.engine.context.SpigotContext;
import ru.lyx.spigot.engine.module.SpigotModule;
import ru.lyx.spigot.engine.module.processor.transaction.ProcessTransaction;

@Getter
@RequiredArgsConstructor
public class ProcessorContext<T extends SpigotModule<?>, C extends SpigotContext> {

    private final SpigotEngine engine;

    private final T module;
    private final C moduleContext;

    @Setter
    private ProcessTransaction previousTransaction;
}
