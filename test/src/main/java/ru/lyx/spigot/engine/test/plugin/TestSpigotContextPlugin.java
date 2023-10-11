package ru.lyx.spigot.engine.test.plugin;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.pocketcontainer.plugin.PocketPluginContainer;
import ru.lyx.spigot.engine.core.plugin.SpigotContextPlugin;
import ru.lyx.spigot.engine.test.event.TestPlayerJoinQuitListener;
import ru.lyx.spigot.engine.test.module.world.WorldClearingHandler;

public class TestSpigotContextPlugin extends SpigotContextPlugin {

    @Override
    public void registerPlugin(@NotNull SpigotEngine engine) {
        engine.doEditContainers()
                .addHandlers(
                        PocketPluginContainer.ofPlugin(this, new WorldClearingHandler()))
                .addListeners(
                        PocketContainer.of(
                                new TestPlayerJoinQuitListener(engine),
                                new TestPlayerJoinQuitListener(engine)
                        ))
                .toEngine();
    }
}
