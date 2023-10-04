package ru.lyx.spigot.engine.core.listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Listener;
import ru.lyx.spigot.engine.core.SpigotEngine;

@Getter
@RequiredArgsConstructor
public abstract class SpigotEngineListener implements Listener {

    private final SpigotEngine engine;
}
