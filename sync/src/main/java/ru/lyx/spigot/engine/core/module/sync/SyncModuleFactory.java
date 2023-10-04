package ru.lyx.spigot.engine.core.module.sync;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.key.SpigotModuleTypes;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactory;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class SyncModuleFactory implements SpigotModuleFactory<SyncModule> {

    @Override
    public KeyProperty<String> getKey() {
        return SpigotModuleTypes.DATA_SYNC.getKeyID();
    }

    @Override
    public SyncModule create() {
        return new SyncModule();
    }
}
