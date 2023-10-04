package ru.lyx.spigot.engine.module.sync;

import lombok.Getter;
import lombok.ToString;
import ru.lyx.spigot.engine.SpigotEngineContext;
import ru.lyx.spigot.engine.context.SpigotIncludedContext;

@Getter
@ToString
public class SyncContext extends SpigotIncludedContext<SpigotEngineContext> {

    private int exchangePort;
    private String handshakePass;

    public SyncContext(SpigotEngineContext previousContext) {
        super(previousContext);
    }
}
