package ru.lyx.spigot.engine.module.world;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.module.world.model.WrappedWorldProperties;
import ru.lyx.spigot.engine.module.world.model.WrappedWorld;

import java.util.HashMap;
import java.util.Map;

public class WorldContext implements SpigotContext {

    private final Map<String, WrappedWorld> wrappedWorldMap = new HashMap<>();

    public WrappedWorld createWrapper(@NotNull World world) {
        return new WrappedWorld(world.getName(), toProperties(world));
    }

    public WrappedWorld getWrapper(@NotNull World world) {
        return getWrapper(world.getName());
    }

    public WrappedWorld getWrapper(@NotNull String name) {
        return wrappedWorldMap.get(name.toLowerCase());
    }

    public void store(@NotNull WrappedWorld wrappedWorld) {
        String name = wrappedWorld.getName();
        wrappedWorldMap.put(name.toLowerCase(), wrappedWorld);
    }

    public WrappedWorldProperties toProperties(World world) {
        GameMode defaultGameMode = Bukkit.getDefaultGameMode();
        Difficulty difficulty = world.getDifficulty();

        return new WrappedWorldProperties(defaultGameMode, difficulty,
                SpigotMetadata.create());
    }
}
