package ru.lyx.spigot.engine.core.module.sync.processor;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.core.module.processor.ProcessorContext;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.sync.SyncContext;
import ru.lyx.spigot.engine.core.module.sync.SyncModule;
import ru.lyx.spigot.engine.core.module.sync.connection.ConnectionBootstrap;
import ru.lyx.spigot.engine.core.module.sync.connection.SynchronizedConnection;

@RequiredArgsConstructor
public class SocketConnectionOpenProcessor implements SpigotModuleProcessor<SyncModule, SyncContext> {

    private final ConnectionBootstrap bootstrap;

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("SocketConnectionOpenProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessorContext<SyncModule, SyncContext> context) {
        SynchronizedConnection connection = bootstrap.connect(context.getModuleContext());
        return context.getPreviousTransaction()
                .withMetadata(KeyProperty.of("connection"), connection)
                .thenContinueTo(SocketConnectionStableProcessor.class);
    }
}
