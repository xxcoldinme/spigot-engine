package ru.lyx.spigot.engine.core;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.pocketcontainer.plugin.PocketPluginContainer;
import ru.lyx.spigot.engine.core.attachment.SpigotAttachment;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;

public class SpigotContainerBuilder implements SpigotContext {

    private final SpigotEngine engine;
    private final SpigotContainer mainContainer, sessionContainer;

    public SpigotContainerBuilder(SpigotEngine engine, SpigotContainer mainContainer) {
        this.engine = engine;
        this.mainContainer = mainContainer;
        this.sessionContainer = mainContainer.clone();
    }

    public SpigotContainerBuilder addHandlers(PocketPluginContainer<SpigotHandler<? extends SpigotModule<?, ?>>> container) {
        sessionContainer.getHandlers().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addListeners(PocketContainer<Listener> container) {
        sessionContainer.getListeners().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addCommands(PocketContainer<CommandExecutor> container) {
        sessionContainer.getCommands().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addRecipes(PocketContainer<Recipe> container) {
        sessionContainer.getRecipes().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addWorlds(PocketContainer<World> container) {
        sessionContainer.getWorlds().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addEnabledLinks(PocketPluginContainer<SpigotAttachment> container) {
        sessionContainer.getEnablingHooks().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addDisabledLinks(PocketPluginContainer<SpigotAttachment> container) {
        sessionContainer.getDisablingHooks().addAll(container);
        return this;
    }

    public final SpigotEngine toEngine() {
        mainContainer.refill(sessionContainer);
        return engine;
    }
}
