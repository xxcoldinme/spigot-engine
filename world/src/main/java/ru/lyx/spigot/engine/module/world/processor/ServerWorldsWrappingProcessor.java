package ru.lyx.spigot.engine.module.world.processor;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.key.KeyProperty;
import ru.lyx.spigot.engine.module.processor.ProcessorContext;
import ru.lyx.spigot.engine.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.module.world.WorldContext;
import ru.lyx.spigot.engine.module.world.WorldModule;
import ru.lyx.spigot.engine.module.world.model.WrappedWorld;

import java.util.List;

public class ServerWorldsWrappingProcessor implements SpigotModuleProcessor<WorldModule, WorldContext> {

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("ServerWorldsRebuildProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessorContext<WorldModule, WorldContext> context) {
        List<World> serverWorldsList = context.getEngine().getServer().getWorlds();
        WorldContext worldContext = context.getModuleContext();

        serverWorldsList
                .forEach(world -> {

                    WrappedWorld wrapper = worldContext.createWrapper(world);
                    worldContext.store(wrapper);
                });

        return context.getPreviousTransaction();
    }
}