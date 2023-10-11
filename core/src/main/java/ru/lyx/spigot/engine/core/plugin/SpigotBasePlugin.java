package ru.lyx.spigot.engine.core.plugin;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.pocketcontainer.plugin.PocketPluginContainer;
import ru.lyx.spigot.engine.core.attachment.SpigotAttachment;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;

public abstract class SpigotBasePlugin
        extends SpigotContextPlugin {

    @Override
    public final void registerPlugin(@NotNull SpigotEngine engine) {
        final PocketPluginContainer<SpigotAttachment> enabledLinkAttachment
                = PocketPluginContainer.<SpigotAttachment>empty()
                    .add(this, (pl) -> doEnable(engine));

        final PocketPluginContainer<SpigotAttachment> disabledLinkAttachment
                = PocketPluginContainer.<SpigotAttachment>empty()
                    .add(this, (pl) -> doDisable(engine));

        final PocketPluginContainer<SpigotHandler<?>> handlersContainer = ofHandlers(engine);
        final PocketContainer<Listener> listenersContainer = ofListeners(engine);
        final PocketContainer<CommandExecutor> commandsContainer = ofCommands(engine);
        final PocketContainer<Recipe> recipesContainer = ofRecipes(engine);
        final PocketContainer<World> worldsContainer = ofWorlds(engine);

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

    protected PocketPluginContainer<SpigotHandler<?>> ofHandlers(@NotNull SpigotEngine engine) {
        // override me.
        return PocketPluginContainer.empty();
    }

    protected PocketContainer<Listener> ofListeners(@NotNull SpigotEngine engine) {
        // override me.
        return PocketContainer.empty();
    }

    protected PocketContainer<CommandExecutor> ofCommands(@NotNull SpigotEngine engine) {
        // override me.
        return PocketContainer.empty();
    }

    protected PocketContainer<Recipe> ofRecipes(@NotNull SpigotEngine engine) {
        // override me.
        return PocketContainer.empty();
    }

    protected PocketContainer<World> ofWorlds(@NotNull SpigotEngine engine) {
        // override me.
        return PocketContainer.empty();
    }

    protected void doEnable(@NotNull SpigotEngine engine) {
        // override me.
    }

    protected void doDisable(@NotNull SpigotEngine engine) {
        // override me.
    }
}
