package ru.lyx.spigot.engine.module.world.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class WrappedWorld {

    private final String name;
    private final WrappedWorldProperties properties;

    public void clearAllEntities() {
        // todo
    }

    public void clearAllDrops() {
        // todo
    }
}
