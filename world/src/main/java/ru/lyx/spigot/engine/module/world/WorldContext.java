package ru.lyx.spigot.engine.module.world;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.module.world.model.WrappedWorld;

import java.util.HashMap;
import java.util.Map;

public class WorldContext implements SpigotContext {

    private final Map<String, WrappedWorld> wrappedWorldMap = new HashMap<>();

    public WrappedWorld createWrapper(@NotNull World world) {
        return null; // todo
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
}
