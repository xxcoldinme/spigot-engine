package ru.lyx.spigot.engine.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.world.WorldModule;

public final class SpigotEngineMain extends JavaPlugin {

    @SuppressWarnings("unchecked")
    private static final AttachmentContainer<Class<? extends SpigotModule<?, ?>>> TOTAL_MODULES
            = AttachmentContainer.of(
                    SyncModule.class, WorldModule.class);

    private final SpigotEngine engine = new SpigotEngine(Bukkit.getServer());

    @Override
    public void onLoad() {
        engine.initEngine();

        TOTAL_MODULES.getDefinitions().forEach(engine::registerModule);
    }

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new SpigotPluginListener(engine), this);
    }
}
