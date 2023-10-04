package ru.lyx.spigot.engine.module.sync;

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
import ru.lyx.spigot.engine.module.sync.connection.ConnectionBootstrap;
import ru.lyx.spigot.engine.module.sync.processor.SocketConnectionOpenProcessor;
import ru.lyx.spigot.engine.module.sync.processor.SocketConnectionStableProcessor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SyncModule extends AbstractSpigotModule<SyncContext> {

    @Factory
    private static SpigotModuleFactory<SyncModule> factory() {
        return new SyncModuleFactory();
    }

    @Override
    protected SyncContext createContext(@NotNull SpigotEngineContext previousContext) {
        return new SyncContext(previousContext);
    }

    @Override
    public AttachmentContainer<SpigotModuleProcessor<?, ?>> ofProcessors(@NotNull SpigotEngine engine, @NotNull SyncContext context) {
        return AttachmentContainer.of(
                new SocketConnectionOpenProcessor(new ConnectionBootstrap()),
                new SocketConnectionStableProcessor()
        );
    }
}
