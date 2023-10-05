package ru.lyx.spigot.engine.core.settingconfig.model;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.key.KeyProperty;
import ru.lyx.spigot.engine.core.metadata.SpigotMetadata;
import ru.lyx.spigot.engine.core.settingconfig.type.SettingProperty;

public abstract class SpigotConfigMetadataModel implements SpigotConfigModel {

    @Getter
    private final SpigotMetadata metadata = SpigotMetadata.create();

    protected abstract AttachmentContainer<String> ofPaths();

    @Override
    public final void initModel(SettingConfigModelContext context) {
        final AttachmentContainer<String> propertiesContainer = ofPaths();
        propertiesContainer.getDefinitions()
                .forEach(path -> initKey(context, path));
    }

    private void initKey(SettingConfigModelContext context, String path) {
        final KeyProperty<String> pathProperty = KeyProperty.of(path);

        context.withProperty(path,
                settingProperty -> metadata.with(pathProperty, settingProperty));
    }

    public final SettingProperty getProperty(@NotNull String path) {
        return metadata.<SettingProperty>get(KeyProperty.of(path))
                .orElse(null);
    }
}
