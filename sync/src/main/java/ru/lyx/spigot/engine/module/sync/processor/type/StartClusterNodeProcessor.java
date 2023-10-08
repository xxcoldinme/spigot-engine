package ru.lyx.spigot.engine.module.sync.processor.type;

import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.processor.ProcessorContext;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.module.sync.SyncContext;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.sync.cluster.ClusterChannel;
import ru.lyx.spigot.engine.module.sync.processor.SyncProcessorMetadataKeys;

public class StartClusterNodeProcessor implements SpigotModuleProcessor<SyncModule, SyncContext> {

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("StartClusterNodeProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessorContext<SyncModule, SyncContext> context) {
        final ProcessTransaction previousTransaction = context.getPreviousTransaction();
        final SpigotMetadata metadata = previousTransaction.getMetadata();

        metadata.<ClusterChannel>get(KeyProperty.of(SyncProcessorMetadataKeys.CHANNEL))
                .ifPresent(channel -> {

                    channel.start();

                    Server server = context.getEngine().getServer();
                    server.getLogger().info("[SyncModule] Cluster node was started");
                });

        return previousTransaction
                .thenContinueTo(SetClusterNodeSingletonProcessor.class);
    }
}
