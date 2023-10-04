package ru.lyx.spigot.engine;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import ru.lyx.spigot.engine.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.attachment.SpigotEngineAttachment;
import ru.lyx.spigot.engine.context.SpigotIncludedContext;
import ru.lyx.spigot.engine.module.SpigotModuleFactory;

public class SpigotContextEditSession extends SpigotIncludedContext<SpigotEngineContext> {

    private final SpigotContainer mainContainer, sessionContainer;

    public SpigotContextEditSession(SpigotContainer mainContainer, SpigotEngineContext previousContext) {
        super(previousContext);

        this.mainContainer = mainContainer;
        this.sessionContainer = mainContainer.clone();
    }

    public SpigotContextEditSession addModules(AttachmentContainer<SpigotModuleFactory<?>> container) {
        sessionContainer.getModules().addAll(container);
        return this;
    }

    public SpigotContextEditSession setModules(AttachmentContainer<SpigotModuleFactory<?>> container) {
        sessionContainer.getModules().setAll(container);
        return this;
    }

    public SpigotContextEditSession addListeners(AttachmentContainer<Listener> container) {
        sessionContainer.getListeners().addAll(container);
        return this;
    }

    public SpigotContextEditSession setListeners(AttachmentContainer<Listener> container) {
        sessionContainer.getListeners().setAll(container);
        return this;
    }

    public SpigotContextEditSession addCommands(AttachmentContainer<CommandExecutor> container) {
        sessionContainer.getCommands().addAll(container);
        return this;
    }

    public SpigotContextEditSession setCommands(AttachmentContainer<CommandExecutor> container) {
        sessionContainer.getCommands().setAll(container);
        return this;
    }

    public SpigotContextEditSession addRecipes(AttachmentContainer<Recipe> container) {
        sessionContainer.getRecipes().addAll(container);
        return this;
    }

    public SpigotContextEditSession setRecipes(AttachmentContainer<Recipe> container) {
        sessionContainer.getRecipes().setAll(container);
        return this;
    }

    public SpigotContextEditSession addWorlds(AttachmentContainer<World> container) {
        sessionContainer.getWorlds().addAll(container);
        return this;
    }

    public SpigotContextEditSession setWorlds(AttachmentContainer<World> container) {
        sessionContainer.getWorlds().setAll(container);
        return this;
    }

    public SpigotContextEditSession addEnabledLinks(AttachmentContainer<SpigotEngineAttachment> container) {
        sessionContainer.getEnablingHooks().addAll(container);
        return this;
    }

    public SpigotContextEditSession setEnabledLinks(AttachmentContainer<SpigotEngineAttachment> container) {
        sessionContainer.getEnablingHooks().setAll(container);
        return this;
    }

    public SpigotContextEditSession addDisabledLinks(AttachmentContainer<SpigotEngineAttachment> container) {
        sessionContainer.getDisablingHooks().addAll(container);
        return this;
    }

    public SpigotContextEditSession setDisabledLinks(AttachmentContainer<SpigotEngineAttachment> container) {
        sessionContainer.getDisablingHooks().setAll(container);
        return this;
    }

    @Override
    public SpigotEngineContext merge() {
        mainContainer.refill(sessionContainer);
        return super.merge();
    }
}
