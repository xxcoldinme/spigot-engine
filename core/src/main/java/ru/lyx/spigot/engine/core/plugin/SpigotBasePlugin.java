package ru.lyx.spigot.engine.core.plugin;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.plugin.PluginAttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.SpigotEngineAttachment;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;

public abstract class SpigotBasePlugin
        extends SpigotContextPlugin {

    @Override
    public final void registerPlugin(@NotNull SpigotEngine engine) {
        final PluginAttachmentContainer<SpigotEngineAttachment> enabledLinkAttachment
                = PluginAttachmentContainer.<SpigotEngineAttachment>empty()
                    .add(this, (pl) -> doEnable(engine));

        final PluginAttachmentContainer<SpigotEngineAttachment> disabledLinkAttachment
                = PluginAttachmentContainer.<SpigotEngineAttachment>empty()
                    .add(this, (pl) -> doDisable(engine));

        final PluginAttachmentContainer<SpigotHandler<?>> handlersContainer = ofHandlers(engine);
        final AttachmentContainer<Listener> listenersContainer = ofListeners(engine);
        final AttachmentContainer<CommandExecutor> commandsContainer = ofCommands(engine);
        final AttachmentContainer<Recipe> recipesContainer = ofRecipes(engine);
        final AttachmentContainer<World> worldsContainer = ofWorlds(engine);

        engine.doEditContainers()
                .addHandlers(handlersContainer)
                .addListeners(listenersContainer)
                .addCommands(commandsContainer)
                .addRecipes(recipesContainer)
                .addWorlds(worldsContainer)

                .addEnabledLinks(enabledLinkAttachment)
                .addDisabledLinks(disabledLinkAttachment)
                .toEngine();
    }

    protected PluginAttachmentContainer<SpigotHandler<?>> ofHandlers(@NotNull SpigotEngine engine) {
        // override me.
        return PluginAttachmentContainer.empty();
    }

    protected AttachmentContainer<Listener> ofListeners(@NotNull SpigotEngine engine) {
        // override me.
        return AttachmentContainer.empty();
    }

    protected AttachmentContainer<CommandExecutor> ofCommands(@NotNull SpigotEngine engine) {
        // override me.
        return AttachmentContainer.empty();
    }

    protected AttachmentContainer<Recipe> ofRecipes(@NotNull SpigotEngine engine) {
        // override me.
        return AttachmentContainer.empty();
    }

    protected AttachmentContainer<World> ofWorlds(@NotNull SpigotEngine engine) {
        // override me.
        return AttachmentContainer.empty();
    }

    protected void doEnable(@NotNull SpigotEngine engine) {
        // override me.
    }

    protected void doDisable(@NotNull SpigotEngine engine) {
        // override me.
    }
}
