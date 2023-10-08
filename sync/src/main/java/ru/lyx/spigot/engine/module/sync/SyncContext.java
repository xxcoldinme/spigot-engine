package ru.lyx.spigot.engine.module.sync;

import lombok.Getter;
import lombok.Setter;
import ru.lyx.spigot.engine.core.SpigotContext;
import ru.lyx.spigot.engine.module.sync.transport.TransportChannel;
import ru.lyx.spigot.engine.module.sync.transport.TransportManager;
import ru.lyx.spigot.engine.module.sync.util.GZipCompressor;

@Getter
public class SyncContext implements SpigotContext {

    private final GZipCompressor gZipCompressor;
    private final TransportManager transportManager;

    @Setter
    @Getter
    private TransportChannel channel;

    public SyncContext() {
        gZipCompressor = new GZipCompressor();
        transportManager = new TransportManager(gZipCompressor);
    }
}
