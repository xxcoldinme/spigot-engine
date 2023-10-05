package ru.lyx.spigot.engine.core.settingconfig;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ru.lyx.spigot.engine.core.settingconfig.model.SettingGroup;
import ru.lyx.spigot.engine.core.settingconfig.model.SettingProperty;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class SettingConfig {

    private final Set<SettingGroup> groups;

    private Optional<SettingGroup> readGroup(String groupName) {
        return groups.stream().filter(group -> group.getKey().equals(groupName))
                .findFirst();
    }

    private Optional<SettingProperty> readProperty(String group, String field) {
        final Optional<SettingGroup> settingGroup = readGroup(group);
        return settingGroup
                .flatMap(value -> Stream.of(value.getProperties())
                .filter(property -> property.getKey().equals(field))
                .findFirst());
    }

    public String readString(String group, String field, String def) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValue).orElse(def);
    }

    public Optional<String> readString(String group, String field) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValue);
    }

    public boolean readBoolean(String group, String field, boolean def) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsBoolean).orElse(def);
    }

    public Optional<Boolean> readBoolean(String group, String field) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsBoolean);
    }

    public int readInt(String group, String field, int def) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsInt).orElse(def);
    }

    public Optional<Integer> readInt(String group, String field) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsInt);
    }

    public long readLong(String group, String field, long def) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsLong).orElse(def);
    }

    public Optional<Long> readLong(String group, String field) {
        final Optional<SettingProperty> settingProperty = readProperty(group, field);
        return settingProperty.map(SettingProperty::getValueAsLong);
    }
}
