package ru.lyx.spigot.engine.core.module.sync.cluster;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.module.sync.SpigotSyncModuleException;
import ru.lyx.spigot.engine.core.module.sync.cluster.handshake.HandshakeRequest;
import ru.lyx.spigot.engine.core.module.sync.cluster.handshake.HandshakeResponse;
import ru.lyx.spigot.engine.core.module.sync.cluster.socket.SocketChannel;
import ru.lyx.spigot.engine.core.module.sync.cluster.socket.SocketState;
import ru.lyx.spigot.engine.core.module.sync.transport.*;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class ClusterChannel implements TransportChannel {

    private static final String HANDSHAKE_QUEUE = "handshake";

    private final TransportManager transportManager;
    private final SocketChannel socketChannel;

    public void start() {
        socketChannel.start();

        if (!socketChannel.isServer()) {
            processHandshakeExchange();
        }
    }

    private void processHandshakeExchange() {
        HandshakeRequest handshakeRequest = new HandshakeRequest();
        TransportObject transportObject = new TransportObject(HANDSHAKE_QUEUE, handshakeRequest);

        socketChannel.sendData(transportManager.toData(transportObject));
        socketChannel.subscribe(bytes -> {

            HandshakeResponse response = SerializationUtils.deserialize(bytes);

            socketChannel.setConnectionID(response.getNodeID());

            socketChannel.resetConsumers();
            socketChannel.setState(SocketState.HANDSHAKE);
        });
    }

    private void validateTransportOperation(String queue) {
        if (socketChannel.getState() != SocketState.ACTIVE) {
            throw new SpigotSyncModuleException("channel state is not active");
        }

        if (HANDSHAKE_QUEUE.equals(queue)) {
            throw new SpigotSyncModuleException("handshake data has already exchanged");
        }
    }

    @Override
    public void sendMessage(@NotNull String queue, @NotNull TransportMessage message) {
        validateTransportOperation(queue);

        byte[] data = transportManager.toData(new TransportObject(queue, message));
        socketChannel.sendData(data);
    }

    @Override
    public <T extends TransportMessage> void subscribe(@NotNull String queue, @NotNull TransportConsumer<T> consumer) {
        validateTransportOperation(queue);
        socketChannel.subscribe(new BytesToMessageConsumer<>(consumer));
    }

    @RequiredArgsConstructor
    private class BytesToMessageConsumer<T extends TransportMessage>
            implements Consumer<byte[]> {

        private final TransportConsumer<T> consumer;

        @SuppressWarnings("unchecked")
        @Override
        public void accept(byte[] data) {
            // todo - add queue name checking.

            TransportObject transportObject = transportManager.fromData(data);
            consumer.handle((T) transportObject.getMessage());
        }
    }
}
