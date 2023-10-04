package ru.lyx.spigot.engine.core.module;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import ru.lyx.spigot.engine.core.exception.SpigotEngineException;
import ru.lyx.spigot.engine.core.key.KeyProperty;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@UtilityClass
public class SpigotModuleFactoryHelper {

    private static final KeyProperty<String> UNDEFINED_KEY = KeyProperty.of("#_UNDEFINED");
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private final Map<Class<?>, SpigotModuleFactory<?>> factoriesCacheMap = new HashMap<>();

    public <T extends SpigotModule<?>> SpigotModuleFactory<T> of(Class<T> cls) {
        return lookupFactory(cls);
    }

    @SuppressWarnings("unchecked")
    private <T extends SpigotModule<?>> SpigotModuleFactory<T> lookupFactory(Class<T> cls) {
        return (SpigotModuleFactory<T>) factoriesCacheMap.computeIfAbsent(cls, f -> createFactory(cls));
    }

    private <T extends SpigotModule<?>> SpigotModuleFactory<T> createFactory(Class<T> cls) {
        FactoryMethod<T> factory = findFactory(cls);
        if (factory == null) {
            return new DefaultModuleFactory<>(UNDEFINED_KEY, newConstructorInstance(cls));
        }

        T instance = factory.createInstance();
        return new DefaultModuleFactory<>(instance.getKey(), instance);
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
        try {
            Constructor<T> constructor = cls.getConstructor();
            constructor.setAccessible(true);

            return constructor.newInstance();
        }
        catch (Exception exception) {
            return null;
        }
    }

    @RequiredArgsConstructor
    private class FactoryMethod<T> {

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

    @RequiredArgsConstructor
    private static class DefaultModuleFactory<T extends SpigotModule<?>>
            implements SpigotModuleFactory<T> {

        @Getter
        private final KeyProperty<String> key;
        private final T spigotModule;

        @Override
        public T create() {
            return spigotModule;
        }
    }
}
