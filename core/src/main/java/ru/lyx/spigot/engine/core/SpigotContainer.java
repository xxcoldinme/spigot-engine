package ru.lyx.spigot.engine.core;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.SpigotAttachment;
import ru.lyx.spigot.engine.core.attachment.plugin.PluginAttachmentContainer;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;

@Getter
@ToString
public final class SpigotContainer {

    private final PluginAttachmentContainer<SpigotAttachment> enablingHooks
            = PluginAttachmentContainer.empty();
    private final PluginAttachmentContainer<SpigotAttachment> disablingHooks
            = PluginAttachmentContainer.empty();

    private final PluginAttachmentContainer<SpigotHandler<?>> handlers = PluginAttachmentContainer.empty();

    private final AttachmentContainer<CommandExecutor> commands = AttachmentContainer.empty();
    private final AttachmentContainer<Listener> listeners = AttachmentContainer.empty();
    private final AttachmentContainer<Recipe> recipes = AttachmentContainer.empty();
    private final AttachmentContainer<World> worlds = AttachmentContainer.empty();

    public synchronized void refill(@NotNull SpigotContainer container) {
        enablingHooks.setAll(container.getEnablingHooks());
        disablingHooks.setAll(container.getDisablingHooks());

        handlers.setAll(container.getHandlers());

        commands.setAll(container.getCommands());
        listeners.setAll(container.getListeners());
        recipes.setAll(container.getRecipes());
        worlds.setAll(container.getWorlds());
    }

    @Override
    public SpigotContainer clone() {
        try {
            return (SpigotContainer) super.clone();
        }
        catch (CloneNotSupportedException exception) {
            throw new SpigotEngineException(exception);
        }
    }
}
