package ru.lyx.spigot.engine.module.world.processor;

import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.module.processor.ProcessController;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.module.world.WorldContext;
import ru.lyx.spigot.engine.module.world.WorldModule;
import ru.lyx.spigot.engine.module.world.model.WrappedWorld;

import java.util.List;

public class ServerWorldsWrappingProcessor implements SpigotModuleProcessor<WorldModule, WorldContext> {

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("ServerWorldsWrappingProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessController<WorldModule, WorldContext> controller) {
        List<World> serverWorldsList = controller.getEngine().getServer().getWorlds();
        WorldContext worldContext = controller.getContext();

        serverWorldsList
                .forEach(world -> {

                    WrappedWorld wrapper = worldContext.createWrapper(world);
                    worldContext.store(wrapper);
                });

        return controller.getPreviousTransaction();
    }
}
