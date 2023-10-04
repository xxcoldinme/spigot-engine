package ru.lyx.spigot.engine.plugin;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.SpigotEngine;
import ru.lyx.spigot.engine.SpigotEngineContext;

public abstract class SpigotContextPlugin extends JavaPlugin {

    @Getter
    @Setter
    private SpigotEngine engine;

    public abstract void registerPlugin(@NotNull SpigotEngine engine, @NotNull SpigotEngineContext context);
}
