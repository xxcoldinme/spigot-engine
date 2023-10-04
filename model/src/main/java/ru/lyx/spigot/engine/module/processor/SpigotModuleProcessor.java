package ru.lyx.spigot.engine.module.processor;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.context.SpigotContext;
import ru.lyx.spigot.engine.key.Keyable;
import ru.lyx.spigot.engine.module.SpigotModule;
import ru.lyx.spigot.engine.module.processor.transaction.ProcessTransaction;

public interface SpigotModuleProcessor<T extends SpigotModule<C>, C extends SpigotContext> extends Keyable<String> {

    ProcessTransaction process(@NotNull ProcessorContext<T, C> context);
}
