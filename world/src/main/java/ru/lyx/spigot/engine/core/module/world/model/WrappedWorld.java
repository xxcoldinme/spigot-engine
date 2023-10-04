package ru.lyx.spigot.engine.core.module.world.model;

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
    private final WorldProperties properties;
}
