package ru.lyx.spigot.engine.core.settingconfig.model;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.lyx.spigot.engine.core.attachment.AttachmentContainer;
import ru.lyx.spigot.engine.core.settingconfig.SettingConfig;
import ru.lyx.spigot.engine.core.settingconfig.type.SettingProperty;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

@ToString
@RequiredArgsConstructor
public final class SettingConfigModelContext {

    private final AttachmentContainer<ConfigContextProperty<?>> propertiesContainer
            = AttachmentContainer.empty();

    private <F> void addProperty(String path, Function<SettingProperty, F> mapping, Consumer<F> setter) {
        propertiesContainer.add(new ConfigContextProperty<>(path, mapping, setter));
    }

    public void withProperty(String path, Consumer<SettingProperty> setter) {
        addProperty(path, property -> property, setter);
    }

    public void apply(SettingConfig config) {
        new ArrayList<>(propertiesContainer.getDefinitions())
                .forEach(property -> applySetter(config, property));
    }

    private <F> void applySetter(SettingConfig config, ConfigContextProperty<F> property) {
        String[] data = splitPathData(property.path);

        SettingProperty settingProperty = config.readProperty(data[0], data[1])
                .orElse(null);

        F apply = property.mapping.apply(settingProperty);
        property.setter.accept(apply);
    }

    private String[] splitPathData(String path) {
        return path.split("\\.", 2);
    }

    @ToString
    @RequiredArgsConstructor
    private static class ConfigContextProperty<F> {

        private final String path;
        private final Function<SettingProperty, F> mapping;
        private final Consumer<F> setter;
    }
}
