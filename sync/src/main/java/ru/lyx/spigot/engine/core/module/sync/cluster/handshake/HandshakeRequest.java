package ru.lyx.spigot.engine.core.module.sync.cluster.handshake;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.core.SpigotServerInfo;
import ru.lyx.spigot.engine.core.module.sync.transport.EmptyMessage;
import ru.lyx.spigot.engine.core.module.sync.transport.TransportMessage;

@Getter
@RequiredArgsConstructor
public class HandshakeRequest extends EmptyMessage {
}
