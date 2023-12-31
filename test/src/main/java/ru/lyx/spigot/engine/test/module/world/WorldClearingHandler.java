package ru.lyx.spigot.engine.test.module.world;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandlerContext;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandleTrigger;
import ru.lyx.spigot.engine.module.world.WorldContext;
import ru.lyx.spigot.engine.module.world.WorldModule;
import ru.lyx.spigot.engine.module.world.model.WrappedWorld;

public class WorldClearingHandler implements SpigotHandler<WorldModule> {

    @Override
    public boolean validate(@NotNull SpigotHandlerContext<WorldModule> context) {
        return ALWAYS;
    }

    @Override
    public void handle(@NotNull SpigotHandlerContext<WorldModule> context) {
        WorldContext worldContext = context.getModule().getContext();

        if (context.getTrigger() == SpigotHandleTrigger.PLUGIN_REGISTERED) {

            World bukkitWorld = context.getEngine().getServer().getWorld("worlds");
            WrappedWorld wrappedWorld = worldContext.createWrapper(
                    bukkitWorld
            );

            clear(wrappedWorld);
        }
    }

    private void clear(WrappedWorld world) {
        world.clearAllEntities();
        world.clearAllDrops();
    }
}
