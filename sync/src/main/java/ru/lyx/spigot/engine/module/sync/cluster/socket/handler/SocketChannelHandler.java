package ru.lyx.spigot.engine.module.sync.cluster.socket.handler;

import ru.lyx.spigot.engine.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.module.sync.cluster.socket.SocketChannel;

public interface SocketChannelHandler {

    void handleConnect(SocketChannel channel, SyncConfigModel config);

    void handleClose(SocketChannel channel);

    void handleDataSending(SocketChannel channel, byte[] data);
}
