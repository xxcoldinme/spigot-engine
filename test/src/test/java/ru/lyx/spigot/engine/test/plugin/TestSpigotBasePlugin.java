package ru.lyx.spigot.engine.test.plugin;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.SpigotEngine;
import ru.lyx.spigot.engine.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.module.SpigotModuleFactory;
import ru.lyx.spigot.engine.module.SpigotModuleFactoryHelper;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.world.WorldModule;
import ru.lyx.spigot.engine.plugin.SpigotBasePlugin;
import ru.lyx.spigot.engine.test.event.TestPlayerJoinQuitListener;

public class TestSpigotBasePlugin extends SpigotBasePlugin {

    @Override
    protected AttachmentContainer<SpigotModuleFactory<?>> ofModules(@NotNull SpigotEngine engine) {
        return AttachmentContainer.of(
                SpigotModuleFactoryHelper.of(SyncModule.class),
                SpigotModuleFactoryHelper.of(WorldModule.class)
        );
    }

    @Override
    protected AttachmentContainer<Listener> ofListeners(@NotNull SpigotEngine engine) {
        return AttachmentContainer.of(
                new TestPlayerJoinQuitListener(engine)
        );
    }


}
