package ru.lyx.spigot.engine.core.module.sync.processor.type;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.module.processor.ProcessorContext;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.core.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.core.module.sync.SyncContext;
import ru.lyx.spigot.engine.core.module.sync.SyncModule;
import ru.lyx.spigot.engine.core.module.sync.cluster.ClusterChannel;
import ru.lyx.spigot.engine.core.module.sync.cluster.socket.SocketChannel;
import ru.lyx.spigot.engine.core.module.sync.processor.SyncProcessorMetadataKeys;
import ru.lyx.spigot.engine.core.module.sync.transport.TransportChannel;
import ru.lyx.spigot.engine.core.module.sync.transport.TransportManager;

@RequiredArgsConstructor
public class CreateClusterNodeProcessor implements SpigotModuleProcessor<SyncModule, SyncContext> {

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("ClusterChannelBootstrapProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessorContext<SyncModule, SyncContext> context) {
        SyncConfigModel configModel = context.getModule().getConfigModel();

        TransportManager transportManager = context.getModuleContext().getTransportManager();

        final SocketChannel socketChannel = new SocketChannel(configModel);
        final TransportChannel channel = new ClusterChannel(transportManager, socketChannel);

        return context.getPreviousTransaction()
                .withMetadata(SyncProcessorMetadataKeys.CHANNEL, channel)
                .thenContinueTo(StartClusterNodeProcessor.class);
    }
}
