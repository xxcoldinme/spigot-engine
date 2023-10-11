package ru.lyx.spigot.engine.module.sync.socket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.lyx.spigot.engine.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.module.sync.socket.handler.ClientSocketChannelHandler;
import ru.lyx.spigot.engine.module.sync.socket.handler.ServerSocketChannelHandler;
import ru.lyx.spigot.engine.module.sync.socket.handler.SocketChannelHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class SocketChannel {

    private final Set<Consumer<byte[]>> dataConsumersSet = new CopyOnWriteArraySet<>();

    private final ExecutorService executorService = Executors.newWorkStealingPool();

    private final Logger logger;
    private final SyncConfigModel config;

    @Getter
    @Setter
    private int connectionID;

    @Getter
    @Setter
    private SocketState state;

    @Getter
    @Setter
    private SocketChannelHandler handler;

    public void start() {
        if (config.isClusterDebugEnabled()) {
            logger.info("Starting a cluster socket channel...");
        }

        final ClientSocketChannelHandler handler = new ClientSocketChannelHandler(executorService, logger);
        handler.handleConnect(this, config);
    }

    public void close() {
        handler.handleClose(this);

        if (config.isClusterDebugEnabled()) {
            logger.info("Cluster socket was closed");
        }
    }

    public void sendData(byte[] data) {
        handler.handleDataSending(this, data);
    }

    public void subscribe(Consumer<byte[]> consumer) {
        dataConsumersSet.add(consumer);
    }

    public void receiveData(byte[] data) {
        dataConsumersSet.forEach(consumer -> consumer.accept(data));
    }

    public boolean isServer() {
        return (handler instanceof ServerSocketChannelHandler);
    }
}
