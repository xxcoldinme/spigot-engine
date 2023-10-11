package ru.lyx.spigot.engine.module.sync.transport.message.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.module.sync.transport.message.TransportMessage;

@Getter
@ToString
@RequiredArgsConstructor
public abstract class ServerMessage implements TransportMessage {

    private final String serverId;
}
