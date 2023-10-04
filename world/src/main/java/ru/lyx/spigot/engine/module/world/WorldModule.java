package ru.lyx.spigot.engine.module.world;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.SpigotEngineContext;
import ru.lyx.spigot.engine.SpigotEngine;
import ru.lyx.spigot.engine.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.module.AbstractSpigotModule;
import ru.lyx.spigot.engine.module.Factory;
import ru.lyx.spigot.engine.module.SpigotModuleFactory;
import ru.lyx.spigot.engine.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.module.world.processor.ServerWorldsWrappingProcessor;

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
