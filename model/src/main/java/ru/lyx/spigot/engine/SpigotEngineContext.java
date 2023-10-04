package ru.lyx.spigot.engine;

import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.context.SpigotContext;

@RequiredArgsConstructor
public class SpigotEngineContext implements SpigotContext {

    private final SpigotContainer container;

    public final SpigotContextEditSession openEditSession() {
        return new SpigotContextEditSession(container, this);
    }
}
