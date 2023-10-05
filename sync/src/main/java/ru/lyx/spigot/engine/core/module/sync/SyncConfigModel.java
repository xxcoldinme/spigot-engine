package ru.lyx.spigot.engine.core.module.sync;

import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigMetadataModel;

public final class SyncConfigModel extends SpigotConfigMetadataModel {

    public static final String CONNECTION_HOST = "connection.host";
    public static final String CONNECTION_PORT = "connection.port";

    @Override
    protected AttachmentContainer<String> ofPaths() {
        return AttachmentContainer.of(CONNECTION_HOST, CONNECTION_PORT);
    }
}
