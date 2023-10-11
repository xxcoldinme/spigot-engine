package ru.lyx.spigot.engine.core.module;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.key.Keyable;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigModel;

/**
 * The implementations of this interface are modules
 * of SpigotEngine framework.
 */
public interface SpigotModule<T extends SpigotContext, M extends SpigotConfigModel>
        extends Keyable<String> {

    /**
     * Initialization internal processors at container.
     * @param engine SpigotEngine instance.
     */
    PocketContainer<SpigotModuleProcessor<?, ?>> ofProcessors(@NotNull SpigotEngine engine);

    /**
     * Pre initialization process handling.
     * @param engine SpigotEngine instance.
     */
    void init(@NotNull SpigotEngine engine);

    /**
     * @return The module context data.
     */
    T getContext();

    /**
     * @return The module .ini configuration model.
     */
    M getConfig();
}
