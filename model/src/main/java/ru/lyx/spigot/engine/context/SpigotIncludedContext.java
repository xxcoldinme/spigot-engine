package ru.lyx.spigot.engine.context;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class SpigotIncludedContext<T extends SpigotContext> implements SpigotContext {

    private final T previousContext;

    public T merge() {
        return previousContext;
    }
}
