package ru.lyx.spigot.engine.module.sync.cluster.socket;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.lyx.spigot.engine.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.module.sync.cluster.socket.handler.ClientSocketChannelHandler;
import ru.lyx.spigot.engine.module.sync.cluster.socket.handler.SocketChannelHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class SocketChannel {

    private final Set<Consumer<byte[]>> dataConsumersSet = new HashSet<>();

    private final ExecutorService executorService = Executors.newWorkStealingPool();
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
        final ClientSocketChannelHandler handler = new ClientSocketChannelHandler(executorService);
        handler.handleConnect(this, config);
    }

    public void close() {
        handler.handleClose(this);
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
}
