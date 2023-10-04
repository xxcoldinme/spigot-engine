package ru.lyx.spigot.engine.test.plugin;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngineContext;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactoryHelper;
import ru.lyx.spigot.engine.core.module.sync.SyncModule;
import ru.lyx.spigot.engine.core.module.world.WorldModule;
import ru.lyx.spigot.engine.core.plugin.SpigotContextPlugin;
import ru.lyx.spigot.engine.test.event.TestPlayerJoinQuitListener;

public class TestSpigotContextPlugin extends SpigotContextPlugin {

    @Override
    public void registerPlugin(@NotNull SpigotEngine engine, @NotNull SpigotEngineContext context) {
        context.openEditSession()
                .setListeners(
                        AttachmentContainer.of(
                                new TestPlayerJoinQuitListener(engine),
                                new TestPlayerJoinQuitListener(engine)
                        ))
                .setModules(
                        AttachmentContainer.of(
                                SpigotModuleFactoryHelper.of(SyncModule.class),
                                SpigotModuleFactoryHelper.of(WorldModule.class)
                        ))
                .merge();
    }
}
