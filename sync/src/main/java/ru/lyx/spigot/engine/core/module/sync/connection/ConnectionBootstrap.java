package ru.lyx.spigot.engine.core.module.sync.connection;

import ru.lyx.spigot.engine.core.module.sync.SyncContext;

public class ConnectionBootstrap {

    public SynchronizedConnection connect(SyncContext context) {
        return new SynchronizedConnection();
    }
}
