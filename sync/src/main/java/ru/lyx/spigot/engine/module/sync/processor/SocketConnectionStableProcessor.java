package ru.lyx.spigot.engine.module.sync.processor;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.key.KeyProperty;
import ru.lyx.spigot.engine.module.processor.transaction.ProcessTransaction;
import ru.lyx.spigot.engine.module.processor.ProcessorContext;
import ru.lyx.spigot.engine.module.processor.SpigotModuleProcessor;
import ru.lyx.spigot.engine.module.sync.SyncContext;
import ru.lyx.spigot.engine.module.sync.SyncModule;

public class SocketConnectionStableProcessor implements SpigotModuleProcessor<SyncModule, SyncContext> {

    @Override
    public KeyProperty<String> getKey() {
        return KeyProperty.of("SocketConnectionStableProcessor");
    }

    @Override
    public ProcessTransaction process(@NotNull ProcessorContext<SyncModule, SyncContext> context) {
        return null;
    }
}
