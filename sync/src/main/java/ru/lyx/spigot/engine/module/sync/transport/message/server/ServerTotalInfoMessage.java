package ru.lyx.spigot.engine.module.sync.transport.message.server;

import lombok.Getter;
import lombok.ToString;
import ru.lyx.spigot.engine.core.SpigotServerInfo;

@Getter
@ToString
public class ServerTotalInfoMessage extends ServerMessage {

    private final SpigotServerInfo subject;

    public ServerTotalInfoMessage(String serverId, SpigotServerInfo subject) {
        super(serverId);
        this.subject = subject;
    }
}
