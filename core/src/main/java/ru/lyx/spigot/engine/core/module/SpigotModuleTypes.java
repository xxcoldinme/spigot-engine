package ru.lyx.spigot.engine.core.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.core.key.KeyProperty;

@Getter
@ToString
@RequiredArgsConstructor
public enum SpigotModuleTypes {

    DATA_SYNC(KeyProperty.of("#_DATA_SYNC")),
    WORLD_MANAGEMENTS(KeyProperty.of("#_WORLD_MANAGEMENTS")),
    ;

    private final KeyProperty<String> keyID;
}
