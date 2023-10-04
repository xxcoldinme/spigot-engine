package ru.lyx.spigot.engine.core.key;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum SpigotModuleTypes {

    DATA_SYNC(KeyProperty.of("#_DATA_SYNC")),
    WORLD_MANAGEMENTS(KeyProperty.of("#_WORLD_MANAGEMENTS")),
    ;

    private final KeyProperty<String> keyID;
}
