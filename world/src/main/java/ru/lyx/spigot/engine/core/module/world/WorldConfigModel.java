package ru.lyx.spigot.engine.core.module.world;

import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigMetadataModel;

public class WorldConfigModel extends SpigotConfigMetadataModel {

    @Override
    protected AttachmentContainer<String> ofPaths() {
        return AttachmentContainer.empty();
    }
}
