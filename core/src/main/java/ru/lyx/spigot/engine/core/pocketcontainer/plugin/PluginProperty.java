package ru.lyx.spigot.engine.core.pocketcontainer.plugin;

import lombok.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PluginProperty<T> {

    public static <T> PluginProperty<T> create(@NotNull Plugin plugin, @Nullable T element) {
        return new PluginProperty<>(plugin, element);
    }

    private final Plugin plugin;
    private final T element;

    @Override
    public String toString() {
        return element.toString();
    }
}
