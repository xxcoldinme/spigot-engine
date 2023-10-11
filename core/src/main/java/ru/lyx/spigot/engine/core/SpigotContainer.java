package ru.lyx.spigot.engine.core;

import lombok.*;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.attachment.SpigotAttachment;
import ru.lyx.spigot.engine.core.pocketcontainer.plugin.PocketPluginContainer;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;

@Getter
@ToString
public final class SpigotContainer {

    private final PocketPluginContainer<SpigotAttachment> enablingHooks
            = PocketPluginContainer.empty();
    private final PocketPluginContainer<SpigotAttachment> disablingHooks
            = PocketPluginContainer.empty();

    private final PocketPluginContainer<SpigotHandler<?>> handlers = PocketPluginContainer.empty();

    private final PocketContainer<CommandExecutor> commands = PocketContainer.empty();
    private final PocketContainer<Listener> listeners = PocketContainer.empty();
    private final PocketContainer<Recipe> recipes = PocketContainer.empty();
    private final PocketContainer<World> worlds = PocketContainer.empty();

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
        SpigotContainer clone = new SpigotContainer();
        clone.refill(this);

        return clone;
    }
}
