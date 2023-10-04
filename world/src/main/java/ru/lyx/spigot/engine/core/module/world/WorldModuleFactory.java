package ru.lyx.spigot.engine.core.module.world;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.lyx.spigot.engine.core.module.SpigotModuleFactory;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class WorldModuleFactory implements SpigotModuleFactory<WorldModule> {

    @Override
    public WorldModule create() {
        return new WorldModule();
    }
}
