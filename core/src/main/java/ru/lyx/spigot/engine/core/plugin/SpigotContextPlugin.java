package ru.lyx.spigot.engine.core.plugin;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;

public abstract class SpigotContextPlugin extends JavaPlugin {

    @Getter
    @Setter
    private SpigotEngine engine;

    public abstract void registerPlugin(@NotNull SpigotEngine engine);

    @Override
    public final void onDisable() {
        // can`t override that.
    }

    @Override
    public final void onEnable() {
        // can`t override that.
    }
}
