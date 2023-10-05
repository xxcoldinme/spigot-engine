package ru.lyx.spigot.engine.core.module;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.key.Keyable;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigModel;

public interface SpigotModule<T extends SpigotContext, M extends SpigotConfigModel>
        extends Keyable<String> {

    AttachmentContainer<SpigotModuleProcessor<?, ?>> ofProcessors(@NotNull SpigotEngine engine);

    void init(@NotNull SpigotEngine engine);

    T getContext();

    M getConfigModel();
}
