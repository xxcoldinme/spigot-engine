package ru.lyx.spigot.engine.core;

import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.core.context.SpigotContext;

@RequiredArgsConstructor
public class SpigotEngineContext implements SpigotContext {

    private final SpigotContainer container;
}
