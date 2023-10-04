package ru.lyx.spigot.engine.core.module;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.SpigotEngineContext;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.context.SpigotContext;
import ru.lyx.spigot.engine.core.key.Keyable;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;

public interface SpigotModule<T extends SpigotContext>
        extends Keyable<String> {

    AttachmentContainer<SpigotModuleProcessor<?, ?>> ofProcessors(@NotNull SpigotEngine engine, @NotNull T context);

    T lookupContext(@NotNull SpigotEngineContext previousContext);
}
