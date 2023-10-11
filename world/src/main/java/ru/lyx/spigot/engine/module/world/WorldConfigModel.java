package ru.lyx.spigot.engine.module.world;

import ru.lyx.spigot.engine.core.pocketcontainer.PocketContainer;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigMetadataModel;

public class WorldConfigModel extends SpigotConfigMetadataModel {

    @Override
    protected PocketContainer<String> ofPaths() {
        return PocketContainer.empty();
    }
}
