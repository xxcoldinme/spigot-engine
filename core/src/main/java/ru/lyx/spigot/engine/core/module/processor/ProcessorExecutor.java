package ru.lyx.spigot.engine.core.module.processor;

import com.google.common.collect.Iterables;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.module.SpigotModule;
import ru.lyx.spigot.engine.core.module.processor.transaction.LinkedProcessor;
import ru.lyx.spigot.engine.core.module.processor.transaction.ProcessTransaction;

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

    public <T extends SpigotModule<C, ?>, C extends SpigotContext> void execProcessors(@NotNull T module) {
        final C context = module.getContext();

        final AttachmentContainer<SpigotModuleProcessor<?, ?>> processors
                = module.ofProcessors(engine);

        final AtomicReference<ProcessTransaction> transaction
                = new AtomicReference<>(ProcessTransaction.create(processors));

        iterateContextLinks(processors,
                processor -> {

                    handleProcessor(module, context, transaction, processor);
                    return transaction.get();
                });
    }

    @SuppressWarnings("unchecked")
    private <T extends SpigotModule<C, ?>, C extends SpigotContext> void handleProcessor(T module, C context,
                         AtomicReference<ProcessTransaction> transaction,
                         SpigotModuleProcessor<?, ?> processor) {

        final ProcessorContext<T, C> processorContext = new ProcessorContext<>(engine, module, context, transaction.get());
        final SpigotModuleProcessor<T, C> castedProcessor = (SpigotModuleProcessor<T, C>) processor;

        logger.info(format("Module '%s' has processing '%s'", module.getKey(), processor.getKey()));

        ProcessTransaction processTransaction = castedProcessor.process(processorContext);
        transaction.set(processTransaction);
    }

    private void iterateContextLinks(AttachmentContainer<SpigotModuleProcessor<?, ?>> processors,
                                     Function<SpigotModuleProcessor<?, ?>, ProcessTransaction> processFunction) {

        SpigotModuleProcessor<?, ?> current = Iterables
                .getFirst(processors.getDefinitions(), null);

        while (current != null) {
            ProcessTransaction transaction = processFunction.apply(current);

            if (transaction == null || transaction.getNext() == null) {
                current = null;
            }
            else {
                current = transaction.getNext().lookupCached(processors);
                transaction.doNext();
            }
        }
    }
}
