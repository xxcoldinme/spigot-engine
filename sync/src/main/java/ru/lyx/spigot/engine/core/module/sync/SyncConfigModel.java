package ru.lyx.spigot.engine.core.module.sync;

import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigMetadataModel;
import ru.lyx.spigot.engine.core.settingconfig.type.SettingProperty;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public final class SyncConfigModel extends SpigotConfigMetadataModel {

    private static final String HOST_NAME = "connection.host";
    private static final String CONNECT_TIMEOUT = "connection.connect_timeout";
    private static final String HANDSHAKE_TIMEOUT = "connection.handshake_timeout";
    private static final String CLUSTER_PORT = "cluster.port";
    private static final String QUEUE_EXPIRE_TIME_IN_HOURS = "queue.expire_delay_hours";

    @Override
    protected AttachmentContainer<String> ofPaths() {
        return AttachmentContainer.of(HOST_NAME, CONNECT_TIMEOUT, HANDSHAKE_TIMEOUT,
                CLUSTER_PORT, QUEUE_EXPIRE_TIME_IN_HOURS);
    }

    public String getHostName() {
        final SettingProperty property = getProperty(HOST_NAME);
        return property.getValue();
    }

    public int getConnectTimeout() {
        final SettingProperty property = getProperty(CONNECT_TIMEOUT);
        return property.getValueAsInt();
    }

    public int getHandshakeTimeout() {
        final SettingProperty property = getProperty(HANDSHAKE_TIMEOUT);
        return property.getValueAsInt();
    }

    public int getClusterPort() {
        final SettingProperty property = getProperty(CLUSTER_PORT);
        return property.getValueAsInt();
    }

    public long getQueueExpireTime(TimeUnit timeUnit) {
        final SettingProperty property = getProperty(QUEUE_EXPIRE_TIME_IN_HOURS);
        int valueAsInt = property.getValueAsInt();

        return timeUnit.convert(valueAsInt, TimeUnit.HOURS);
    }

    public InetSocketAddress getClusterAddress() {
        return new InetSocketAddress(getHostName(), getClusterPort());
    }
}
