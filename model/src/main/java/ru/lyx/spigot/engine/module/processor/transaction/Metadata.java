package ru.lyx.spigot.engine.module.processor.transaction;

import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lyx.spigot.engine.key.KeyProperty;

import java.util.Map;
import java.util.WeakHashMap;

@ToString
public class Metadata {

    private final Map<KeyProperty<?>, Object> metadata = new WeakHashMap<>();

    public void save(@NotNull KeyProperty<?> key, @Nullable Object value) {
        metadata.put(key, value);
    }

    public void remove(@NotNull KeyProperty<?> key) {
        metadata.remove(key);
    }

    public <T> T find(@NotNull KeyProperty<?> key) {
        return (T) metadata.get(key);
    }
}
