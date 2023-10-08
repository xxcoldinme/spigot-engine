package ru.lyx.spigot.engine.core.attachment.plugin;

import lombok.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PluginProperty<T> {

    public static <T> PluginProperty<T> create(@NotNull Plugin plugin, @Nullable T definition) {
        return new PluginProperty<>(plugin, definition);
    }

    private final Plugin plugin;
    private final T definition;

    @Override
    public String toString() {
        return definition.toString();
    }
}
