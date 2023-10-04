package ru.lyx.spigot.engine.test.plugin;

import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.plugin.PluginAttachmentContainer;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;
import ru.lyx.spigot.engine.core.plugin.SpigotBasePlugin;
import ru.lyx.spigot.engine.test.event.TestPlayerJoinQuitListener;
import ru.lyx.spigot.engine.test.module.WorldClearingHandler;

public class TestSpigotBasePlugin extends SpigotBasePlugin {

    @Override
    protected PluginAttachmentContainer<SpigotHandler<?>> ofHandlers(@NotNull SpigotEngine engine) {
        return PluginAttachmentContainer.ofPlugin(this,
                new WorldClearingHandler()
        );
    }

    @Override
    protected AttachmentContainer<Listener> ofListeners(@NotNull SpigotEngine engine) {
        return AttachmentContainer.of(
                new TestPlayerJoinQuitListener(engine)
        );
    }
}
