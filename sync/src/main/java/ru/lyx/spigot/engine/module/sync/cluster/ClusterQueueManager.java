package ru.lyx.spigot.engine.module.sync.cluster;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.module.sync.transport.TransportConsumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ClusterQueueManager {

    public static final String DEFAULT_NAME = "#default";

    private final Map<String, PocketContainer<TransportConsumer<?>>> subscribedQueuesMap
            = new ConcurrentHashMap<>();

    public synchronized void store(@NotNull String queue, @NotNull TransportConsumer<?> consumer) {
        subscribedQueuesMap.computeIfAbsent(queue, __ -> PocketContainer.empty())
                .add(consumer);
    }

    public synchronized PocketContainer<TransportConsumer<?>> peek(@NotNull String queue) {
        return subscribedQueuesMap.get(queue);
    }
}
