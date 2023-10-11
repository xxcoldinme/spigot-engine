package ru.lyx.spigot.engine.module.sync;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.reflection.GetGenericTypeHandler;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;
import ru.lyx.spigot.engine.module.sync.socket.SocketChannel;
import ru.lyx.spigot.engine.module.sync.socket.SocketState;
import ru.lyx.spigot.engine.module.sync.transport.*;
import ru.lyx.spigot.engine.module.sync.transport.message.TransportMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ClusterChannel implements TransportChannel {

    private final ReflectionService reflectionService;
    private final TransportManager transportManager;
    private final SocketChannel socketChannel;

    private final Map<String, PocketContainer<TransportConsumer<?>>> subscribedQueuesMap
            = new ConcurrentHashMap<>();

    public void start() {
        socketChannel.start();
    }

    public void shutdown() {
        socketChannel.close();
    }

    private void subscribeQueue(@NotNull String queue, @NotNull TransportConsumer<?> consumer) {
        subscribedQueuesMap.computeIfAbsent(queue, __ -> PocketContainer.empty())
                .add(consumer);
    }

    private void validateTransportOperation() {
        if (socketChannel.getState() != SocketState.ACTIVE) {
            throw new SpigotSyncException("channel state is not active");
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
            final PocketContainer<TransportConsumer<?>> consumers
                    = subscribedQueuesMap.get(transportObject.getQueue());

            System.out.println("Consumers = " + consumers); // debugging for tests.

            consumers.getElements()
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
            return reflectionService.getGenericType(
                    SpigotMetadata.create()
                            .with(GetGenericTypeHandler.GENERIC_TYPE_INDEX, 0)
                            .with(GetGenericTypeHandler.TARGET_CLASS, consumer.getClass()));
        }
    }
}
