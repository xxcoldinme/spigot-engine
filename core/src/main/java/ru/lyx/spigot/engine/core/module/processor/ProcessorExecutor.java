package ru.lyx.spigot.engine.core.module.processor;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.processor.transaction.LinkedProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
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

        final AtomicReference<ProcessTransaction> transaction
                = new AtomicReference<>(ProcessTransaction.create(processors));

        iterateContextLinks(processors,
                processor -> {

                    final ProcessorContext<T, C> processorContext = new ProcessorContext<>(engine, module, context);
                    final SpigotModuleProcessor<T, C> castedProcessor = (SpigotModuleProcessor<T, C>) processor;

                    processorContext.setPreviousTransaction(transaction.get());

                    logger.info(format("Module '%s' has processing '%s'", module.getKey().get(), processor.getKey().get()));

                    transaction.set(castedProcessor.process(processorContext));
                    return transaction.get();
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

            transaction.doNext();
            current = linkedProcessor.lookupCached(processors);

        } while (current != null);

        cached = null;
    }
}
