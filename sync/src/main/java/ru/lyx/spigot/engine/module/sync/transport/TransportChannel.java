package ru.lyx.spigot.engine.module.sync.transport;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.module.sync.transport.message.TransportMessage;

/**
 * A channel used for transporting messages between
 * cluster nodes, as well as receiving
 * and processing incoming cluster messages.
 */
public interface TransportChannel {

    /**
     * Subscribe to incoming cluster messages.
     *
     * @param queue Name of cluster queue.
     * @param consumer Handler of incoming messages.
     */
    <T extends TransportMessage> void subscribe(@NotNull String queue, @NotNull TransportConsumer<T> consumer);

    /**
     * Sending a general messages to all cluster nodes.
     *
     * @param queue Name of cluster queue.
     * @param message Outgoing message.
     */
    void broadcast(@NotNull String queue, @NotNull TransportMessage message);
}
