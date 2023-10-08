package ru.lyx.spigot.engine.module.sync.cluster.socket.handler;

import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.module.sync.cluster.socket.SocketChannel;
import ru.lyx.spigot.engine.module.sync.cluster.socket.SocketState;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class ClientSocketChannelHandler extends AbstractSocketChannelHandler {

    private final ExecutorService executorService;

    private byte[] exceptionallyBytes;

    private Socket socket;
    private SyncConfigModel config;

    private void initSocket(SyncConfigModel config) {
        final InetSocketAddress clusterAddress = config.getClusterAddress();
        final int connectTimeout = config.getConnectTimeout();

        try {
            if (socket != null) {
                socket.close();
            }

            socket = new Socket();
            socket.connect(clusterAddress, connectTimeout);

        } catch (IOException ignored) {
            socket = null;
        }
    }

    @Override
    public synchronized void handleConnect(SocketChannel channel, SyncConfigModel config) {
        this.config = config;
        initSocket(config);

        if (socket == null) {
            new ServerSocketChannelHandler(executorService)
                    .handleConnect(channel, config);
        } else {
            channel.setHandler(this);
            channel.setState(SocketState.ACTIVE);

            executorService.submit(() -> super.handleAutoDisconnect(socket, channel));
            executorService.submit(() -> super.handleDataReceiving(socket, channel));
        }
    }

    @Override
    public void handleClose(SocketChannel channel) {
        executorService.shutdown();
        super.closeSocket(socket, channel, () -> socket = null);
    }

    @Override
    public void handleDataSending(SocketChannel channel, byte[] data) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
        }
        catch (IOException exception) {
            exceptionallyBytes = data;
            handleDisconnect(socket, channel);
        }
    }

    @Override
    protected void handleDisconnect(Socket socket, SocketChannel channel) {
        handleConnect(channel, config);

        if (exceptionallyBytes != null) {

            channel.sendData(exceptionallyBytes);
            exceptionallyBytes = null;
        }
    }

    @Override
    protected void onDataReceived(Socket socket, SocketChannel channel, byte[] data) {
        executorService.submit(() -> super.handleAutoDisconnect(socket, channel));
    }
}
