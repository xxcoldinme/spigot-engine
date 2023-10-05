package ru.lyx.spigot.engine.core.module;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.context.SpigotContext;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.module.processor.ProcessorExecutor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class SpigotModuleLoader {

    private final SpigotEngine engine;
    private final ProcessorExecutor processorExecutor;
    private final Logger logger;

    private final Map<KeyProperty<String>, SpigotModule<?>> modulesMap = new ConcurrentHashMap<>();

    public void registerModule(@NotNull SpigotModuleFactory<?> factory) {
        final SpigotModule<?> spigotModule = factory.create();

        logger.info("Registered engine module - " + spigotModule.getKey());
        modulesMap.put(spigotModule.getKey(), spigotModule);

        //noinspection unchecked
        processorExecutor.execProcessors((SpigotModule<SpigotContext>) spigotModule);
    }

    public void unregisterModule(@NotNull SpigotModule<?> spigotModule) {
        final KeyProperty<String> key = spigotModule.getKey();

        logger.warning("Unregister engine module - " + key);
        modulesMap.remove(key);
    }

    public boolean isModuleRegistered(@NotNull SpigotModule<?> spigotModule) {
        return isModuleRegistered(spigotModule.getKey());
    }

    public boolean isModuleRegistered(@NotNull KeyProperty<String> key) {
        return modulesMap.containsKey(key);
    }

    public SpigotModule<?> findRegisteredModule(@NotNull KeyProperty<String> key) {
        return modulesMap.get(key);
    }

    public Optional<SpigotModule<?>> lookup(@NotNull Class<? extends SpigotModule<?>> cls) {
        return modulesMap.values()
                .stream()
                .filter(spigotModule -> spigotModule.getClass().equals(cls))
                .findFirst();
    }
}
