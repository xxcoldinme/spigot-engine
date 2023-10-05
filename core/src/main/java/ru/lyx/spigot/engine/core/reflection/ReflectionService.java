package ru.lyx.spigot.engine.core.reflection;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class ReflectionService {

    private final Logger logger;

    public <T> T constructInstance(@NotNull SpigotMetadata metadata) {
        logger.info("ReflectionService.newInstanceByConstructor; " + metadata);

        ConstructInstanceHandler<T> handler = new ConstructInstanceHandler<>();
        return handler.handle(metadata);
    }

    public <T> Class<T> getGenericType(@NotNull SpigotMetadata metadata) {
        logger.info("ReflectionService.getGenericType; " + metadata);

        GetGenericTypeHandler<T> handler = new GetGenericTypeHandler<>();
        return handler.handle(metadata);
    }
}
