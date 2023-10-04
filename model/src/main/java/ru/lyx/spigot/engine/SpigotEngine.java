package ru.lyx.spigot.engine;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.key.KeyProperty;
import ru.lyx.spigot.engine.plugin.SpigotContextPlugin;

@RequiredArgsConstructor
public class SpigotEngine {

    @Getter
    private final Server server;

    @Getter
    private SpigotEngineContext context;

    private SpigotContainer contextContainer;
    private SpigotProcessor contextProcessor;

    private void initContext() {
        contextContainer = new SpigotContainer();
        contextProcessor = new SpigotProcessor(contextContainer);

        context = new SpigotEngineContext(contextContainer);
    }

    public void registerPlugin(@NotNull SpigotContextPlugin plugin) {
        initContext();
        plugin.registerPlugin(this, context);
    }

    public void unregisterPlugin(@NotNull SpigotContextPlugin plugin) {
        // todo
    }

    public boolean isModuleLoaded(@NotNull KeyProperty<String> keyID) {
        return false; // todo
    }
}
