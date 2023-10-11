package ru.lyx.spigot.engine.module.sync.test.transport;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.module.sync.transport.TransportManager;
import ru.lyx.spigot.engine.module.sync.transport.message.TransportMessage;
import ru.lyx.spigot.engine.module.sync.transport.TransportObject;
import ru.lyx.spigot.engine.module.sync.util.GZipCompressor;

public class TransportObjectTest {

    @ToString
    @RequiredArgsConstructor
    static class TestMessage implements TransportMessage {

        private final String username;
    }

    public static void main(String[] args) {
        TransportManager transportManager = new TransportManager(
                new GZipCompressor()
        );

        TestMessage message = new TestMessage("lyx");
        TransportObject transportObject = new TransportObject("UserInfo", message);

        byte[] bytes = transportManager.toData(transportObject);

        System.out.println(transportManager.fromData(bytes));
    }
}
