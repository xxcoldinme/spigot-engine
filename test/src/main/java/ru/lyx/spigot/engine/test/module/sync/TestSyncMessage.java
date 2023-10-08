package ru.lyx.spigot.engine.test.module.sync;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.module.sync.transport.TransportMessage;

@Getter
@ToString
@RequiredArgsConstructor
public class TestSyncMessage implements TransportMessage {

    private final String playerName;
    private final int newPlayerLevel;
}
