package ru.lyx.spigot.engine.key;

import lombok.*;

import java.util.concurrent.atomic.AtomicReference;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class KeyProperty<T> {

    public static <T> KeyProperty<T> of(T defaultVal) {
        return new KeyProperty<>(new AtomicReference<>(defaultVal));
    }

    public static <T> KeyProperty<T> ofNull() {
        return new KeyProperty<>(new AtomicReference<>());
    }

    private final AtomicReference<T> currentValue;

    public T get() {
        return currentValue.get();
    }

    public void set(T val) {
        currentValue.set(val);
    }
}
