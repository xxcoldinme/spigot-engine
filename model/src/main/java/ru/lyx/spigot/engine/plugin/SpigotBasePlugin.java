package ru.lyx.spigot.engine.plugin;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.SpigotEngineContext;
import ru.lyx.spigot.engine.SpigotEngine;
import ru.lyx.spigot.engine.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.attachment.SpigotEngineAttachment;
import ru.lyx.spigot.engine.module.SpigotModuleFactory;

public abstract class SpigotBasePlugin
        extends SpigotContextPlugin {

    @Override
    public final void registerPlugin(@NotNull SpigotEngine engine, @NotNull SpigotEngineContext context) {
        final AttachmentContainer<SpigotModuleFactory<?>> modulesContainer = ofModules(engine);
        final AttachmentContainer<Listener> listenersContainer = ofListeners(engine);
        final AttachmentContainer<CommandExecutor> commandsContainer = ofCommands(engine);
        final AttachmentContainer<Recipe> recipesContainer = ofRecipes(engine);
        final AttachmentContainer<World> worldsContainer = ofWorlds(engine);

        final AttachmentContainer<SpigotEngineAttachment> enabledLinkAttachment
                = AttachmentContainer.of(plugin -> doEnable(engine));
        final AttachmentContainer<SpigotEngineAttachment> disabledLinkAttachment
                = AttachmentContainer.of(plugin -> doDisable(engine));

        context.openEditSession()
                .setModules(modulesContainer)
                .setListeners(listenersContainer)
                .setCommands(commandsContainer)
                .setRecipes(recipesContainer)
                .setWorlds(worldsContainer)

                .setEnabledLinks(enabledLinkAttachment)
                .setDisabledLinks(disabledLinkAttachment)
                .merge();
    }

    protected AttachmentContainer<SpigotModuleFactory<?>> ofModules(@NotNull SpigotEngine engine) {
        // override me.
        return AttachmentContainer.empty();
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
