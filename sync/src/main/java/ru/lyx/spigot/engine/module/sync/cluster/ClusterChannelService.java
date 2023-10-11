package ru.lyx.spigot.engine.module.sync.cluster;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lyx.spigot.engine.module.sync.SpigotSyncException;
import ru.lyx.spigot.engine.module.sync.socket.SocketChannel;
import ru.lyx.spigot.engine.module.sync.transport.TransportManager;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class ClusterChannelService {

    private final ClusterQueueManager clusterQueueManager;
    private final TransportManager transportManager;

    private ClusterChannel channel;

    public ClusterChannel createStartedChannel(@Nullable Logger logger, @NotNull SocketChannel socketChannel) {
        if (channel != null) {
            throw new SpigotSyncException("channel has already created");
        }

        BytesToMessageConsumer bytesToMessageConsumer = new BytesToMessageConsumer(logger, clusterQueueManager, transportManager);

        ClusterChannel channel = new ClusterChannel(clusterQueueManager, transportManager, socketChannel);
        channel.start(bytesToMessageConsumer);

        return this.channel = channel;
    }
}
