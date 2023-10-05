package ru.lyx.spigot.engine.core.module.processor;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.key.Keyable;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;

public interface SpigotModuleProcessor<T extends SpigotModule<C, ?>, C extends SpigotContext> extends Keyable<String> {

    ProcessTransaction process(@NotNull ProcessorContext<T, C> context);
}
