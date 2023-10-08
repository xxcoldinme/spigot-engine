package ru.lyx.spigot.engine.core.key;

import lombok.*;

import java.util.concurrent.atomic.AtomicReference;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class KeyProperty<T> {

    public static <T> KeyProperty<T> of(T defaultVal) {
        return new KeyProperty<>(new AtomicReference<>(defaultVal));
    }

    public static <T> KeyProperty<T> ofNull() {
        return new KeyProperty<>(new AtomicReference<>());
    }

    private final AtomicReference<T> currentValue;

    @EqualsAndHashCode.Include
    public T get() {
        return currentValue.get();
    }

    public void set(T val) {
        currentValue.set(val);
    }

    @Override
    public String toString() {
        return currentValue.get().toString();
    }
}
