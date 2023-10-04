package ru.lyx.spigot.engine.test.plugin;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.SpigotEngineContext;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.plugin.PluginAttachmentContainer;
import ru.lyx.spigot.engine.core.plugin.SpigotContextPlugin;
import ru.lyx.spigot.engine.test.event.TestPlayerJoinQuitListener;
import ru.lyx.spigot.engine.test.module.WorldClearingHandler;

public class TestSpigotContextPlugin extends SpigotContextPlugin {

    @Override
    public void registerPlugin(@NotNull SpigotEngine engine, @NotNull SpigotEngineContext context) {
        context.openEditSession()
                .addHandlers(PluginAttachmentContainer.ofPlugin(this, new WorldClearingHandler()))
                .addListeners(
                        AttachmentContainer.of(
                                new TestPlayerJoinQuitListener(engine),
                                new TestPlayerJoinQuitListener(engine)
                        ))
                .merge();
    }
}
