package ru.lyx.spigot.engine.core.settingconfig;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.lyx.spigot.engine.core.settingconfig.model.SettingConfigModelContext;
import ru.lyx.spigot.engine.core.settingconfig.model.SpigotConfigModel;
import ru.lyx.spigot.engine.core.settingconfig.type.SettingGroup;
import ru.lyx.spigot.engine.core.settingconfig.type.SettingProperty;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SettingConfig {

    private final Set<SettingGroup> groups;

    public Optional<SettingGroup> readGroup(String groupName) {
        return groups.stream()
                .filter(group -> group.getKey().equals(groupName))
                .findFirst();
    }

    public Optional<SettingProperty> readProperty(String group, String field) {
        final Optional<SettingGroup> settingGroup = readGroup(group);
        return settingGroup
                .flatMap(value -> Stream.of(value.getProperties())
                .filter(property -> property.getKey().equals(field))
                .findFirst());
    }

    public String readString(@NotNull String group, @NotNull String field, @Nullable String def) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValue).orElse(def);
    }

    public Optional<String> readString(@NotNull String group, @NotNull String field) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValue);
    }

    public boolean readBoolean(@NotNull String group, @NotNull String field, boolean def) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsBoolean).orElse(def);
    }

    public Optional<Boolean> readBoolean(@NotNull String group, @NotNull String field) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsBoolean);
    }

    public int readInt(@NotNull String group, @NotNull String field, int def) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsInt).orElse(def);
    }

    public Optional<Integer> readInt(@NotNull String group, @NotNull String field) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsInt);
    }

    public long readLong(@NotNull String group, @NotNull String field, long def) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsLong).orElse(def);
    }

    public Optional<Long> readLong(@NotNull String group, @NotNull String field) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsLong);
    }

    public <M extends SpigotConfigModel> void mergeModel(M model) {
        final SettingConfigModelContext context = new SettingConfigModelContext();
        model.initModel(context);
        context.apply(this);
    }
}
