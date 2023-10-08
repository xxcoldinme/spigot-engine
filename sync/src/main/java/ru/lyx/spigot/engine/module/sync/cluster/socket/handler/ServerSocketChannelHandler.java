package ru.lyx.spigot.engine.module.sync.cluster.socket.handler;

import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.module.sync.SpigotSyncModuleException;
import ru.lyx.spigot.engine.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.module.sync.cluster.socket.SocketChannel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;

@RequiredArgsConstructor
public class ServerSocketChannelHandler extends AbstractSocketChannelHandler {

    private final ExecutorService executorService;

    private ServerSocket socket;
    private final List<Socket> incomingSocketsList = new CopyOnWriteArrayList<>();

    @Override
    public synchronized void handleConnect(SocketChannel channel, SyncConfigModel config) {
        try {
            socket = new ServerSocket();
            socket.bind(config.getClusterAddress());

            channel.setHandler(this);

            handleIncomingSockets(channel);
        }
        catch (IOException exception) {
            new ClientSocketChannelHandler(executorService)
                    .handleConnect(channel, config);
        }
    }

    private void handleIncomingSockets(SocketChannel channel) {
        executorService.submit(() -> {
            try {
                for ( ;; ) {
                    Socket incomingSocket = socket.accept();
                    incomingSocketsList.add(incomingSocket);

                    // ForkJoinPool parallelism of tasks.
                    executorService.submit(() -> super.handleDataReceiving(incomingSocket, channel));
                }
            }
            catch (IOException exception) {
                throw new SpigotSyncModuleException(exception);
            }
        });
    }

    @Override
    public void handleClose(SocketChannel channel) {
        executorService.shutdown();
        incomingSocketsList.forEach(socket -> super.closeSocket(socket, channel, null));

        super.closeSocket(socket, channel, () -> socket = null);
    }

    @Override
    public void handleDataSending(SocketChannel channel, byte[] data) {
        for (Socket socket : new ArrayList<>(incomingSocketsList)) {
            try {
                OutputStream outputStream = socket.getOutputStream();
                write(outputStream, data);
            }
            catch (IOException exception) {
                handleDisconnect(socket, channel);
            }
        }
    }

    @Override
    protected void handleDisconnect(Socket socket, SocketChannel channel) {
        incomingSocketsList.remove(socket);
    }

    @Override
    protected void onDataReceived(Socket socket, SocketChannel channel, byte[] data) {
        incomingSocketsList.remove(socket);
        handleDataSending(channel, data);
        incomingSocketsList.add(socket);
    }
}
