package ru.lyx.spigot.engine.module.sync.transport.message.server;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

@Getter
@ToString
public class ServerPlayersListMessage extends ServerMessage {

    private final String[] playersNames;

    public ServerPlayersListMessage(String serverId, String[] playersNames) {
        super(serverId);
        this.playersNames = playersNames;
    }

    public ServerPlayersListMessage(String serverId, Collection<? extends Player> players) {
        this(serverId, players.stream().map(Player::getName).toArray(String[]::new));
    }

    public ServerPlayersListMessage(String serverId) {
        this(serverId, Bukkit.getOnlinePlayers());
    }
}
