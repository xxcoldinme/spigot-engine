package ru.lyx.spigot.engine.module.sync;

import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigMetadataModel;
import ru.lyx.spigot.engine.core.settingconfig.type.SettingProperty;

import java.net.InetSocketAddress;

public final class SyncConfigModel extends SpigotConfigMetadataModel {

    private static final String SERVER_ID = "server.name";
    private static final String HOST_NAME = "connection.host";
    private static final String CONNECT_TIMEOUT = "connection.connect_timeout";
    private static final String CLUSTER_PORT = "cluster.port";
    private static final String CLUSTER_DEBUG_STATE = "cluster.debug";

    @Override
    protected PocketContainer<String> ofPaths() {
        return PocketContainer.of(SERVER_ID, HOST_NAME, CONNECT_TIMEOUT,
                CLUSTER_PORT, CLUSTER_DEBUG_STATE);
    }

    public String getServerId() {
        final SettingProperty property = getProperty(SERVER_ID);
        return property.getValue();
    }

    public String getHostName() {
        final SettingProperty property = getProperty(HOST_NAME);
        return property.getValue();
    }

    public int getConnectTimeout() {
        final SettingProperty property = getProperty(CONNECT_TIMEOUT);
        return property.getValueAsInt();
    }

    public int getClusterPort() {
        final SettingProperty property = getProperty(CLUSTER_PORT);
        return property.getValueAsInt();
    }

    public InetSocketAddress getClusterAddress() {
        return new InetSocketAddress(getHostName(), getClusterPort());
    }

    public boolean isClusterDebugEnabled() {
        final SettingProperty property = getProperty(CLUSTER_DEBUG_STATE);
        return property.getValueAsBoolean();
    }
}
