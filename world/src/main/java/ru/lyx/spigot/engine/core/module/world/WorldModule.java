package ru.lyx.spigot.engine.core.module.world;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.AbstractSpigotModule;
import ru.lyx.spigot.engine.core.module.Factory;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactory;
import ru.lyx.spigot.engine.core.module.SpigotModuleTypes;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.world.processor.ServerWorldsWrappingProcessor;

public class WorldModule extends AbstractSpigotModule<WorldContext, WorldConfigModel> {

    @Factory
    private static SpigotModuleFactory<WorldModule> factory() {
        return new WorldModuleFactory();
    }

    WorldModule() {
        super(SpigotModuleTypes.WORLDS);
    }

    @Override
    public AttachmentContainer<SpigotModuleProcessor<?, ?>> ofProcessors(@NotNull SpigotEngine engine) {
        return AttachmentContainer.of(
                new ServerWorldsWrappingProcessor()
        );
    }

    @Override
    protected WorldContext createContext() {
        return new WorldContext();
    }

    @Override
    protected WorldConfigModel createConfigModel() {
        return new WorldConfigModel();
    }
}















