package ru.lyx.spigot.engine.test.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.listener.SpigotEngineListener;

public class TestPlayerJoinQuitListener extends SpigotEngineListener {

    public TestPlayerJoinQuitListener(SpigotEngine engine) {
        super(engine);
    }

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void handle(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(String.format("Player %s has joined!", player.getName()));
    }
}
