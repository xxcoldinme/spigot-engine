package ru.lyx.spigot.engine.module.sync.connection;

import ru.lyx.spigot.engine.module.sync.SyncContext;

public class ConnectionBootstrap {

    public SynchronizedConnection connect(SyncContext context) {
        return new SynchronizedConnection();
    }
}
