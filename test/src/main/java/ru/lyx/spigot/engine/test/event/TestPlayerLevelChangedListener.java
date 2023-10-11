package ru.lyx.spigot.engine.test.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import ru.lyx.spigot.engine.core.SpigotEngine;
import ru.lyx.spigot.engine.core.listener.SpigotEngineListener;
import ru.lyx.spigot.engine.module.sync.SyncModule;
import ru.lyx.spigot.engine.module.sync.transport.TransportChannel;
import ru.lyx.spigot.engine.module.sync.transport.message.player.PlayerLevelingMessage;

public class TestPlayerLevelChangedListener extends SpigotEngineListener {

    public TestPlayerLevelChangedListener(SpigotEngine engine) {
        super(engine);
    }

    @EventHandler
    public void handle(PlayerLevelChangeEvent event) {
        SpigotEngine engine = getEngine();

        SyncModule syncModule = engine.lookupModule(SyncModule.class);
        TransportChannel channel = syncModule.getContext().getChannel();

        channel.sendMessage("levelChange", new PlayerLevelingMessage(event));
    }
}
