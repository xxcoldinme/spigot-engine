package ru.lyx.spigot.engine.core.module;

import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.core.SpigotEngineException;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.reflection.ConstructInstanceHandler;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SpigotModuleFactoryHelper {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private final Map<Class<?>, SpigotModuleFactory<?>> factoriesCacheMap = new HashMap<>();
    private final ReflectionService reflectionService;

    public <T extends SpigotModule<?, ?>> SpigotModuleFactory<T> of(Class<T> cls) {
        return lookupFactory(cls);
    }

    @SuppressWarnings("unchecked")
    private <T extends SpigotModule<?, ?>> SpigotModuleFactory<T> lookupFactory(Class<T> cls) {
        return (SpigotModuleFactory<T>) factoriesCacheMap.computeIfAbsent(cls, f -> createFactory(cls));
    }

    private <T extends SpigotModule<?, ?>> SpigotModuleFactory<T> createFactory(Class<T> cls) {
        FactoryMethod<T> factory = findFactory(cls);
        if (factory == null) {
            return (() -> newConstructorInstance(cls));
        }

        return (factory::createInstance);
    }

    private <T> FactoryMethod<T> findFactory(Class<T> cls) {
        Method reflectMethod = Stream.of(cls.getMethods())
                .filter(method -> (method.getModifiers() & Modifier.STATIC) == Modifier.STATIC)
                .filter(method -> method.isAnnotationPresent(Factory.class))
                .findFirst()
                .orElse(null);

        if (reflectMethod == null) {
            return null;
        }

        try {
            MethodHandle handler = LOOKUP.unreflect(reflectMethod);
            return new FactoryMethod<>(handler);
        }
        catch (IllegalAccessException exception) {
            throw new SpigotEngineException(exception);
        }
    }

    private <T> T newConstructorInstance(Class<T> cls) {
        return reflectionService.constructInstance(
                SpigotMetadata.create()
                        .with(ConstructInstanceHandler.THROW_EXCEPTION.clone(false))
                        .with(ConstructInstanceHandler.TARGET_CLASS.clone(cls))
        );
    }

    @RequiredArgsConstructor
    private static class FactoryMethod<T> {

        private final MethodHandle handler;

        @SuppressWarnings("unchecked")
        public T createInstance() {
            try {
                return (T) handler.invoke();
            }
            catch (Throwable ex) {
                throw new SpigotEngineException(ex);
            }
        }
    }
}
