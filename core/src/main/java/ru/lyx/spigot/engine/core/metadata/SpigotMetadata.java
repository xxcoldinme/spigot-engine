package ru.lyx.spigot.engine.core.metadata;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.key.KeyProperty;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpigotMetadata implements Serializable {

    public static SpigotMetadata create() {
        return new SpigotMetadata();
    }

    private final PocketContainer<MetadataProperty<?>> container
            = PocketContainer.empty();

    public SpigotMetadata with(@NotNull MetadataProperty<?> property) {
        container.add(property);
        return this;
    }

    public <T> SpigotMetadata with(@NotNull MetadataProperty<T> property, @Nullable T value) {
        return with(property.clone(value));
    }

    public SpigotMetadata with(@NotNull KeyProperty<?> keyProperty, @Nullable Object value) {
        container.add(MetadataProperty.of(keyProperty, value));
        return this;
    }

    public SpigotMetadata remove(@NotNull MetadataProperty<?> property) {
        container.remove(property);
        return this;
    }

    public <T> Optional<T> get(@NotNull MetadataProperty<T> property) {
        return container.findFirst(definition -> definition.equals(property))
                .map(definition -> (T) definition.getValue());
    }

    public <T> Optional<T> get(@NotNull KeyProperty<?> keyProperty) {
        return container.findFirst(definition -> definition.getKey().equals(keyProperty))
                .map(definition -> (T) definition.getValue());
    }

    public Set<KeyProperty<?>> keys() {
        return container.getElements()
                .stream()
                .map(MetadataProperty::getKey)
                .collect(Collectors.toSet());
    }
}
