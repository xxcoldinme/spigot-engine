package ru.lyx.spigot.engine.test.module.sync;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandler;
import ru.lyx.spigot.engine.core.module.handler.SpigotHandlerContext;
import ru.lyx.spigot.engine.module.sync.SyncContext;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.sync.transport.TransportChannel;
import ru.lyx.spigot.engine.module.sync.transport.message.player.PlayerLevelingMessage;

public class SyncMessageHandler implements SpigotHandler<SyncModule> {

    @Override
    public boolean validate(@NotNull SpigotHandlerContext<SyncModule> context) {
        return ALWAYS;
    }

    @Override
    public void handle(@NotNull SpigotHandlerContext<SyncModule> context) {
        SyncContext syncContext = context.getModule().getContext();
        TransportChannel channel = syncContext.getChannel();

        channel.<PlayerLevelingMessage>subscribe("levelChange", message -> {

            String playerName = message.getPlayerName();
            int newPlayerLevel = message.getNewLevel();

            Player player = Bukkit.getPlayer(playerName);

            if (player != null) {
                player.setLevel(newPlayerLevel);
            }
        });
    }
}
