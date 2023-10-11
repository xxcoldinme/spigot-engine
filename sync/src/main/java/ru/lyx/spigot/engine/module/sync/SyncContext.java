package ru.lyx.spigot.engine.module.sync;

import lombok.Getter;
import lombok.Setter;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.module.sync.transport.TransportChannel;
import ru.lyx.spigot.engine.module.sync.transport.TransportManager;
import ru.lyx.spigot.engine.module.sync.util.GZipCompressor;

@Getter
public class SyncContext implements SpigotContext {

    public static final KeyProperty<?> CHANNEL_PROPERTY = KeyProperty.of("channel");

    private final GZipCompressor gZipCompressor;
    private final TransportManager transportManager;

    @Setter
    private TransportChannel channel;

    public SyncContext() {
        this.gZipCompressor = new GZipCompressor();
        this.transportManager = new TransportManager(gZipCompressor);
    }
}
