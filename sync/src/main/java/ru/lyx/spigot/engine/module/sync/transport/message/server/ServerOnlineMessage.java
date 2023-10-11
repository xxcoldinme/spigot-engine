package ru.lyx.spigot.engine.module.sync.transport.message.server;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ServerOnlineMessage extends ServerMessage {

    private final int online;

    public ServerOnlineMessage(String serverId, int online) {
        super(serverId);
        this.online = online;
    }
}
