package ru.lyx.spigot.engine.module.sync;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.AbstractSpigotModule;
import ru.lyx.spigot.engine.core.module.Factory;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactory;
import ru.lyx.spigot.engine.core.module.SpigotModuleTypes;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.module.sync.processor.CreateClusterChannelProcessor;
import ru.lyx.spigot.engine.module.sync.processor.ClusterChannelInitProcessor;
import ru.lyx.spigot.engine.module.sync.processor.ClusterChannelStartProcessor;

public class SyncModule extends AbstractSpigotModule<SyncContext, SyncConfigModel> {

    @Factory
    private static SpigotModuleFactory<SyncModule> factory() {
        return new SyncModuleFactory();
    }

    SyncModule() {
        super(SpigotModuleTypes.SYNC_SERVERS);
    }

    @Override
    public AttachmentContainer<SpigotModuleProcessor<?, ?>> ofProcessors(@NotNull SpigotEngine engine) {
        return AttachmentContainer.of(
                new CreateClusterChannelProcessor(),
                new ClusterChannelStartProcessor(),
                new ClusterChannelInitProcessor()
        );
    }

    @Override
    protected SyncContext createContext() {
        return new SyncContext();
    }

    @Override
    protected SyncConfigModel createConfigModel() {
        return new SyncConfigModel();
    }
}
