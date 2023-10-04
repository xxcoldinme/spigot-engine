package ru.lyx.spigot.engine.module;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.SpigotEngine;
import ru.lyx.spigot.engine.SpigotEngineContext;
import ru.lyx.spigot.engine.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.context.SpigotContext;
import ru.lyx.spigot.engine.module.processor.SpigotModuleProcessor;

public interface SpigotModule<T extends SpigotContext> {

    AttachmentContainer<SpigotModuleProcessor<?, ?>> ofProcessors(@NotNull SpigotEngine engine, @NotNull T context);

    T lookupContext(@NotNull SpigotEngineContext previousContext);
}
