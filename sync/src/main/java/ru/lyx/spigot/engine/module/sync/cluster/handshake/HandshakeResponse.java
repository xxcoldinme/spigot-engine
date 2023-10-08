package ru.lyx.spigot.engine.module.sync.cluster.handshake;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.module.sync.transport.TransportMessage;

@Getter
@RequiredArgsConstructor
public class HandshakeResponse implements TransportMessage {

    private final int nodeID;
}
