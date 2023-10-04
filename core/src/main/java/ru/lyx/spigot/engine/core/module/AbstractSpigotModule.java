package ru.lyx.spigot.engine.core.module;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngineContext;
import ru.lyx.spigot.engine.core.context.SpigotContext;
import ru.lyx.spigot.engine.core.key.KeyProperty;

import java.lang.ref.WeakReference;

@RequiredArgsConstructor
public abstract class AbstractSpigotModule<T extends SpigotContext>
        implements SpigotModule<T> {

    private final SpigotModuleTypes type;
    private WeakReference<T> contextRef;

    protected abstract T createContext(SpigotEngineContext previousContext);

    @Override
    public KeyProperty<String> getKey() {
        return type.getKeyID();
    }

    @Override
    public final T lookupContext(@NotNull SpigotEngineContext previousContext) {
        if (contextRef == null || contextRef.get() == null) {
            contextRef = new WeakReference<>(createContext(previousContext));
        }
        return contextRef.get();
    }
}
