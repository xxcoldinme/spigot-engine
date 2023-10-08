package ru.lyx.spigot.engine.core.attachment.plugin;

import lombok.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PluginAttachmentContainer<T> {

    public static <T> PluginAttachmentContainer<T> empty() {
        return new PluginAttachmentContainer<>();
    }

    public static <T> PluginAttachmentContainer<T> of(PluginProperty<T>... properties) {
        return new PluginAttachmentContainer<>(AttachmentContainer.of(properties));
    }

    public static <T> PluginAttachmentContainer<T> of(Collection<PluginProperty<T>> collection) {
        return new PluginAttachmentContainer<>(AttachmentContainer.of(collection));
    }

    @SafeVarargs
    public static <T> PluginAttachmentContainer<T> ofPlugin(Plugin plugin, T... definitions) {
        return new PluginAttachmentContainer<>(
                AttachmentContainer.of(
                        Stream.of(definitions)
                                .map(definition -> new PluginProperty<>(plugin, definition))
                                .collect(Collectors.toList())
                ));
    }

    @Getter
    @EqualsAndHashCode.Include
    private AttachmentContainer<PluginProperty<T>> parent
            = AttachmentContainer.empty();

    public PluginAttachmentContainer<T> removeAll(Plugin plugin) {
        Collection<PluginProperty<T>> removed = findByPlugin(plugin);
        removed.forEach(this::remove);

        return this;
    }

    public PluginAttachmentContainer<T> remove(PluginProperty<T> property) {
        parent.remove(property);
        return this;
    }

    public PluginAttachmentContainer<T> remove(Plugin plugin, T element) {
        parent.remove(property(plugin, element));
        return this;
    }

    public PluginAttachmentContainer<T> add(PluginProperty<T> property) {
        parent.add(property);
        return this;
    }

    public PluginAttachmentContainer<T> add(Plugin plugin, T element) {
        parent.add(property(plugin, element));
        return this;
    }

    public PluginAttachmentContainer<T> addAll(Plugin plugin, T... array) {
        Stream.of(array).forEach(definition -> add(plugin, definition));
        return this;
    }

    public PluginAttachmentContainer<T> addAll(PluginAttachmentContainer<T> attachments) {
        parent.addAll(attachments.parent);
        return this;
    }

    public PluginAttachmentContainer<T> setAll(PluginAttachmentContainer<T> attachments) {
        parent.setAll(attachments.parent);
        return this;
    }

    public Collection<PluginProperty<T>> findByPlugin(@NotNull Plugin plugin) {
        return parent.find(property -> property.getPlugin().equals(plugin));
    }

    public Collection<PluginProperty<T>> find(@NotNull Predicate<PluginProperty<T>> predicate) {
        return parent.getDefinitions().stream().filter(predicate).collect(Collectors.toList());
    }

    public Optional<PluginProperty<T>> findFirst(@NotNull Predicate<PluginProperty<T>> predicate) {
        return parent.getDefinitions().stream().filter(predicate).findFirst();
    }

    private PluginProperty<T> property(Plugin plugin, T definition) {
        return new PluginProperty<>(plugin, definition);
    }

    @Override
    public String toString() {
        return parent.getDefinitions().toString();
    }
}