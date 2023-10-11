package ru.lyx.spigot.engine.module.sync.transport.message.player;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLevelChangeEvent;

@Getter
@ToString
public class PlayerLevelingMessage extends PlayerMessage {

    private final int oldLevel;
    private final int newLevel;

    public PlayerLevelingMessage(String playerName, int oldLevel, int newLevel) {
        super(playerName);

        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }

    public PlayerLevelingMessage(Player player,  int oldLevel, int newLevel) {
        this(player.getName(), oldLevel, newLevel);
    }

    public PlayerLevelingMessage(Player player, int newLevel) {
        this(player, player.getLevel(), newLevel);
    }

    public PlayerLevelingMessage(PlayerLevelChangeEvent event) {
        this(event.getPlayer(), event.getNewLevel(), event.getOldLevel());
    }
}
