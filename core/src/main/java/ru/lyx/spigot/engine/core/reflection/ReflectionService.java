package ru.lyx.spigot.engine.core.reflection;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class ReflectionService {

    private final Logger logger;

    public <T> T newInstanceByConstructor(@NotNull SpigotMetadata metadata) {
        logger.info("ReflectionService.newInstanceByConstructor; " + metadata);

        InstanceByConstructorHandler<T> handler = new InstanceByConstructorHandler<>();
        return handler.handle(metadata);
    }

    public <T> Class<T> getGenericType(@NotNull SpigotMetadata metadata) {
        logger.info("ReflectionService.getGenericType; " + metadata);

        GetGenericTypeHandler<T> handler = new GetGenericTypeHandler<>();
        return handler.handle(metadata);
    }
}
