package ru.lyx.spigot.engine.core.module.processor;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.key.Keyable;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;

/**
 * The module's internal processor, which starts with the initialization
 * of its module and handles the module's internal initialization processes.
 */
public interface SpigotModuleProcessor<T extends SpigotModule<C, ?>, C extends SpigotContext>
        extends Keyable<String> {

    /**
     * Processing the module initialization part.
     *
     * @param controller Initialization controller.
     * @return Initialization transaction.
     */
    ProcessTransaction process(@NotNull ProcessController<T, C> controller);
}
