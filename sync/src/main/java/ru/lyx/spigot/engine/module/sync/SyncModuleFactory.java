package ru.lyx.spigot.engine.module.sync;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactory;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SyncModuleFactory implements SpigotModuleFactory<SyncModule> {

    @Override
    public SyncModule create() {
        return new SyncModule();
    }
}
