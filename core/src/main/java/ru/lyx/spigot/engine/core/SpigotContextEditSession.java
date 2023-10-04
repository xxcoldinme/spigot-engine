package ru.lyx.spigot.engine.core;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.plugin.PluginAttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.SpigotEngineAttachment;
import ru.lyx.spigot.engine.core.context.SpigotIncludedContext;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;

public class SpigotContextEditSession extends SpigotIncludedContext<SpigotEngineContext> {

    private final SpigotContainer mainContainer, sessionContainer;

    public SpigotContextEditSession(SpigotContainer mainContainer, SpigotEngineContext previousContext) {
        super(previousContext);

        this.mainContainer = mainContainer;
        this.sessionContainer = mainContainer.clone();
    }

    public SpigotContextEditSession addHandlers(PluginAttachmentContainer<SpigotHandler<? extends SpigotModule<?>>> container) {
        sessionContainer.getHandlers().addAll(container);
        return this;
    }

    public SpigotContextEditSession addListeners(AttachmentContainer<Listener> container) {
        sessionContainer.getListeners().addAll(container);
        return this;
    }

    public SpigotContextEditSession addCommands(AttachmentContainer<CommandExecutor> container) {
        sessionContainer.getCommands().addAll(container);
        return this;
    }

    public SpigotContextEditSession addRecipes(AttachmentContainer<Recipe> container) {
        sessionContainer.getRecipes().addAll(container);
        return this;
    }

    public SpigotContextEditSession addWorlds(AttachmentContainer<World> container) {
        sessionContainer.getWorlds().addAll(container);
        return this;
    }

    public SpigotContextEditSession addEnabledLinks(PluginAttachmentContainer<SpigotEngineAttachment> container) {
        sessionContainer.getEnablingHooks().addAll(container);
        return this;
    }

    public SpigotContextEditSession addDisabledLinks(PluginAttachmentContainer<SpigotEngineAttachment> container) {
        sessionContainer.getDisablingHooks().addAll(container);
        return this;
    }

    @Override
    public SpigotEngineContext merge() {
        mainContainer.refill(sessionContainer);
        return super.merge();
    }
}
