package ru.lyx.spigot.engine.module.sync.transport.message.player;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
public class PlayerChatMessage extends PlayerMessage {

    private final String input;

    public PlayerChatMessage(String playerName, String input) {
        super(playerName);
        this.input = input;
    }

    public PlayerChatMessage(Player player, String input) {
        this(player.getName(), input);
    }

    public PlayerChatMessage(AsyncPlayerChatEvent event) {
        this(event.getPlayer(), event.getMessage());
    }
}
