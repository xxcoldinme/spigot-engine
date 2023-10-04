package ru.lyx.spigot.engine.core.module.sync;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngineContext;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.AbstractSpigotModule;
import ru.lyx.spigot.engine.core.module.Factory;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactory;
import ru.lyx.spigot.engine.core.module.SpigotModuleTypes;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.sync.connection.ConnectionBootstrap;
import ru.lyx.spigot.engine.core.module.sync.processor.SocketConnectionOpenProcessor;
import ru.lyx.spigot.engine.core.module.sync.processor.SocketConnectionStableProcessor;

public class SyncModule extends AbstractSpigotModule<SyncContext> {

    @Factory
    private static SpigotModuleFactory<SyncModule> factory() {
        return new SyncModuleFactory();
    }

    SyncModule() {
        super(SpigotModuleTypes.SYNC_SERVERS);
    }

    @Override
    protected SyncContext createContext(@NotNull SpigotEngineContext previousContext) {
        return new SyncContext(previousContext);
    }

    @Override
    public AttachmentContainer<SpigotModuleProcessor<?, SyncContext>> ofProcessors(@NotNull SpigotEngine engine, @NotNull SyncContext context) {
        return AttachmentContainer.of(
                new SocketConnectionOpenProcessor(new ConnectionBootstrap()),
                new SocketConnectionStableProcessor()
        );
    }
}
