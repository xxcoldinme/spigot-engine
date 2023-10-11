package ru.lyx.spigot.engine.module.sync.transport.message.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.lyx.spigot.engine.module.sync.transport.message.TransportMessage;

@Getter
@ToString
@RequiredArgsConstructor
public abstract class PlayerMessage implements TransportMessage {

    private final String playerName;

    public PlayerMessage(Player player) {
        this(player.getName());
    }

    public Player getPlayerExact() {
        return Bukkit.getPlayerExact(playerName);
    }
}
