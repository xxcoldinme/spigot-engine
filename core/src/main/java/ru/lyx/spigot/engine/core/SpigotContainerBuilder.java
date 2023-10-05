package ru.lyx.spigot.engine.core;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.plugin.PluginAttachmentContainer;
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

    public SpigotContainerBuilder addHandlers(PluginAttachmentContainer<SpigotHandler<? extends SpigotModule<?, ?>>> container) {
        sessionContainer.getHandlers().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addListeners(AttachmentContainer<Listener> container) {
        sessionContainer.getListeners().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addCommands(AttachmentContainer<CommandExecutor> container) {
        sessionContainer.getCommands().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addRecipes(AttachmentContainer<Recipe> container) {
        sessionContainer.getRecipes().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addWorlds(AttachmentContainer<World> container) {
        sessionContainer.getWorlds().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addEnabledLinks(PluginAttachmentContainer<SpigotAttachment> container) {
        sessionContainer.getEnablingHooks().addAll(container);
        return this;
    }

    public SpigotContainerBuilder addDisabledLinks(PluginAttachmentContainer<SpigotAttachment> container) {
        sessionContainer.getDisablingHooks().addAll(container);
        return this;
    }

    public final SpigotEngine toEngine() {
        mainContainer.refill(sessionContainer);
        return engine;
    }
}
