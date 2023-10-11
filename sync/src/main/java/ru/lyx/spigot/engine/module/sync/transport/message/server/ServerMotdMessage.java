package ru.lyx.spigot.engine.module.sync.transport.message.server;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ServerMotdMessage extends ServerMessage {

    private final String motd;

    public ServerMotdMessage(String serverId, String motd) {
        super(serverId);
        this.motd = motd;
    }
}
