package ru.lyx.spigot.engine.module.sync.cluster;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.module.sync.SpigotSyncException;
import ru.lyx.spigot.engine.module.sync.socket.SocketChannel;
import ru.lyx.spigot.engine.module.sync.socket.SocketState;
import ru.lyx.spigot.engine.module.sync.transport.TransportChannel;
import ru.lyx.spigot.engine.module.sync.transport.TransportConsumer;
import ru.lyx.spigot.engine.module.sync.transport.TransportManager;
import ru.lyx.spigot.engine.module.sync.transport.TransportObject;
import ru.lyx.spigot.engine.module.sync.transport.message.TransportMessage;

@RequiredArgsConstructor
public class ClusterChannel implements TransportChannel {

    private final ClusterQueueManager clusterQueueManager;
    private final TransportManager transportManager;
    private final SocketChannel socketChannel;

    public void start(BytesToMessageConsumer bytesToMessageConsumer) {
        socketChannel.start();
        socketChannel.subscribe(bytesToMessageConsumer);
    }

    public void shutdown() {
        socketChannel.close();
    }

    private void validateTransportOperation() {
        if (socketChannel.getState() != SocketState.ACTIVE) {
            throw new SpigotSyncException("channel state is not active");
        }
    }

    @Override
    public void broadcast(@NotNull String queue, @NotNull TransportMessage message) {
        validateTransportOperation();

        byte[] data = transportManager.toData(new TransportObject(queue, message));
        socketChannel.sendData(data);
    }

    @Override
    public <T extends TransportMessage> void subscribe(@NotNull String queue, @NotNull TransportConsumer<T> consumer) {
        validateTransportOperation();
        clusterQueueManager.store(queue, consumer);
    }

    public boolean isParentNode() {
        return socketChannel.isServer();
    }
}
