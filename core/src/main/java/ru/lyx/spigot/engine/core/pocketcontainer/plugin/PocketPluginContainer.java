package ru.lyx.spigot.engine.core.pocketcontainer.plugin;

import lombok.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PocketPluginContainer<T> {

    public static <T> PocketPluginContainer<T> empty() {
        return new PocketPluginContainer<>();
    }

    public static <T> PocketPluginContainer<T> of(PluginProperty<T>... properties) {
        return new PocketPluginContainer<>(PocketContainer.of(properties));
    }

    public static <T> PocketPluginContainer<T> of(Collection<PluginProperty<T>> collection) {
        return new PocketPluginContainer<>(PocketContainer.of(collection));
    }

    @SafeVarargs
    public static <T> PocketPluginContainer<T> ofPlugin(Plugin plugin, T... elements) {
        return new PocketPluginContainer<>(
                PocketContainer.of(
                        Stream.of(elements)
                                .map(definition -> new PluginProperty<>(plugin, definition))
                                .collect(Collectors.toList())
                ));
    }

    @Getter
    @EqualsAndHashCode.Include
    private PocketContainer<PluginProperty<T>> parent
            = PocketContainer.empty();

    public PocketPluginContainer<T> removeAll(Plugin plugin) {
        Collection<PluginProperty<T>> removed = findByPlugin(plugin);
        removed.forEach(this::remove);

        return this;
    }

    public PocketPluginContainer<T> remove(PluginProperty<T> property) {
        parent.remove(property);
        return this;
    }

    public PocketPluginContainer<T> remove(Plugin plugin, T element) {
        parent.remove(property(plugin, element));
        return this;
    }

    public PocketPluginContainer<T> add(PluginProperty<T> property) {
        parent.add(property);
        return this;
    }

    public PocketPluginContainer<T> add(Plugin plugin, T element) {
        parent.add(property(plugin, element));
        return this;
    }

    public PocketPluginContainer<T> addAll(Plugin plugin, T... array) {
        Stream.of(array).forEach(definition -> add(plugin, definition));
        return this;
    }

    public PocketPluginContainer<T> addAll(PocketPluginContainer<T> other) {
        parent.addAll(other.parent);
        return this;
    }

    public PocketPluginContainer<T> setAll(PocketPluginContainer<T> other) {
        parent.setAll(other.parent);
        return this;
    }

    public Collection<PluginProperty<T>> findByPlugin(@NotNull Plugin plugin) {
        return parent.find(property -> property.getPlugin().equals(plugin));
    }

    public Collection<PluginProperty<T>> find(@NotNull Predicate<PluginProperty<T>> predicate) {
        return parent.getElements().stream().filter(predicate).collect(Collectors.toList());
    }

    public Optional<PluginProperty<T>> findFirst(@NotNull Predicate<PluginProperty<T>> predicate) {
        return parent.getElements().stream().filter(predicate).findFirst();
    }

    private PluginProperty<T> property(Plugin plugin, T element) {
        return new PluginProperty<>(plugin, element);
    }

    @Override
    public String toString() {
        return parent.getElements().toString();
    }
}