package ru.lyx.spigot.engine.main;

import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.module.sync.ClusterChannel;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.sync.transport.TransportChannel;
import ru.lyx.spigot.engine.module.world.WorldModule;

public final class SpigotEngineMain extends JavaPlugin {

    @SuppressWarnings("unchecked")
    private static final AttachmentContainer<Class<? extends SpigotModule<?, ?>>> TOTAL_MODULES
            = AttachmentContainer.of(
                    SyncModule.class, WorldModule.class);

    @Getter
    private SpigotEngine engine;

    @Override
    public void onLoad() {
        engine = new SpigotEngine(getServer(), getLogger());
        engine.initEngine();

        TOTAL_MODULES.getDefinitions().forEach(engine::registerModule);
    }

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new SpigotPluginListener(engine), this);
    }

    @Override
    public void onDisable() {
        SyncModule syncModule = engine.lookupModule(SyncModule.class);

        TransportChannel channel = syncModule.getContext().getChannel();

        if (channel != null) {
            ((ClusterChannel) channel).shutdown();
        }
    }
}
