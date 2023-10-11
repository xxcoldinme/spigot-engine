package ru.lyx.spigot.engine.module.sync.processor;

import lombok.var;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.module.processor.ProcessController;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.module.sync.SyncContext;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.sync.cluster.ClusterChannelService;
import ru.lyx.spigot.engine.module.sync.cluster.ClusterQueueManager;
import ru.lyx.spigot.engine.module.sync.socket.SocketChannel;

public class CreateChannelProcessor implements SpigotModuleProcessor<SyncModule, SyncContext> {

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("CreateChannelProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessController<SyncModule, SyncContext> controller) {
        var config = controller.getModule().getConfig();
        var context = controller.getContext();

        var logger = controller.getEngine().getLogger();

        var socketChannel = new SocketChannel(logger, config);
        var clusterChannelService =
                new ClusterChannelService(
                        new ClusterQueueManager(), context.getTransportManager());

        var channel = clusterChannelService.createStartedChannel(
                config.isClusterDebugEnabled() ? logger : null, socketChannel);

        context.setChannel(channel);

        return controller.getPreviousTransaction()
                .withMetadata(SyncContext.CHANNEL_PROPERTY, channel);
    }
}
