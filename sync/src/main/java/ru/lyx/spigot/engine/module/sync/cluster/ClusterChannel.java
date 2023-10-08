package ru.lyx.spigot.engine.module.sync.cluster;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.module.sync.SpigotSyncModuleException;
import ru.lyx.spigot.engine.module.sync.cluster.handshake.HandshakeRequest;
import ru.lyx.spigot.engine.module.sync.cluster.handshake.HandshakeResponse;
import ru.lyx.spigot.engine.module.sync.cluster.socket.SocketChannel;
import ru.lyx.spigot.engine.module.sync.cluster.socket.SocketState;
import ru.lyx.spigot.engine.core.reflection.GetGenericTypeHandler;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;
import ru.lyx.spigot.engine.module.sync.transport.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ClusterChannel implements TransportChannel {

    private static final ReflectionService REFLECTION_SERVICE
            = new ReflectionService(
                    Logger.getLogger("ClusterChannel"));

    private final TransportManager transportManager;
    private final SocketChannel socketChannel;

    private final Map<String, AttachmentContainer<TransportConsumer<?>>> subscribedQueuesMap
            = new ConcurrentHashMap<>();

    public void start() {
        socketChannel.start();

        if (!socketChannel.isServer()) {
            processHandshakeExchange();
        }
    }

    private void processHandshakeExchange() {
        HandshakeRequest handshakeRequest = new HandshakeRequest();

        socketChannel.sendData(SerializationUtils.serialize(handshakeRequest));
        socketChannel.subscribe(bytes -> {

            HandshakeResponse response = SerializationUtils.deserialize(bytes);
            processHandshakeResponse(response);
        });
    }

    private void processHandshakeResponse(HandshakeResponse response) {
        socketChannel.setConnectionID(response.getNodeID());

        socketChannel.resetConsumers();
        socketChannel.setState(SocketState.HANDSHAKE);
    }

    private void subscribeQueue(@NotNull String queue, @NotNull TransportConsumer<?> consumer) {
        subscribedQueuesMap.computeIfAbsent(queue, __ -> AttachmentContainer.empty())
                .add(consumer);
    }

    private void validateTransportOperation() {
        if (socketChannel.getState() != SocketState.ACTIVE) {
            throw new SpigotSyncModuleException("channel state is not active");
        }
    }

    @Override
    public void sendMessage(@NotNull String queue, @NotNull TransportMessage message) {
        validateTransportOperation();

        byte[] data = transportManager.toData(new TransportObject(queue, message));
        socketChannel.sendData(data);
    }

    @Override
    public <T extends TransportMessage> void subscribe(@NotNull String queue, @NotNull TransportConsumer<T> consumer) {
        validateTransportOperation();

        subscribeQueue(queue, consumer);
        socketChannel.subscribe(new BytesToMessageConsumer<>());
    }

    @RequiredArgsConstructor
    private class BytesToMessageConsumer<T extends TransportMessage>
            implements Consumer<byte[]> {

        @SuppressWarnings({"EqualsBetweenInconvertibleTypes", "unchecked"})
        @Override
        public void accept(byte[] data) {
            final TransportObject transportObject = transportManager.fromData(data);
            final AttachmentContainer<TransportConsumer<?>> consumers
                    = subscribedQueuesMap.get(transportObject.getQueue());

            System.out.println("Consumers" + consumers);

            consumers.getDefinitions()
                    .forEach(transportConsumer -> {

                        final TransportMessage message = transportObject.getMessage();
                        final Class<?> messageType = getMessageType(transportConsumer);

                        System.out.println("MessageType = " + messageType);    //  ]...] Debugging for test running
                        System.out.println("Message = " + message.getClass()); //  ]...] a framework.

                        if (message.equals(messageType)) {
                            ((TransportConsumer<T>) transportConsumer).handle((T) message);
                        }
                    });
        }

        private Class<?> getMessageType(TransportConsumer<?> consumer) {
            return REFLECTION_SERVICE.getGenericType(
                    SpigotMetadata.create()
                            .with(GetGenericTypeHandler.TARGET_CLASS, consumer.getClass())
                            .with(GetGenericTypeHandler.GENERIC_TYPE_INDEX, 0));
        }
    }
}
