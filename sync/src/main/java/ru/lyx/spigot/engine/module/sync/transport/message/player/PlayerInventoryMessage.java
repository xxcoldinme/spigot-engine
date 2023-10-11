package ru.lyx.spigot.engine.module.sync.transport.message.player;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
public class PlayerInventoryMessage extends PlayerMessage {

    private final ItemStack[] content;

    public PlayerInventoryMessage(String playerName, PlayerInventory inventory) {
        super(playerName);
        this.content = inventory.getStorageContents();
    }

    public PlayerInventoryMessage(Player player) {
        this(player.getName(), player.getInventory());
    }

    public void refreshTo(@NotNull Player player) {
        PlayerInventory inventory = player.getInventory();

        for (int slotId = 0; slotId < content.length; slotId++) {
            inventory.setItem(slotId, content[slotId]);
        }
    }
}
