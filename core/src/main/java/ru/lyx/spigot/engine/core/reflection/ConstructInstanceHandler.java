package ru.lyx.spigot.engine.core.reflection;

import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.SpigotEngineException;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.metadata.MetadataProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Stream;

public class ConstructInstanceHandler<T> implements ReflectionHandler<T> {

    public static final MetadataProperty<Class<?>> TARGET_CLASS = MetadataProperty.ofNullable(
            KeyProperty.of("TargetClass"));
    public static final MetadataProperty<Object[]> CONSTRUCTOR_PARAMS = MetadataProperty.ofNullable(
            KeyProperty.of("ConstructorParams"));
    public static final MetadataProperty<Boolean> THROW_EXCEPTION = MetadataProperty.ofNullable(
            KeyProperty.of("ThrowException"));

    @SuppressWarnings("unchecked")
    @Override
    public T handle(@NotNull SpigotMetadata metadata) {
        final Object[] parameters = metadata.get(CONSTRUCTOR_PARAMS).orElse(new Object[0]);
        final Class<T> targetClass = (Class<T>) metadata.get(TARGET_CLASS)
                .orElseThrow(() -> new NullPointerException("metadata key - TARGET_CLASS"));

        try {
            return newConstructorInstance(targetClass, parameters);
        }
        catch (Exception exception) {
            return printStackTrace(metadata, exception);
        }
    }

    private T printStackTrace(SpigotMetadata metadata, Exception exception) {
        boolean canPrint = metadata.get(THROW_EXCEPTION)
                .orElse(true);

        if (canPrint) {
            throw new SpigotEngineException(exception);
        }

        return null;
    }

    private T newConstructorInstance(Class<T> targetClass, Object[] parameters) throws Exception {
        Constructor<T> constructor = targetClass.getConstructor(
                Stream.of(parameters)
                        .map(Object::getClass)
                        .toArray(Class<?>[]::new));

        constructor.setAccessible(true);
        return constructor.newInstance(parameters);
    }
}
