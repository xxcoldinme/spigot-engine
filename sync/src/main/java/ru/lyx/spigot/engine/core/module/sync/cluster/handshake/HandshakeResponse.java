package ru.lyx.spigot.engine.core.module.sync.cluster.handshake;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.core.module.sync.transport.TransportMessage;

@Getter
@RequiredArgsConstructor
public class HandshakeResponse implements TransportMessage {

    private final int nodeID;
}
