package ru.lyx.spigot.engine.core.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.core.key.KeyProperty;

@Getter
@ToString
@RequiredArgsConstructor
public enum SpigotModuleTypes {

    ENTITIES(KeyProperty.of("entities")),
    EVENTS(KeyProperty.of("events")),
    CRAFTING_RECIPES(KeyProperty.of("recipes")),
    SYNC_SERVERS(KeyProperty.of("sync")),
    WORLDS(KeyProperty.of("worlds")),
    ;

    private final KeyProperty<String> keyID;
}
