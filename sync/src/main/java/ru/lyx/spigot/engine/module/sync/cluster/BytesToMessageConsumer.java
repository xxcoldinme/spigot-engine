package ru.lyx.spigot.engine.module.sync.cluster;

import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.module.sync.transport.TransportConsumer;
import ru.lyx.spigot.engine.module.sync.transport.TransportManager;
import ru.lyx.spigot.engine.module.sync.transport.TransportObject;
import ru.lyx.spigot.engine.module.sync.transport.message.TransportMessage;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class BytesToMessageConsumer implements Consumer<byte[]> {

    private final ClusterQueueManager clusterQueueManager;
    private final TransportManager transportManager;

    @SuppressWarnings({"unchecked"})
    @Override
    public void accept(byte[] data) {
        final TransportObject transportObject = transportManager.fromData(data);
        final PocketContainer<TransportConsumer<?>> consumersContainer
                = clusterQueueManager.peek(transportObject.getQueue());

        consumersContainer
                .forEach(consumer -> {

                    TransportMessage message = transportObject.getMessage();
                    ((TransportConsumer<TransportMessage>) consumer).handle(message);
                });
    }
}