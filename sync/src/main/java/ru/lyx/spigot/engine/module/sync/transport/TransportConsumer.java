package ru.lyx.spigot.engine.module.sync.transport;

@FunctionalInterface
public interface TransportConsumer<T extends TransportMessage> {

    void handle(T message);
}
