package ru.lyx.spigot.engine.listener;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Listener;
import ru.lyx.spigot.engine.SpigotEngine;

@Getter
@RequiredArgsConstructor
public abstract class SpigotEngineListener implements Listener {

    private final SpigotEngine engine;
}
