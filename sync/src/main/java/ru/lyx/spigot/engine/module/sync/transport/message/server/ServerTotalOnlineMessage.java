package ru.lyx.spigot.engine.module.sync.transport.message.server;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ServerTotalOnlineMessage extends ServerOnlineMessage {

    private final int online;
    private final int maxOnline;

    public ServerTotalOnlineMessage(String serverId, int online, int maxOnline) {
        super(serverId, online);

        this.online = online;
        this.maxOnline = maxOnline;
    }
}
