package ru.lyx.spigot.engine.core.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.core.key.KeyProperty;

@Getter
@ToString
@RequiredArgsConstructor
public enum SpigotModuleTypes {

    ENTITIES(KeyProperty.of("#_ENTITIES")),
    EVENTS(KeyProperty.of("#_EVENTS")),
    CRAFTING_RECIPES(KeyProperty.of("#_CRAFTING_RECIPES")),
    SYNC_SERVERS(KeyProperty.of("#_SYNC_SERVERS")),
    WORLDS(KeyProperty.of("#_WORLDS")),
    ;

    private final KeyProperty<String> keyID;
}
