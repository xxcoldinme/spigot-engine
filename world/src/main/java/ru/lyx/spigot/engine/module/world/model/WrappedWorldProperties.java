package ru.lyx.spigot.engine.module.world.model;

import lombok.*;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class WrappedWorldProperties {

    private GameMode defaultGameMode;
    private Difficulty defaultDifficulty;

    private final SpigotMetadata rulesMetadata;
}
