package ru.lyx.spigot.engine.core.module.processor;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.processor.transaction.LinkedProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.core.reflection.GetGenericTypeHandler;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

import static java.lang.String.format;

@RequiredArgsConstructor
public class ProcessorExecutor {

    private final Logger logger;

    private final SpigotEngine engine;
    private final ReflectionService reflectionService;

    @SuppressWarnings({"unchecked"})
    public <T extends SpigotModule<C, ?>, C extends SpigotContext> void execProcessors(@NotNull T module) {
        final C context = module.getContext();

        final AttachmentContainer<SpigotModuleProcessor<?, ?>> processors
                = module.ofProcessors(engine);

        iterateContextLinks(processors,
                processor -> {

                    if (!isProcessorLinked(module, processor)) {
                        return null;
                    }

                    final ProcessorContext<T, C> processorContext = new ProcessorContext<>(engine, module, context);
                    final SpigotModuleProcessor<T, C> castedProcessor = (SpigotModuleProcessor<T, C>) processor;

                    logger.info(format("Module '%s' has processing '%s'", module.getKey().get(), processor.getKey().get()));
                    return castedProcessor.process(processorContext);
                });
    }

    private void iterateContextLinks(AttachmentContainer<SpigotModuleProcessor<?, ?>> processors,
                                     Function<SpigotModuleProcessor<?, ?>, ProcessTransaction> processFunction) {
        final Iterator<SpigotModuleProcessor<?, ?>> iterator = processors.getDefinitions()
                .iterator();

        Set<SpigotModuleProcessor<?, ?>> cached = new HashSet<>();
        SpigotModuleProcessor<?, ?> current = iterator.hasNext() ? iterator.next() : null;

        do {
            if (cached.contains(current)) {
                continue;
            }

            cached.add(current);

            ProcessTransaction transaction = processFunction.apply(current);
            if (transaction == null) {
                continue;
            }

            LinkedProcessor linkedProcessor = transaction.getNext();
            if (linkedProcessor == null) {
                break;
            }

            current = linkedProcessor.lookupCached(processors);

        } while (current != null);

        cached = null;
    }

    private boolean isProcessorLinked(SpigotModule<?, ?> module, SpigotModuleProcessor<?, ?> processor) {
        Class<Object> genericType = reflectionService.getGenericType(
                SpigotMetadata.create()
                        .with(GetGenericTypeHandler.GENERIC_TYPE_INDEX.clone(0))
                        .with(GetGenericTypeHandler.TARGET_CLASS.clone(processor.getClass())));

        Class<?> moduleClass = module.getClass();
        return genericType.isAssignableFrom(moduleClass) || moduleClass.isAssignableFrom(genericType);
    }
}
