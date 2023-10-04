package ru.lyx.spigot.engine.core.metadata;

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lyx.spigot.engine.core.exception.SpigotEngineException;
import ru.lyx.spigot.engine.core.key.KeyProperty;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MetadataProperty<T> {

    public static <T> MetadataProperty<T> ofNullable(@NotNull KeyProperty<?> key) {
        return of(key, null);
    }

    public static <T> MetadataProperty<T> of(@NotNull KeyProperty<?> key, @Nullable T value) {
        return new MetadataProperty<T>(key, value);
    }

    @EqualsAndHashCode.Include
    private final KeyProperty<?> key;

    private T value;

    public MetadataProperty<T> setValue(@Nullable T value) {
        this.value = value;
        return this;
    }

    public MetadataProperty<T> clone(@Nullable T value) {
        MetadataProperty<T> clone = clone();
        clone.value = value;

        return clone;
    }

    @Override
    public MetadataProperty<T> clone() {
        try {
            return (MetadataProperty<T>) super.clone();
        }
        catch (CloneNotSupportedException exception) {
            throw new SpigotEngineException(exception);
        }
    }
}
