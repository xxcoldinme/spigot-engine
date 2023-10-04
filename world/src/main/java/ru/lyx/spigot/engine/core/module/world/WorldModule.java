package ru.lyx.spigot.engine.core.module.world;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngineContext;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.AbstractSpigotModule;
import ru.lyx.spigot.engine.core.module.Factory;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactory;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.world.processor.ServerWorldsWrappingProcessor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class WorldModule extends AbstractSpigotModule<WorldContext> {

    @Factory
    private static SpigotModuleFactory<WorldModule> factory() {
        return new WorldModuleFactory();
    }

    @Override
    protected WorldContext createContext(@NotNull SpigotEngineContext previousContext) {
        return new WorldContext();
    }

    @Override
    public AttachmentContainer<SpigotModuleProcessor<?, ?>> ofProcessors(@NotNull SpigotEngine engine, @NotNull WorldContext context) {
        return AttachmentContainer.of(
                new ServerWorldsWrappingProcessor()
        );
    }
}
