package ru.lyx.spigot.engine.core.metadata;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.key.KeyProperty;

import java.util.Optional;

@SuppressWarnings("unchecked")
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpigotMetadata {

    public static SpigotMetadata create() {
        return new SpigotMetadata();
    }

    private final AttachmentContainer<MetadataProperty<?>> container
            = AttachmentContainer.empty();

    public SpigotMetadata with(@NotNull MetadataProperty<?> property) {
        container.add(property);
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
}
