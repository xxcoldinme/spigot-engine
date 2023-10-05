package ru.lyx.spigot.engine.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactoryHelper;
import ru.lyx.spigot.engine.core.module.SpigotModuleLoader;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandlingService;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandlingTrigger;
import ru.lyx.spigot.engine.core.module.processor.ProcessorExecutor;
import ru.lyx.spigot.engine.core.plugin.SpigotContextPlugin;
import ru.lyx.spigot.engine.core.plugin.SpigotPluginContainer;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;
import ru.lyx.spigot.engine.core.settingconfig.SettingConfig;
import ru.lyx.spigot.engine.core.settingconfig.SettingConfigLoader;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.logging.Logger;

@RequiredArgsConstructor
public final class SpigotEngine {

    @Getter
    private final Server server;

    private SpigotContainer container;
    private SettingConfigLoader configLoader;
    private SpigotModuleLoader moduleLoader;
    private SpigotModuleFactoryHelper moduleFactoryHelper;
    private SpigotPluginContainer pluginContainer;
    private SpigotHandlingService handlingService;

    public void initEngine() {
        Logger logger = server.getLogger();

        ReflectionService reflectionService = new ReflectionService(logger);
        ProcessorExecutor processorExecutor = new ProcessorExecutor(logger, this, reflectionService);

        // ----------------------------------------------------------------------------------- //
        container = new SpigotContainer();
        configLoader = new SettingConfigLoader();

        moduleLoader = new SpigotModuleLoader(this, processorExecutor, logger);
        moduleFactoryHelper = new SpigotModuleFactoryHelper(reflectionService);
        pluginContainer = new SpigotPluginContainer(logger);
        handlingService = new SpigotHandlingService(this, container, reflectionService);
        // ----------------------------------------------------------------------------------- //
    }

    public SpigotContainerBuilder doEditContainers() {
        return new SpigotContainerBuilder(this, container);
    }

    private void removePluginData(@NotNull Plugin plugin) {
        container.getDisablingHooks().removeAll(plugin);
        container.getEnablingHooks().removeAll(plugin);
        container.getHandlers().removeAll(plugin);
    }

    public void registerPlugin(@NotNull SpigotContextPlugin plugin) {
        pluginContainer.addPlugin(plugin);

        plugin.registerPlugin(this);
        sendPluginEmptyHandler(plugin, SpigotHandlingTrigger.PLUGIN_REGISTERED);
    }

    public void unregisterPlugin(@NotNull SpigotContextPlugin plugin) {
        pluginContainer.removePlugin(plugin);
        sendPluginEmptyHandler(plugin, SpigotHandlingTrigger.PLUGIN_UNREGISTERED);

        removePluginData(plugin);
    }

    public void sendTotalHandler(SpigotHandlingTrigger trigger, SpigotMetadata metadata) {
        handlingService.sendTotalHandler(trigger, metadata);
    }

    public void sendTotalEmptyHandler(SpigotHandlingTrigger trigger) {
        handlingService.sendTotalHandler(trigger, SpigotMetadata.create());
    }

    public void sendPluginHandler(Plugin plugin, SpigotHandlingTrigger trigger, SpigotMetadata metadata) {
        handlingService.sendPluginHandler(plugin, trigger, metadata);
    }

    public void sendPluginEmptyHandler(Plugin plugin, SpigotHandlingTrigger trigger) {
        handlingService.sendPluginHandler(plugin, trigger, SpigotMetadata.create());
    }

    public void sendModuleHandler(Class<? extends SpigotModule<?, ?>> cls, SpigotHandlingTrigger trigger, SpigotMetadata metadata) {
        handlingService.sendModuleHandler(cls, trigger, metadata);
    }

    public void sendModuleEmptyHandler(Class<? extends SpigotModule<?, ?>> cls, SpigotHandlingTrigger trigger) {
        handlingService.sendModuleHandler(cls, trigger, SpigotMetadata.create());
    }

    public void registerModule(Class<? extends SpigotModule<?, ?>> cls) {
        moduleLoader.registerModule(moduleFactoryHelper.of(cls));
    }

    public void unregisterModule(Class<? extends SpigotModule<?, ?>> cls) {
        moduleLoader.unregisterModule(lookupModule(cls));
    }

    public boolean isModuleLoaded(KeyProperty<String> keyID) {
        return moduleLoader.isModuleRegistered(keyID);
    }

    @SuppressWarnings("unchecked")
    public <T extends SpigotModule<?, ?>> T findModule(KeyProperty<String> keyID) {
        return (T) moduleLoader.findRegisteredModule(keyID);
    }

    @SuppressWarnings("unchecked")
    public <T extends SpigotModule<?, ?>> T lookupModule(Class<? extends SpigotModule<?, ?>> cls) {
        return (T) moduleLoader.lookup(cls).orElse(null);
    }

    public SettingConfig loadConfig(File file) {
        return configLoader.loadAbsolute(file);
    }

    public SettingConfig loadConfig(Path path) {
        return configLoader.loadAbsolute(path.toFile());
    }

    public SettingConfig loadConfig(InputStream inputStream) {
        return configLoader.load(inputStream);
    }
}
