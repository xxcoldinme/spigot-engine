package ru.lyx.spigot.engine.core.module;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.SpigotEngineException;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.module.processor.ProcessorExecutor;
import ru.lyx.spigot.engine.core.settingconfig.SettingConfig;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigModel;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import static java.lang.String.format;

@RequiredArgsConstructor
public class SpigotModuleLoader {

    private static final String MODULE_INI_CONF_NAME = "config.ini";
    private static final String BASEDIR_MODULES_FOLDER = "modules";
    private static final String CONFIG_PATH_FORMAT = "/%s/%s";

    private final SpigotEngine engine;
    private final ProcessorExecutor processorExecutor;

    private final Logger logger;

    private final Map<KeyProperty<String>, SpigotModule<?, ?>> modulesMap = new ConcurrentHashMap<>();

    public Path getModuleFolder(@NotNull KeyProperty<String> keyID) {
        return Paths.get(BASEDIR_MODULES_FOLDER, keyID.get());
    }

    private void initModule(SpigotModule<?, ?> spigotModule) {
        spigotModule.init(engine);
        loadModel(spigotModule.getKey(), spigotModule.getConfigModel());

        processorExecutor.execProcessors(spigotModule);
    }

    public void registerModule(@NotNull SpigotModuleFactory<?> factory) {
        final SpigotModule<?, ?> spigotModule = factory.create();

        if (isModuleRegistered(spigotModule)) {
            throw new SpigotEngineException("module has already registered");
        }

        logger.info("Registered engine module - " + spigotModule.getKey());
        modulesMap.put(spigotModule.getKey(), spigotModule);

        initModule(spigotModule);
    }

    public void unregisterModule(@NotNull SpigotModule<?, ?> spigotModule) {
        final KeyProperty<String> key = spigotModule.getKey();

        if (!isModuleRegistered(spigotModule)) {
            throw new SpigotEngineException("module still not registered");
        }

        logger.warning("Unregister engine module - " + key);
        modulesMap.remove(key);
    }

    public boolean isModuleRegistered(@NotNull SpigotModule<?, ?> spigotModule) {
        return isModuleRegistered(spigotModule.getKey());
    }

    public boolean isModuleRegistered(@NotNull KeyProperty<String> key) {
        return modulesMap.containsKey(key);
    }

    public SpigotModule<?, ?> findRegisteredModule(@NotNull KeyProperty<String> key) {
        return modulesMap.get(key);
    }

    public Optional<SpigotModule<?, ?>> lookup(@NotNull Class<? extends SpigotModule<?, ?>> cls) {
        return modulesMap.values()
                .stream()
                .filter(spigotModule -> spigotModule.getClass().equals(cls))
                .findFirst();
    }

    private void loadModel(KeyProperty<String> keyID, SpigotConfigModel model) {
        Path moduleFolder = getModuleFolder(keyID);
        Path configPath = moduleFolder.resolve(MODULE_INI_CONF_NAME);

        if (!Files.exists(configPath)) {
            createConfigFile(keyID, moduleFolder, configPath);
        }

        logger.info(format("Module '%s' .ini configuration merge to model - %s", keyID,
                model.getClass().getSimpleName()));

        SettingConfig settingConfig = engine.loadConfig(configPath);
        settingConfig.mergeModel(model);
    }

    @SneakyThrows
    private void createConfigFile(KeyProperty<String> keyID, Path moduleFolder, Path configPath) {
        Files.createDirectories(moduleFolder);

        InputStream resourceAsStream = SpigotEngine.class.getResourceAsStream(
                format(CONFIG_PATH_FORMAT, keyID, MODULE_INI_CONF_NAME));

        assert resourceAsStream != null;
        Files.copy(resourceAsStream, configPath);
    }
}
