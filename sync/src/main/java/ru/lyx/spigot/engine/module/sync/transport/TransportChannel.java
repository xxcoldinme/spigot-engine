package ru.lyx.spigot.engine.module.sync.transport;

import org.jetbrains.annotations.NotNull;

public interface TransportChannel {

    <T extends TransportMessage> void subscribe(@NotNull String queue, @NotNull TransportConsumer<T> consumer);

    void sendMessage(@NotNull String queue, @NotNull TransportMessage message);
}
