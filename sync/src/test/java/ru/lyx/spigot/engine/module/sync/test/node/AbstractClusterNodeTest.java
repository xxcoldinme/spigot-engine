package ru.lyx.spigot.engine.module.sync.test.node;

import ru.lyx.spigot.engine.core.reflection.ReflectionService;
import ru.lyx.spigot.engine.core.settingconfig.SettingConfig;
import ru.lyx.spigot.engine.core.settingconfig.SettingConfigLoader;
import ru.lyx.spigot.engine.module.sync.SyncConfigModel;
import ru.lyx.spigot.engine.module.sync.cluster.BytesToMessageConsumer;
import ru.lyx.spigot.engine.module.sync.cluster.ClusterChannel;
import ru.lyx.spigot.engine.module.sync.cluster.ClusterQueueManager;
import ru.lyx.spigot.engine.module.sync.socket.SocketChannel;
import ru.lyx.spigot.engine.module.sync.transport.TransportManager;
import ru.lyx.spigot.engine.module.sync.transport.message.TextMessage;
import ru.lyx.spigot.engine.module.sync.util.GZipCompressor;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

public abstract class AbstractClusterNodeTest {

    public static void main(String[] args) {
        Logger logger = Logger.getLogger("ClusterNode");

        SocketChannel socketChannel = new SocketChannel(logger, loadConfigModel());

        ClusterQueueManager clusterQueueManager = new ClusterQueueManager();
        TransportManager transportManager = new TransportManager(
                new GZipCompressor()
        );

        ClusterChannel channel = new ClusterChannel(
                clusterQueueManager,
                transportManager,
                socketChannel
        );

        channel.start(new BytesToMessageConsumer(clusterQueueManager, transportManager));

        channel.subscribe("TEST", System.out::println);
        readConsoleInputs(channel);
    }

    @SuppressWarnings("DataFlowIssue")
    private static SyncConfigModel loadConfigModel() {
        SettingConfigLoader settingConfigLoader = new SettingConfigLoader();
        SettingConfig config = settingConfigLoader.load(
                AbstractClusterNodeTest.class.getResourceAsStream("/sync/config.ini"));

        SyncConfigModel syncConfigModel = new SyncConfigModel();

        config.mergeModel(syncConfigModel);

        return syncConfigModel;
    }

    private static void readConsoleInputs(ClusterChannel channel) {
        final Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String consoleInput = scanner.nextLine();
            channel.broadcast("TEST", new TextMessage(consoleInput));
        }
        
        readConsoleInputs(channel);
    }
}
