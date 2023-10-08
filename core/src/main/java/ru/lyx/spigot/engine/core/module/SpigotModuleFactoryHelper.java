package ru.lyx.spigot.engine.core.module;

import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.core.SpigotEngineException;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.reflection.ConstructInstanceHandler;
import ru.lyx.spigot.engine.core.reflection.ReflectionService;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SpigotModuleFactoryHelper {

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

    private <T extends SpigotModule<?, ?>> FactoryMethod<T> findFactory(Class<T> cls) {
        Method reflectMethod = Stream.of(cls.getDeclaredMethods())
                .filter(method -> Modifier.isStatic(method.getModifiers()))
                .filter(method -> method.isAnnotationPresent(Factory.class))
                .findFirst()
                .orElse(null);

        if (reflectMethod == null)
            return null;

        reflectMethod.setAccessible(true);
        return new FactoryMethod<>(reflectMethod);
    }

    private <T> T newConstructorInstance(Class<T> cls) {
        return reflectionService.constructInstance(
                SpigotMetadata.create()
                        .with(ConstructInstanceHandler.THROW_EXCEPTION.clone(false))
                        .with(ConstructInstanceHandler.TARGET_CLASS.clone(cls))
        );
    }

    @RequiredArgsConstructor
    private static class FactoryMethod<T extends SpigotModule<?, ?>> {

        private final Method factoryMethod;

        @SuppressWarnings("unchecked")
        public T createInstance() {
            try {
                SpigotModuleFactory<T> factory = (SpigotModuleFactory<T>)
                        factoryMethod.invoke(null);

                return factory.create();
            }
            catch (Throwable ex) {
                throw new SpigotEngineException(ex);
            }
        }
    }
}
