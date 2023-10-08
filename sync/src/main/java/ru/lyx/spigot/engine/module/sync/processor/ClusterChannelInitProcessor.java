package ru.lyx.spigot.engine.module.sync.processor;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.processor.ProcessorContext;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.module.sync.SyncContext;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.sync.transport.TransportChannel;

@RequiredArgsConstructor
public class ClusterChannelInitProcessor implements SpigotModuleProcessor<SyncModule, SyncContext> {

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("ClusterChannelInitProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessorContext<SyncModule, SyncContext> context) {
        final ProcessTransaction previousTransaction = context.getPreviousTransaction();
        final SpigotMetadata metadata = previousTransaction.getMetadata();

        metadata.<TransportChannel>get(SyncContext.CHANNEL_PROPERTY)
                .ifPresent(channel -> {

                    context.getModuleContext().setChannel(channel);
                });

        return previousTransaction;
    }
}
