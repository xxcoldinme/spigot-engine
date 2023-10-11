package ru.lyx.spigot.engine.module.sync.transport.message.player;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerExpChangeEvent;

@Getter
@ToString
public class PlayerExperienceMessage extends PlayerMessage {

    private final float oldExperience;
    private final float newExperience;

    public PlayerExperienceMessage(String playerName, float oldExperience, float newExperience) {
        super(playerName);

        this.oldExperience = oldExperience;
        this.newExperience = newExperience;
    }

    public PlayerExperienceMessage(Player player, float oldExperience, float newExperience) {
        this(player.getName(), oldExperience, newExperience);
    }

    public PlayerExperienceMessage(Player player, float newExperience) {
        this(player, player.getExp(), newExperience);
    }

    public PlayerExperienceMessage(PlayerExpChangeEvent event) {
        this(event.getPlayer(), event.getPlayer().getExp(), event.getPlayer().getExp() + event.getAmount());
    }
}
