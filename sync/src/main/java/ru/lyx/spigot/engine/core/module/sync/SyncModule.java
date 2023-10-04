package ru.lyx.spigot.engine.core.module.sync;

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
import ru.lyx.spigot.engine.core.module.sync.connection.ConnectionBootstrap;
import ru.lyx.spigot.engine.core.module.sync.processor.SocketConnectionOpenProcessor;
import ru.lyx.spigot.engine.core.module.sync.processor.SocketConnectionStableProcessor;

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
