package ru.lyx.spigot.engine.core.module.processor.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.core.reflection.ConstructInstanceHandler;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;

@Getter
@ToString
@RequiredArgsConstructor
public class LinkedProcessor {

    private final Class<? extends SpigotModuleProcessor<?, ?>> processorClass;

    public SpigotModuleProcessor<?, ?> newProcessorInstance(ReflectionService reflectionService) {
        return reflectionService.constructInstance(
                SpigotMetadata.create()
                        .with(ConstructInstanceHandler.THROW_EXCEPTION, true)
                        .with(ConstructInstanceHandler.TARGET_CLASS, processorClass));
    }

    public SpigotModuleProcessor<?, ?> lookupCached(PocketContainer<SpigotModuleProcessor<?, ?>> processors) {
        return processors.findFirst(processor -> processor.getClass().equals(processorClass))
                .orElseThrow(() -> new NullPointerException("next processor"));
    }
}
