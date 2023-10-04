package ru.lyx.spigot.engine.core.reflection;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.metadata.MetadataProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;

import java.lang.reflect.ParameterizedType;

public class GetGenericTypeHandler<T> implements ReflectionHandler<Class<T>> {

    public static final MetadataProperty<Integer> GENERIC_TYPE_INDEX = MetadataProperty.ofNullable(
            KeyProperty.of("GenericTypeIndex"));
    public static final MetadataProperty<Class<?>> TARGET_CLASS = MetadataProperty.ofNullable(
            KeyProperty.of("TargetClass"));

    @SuppressWarnings("unchecked")
    @Override
    public Class<T> handle(@NotNull SpigotMetadata metadata) {
        Class<?> targetClassType = metadata.get(TARGET_CLASS)
                .orElseThrow(() -> new NullPointerException("metadata value - TARGET_CLASS"));

        int index = metadata.get(GENERIC_TYPE_INDEX).orElse(0);

        return (Class<T>) ((ParameterizedType) targetClassType
                .getGenericSuperclass())
                .getActualTypeArguments()[index];
    }
}
