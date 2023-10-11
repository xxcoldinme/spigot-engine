package ru.lyx.spigot.engine.test.plugin;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.pocketcontainer.plugin.PocketPluginContainer;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;
import ru.lyx.spigot.engine.core.plugin.SpigotBasePlugin;
import ru.lyx.spigot.engine.test.event.TestPlayerJoinQuitListener;
import ru.lyx.spigot.engine.test.module.world.WorldClearingHandler;

public class TestSpigotBasePlugin extends SpigotBasePlugin {

    @Override
    protected PocketPluginContainer<SpigotHandler<?>> ofHandlers(@NotNull SpigotEngine engine) {
        return PocketPluginContainer.ofPlugin(this,
                new WorldClearingHandler()
        );
    }

    @Override
    protected PocketContainer<Listener> ofListeners(@NotNull SpigotEngine engine) {
        return PocketContainer.of(
                new TestPlayerJoinQuitListener(engine)
        );
    }
}
