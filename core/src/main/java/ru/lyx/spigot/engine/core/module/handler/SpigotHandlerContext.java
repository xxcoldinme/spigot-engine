package ru.lyx.spigot.engine.core.module.handler;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.module.SpigotModule;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class SpigotHandlerContext<T extends SpigotModule<?, ?>> {

    private final SpigotHandlingTrigger trigger;
    private final SpigotEngine engine;
    private final SpigotMetadata metadata;
    private final T module;
}
