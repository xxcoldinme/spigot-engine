package ru.lyx.spigot.engine.module.sync.transport;

import ru.lyx.spigot.engine.module.sync.transport.message.TransportMessage;

@FunctionalInterface
public interface TransportConsumer<T extends TransportMessage> {

    void handle(T message);
}
