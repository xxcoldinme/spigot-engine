package ru.lyx.spigot.engine.core.module.sync;

import lombok.Getter;
import lombok.ToString;
import ru.lyx.spigot.engine.core.SpigotEngineContext;
import ru.lyx.spigot.engine.core.context.SpigotIncludedContext;

@Getter
@ToString
public class SyncContext extends SpigotIncludedContext<SpigotEngineContext> {

    private int exchangePort;
    private String handshakePass;

    public SyncContext(SpigotEngineContext previousContext) {
        super(previousContext);
    }
}
