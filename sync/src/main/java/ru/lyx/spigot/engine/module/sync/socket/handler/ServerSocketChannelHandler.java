package ru.lyx.spigot.engine.module.sync.socket.handler;

import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.module.sync.SpigotSyncException;
import ru.lyx.spigot.engine.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.module.sync.socket.SocketChannel;
import ru.lyx.spigot.engine.module.sync.socket.SocketState;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class ServerSocketChannelHandler extends AbstractSocketChannelHandler {

    private final ExecutorService executorService;
    private final Logger logger;

    private ServerSocket socket;
    private final List<Socket> incomingSocketsList = new CopyOnWriteArrayList<>();

    @Override
    public synchronized void handleConnect(SocketChannel channel, SyncConfigModel config) {
        try {
            socket = new ServerSocket();
            socket.bind(config.getClusterAddress());

            channel.setHandler(this);
            channel.setState(SocketState.ACTIVE);

            handleIncomingSockets(channel, config);

            if (config.isClusterDebugEnabled()) {
                logger.info("Cluster server-socket state has changed to ACTIVE");
            }
        }
        catch (IOException exception) {
            if (config.isClusterDebugEnabled()) {
                logger.info("Cluster server-socket has dropped, redirected to client");
            }

            new ClientSocketChannelHandler(executorService, logger)
                    .handleConnect(channel, config);
        }
    }

    private void handleIncomingSockets(SocketChannel channel, SyncConfigModel config) {
        executorService.submit(() -> {
            try {
                for ( ;; ) {
                    Socket incomingSocket = socket.accept();
                    incomingSocketsList.add(incomingSocket);

                    if (config.isClusterDebugEnabled()) {
                        logger.info("Cluster server-socket accepted new input connection: " + incomingSocket.getRemoteSocketAddress());
                    }

                    // ForkJoinPool parallelism of tasks.
                    executorService.submit(() -> super.handleDataReceiving(incomingSocket, channel));
                }
            }
            catch (IOException exception) {
                throw new SpigotSyncException(exception);
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
        logger.info("Cluster server-socket handle data sending");

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
        logger.info("Cluster server-socket handle data receiving");

        incomingSocketsList.remove(socket);
        handleDataSending(channel, data);
        incomingSocketsList.add(socket);
    }
}
