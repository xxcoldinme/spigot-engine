package ru.lyx.spigot.engine.module;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.SpigotEngineContext;
import ru.lyx.spigot.engine.context.SpigotContext;

import java.lang.ref.WeakReference;

public abstract class AbstractSpigotModule<T extends SpigotContext>
        implements SpigotModule<T> {

    private WeakReference<T> contextRef;

    protected abstract T createContext(SpigotEngineContext previousContext);

    @Override
    public final T lookupContext(@NotNull SpigotEngineContext previousContext) {
        if (contextRef == null || contextRef.get() == null) {
            contextRef = new WeakReference<>(createContext(previousContext));
        }
        return contextRef.get();
    }
}
