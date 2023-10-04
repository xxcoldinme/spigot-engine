package ru.lyx.spigot.engine.core;

import lombok.Getter;
import lombok.ToString;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.attachment.SpigotEngineAttachment;
import ru.lyx.spigot.engine.core.exception.SpigotEngineException;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactory;

@Getter
@ToString
public final class SpigotContainer {
    private final AttachmentContainer<SpigotEngineAttachment> enablingHooks = AttachmentContainer.empty();
    private final AttachmentContainer<SpigotEngineAttachment> disablingHooks = AttachmentContainer.empty();

    private final AttachmentContainer<SpigotModuleFactory<?>> modules = AttachmentContainer.empty();
    private final AttachmentContainer<CommandExecutor> commands = AttachmentContainer.empty();
    private final AttachmentContainer<Listener> listeners = AttachmentContainer.empty();
    private final AttachmentContainer<Recipe> recipes = AttachmentContainer.empty();
    private final AttachmentContainer<World> worlds = AttachmentContainer.empty();

    public void refill(@NotNull SpigotContainer container) {
        enablingHooks.setAll(container.getEnablingHooks());
        disablingHooks.setAll(container.getDisablingHooks());

        modules.setAll(container.getModules());
        commands.setAll(container.getCommands());
        listeners.setAll(container.getListeners());
        recipes.setAll(container.getRecipes());
        worlds.setAll(container.getWorlds());
    }

    public SpigotContainer clone() {
        try {
            return (SpigotContainer) super.clone();
        }
        catch (CloneNotSupportedException exception) {
            throw new SpigotEngineException(exception);
        }
    }
}
