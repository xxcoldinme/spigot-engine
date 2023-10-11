package ru.lyx.spigot.engine.module.sync.socket.handler;

import lombok.SneakyThrows;
import ru.lyx.spigot.engine.module.sync.SpigotSyncException;
import ru.lyx.spigot.engine.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.module.sync.socket.SocketChannel;
import ru.lyx.spigot.engine.module.sync.socket.SocketState;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Logger;

public abstract class AbstractSocketChannelHandler implements SocketChannelHandler {

    private SocketAddress sessionAddress;

    protected abstract void handleDisconnect(Socket socket, SocketChannel channel);

    protected void onDataReceived(Socket socket, SocketChannel channel, byte[] data) {
        // override me.
    }

    private synchronized void handleSessionDisconnect(Socket socket, SocketChannel channel) {
        if (sessionAddress != null) {
            handleDisconnect(socket, channel);
            sessionAddress = null;
        }
    }

    protected synchronized void handleAutoDisconnect(Socket socket, SocketChannel channel) {
        if (!socket.isConnected()) {
            Thread.currentThread().interrupt();
            return;
        }

        try {
            InputStream inputStream = socket.getInputStream();
            sessionAddress = socket.getRemoteSocketAddress();

            if (inputStream.read() == -1) {
                handleSessionDisconnect(socket, channel);
            }
        }
        catch (IOException exception) {
            handleSessionDisconnect(socket, channel);
        }
    }

    @SneakyThrows
    protected void handleDataReceiving(Socket socket, SocketChannel channel) {
        try {
            InputStream inputStream = socket.getInputStream();
            while (inputStream.available() == 0) {
                // just wait it.
            }

            receiveData(inputStream, socket, channel);
        }
        catch (IOException exception) {
            handleSessionDisconnect(socket, channel);
        }
    }

    private void receiveData(InputStream inputStream, Socket socket, SocketChannel channel)
    throws IOException {

        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);

        channel.receiveData(bytes);

        onDataReceived(socket, channel, bytes);
        handleDataReceiving(socket, channel);
    }

    protected <T extends Closeable> void closeSocket(T socket, SocketChannel channel, Runnable onClose) {
        channel.setState(SocketState.INACTIVE);
        if (socket != null) {
            try {
                socket.close();
                if (onClose != null)
                    onClose.run();
            }
            catch (IOException exception) {
                throw new SpigotSyncException(exception);
            }
        }
    }

    protected void write(OutputStream outputStream, byte[] data)
    throws IOException {

        byte[] copy = new byte[data.length + 1];
        System.arraycopy(data, 0, copy, 1, data.length);

        outputStream.write(copy);
        outputStream.flush();
    }
}
