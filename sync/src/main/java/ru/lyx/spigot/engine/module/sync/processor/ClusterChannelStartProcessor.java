package ru.lyx.spigot.engine.module.sync.processor;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.processor.ProcessorContext;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.module.sync.SyncContext;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.sync.ClusterChannel;

public class ClusterChannelStartProcessor implements SpigotModuleProcessor<SyncModule, SyncContext> {

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("StartClusterNodeProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessorContext<SyncModule, SyncContext> context) {
        final ProcessTransaction previousTransaction = context.getPreviousTransaction();
        final SpigotMetadata metadata = previousTransaction.getMetadata();

        metadata.<ClusterChannel>get(SyncContext.CHANNEL_PROPERTY)
                .ifPresent(channel -> {

                    channel.start();
                    context.getEngine().getLogger().info("Cluster node was started");
                });

        return previousTransaction
                .thenContinueTo(ClusterChannelInitProcessor.class);
    }
}
