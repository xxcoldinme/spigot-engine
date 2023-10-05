package ru.lyx.spigot.engine.core.module;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigModel;

import java.lang.ref.WeakReference;

@RequiredArgsConstructor
public abstract class AbstractSpigotModule<T extends SpigotContext, M extends SpigotConfigModel>
        implements SpigotModule<T, M> {

    private final SpigotModuleTypes type;

    private WeakReference<T> contextRef;
    private WeakReference<M> modelRef;

    protected abstract T createContext();

    protected abstract M createConfigModel();

    @Override
    public KeyProperty<String> getKey() {
        return type.getKeyID();
    }

    @Override
    public final T getContext() {
        if (contextRef == null || contextRef.get() == null) {
            contextRef = new WeakReference<>(createContext());
        }
        return contextRef.get();
    }

    @Override
    public final M getConfigModel() {
        if (modelRef == null || modelRef.get() == null) {
            modelRef = new WeakReference<>(createConfigModel());
        }
        return modelRef.get();
    }

    @Override
    public void init(@NotNull SpigotEngine engine) {
        // override me.
    }
}
